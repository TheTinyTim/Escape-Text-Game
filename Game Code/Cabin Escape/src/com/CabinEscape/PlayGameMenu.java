package com.CabinEscape;

import com.asciiPanel.AsciiPanel;
import structs.Rect;
import structs.Vector2D;

import java.util.ArrayList;

public class PlayGameMenu {
    
    private GameSettings gameSettings;
    private AsciiPanel gameTerminal;
    
    //GUI variables
    //Game Log
    private Rect gameLogBorder;
    private ArrayList<String> gameLog = new ArrayList<String> ();
    //User Input
    private Rect userInputBorder;
    public String userInput = "";
    //Inventory
    private Rect inventoryBorder;
    //Pause Menu
    private ArrayList<String> pauseMenuButtons = new ArrayList<String> ();
    private Vector2D pauseMenuButtonPos;
    private int selectedMenuButton = 0;
    private Rect pauseMenuBorder;
    private boolean pauseMenuOpen = false;
    
    public PlayGameMenu (GameSettings gameSettings, AsciiPanel gameTerminal)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        
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
                                    11);
        
        pauseMenuButtons.add ("Save Game");
        pauseMenuButtons.add ("Load Game");
        pauseMenuButtons.add ("Settings");
        pauseMenuButtons.add ("Exit");
        
        pauseMenuButtonPos = new Vector2D (pauseMenuBorder.x + ((pauseMenuBorder.width / 2) - 4), pauseMenuBorder.y + ((pauseMenuBorder.height / 2) - 3));
    }
    
    public void drawGUI ()
    {
        //First draw all the borders for the game
        GameRendering.drawBorder (gameLogBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Game Log");
        
        GameRendering.drawBorder (userInputBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Input");
        
        GameRendering.drawBorder (inventoryBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Inventory");
        
        //Draw the users input string
        gameTerminal.write ("> " + userInput,
                            userInputBorder.x + 1,
                            userInputBorder.y + 1,
                            AsciiPanel.green);
        
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
    
    public void changePauseMenu ()
    {
        pauseMenuOpen = !pauseMenuOpen;
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
    
    public void activateSelectedButton (GameMain gameMain)
    {
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
            changePauseMenu ();
            //Tell the program the game won't be running any more
            gameMain.gameIsGoing = false;
            //Display the main menu
            gameMain.currentMenu = GameMain.Menu.MAIN;
        }
    }
}
