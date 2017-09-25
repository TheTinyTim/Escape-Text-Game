package com.menus;

import com.CabinEscape.GameMain;
import com.CabinEscape.GameRendering;
import com.CabinEscape.GameSettings;
import com.asciiPanel.AsciiPanel;
import com.structs.Rect;
import com.structs.Vector2D;

import java.util.ArrayList;

public class MainGameHandler {
    //│ ┤ ┐ └ ┴ ┬ ├ ─ ┼ ┘ ┌
    
    //────────────────────Global Menu Variables─────────────────────────────────────────────────────────────────────────────────────┐
    //These are all the variables that are used within every menu                                                                   │
    private GameSettings gameSettings;  //This stores all the settings for the game                                                 │
    private AsciiPanel gameTerminal;    //This is the terminal that will display everything for the game                            │
    private GameMain gameMain;          //This is the main class for the game that will handle updating the gui and what not        │
    //────────────────────GUI variables─────────────────────────────────────────────────────────────────────────────────────────────┤
    //──────────────────────────────────Game Log────────────────────────────────────────────────────────────────────────────────────┤
    //These variables are needed to draw the game game log segment                                                                  │
    private Rect gameLogBorder;                                     //The border for the game log and its content                   │
    private ArrayList<String> gameLog = new ArrayList<String> ();   //All the data that will be displayed on the game log           │
    //──────────────────────────────────User Input──────────────────────────────────────────────────────────────────────────────────┤
    //These variables are needed to draw the users input segment                                                                    │
    private Rect userInputBorder;   //The border where the player will type everything                                              │
    public String userInput = "";   //What the player has currently typed                                                           │
    //──────────────────────────────────Inventory───────────────────────────────────────────────────────────────────────────────────┤
    //These variables are needed to draw the inventory segment                                                                      │
    private Rect inventoryBorder;   //The inventories border                                                                        │
    //──────────────────────────────────Pause Menu──────────────────────────────────────────────────────────────────────────────────┤
    //These variables are needed to draw and handle the pause menu                                                                  │
    private ArrayList<String> pauseMenuButtons = new ArrayList<String> ();  //All the buttons that go on the pause menu             │
    private Vector2D pauseMenuButtonPos;                                    //The location of the pause menu buttons                │
    private int selectedMenuButton = 0;                                     //The button that's currently being hovered over        │
    private Rect pauseMenuBorder;                                           //The pause menus border                                │
    private boolean pauseMenuOpen = false;                                  //Stores if the pause menu is open or not               │
    public boolean animatePauseMenu = false;                                //Stores if the pause menu needs to be animated open    │
    public boolean hidePauseMenu = false;                                   //Stores if the pause menu needs to be animated closed  │
    //──────────────────────────────────Message Window──────────────────────────────────────────────────────────────────────────────┤
    //These variables are needed to draw and handle the message window                                                              │
    private String message = "Hello there!";        //The message to be displayed in the menu                                       │
    private boolean animateMessageWindow = false;   //Stores if the message window needs to be animated open or not                 │
    private boolean hideMessageWindow = false;      //Stores if the message window needs to be animated closed or not               │
    private boolean displayMessage = false;         //Stores if the message window needs to be shown or not                         │
    private Rect messageBorder;                     //The messages border                                                           │
    //──────────────────────────────────Animation Variables─────────────────────────────────────────────────────────────────────────┤
    //These variables are needed to help with animating different windows                                                           │
    private Rect animatedMenuRect;  //The Rect that stores the information for the being animated at the time                       │
    //──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
    
    public MainGameHandler (GameSettings gameSettings, AsciiPanel gameTerminal, GameMain gameMain)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        this.gameMain = gameMain;
        
        //Set up the variables for the GUI
        gameLogBorder = new Rect (0,
                                  0,
                                  gameSettings.gameWindowWidth - 22,
                                  gameSettings.gameWindowHeight - 3);
        
        userInputBorder = new Rect (0,
                                    gameLogBorder.height,
                                    gameSettings.gameWindowWidth - 22,
                                    3);
        
        inventoryBorder = new Rect (gameLogBorder.width,
                                    0,
                                    22,
                                    gameSettings.gameWindowHeight);
        
