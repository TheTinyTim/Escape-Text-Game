package com.CabinEscape;

import com.asciiPanel.AsciiPanel;
import structs.Vector2D;

import java.util.ArrayList;

public class SettingsMenu {
    
    private GameSettings gameSettings;
    
    private AsciiPanel gameTerminal;
    
    //GUI settings
    //Title
    String title = "Settings";
    Vector2D titlePos;
    
    //Buttons
    private ArrayList<String> buttons = new ArrayList<String> ();
    private Vector2D buttonStartPos;
    private Vector2D soundPercentagePos;
    private int selectedButton = 0;
    private final int BUTTON_SPACE = 3;
    
    public SettingsMenu (GameSettings gameSettings, AsciiPanel gameTerminal)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        
        buttons.add ("Master Sound");
        buttons.add ("Music Sound");
        buttons.add ("FX Sounds");
        buttons.add ("Back");
        
        //Set up all the positions of the GUI for this menu
        titlePos = new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength (title), 5);
        buttonStartPos = new Vector2D (gameSettings.gameWindowWidth / 2, gameSettings.gameWindowHeight / 2);
        soundPercentagePos = buttonStartPos;
    }
    
    public void drawMenu ()
    {
        System.out.println (titlePos.x + " " + titlePos.y);
        //Draw the title of this menu
        GameRendering.drawMenuTitle (titlePos,
                                     title,
                                     gameTerminal,
                                     AsciiPanel.white);
        
        //Draw the buttons
        GameRendering.drawButtons (buttonStartPos,
                                   buttons,
                                   BUTTON_SPACE,
                                   selectedButton,
                                   gameTerminal,
                                   AsciiPanel.yellow,
                                   AsciiPanel.white);
        
        //Draw the percentages of the sounds next to the selections
        //First get all the percentages
        int masterPercentage = (gameSettings.masterSound / gameSettings.SOUND_MAX) * 100;
        int musicPercentage = (gameSettings.musicSound / gameSettings.SOUND_MAX) * 100;
        int fxPercentage = (gameSettings.fxSound / gameSettings.SOUND_MAX) * 100;
        
        //Now actually draw the percentages
        //Master Sound
        gameTerminal.write ("[" + masterPercentage + "%]",
                            soundPercentagePos.x + 4,
                            soundPercentagePos.y);
        //Music Sound
        gameTerminal.write ("[" + musicPercentage + "%]",
                            soundPercentagePos.x + 4,
                            soundPercentagePos.y + BUTTON_SPACE);
        //FX Sound
        gameTerminal.write ("[" + fxPercentage + "%]",
                            soundPercentagePos.x + 4,
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
    }
    
    //Create the function that will handle the user pressing the enter button on the current selected button
    public void activateSelectedButton (GameMain gameMain)
    {
        //Check to see what button is being selected and do what needs to be done
        if (selectedButton == 4){
            gameMain.currentMenu = 0;
        }
    }
}
