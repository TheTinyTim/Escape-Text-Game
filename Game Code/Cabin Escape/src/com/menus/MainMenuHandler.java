package com.menus;

import com.CabinEscape.GameMain;
import com.CabinEscape.GameRendering;
import com.CabinEscape.GameSettings;
import com.asciiPanel.AsciiPanel;
import com.structs.Vector2D;

import java.util.ArrayList;

public class MainMenuHandler {
    
    //────────────────────Global Menu Variables─────────────────────────────────────────────────────────────────────────────────────┐
    //These are all the variables that are used within every menu                                                                   │
    private GameSettings gameSettings;  //This stores all the settings for the game                                                 │
    private AsciiPanel gameTerminal;    //This is the terminal that will display everything for the game                            │
    private GameMain gameMain;          //This is the main class for the game that will handle updating the gui and what not        │
    //────────────────────Button Variables──────────────────────────────────────────────────────────────────────────────────────────┤
    private ArrayList<String> buttons = new ArrayList<String> ();   //The button names                                              │
    private int selectedButton = 0;                                 //The currently selected button                                 │
    private final int BUTTON_SPACE = 2;                             //How much space there is between each buttons                  │
    //────────────────────User Save Variables───────────────────────────────────────────────────────────────────────────────────────┤
    public boolean hasSaveGame = false; //If the user has a save                                                                    │
    //──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
    
    public MainMenuHandler (GameSettings gameSettings, AsciiPanel gameTerminal, GameMain gameMain)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        this.gameMain = gameMain;
        
        //TODO Check to see if the user has a save game and if so add the continue button
        if (hasSaveGame)
            buttons.add ("Continue Game");
        
        buttons.add ("New Game");
        buttons.add ("Settings");
        buttons.add ("Quit");
    }
    
    //Create the function that will handle changing the selected button
    public void changeSelectedButton (int change)
    {
        //Change the selection variable based on the change
        selectedButton += change;
        
        //Now check if the selection has gone over/under the max/min
        if (selectedButton < 0)
            selectedButton = buttons.size () - 1;
        else if (selectedButton > buttons.size () - 1)
            selectedButton = 0;
    }
    
    //Create the function that will handle the user pressing the enter button on the current selected button
    public void activateSelectedButton ()
    {
        //Find out which button is selected (0=Play 1=Settings 2=Quit)
        if (selectedButton == 0) {
            //Check to see if the game currently has a save game if so the button will be different if it doesn't
            if (hasSaveGame) {
                //----Continue Game----\\
                //TODO Continue game
            } else {
                //----New Game----\\
                //Tell the game that this is a new game being played
                gameSettings.isNewGame = true;
                //Tell the program the game is running
                gameMain.gameIsGoing = true;
                //Start the main game and display the GUI
                gameMain.changeMenu (GameMain.Menu.GAME);
            }
        } else if (selectedButton == 1) {
            //Check to see if the game currently has a save game if so the button will be different if it doesn't
            if (hasSaveGame) {
                //----New Game----\\
                //Tell the game that this is a new game being played
                gameSettings.isNewGame = true;
                //Tell the program the game is running
                gameMain.gameIsGoing = true;
                //Start the main game and display the GUI
                gameMain.changeMenu (GameMain.Menu.GAME);
            } else {
                //----Settings----\\
                //Change the menu to the settings menu
                gameMain.changeMenu (GameMain.Menu.SETTINGS);
            }
        } else if (selectedButton == 2) {
            //Check to see if the game currently has a save game if so the button will be different if it doesn't
            if (hasSaveGame) {
                //----Settings----\\
                //Change the menu to the settings menu
                gameMain.changeMenu (GameMain.Menu.SETTINGS);
            } else {
                //----Exit----\\
                //Close the window and stop the application
                gameMain.dispose ();
            }
        } else if (selectedButton == 3) {
            //----Exit----\\
            //Close the window and stop the application
            gameMain.dispose ();
        }
    }
    
    //--------------------------Everything GUI Related--------------------------\\
    
    //The main method that handles drawing all the needed things to the terminal
    public void drawGUI ()
    {
        //Draw the title of the game on the screen
        drawTitles ();
        
        //Now draw the buttons for the menu
        drawButtons ();
    }
    
    //------------Titles------------\\
    //Draw the titles for this menu
    private void drawTitles ()
    {
        GameRendering.drawMenuTitle (new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Cabin"), 5),
                "Cabin",
                gameTerminal,
                AsciiPanel.white);
    
        GameRendering.drawMenuTitle (new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Escape"), 13),
                "Escape",
                gameTerminal,
                AsciiPanel.red);
    }
    
    //------------Buttons------------\\
    private void drawButtons ()
    {
        GameRendering.drawButtons (new Vector2D ((gameSettings.gameWindowWidth / 2) - 6, (gameSettings.gameWindowHeight / 2) + 15),
                buttons,
                gameTerminal,
                selectedButton,
                BUTTON_SPACE,
                AsciiPanel.yellow,
                true,
                true);
    }
}
