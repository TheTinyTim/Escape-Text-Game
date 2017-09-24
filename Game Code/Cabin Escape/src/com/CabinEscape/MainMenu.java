package com.CabinEscape;

import com.asciiPanel.AsciiPanel;
import structs.Vector2D;

import java.util.ArrayList;

public class MainMenu {
    
    private GameSettings gameSettings;
    
    private AsciiPanel gameTerminal;
    
    private ArrayList<String> buttons = new ArrayList<String> ();
    private int selectedButton = 0;
    private final int BUTTON_SPACE = 2;
    
    public boolean hasSaveGame = false;
    
    public MainMenu (GameSettings gameSettings, AsciiPanel gameTerminal)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        
        //TODO Check to see if the user has a save game and if so add the continue button
        if (hasSaveGame)
            buttons.add ("Continue Game");
        
        buttons.add ("New Game");
        buttons.add ("Settings");
        buttons.add ("Quit");
    }
    
    //Draw the menu
    public void drawGUI ()
    {
//        GameRendering.drawBorder (new Rect (0, 0, 30, 30),
//                                  gameTerminal,
//                                  AsciiPanel.brightGreen,
//                                  null,
//                                  "Main Menu");
        
        //Draw the title of the game on the screen
    
        //Now call the function to draw the title
        GameRendering.drawMenuTitle (new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Cabin"), 5),
                                                   "Cabin",
                                                   gameTerminal,
                                                   AsciiPanel.white);
        
        GameRendering.drawMenuTitle (new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Escape"), 13),
                                                   "Escape",
                                                   gameTerminal,
                                                   AsciiPanel.red);
        
        GameRendering.drawButtons (new Vector2D ((gameSettings.gameWindowWidth / 2) - 6, (gameSettings.gameWindowHeight / 2) + 15),
                                   buttons,
                                   gameTerminal,
                                   selectedButton,
                                   BUTTON_SPACE,
                                   AsciiPanel.yellow,
                                   true,
                                   true);
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
    public void activateSelectedButton (GameMain gameMain)
    {
        //Find out which button is selected (0=Play 1=Settings 2=Quit)
        if (selectedButton == 0) {
            //Check to see if the game currently has a save game if so the button will be different if it doesn't
            if (hasSaveGame) {
                //TODO Continue game
            } else {
                //Tell the program the game is running
                gameMain.gameIsGoing = true;
                //Start the main game and display the GUI
                gameMain.currentMenu = GameMain.Menu.GAME;
            }
        } else if (selectedButton == 1) {
            //Check to see if the game currently has a save game if so the button will be different if it doesn't
            if (hasSaveGame) {
                //Tell the program the game is running
                gameMain.gameIsGoing = true;
                //Start the main game and display the GUI
                gameMain.currentMenu = GameMain.Menu.GAME;
            } else {
                //Change the menu to the settings menu
                gameMain.currentMenu = GameMain.Menu.SETTINGS;
            }
        } else if (selectedButton == 2) {
            //Check to see if the game currently has a save game if so the button will be different if it doesn't
            if (hasSaveGame) {
                //Change the menu to the settings menu
                gameMain.currentMenu = GameMain.Menu.SETTINGS;
            } else {
                //Close the window and stop the application
                gameMain.dispose ();
            }
        } else if (selectedButton == 3) {
            //Close the window and stop the application
            gameMain.dispose ();
        }
    }
}