        pauseMenuBorder = new Rect ((gameSettings.gameWindowWidth / 2) - 12,
                                    (gameSettings.gameWindowHeight / 2) - 5,
                                    25,
                                    13);
        
        pauseMenuButtons.add ("Save Game");
        pauseMenuButtons.add ("Load Game");
        pauseMenuButtons.add ("Settings");
        pauseMenuButtons.add ("Back");
        pauseMenuButtons.add ("Exit Game");
        
        pauseMenuButtonPos = new Vector2D (pauseMenuBorder.x + ((pauseMenuBorder.width / 2) - 4), pauseMenuBorder.y + ((pauseMenuBorder.height / 2) - 4));
        
        messageBorder = new Rect (30, 5, gameSettings.gameWindowWidth - 60, gameSettings.gameWindowHeight - 10);
    }
    
    //This is the main method that handles everything that needs to be draw to the terminal for this menu
    public void drawGUI ()
    {
        //First draw all the borders for the game
        drawGameSegments ();
        
        //Now draw the the message window if there is one being displayed
        drawMessageWindow ();
        
        //Finally draw the pause menu if one is trying to open/closed or if it's already open
        drawPauseMenu ();
    }
    
    public void setupAnimatedBorder ()
    {
        //Find out if the pause menu is being opened or closed
        if (!pauseMenuOpen) {
            animatePauseMenu = true;
            animatedMenuRect = new Rect (pauseMenuBorder.x + (pauseMenuBorder.width / 2),
                                              pauseMenuBorder.y + (pauseMenuBorder.height / 2),
                                              2,
                                              2);
        } else {
            hidePauseMenu = true;
            pauseMenuOpen = false;
            animatedMenuRect = pauseMenuBorder.clone ();
        }
        gameMain.setUpdateTerminalTimer (true);
    }
    
    public void changePauseMenu ()
    {
        setupAnimatedBorder ();
        selectedMenuButton = 0;
    }
    
    //Function that makes sure the users input isn't longer then the border
    public boolean checkUserInputLength ()
    {
        int inputMax = userInputBorder.width - ("> ".length () + 2);
        
        if (userInput.length () != inputMax)
            return true;
        
        return false;
    }
    
    //Create the function that will handle changing the selected button
    public void changeSelectedButton (int change)
    {
        //Change the selection variable based on the change
        selectedMenuButton += change;
        
        //Now check if the selection has gone over/under the max/min
        if (selectedMenuButton < 0)
            selectedMenuButton = pauseMenuButtons.size () - 1;
        else if (selectedMenuButton > pauseMenuButtons.size () - 1)
            selectedMenuButton = 0;
    }
    
    public void activateSelectedButton ()
    {
        //Only open these if the pause menu is open
        if (pauseMenuOpen) {
            //Check to see what button is currently selected
            if (selectedMenuButton == 0) {
                //Display the save game menu
                gameMain.currentMenu = GameMain.Menu.SAVE;
            } else if (selectedMenuButton == 1) {
                //Display the load game menu
                gameMain.currentMenu = GameMain.Menu.LOAD;
            } else if (selectedMenuButton == 2) {
                //Display the settings game menu
                gameMain.currentMenu = GameMain.Menu.SETTINGS;
            } else if (selectedMenuButton == 3) {
                //Close the pause menu
                setupAnimatedBorder ();
            } else if (selectedMenuButton == 4) {
                //Close the pause menu
                pauseMenuOpen = false;
                //Tell the program the game won't be running any more
                gameMain.gameIsGoing = false;
                //Display the main menu
                gameMain.currentMenu = GameMain.Menu.MAIN;
            }
        }
    }
    
    public void enterPressed ()
    {
        //Check to see if the message window is open
        if (displayMessage) {
            //If it is open close it
            displayMessage = false;
            hideMessageWindow = true;
            gameMain.setUpdateTerminalTimer (true);
            animatedMenuRect = messageBorder.clone ();
        }
    }
    
    public void displayMessage (GameMain gameMain)
    {
        if (!hideMessageWindow && !animateMessageWindow) {
            if (!displayMessage) {
                animateMessageWindow = true;
                gameMain.setUpdateTerminalTimer (true);
                animatedMenuRect = new Rect (messageBorder.x + (messageBorder.width / 2),
                        messageBorder.y + (messageBorder.height / 2),
                        2,
                        2);
            }
        }
    }
    
    //-----------------------------Everything GUI related-----------------------------\\
    
    //This will draw all the different segments in the game menu like the game log and inventory screens
    private void drawGameSegments ()
    {
        //---------------Draw everything needed for the game log---------------\\
        GameRendering.drawBorder (gameLogBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Game Log");
    
        //---------------Draw everything needed for the players input---------------\\
        GameRendering.drawBorder (userInputBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Input");
    
        //Draw what the user has typed
        gameTerminal.write ("> " + userInput,
                userInputBorder.x + 1,
                userInputBorder.y + 1,
                AsciiPanel.green);
    
        //---------------Draw everything needed for the players inventory---------------\\
        GameRendering.drawBorder (inventoryBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Inventory");
    }
    
    //This will draw the message window if there is one that's being opened/closed/displayed
    private void drawMessageWindow ()
    {
        //Check to see if there is supposed to be a message to display
        if (animateMessageWindow) {
            if (GameRendering.displayAnimatedBorder (messageBorder, animatedMenuRect, gameTerminal, 2, "Message", false)) {
                animateMessageWindow = false;
                displayMessage = true;
                gameMain.setUpdateTerminalTimer (false);
            }
        }
    
        //Check to see if the message window should be hidden
        if (hideMessageWindow) {
            if (GameRendering.hideAnimatedBorder (messageBorder, animatedMenuRect, gameTerminal, 2, "Message", false)) {
                hideMessageWindow = false;
                gameTerminal.clear (' ',
                        animatedMenuRect.x,
                        animatedMenuRect.y,
                        animatedMenuRect.width,
                        animatedMenuRect.height);
                gameMain.setUpdateTerminalTimer (false);
                drawGUI ();
            }
        }
    
        //Now draw the message if it's opened
        if (displayMessage) {
            gameTerminal.clear (' ', messageBorder.x, messageBorder.y, messageBorder.width, messageBorder.height);
            GameRendering.displayMessage (messageBorder, "Message", message, gameMain, gameTerminal, null, null);
        }
    }
    
    //This will draw the pause menu if it's being opened/closed/displayed
    private void drawPauseMenu ()
    {
        //Check to see if the user is trying to open the pause menu. If so animate it to open
        if (animatePauseMenu) {
            if (GameRendering.displayAnimatedBorder (pauseMenuBorder, animatedMenuRect, gameTerminal, 1, "Pause Menu", false)) {
                pauseMenuOpen = true;
                animatePauseMenu = false;
                gameMain.setUpdateTerminalTimer (false);
            }
        }
    
        //Check to see if the pause menu should be hidden
        if (hidePauseMenu) {
            if (GameRendering.hideAnimatedBorder (pauseMenuBorder, animatedMenuRect, gameTerminal, 1, "Pause Menu", false)) {
                hidePauseMenu = false;
                gameTerminal.clear (' ',
                        animatedMenuRect.x,
                        animatedMenuRect.y,
                        animatedMenuRect.width,
                        animatedMenuRect.height);
                gameMain.setUpdateTerminalTimer (false);
                drawGUI ();
            }
        }
    
        //Draw the pause menu if it's open
        if (pauseMenuOpen) {
            //Clear the area the menu will go in
            gameTerminal.clear (' ', pauseMenuBorder.x, pauseMenuBorder.y, pauseMenuBorder.width, pauseMenuBorder.height);
        
            //Draw the menus border
            GameRendering.drawBorder (pauseMenuBorder,
                    gameTerminal,
                    AsciiPanel.white,
                    null,
                    "Pause Menu");
        
            //Draw the menu buttons
            GameRendering.drawButtons (pauseMenuButtonPos.clone (),
                    pauseMenuButtons,
                    gameTerminal,
                    selectedMenuButton,
                    2,
                    AsciiPanel.yellow,
                    true,
                    true);
        }
    }
}
