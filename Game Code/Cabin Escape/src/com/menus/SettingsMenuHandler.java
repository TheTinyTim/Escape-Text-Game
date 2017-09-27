package com.menus;

import com.CabinEscape.GameMain;
import com.CabinEscape.GameRendering;
import com.CabinEscape.GameSettings;
import com.asciiPanel.AsciiPanel;
import com.structs.Vector2D;

import java.util.ArrayList;

public class SettingsMenuHandler {
    
    //────────────────────Global Menu Variables─────────────────────────────────────────────────────────────────────────────────────┐
    //These are all the variables that are used within every menu                                                                   │
    private GameSettings gameSettings;  //This stores all the settings for the game                                                 │
    private AsciiPanel gameTerminal;    //This is the terminal that will display everything for the game                            │
    private GameMain gameMain;          //This is the main class for the game that will handle updating the gui and what not        │
    //────────────────────Title Data────────────────────────────────────────────────────────────────────────────────────────────────┤
    Vector2D titlePos;  //The position for the menus title                                                                          │
    //────────────────────Button Data───────────────────────────────────────────────────────────────────────────────────────────────┤
    private ArrayList<String> buttons = new ArrayList<String> ();   //The button names                                              │
    private Vector2D buttonStartPos;                                //The starting position of the buttons                          │
    private Vector2D soundPercentagePos;                            //The starting position of the percentages                      │
    private int selectedButton = 0;                                 //The currently selected button                                 │
    private final int BUTTON_SPACE = 3;                             //The amount of space between spaces                            │
    //──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
    
    public SettingsMenuHandler (GameSettings gameSettings, AsciiPanel gameTerminal, GameMain gameMain)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        this.gameMain = gameMain;
        
        buttons.add ("Master Sound");
        buttons.add ("Music Sound");
        buttons.add ("FX Sounds");
        buttons.add ("Back");
        
        //Set up all the positions of the GUI for this menu
        titlePos = new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Settings"), 5);
        buttonStartPos = new Vector2D (gameSettings.gameWindowWidth / 2, gameSettings.gameWindowHeight / 2);
        soundPercentagePos = buttonStartPos.clone ();
    }
    
    public void drawGUI ()
    {
        //Draw the title of this menu
        GameRendering.drawMenuTitle (titlePos.clone (),
                                     "Settings",
                                     gameTerminal,
                                     AsciiPanel.white);
        
        //Draw the buttons
        GameRendering.drawButtons (buttonStartPos.clone (),
                                   buttons,
                                   gameTerminal,
                                   selectedButton,
                                   BUTTON_SPACE,
                                   AsciiPanel.yellow,
                                   true,
                                   true);
        
        //Draw the percentages of the sounds next to the selections
        //First get all the percentages
        int masterPercentage = (int)((gameSettings.masterSound / gameSettings.SOUND_MAX) * 100);
        int musicPercentage = (int)((gameSettings.musicSound / gameSettings.SOUND_MAX) * 100);
        int fxPercentage = (int)((gameSettings.fxSound / gameSettings.SOUND_MAX) * 100);
        
        //Now actually draw the percentages
        //Master Sound
        gameTerminal.write ("[" + masterPercentage + "%]",
                            soundPercentagePos.x + buttons.get (0).length () + 4,
                            soundPercentagePos.y);
        //Music Sound
        gameTerminal.write ("[" + musicPercentage + "%]",
                            soundPercentagePos.x + buttons.get (0).length () + 4,
                            soundPercentagePos.y + BUTTON_SPACE);
        //FX Sound
        gameTerminal.write ("[" + fxPercentage + "%]",
                            soundPercentagePos.x + buttons.get (0).length () + 4,
                            soundPercentagePos.y + (BUTTON_SPACE * 2));
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
    
    //Create the function that will handle changing sound levels
    public void changeSoundLevel (int change)
    {
        //System.out.println (gameSettings.masterSound);
        //Find out which sound is being changed
        if (selectedButton == 0) {
            //Lower or raise the Master sound but only if it's not 0 or the max
            if (gameSettings.masterSound > 0 && gameSettings.masterSound < gameSettings.SOUND_MAX)
                gameSettings.masterSound += change;
            else if (gameSettings.masterSound == 0 && change > 0)
                gameSettings.masterSound += change;
            else if (gameSettings.masterSound == gameSettings.SOUND_MAX && change < 0)
                gameSettings.masterSound += change;
        } else if (selectedButton == 1) {
            //Lower or raise the Music sound but only if it's not 0 or the max
            if (gameSettings.musicSound > 0 && gameSettings.musicSound < gameSettings.SOUND_MAX)
                gameSettings.musicSound += change;
            else if (gameSettings.musicSound == 0 && change > 0)
                gameSettings.musicSound += change;
            else if (gameSettings.musicSound == gameSettings.SOUND_MAX && change < 0)
                gameSettings.musicSound += change;
        } else if (selectedButton == 2) {
            //Lower or raise the FX sound but only if it's not 0 or the max
            if (gameSettings.fxSound > 0 && gameSettings.fxSound < gameSettings.SOUND_MAX)
                gameSettings.fxSound += change;
            else if (gameSettings.fxSound == 0 && change > 0)
                gameSettings.fxSound += change;
            else if (gameSettings.fxSound == gameSettings.SOUND_MAX && change < 0)
                gameSettings.fxSound += change;
        }
        //System.out.println (gameSettings.masterSound);
    }
    
    //Create the function that will handle the user pressing the enter button on the current selected button
    public void activateSelectedButton ()
    {
        //Check to see what button is being selected and do what needs to be done
        if (selectedButton == 3){
            //Display the correct menu based on if the user was playing the game when this menu was opened
            if (gameMain.gameIsGoing)
                gameMain.changeMenu (GameMain.Menu.GAME);
            else
                gameMain.changeMenu (GameMain.Menu.MAIN);
        }
    }
}
