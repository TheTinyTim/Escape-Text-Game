package com.CabinEscape;

import com.asciiPanel.AsciiPanel;
import structs.Rect;
import structs.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class SaveMenu {
    
    private GameSettings gameSettings;
    
    private AsciiPanel gameTerminal;
    
    private Vector2D saveTitlePos;
    private Vector2D gameTitlePos;
    
    private Rect saveListBorder;
    
    private Vector2D saveListFuncButtonsPos;
    private ArrayList<String> saveListFuncButtons = new ArrayList<String> ();
    private boolean showFuncButtonSelected = false;
    private int funcButtonSelected = 0;
    
    private Vector2D userSavesStartPos;
    private ArrayList<String> userSaves = new ArrayList<String> ();
    private boolean controlSaveButtons = true;
    private int saveButtonSelected = 0;
    
    private int maxFileNameLength;
    
    public SaveMenu (GameSettings gameSettings, AsciiPanel gameTerminal)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        
        saveTitlePos = new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Save"), 5);
        gameTitlePos = new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Game"), 13);
        
        saveListBorder = new Rect (30,
                                   10,
                                   30,
                                   gameSettings.gameWindowHeight - 14);
    
        saveListFuncButtons.add ("New Save");
        saveListFuncButtons.add ("Delete");
        saveListFuncButtons.add ("Exit");
        
        userSaves.add ("Save 1");
        userSaves.add ("Save 2");
        userSaves.add ("Save 3");
        userSaves.add ("Save 4");
        
        saveListFuncButtonsPos = new Vector2D (saveListBorder.x - 11, saveListBorder.y + 2);
        userSavesStartPos = new Vector2D (saveListBorder.x + 3, saveListBorder.y + 1);
        
        //Find out the max length file names can be
        maxFileNameLength = saveListBorder.width - 6;
    }
    
    public void drawGUI ()
    {
        //Draw the title of the screen
        GameRendering.drawMenuTitle (saveTitlePos.clone (),
                                     "Save",
                                     gameTerminal,
                                     AsciiPanel.white);
    
        GameRendering.drawMenuTitle (gameTitlePos.clone (),
                                     "Game",
                                     gameTerminal,
                                     AsciiPanel.red);
        
        //Draw the border for the save files
        GameRendering.drawBorder (saveListBorder,
                                  gameTerminal,
                                  null,
                                  null,
                                  "Saved Files");
        
        //Draw the side buttons
        GameRendering.drawButtons (saveListFuncButtonsPos.clone (),
                                   saveListFuncButtons,
                                   gameTerminal,
                                   funcButtonSelected,
                                   1,
                                   AsciiPanel.yellow,
                                   showFuncButtonSelected,
                                   showFuncButtonSelected);
        
        GameRendering.drawButtons (userSavesStartPos.clone (),
                                   userSaves,
                                   gameTerminal,
                                   saveButtonSelected,
                                   1,
                                   AsciiPanel.black,
                                   true,
                                   controlSaveButtons,
                                   null,
                                   Color.gray,
                                   true);
    }
    
    //Create the function that will handle changing the selected button
    public void changeSelectedButton (int change)
    {
        //Find out which buttons are being used by the player
        if (controlSaveButtons) {
            //Change the selection variable based on the change
            saveButtonSelected += change;
    
            //Now check if the selection has gone over/under the max/min
            if (saveButtonSelected < 0)
                saveButtonSelected = userSaves.size () - 1;
            else if (saveButtonSelected > userSaves.size () - 1)
                saveButtonSelected = 0;
        } else {
            //Change the selection variable based on the change
            funcButtonSelected += change;
    
            //Now check if the selection has gone over/under the max/min
            if (funcButtonSelected < 0)
                funcButtonSelected = saveListFuncButtons.size () - 1;
            else if (funcButtonSelected > saveListFuncButtons.size () - 1)
                funcButtonSelected = 0;
        }
    }
    
    //This will handle when the player is trying to move over to the side buttons or save files
    public void changeButtonControl ()
    {
        //Set both control bools opposite to what they are
        controlSaveButtons = !controlSaveButtons;
        showFuncButtonSelected = !showFuncButtonSelected;
    }
    
    public void activateSelectedButton (GameMain gameMain)
    {
        //Check which buttons are being controlled
        if (controlSaveButtons) {
            //TODO overwrite the save that is currently selected with a new save file
        } else {
            if (funcButtonSelected == 0) {
                //TODO make a new save file
            } else if (funcButtonSelected == 1) {
                //TODO delete the currently selected save file
            } else if (funcButtonSelected == 2) {
                //Reset the variables for the selected buttons
                funcButtonSelected = 0;
                saveButtonSelected = 0;
                //Reset which buttons are able to be controled
                changeButtonControl ();
                //Go back to the game menu
                gameMain.currentMenu = GameMain.Menu.GAME;
            }
        }
    }
}
