package com.CabinEscape;

import com.asciiPanel.AsciiPanel;
import structs.Rect;
import structs.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class LoadMenu {
    
    private GameSettings gameSettings;
    
    private AsciiPanel gameTerminal;
    
    private Vector2D loadTitlePos;
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
    
    public boolean animateExtraMenu = false;
    public boolean hideExtraMenu = false;
    private Rect animatedExtraMenuRect;
    
    private boolean showExtraMenu = false;
    private boolean funcButtonLastState = false;
    private boolean saveButtonLastState = false;
    private int extraMenu = 0;
    private int selectedExtraMenuButton = 1;
    private Rect extraMenuRect;
    private Vector2D extraMenuButtonPos;
    private ArrayList<String> extraMenuButtons = new ArrayList<String> ();
    
    public LoadMenu (GameSettings gameSettings, AsciiPanel gameTerminal)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
    
        loadTitlePos = new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Load"), 5);
        gameTitlePos = new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Game"), 13);
    
        saveListBorder = new Rect (30,
                10,
                30,
                gameSettings.gameWindowHeight - 14);
    
        saveListFuncButtons.add ("Load Save");
        saveListFuncButtons.add ("Delete");
        saveListFuncButtons.add ("Exit");
    
        userSaves.add ("Save 1");
        userSaves.add ("Save 2");
        userSaves.add ("Save 3");
        userSaves.add ("Save 4");
    
        saveListFuncButtonsPos = new Vector2D (saveListBorder.x - 11, saveListBorder.y + 2);
        userSavesStartPos = new Vector2D (saveListBorder.x + 3, saveListBorder.y + 1);
    
        //Get all the stuff for the extra menus
        extraMenuRect = new Rect ((gameSettings.gameWindowWidth / 2) - 15,
                (gameSettings.gameWindowHeight / 2) - 7,
                30,
                15);
    
        extraMenuButtonPos = new Vector2D (((extraMenuRect.width / 2) + extraMenuRect.x) - 9,
                (extraMenuRect.height + extraMenuRect.y) - 3);
        extraMenuButtons.add ("Yes");
        extraMenuButtons.add ("No");
    }
    
    public void drawGUI (GameMain gameMain)
    {
        //Draw the title of the screen
        GameRendering.drawMenuTitle (loadTitlePos.clone (),
                                    "Load",
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
                                   true,
                                   true);
    
        //Check to see if the extra menu should be open or not
        if (animateExtraMenu) {
            if (GameRendering.displayAnimatedBorder (extraMenuRect, animatedExtraMenuRect, gameTerminal, 1, "Warning!")) {
                showExtraMenu = true;
                animateExtraMenu = false;
                gameMain.setUpdateTerminalTimer (false);
            }
        }
    
        //Check to see if the extra menu should be hidden
        if (hideExtraMenu) {
            if (GameRendering.hideAnimatedBorder (extraMenuRect, animatedExtraMenuRect, gameTerminal, 1, "Warning!")) {
                hideExtraMenu = false;
                gameTerminal.clear (' ',
                        animatedExtraMenuRect.x,
                        animatedExtraMenuRect.y,
                        animatedExtraMenuRect.width,
                        animatedExtraMenuRect.height);
                gameMain.setUpdateTerminalTimer (false);
                turnOnButtonControl ();
                drawGUI (gameMain);
            }
        }
        
        //Check to see if the extra menu should be open or not
        if (showExtraMenu)
            showExtraMenu ();
    }
    
    //This will show an extra menu based on which one should show
    public void showExtraMenu ()
    {
        //Clear the area the extra menu will be
        gameTerminal.clear (' ',
                extraMenuRect.x,
                extraMenuRect.y,
                extraMenuRect.width,
                extraMenuRect.height);
        
        //Draw the border of the menu
        GameRendering.drawBorder (extraMenuRect,
                gameTerminal,
                null,
                null,
                "Warning!");
        
        //Start to display the menus text
        gameTerminal.write ("Are you sure you",
                (extraMenuRect.x + (extraMenuRect.width / 2)) - 8,
                extraMenuRect.y + 2);
        
        //Find out which menu should be shown (0 = overwrite 1 = delete) and display the action they're being warned about
        if (extraMenu == 0) {
            gameTerminal.write ("want to load",
                    (extraMenuRect.x + (extraMenuRect.width / 2)) - 6,
                    extraMenuRect.y + 3);
        } else {
            gameTerminal.write ("want to delete",
                    (extraMenuRect.x + (extraMenuRect.width / 2)) - 7,
                    extraMenuRect.y + 3);
        }
        
        //Now display the file they're being warned about
        gameTerminal.write ("( ",
                (extraMenuRect.x + (extraMenuRect.width / 2)) - (userSaves.get (saveButtonSelected).length () / 2) - 2,
                extraMenuRect.y + 4,
                AsciiPanel.white);
        gameTerminal.write (userSaves.get (saveButtonSelected),
                (extraMenuRect.x + (extraMenuRect.width / 2)) - (userSaves.get (saveButtonSelected).length () / 2),
                extraMenuRect.y + 4);
        gameTerminal.write (" )",
                (extraMenuRect.x + (extraMenuRect.width / 2)) + (userSaves.get (saveButtonSelected).length () / 2),
                extraMenuRect.y + 4,
                AsciiPanel.white);
        
        //Now draw the buttons
        GameRendering.drawButtons (extraMenuButtonPos.clone (),
                extraMenuButtons,
                gameTerminal,
                selectedExtraMenuButton,
                10,
                AsciiPanel.yellow,
                true,
                true,
                null,
                null,
                false,
                false);
    }
    
    private void setupAnimatedBorder (GameMain gameMain)
    {
        if (!showExtraMenu) {
            animateExtraMenu = true;
            animatedExtraMenuRect = new Rect (extraMenuRect.x + (extraMenuRect.width / 2),
                    extraMenuRect.y + (extraMenuRect.height / 2));
        } else {
            hideExtraMenu = true;
            showExtraMenu = false;
            animatedExtraMenuRect = extraMenuRect.clone ();
        }
        gameMain.setUpdateTerminalTimer (true);
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
    public void changeButtonControl (int change)
    {
        //Make sure only to allow control of this if the extra menu is open or not
        if (!showExtraMenu) {
            //Set both control bools opposite to what they are
            controlSaveButtons = !controlSaveButtons;
            showFuncButtonSelected = !showFuncButtonSelected;
        } else {
            //Change the selection variable based on the change
            selectedExtraMenuButton += change;
        
            //Now check if the selection has gone over/under the max/min
            if (selectedExtraMenuButton < 0)
                selectedExtraMenuButton = extraMenuButtons.size () - 1;
            else if (selectedExtraMenuButton > extraMenuButtons.size () - 1)
                selectedExtraMenuButton = 0;
        }
    }
    
    //This will turn off both controls for the button lists as well as save their last states
    public void turnOffButtonControl ()
    {
        funcButtonLastState = showFuncButtonSelected;
        saveButtonLastState = controlSaveButtons;
        
        showFuncButtonSelected = false;
        controlSaveButtons = false;
    }
    
    //This will turn the buttons back to the original state before being turned off
    public void turnOnButtonControl ()
    {
        showFuncButtonSelected = funcButtonLastState;
        controlSaveButtons = saveButtonLastState;
    }
    
    public void activateSelectedButton (GameMain gameMain)
    {
        if (showExtraMenu) {
            //Now figure out which button is being selected
            if (selectedExtraMenuButton == 0) {
                //Yes button pressed
                //Figure out which menu is being shown
                if (extraMenu == 0) {
                    //TODO overwrite the save that is currently selected with a new save file
                } else {
                    //TODO delete the currently selected save file
                }
            } else {
                //No button pressed
            }
            selectedExtraMenuButton = 1;
            extraMenu = 0;
            setupAnimatedBorder (gameMain);
        } else if (controlSaveButtons) {
            //Show the extra menu to load the save
            turnOffButtonControl ();
            setupAnimatedBorder (gameMain);
            extraMenu = 0;
        } else {
            if (funcButtonSelected == 0) {
                //Show the extra menu to load the save
                turnOffButtonControl ();
                showExtraMenu = true;
                extraMenu = 0;
            } else if (funcButtonSelected == 1) {
                //Show the extra menu to delete the currently selected save file
                turnOffButtonControl ();
                setupAnimatedBorder (gameMain);
                extraMenu = 1;
            } else if (funcButtonSelected == 2) {
                //Reset the variables for the selected buttons
                funcButtonSelected = 0;
                saveButtonSelected = 0;
                //Reset which buttons are able to be controled
                changeButtonControl (0);
                //Go back to the game menu
                gameMain.currentMenu = GameMain.Menu.GAME;
            }
        }
    }
}
