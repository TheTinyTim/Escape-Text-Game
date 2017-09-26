package com.menus;

import com.CabinEscape.GameMain;
import com.CabinEscape.GameRendering;
import com.CabinEscape.GameSettings;
import com.asciiPanel.AsciiPanel;
import com.structs.Rect;
import com.structs.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class SaveFileHandler {
    
    //────────────────────Global Menu Variables─────────────────────────────────────────────────────────────────────────────────────┐
    //These are all the variables that are used within every menu                                                                   │
    private GameSettings gameSettings;  //This stores all the settings for the game                                                 │
    private AsciiPanel gameTerminal;    //This is the terminal that will display everything for the game                            │
    private GameMain gameMain;          //This is the main class for the game that will handle updating the gui and what not        │
    //────────────────────Title Positions───────────────────────────────────────────────────────────────────────────────────────────┤
    //These set the positions of the menu titles                                                                                    │
    private Vector2D saveTitlePos;  //The position of the "Save" title                                                              │
    private Vector2D gameTitlePos;  //The position of the "Game" title                                                              │
    //────────────────────Border for the save list──────────────────────────────────────────────────────────────────────────────────┤
    //This is the border of the save list                                                                                           │
    private Rect saveListBorder;    //                                                                                              │
    //────────────────────Save list buttons─────────────────────────────────────────────────────────────────────────────────────────┤
    //These are the variables for the function buttons on the side of the save border                                               │
    private Vector2D saveListFuncButtonsPos;                                    //The starting position of the side buttons         │
    private ArrayList<String> saveListFuncButtons = new ArrayList<String> ();   //The names of all the buttons                      │
    private boolean showFuncButtonSelected = false;                             //If the side buttons selection should be shown     │
    private int funcButtonSelected = 0;                                         //Which button is selected in the side buttons      │
    //────────────────────Positions for the Users Saves─────────────────────────────────────────────────────────────────────────────┤
    //These are all the variables for the ssaves that go in the save border                                                         │
    private Vector2D userSavesStartPos;                             //The location where the saves start in the border              │
    private ArrayList<String> userSaves = new ArrayList<String> (); //All the users saves on their computer                         │
    private boolean controlSaveButtons = true;                      //If the user has control over the save buttons                 │
    private int saveButtonSelected = 0;                             //Which button is currently selected                            │
    //────────────────────Extra Menu Data───────────────────────────────────────────────────────────────────────────────────────────┤
    //These are all the variables for the extra menu                                                                                │
    private boolean showExtraMenu = false;      //If the extra menu should be shown or not                                          │
    public boolean animateExtraMenu = false;    //If the extra menu should be animated open                                         │
    public boolean hideExtraMenu = false;       //If the extra menu should be animated closed                                       │
    private Rect animatedExtraMenuRect;         //The rect that the animated window uses                                            │
    //────────────────────Extra Menu Buttons────────────────────────────────────────────────────────────────────────────────────────┴──────────────────────┐
    //These are all the variables to change between the side buttons and save files                                                                        │
    private boolean funcButtonLastState = false;                            //Stores if the side buttons were controlled before the extra menu was opened  │
    private boolean saveButtonLastState = false;                            //Stores if the save buttons were controlled before the extra menu was opened  │
    private int extraMenu = 0;                                              //Which extra menu is being opened                                             │
    private int selectedExtraMenuButton = 1;                                //Which button is selected in the extra menu                                   │
    private Rect extraMenuRect;                                             //The rect of the extra menu                                                   │
    private Vector2D extraMenuButtonPos;                                    //The position where the extra menu buttons are                                │
    private ArrayList<String> extraMenuButtons = new ArrayList<String> ();  //All the name of the buttons                                                  │
    //─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
    
    private int maxFileNameLength;
    
    public SaveFileHandler (GameSettings gameSettings, AsciiPanel gameTerminal, GameMain gameMain)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        this.gameMain = gameMain;
        
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
        
        //Get all the stuff for the extra menus
        extraMenuRect = new Rect ((gameSettings.gameWindowWidth / 2) - 15,
                                  (gameSettings.gameWindowHeight / 2) - 7,
                                  30,
                                  15);
        
        extraMenuButtonPos = new Vector2D (((extraMenuRect.width / 2) + extraMenuRect.x) - 9,
                                           (extraMenuRect.height + extraMenuRect.y) - 3);
        extraMenuButtons.add ("Yes");
        extraMenuButtons.add ("No");
        
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
                                   true,
                                   true);
        
        //Check to see if the extra menu should be open or not
        if (animateExtraMenu) {
            if (GameRendering.displayAnimatedBorder (extraMenuRect, animatedExtraMenuRect, gameTerminal, 1, "Warning!", true)) {
                showExtraMenu = true;
                animateExtraMenu = false;
                gameMain.setUpdateTerminalTimer (false);
            }
        }
        
        //Check to see if the extra menu should be hidden
        if (hideExtraMenu) {
            if (GameRendering.hideAnimatedBorder (extraMenuRect, animatedExtraMenuRect, gameTerminal, 1, "Warning!", true)) {
                hideExtraMenu = false;
                gameTerminal.clear (' ',
                        animatedExtraMenuRect.x,
                        animatedExtraMenuRect.y,
                        animatedExtraMenuRect.width,
                        animatedExtraMenuRect.height);
                gameMain.setUpdateTerminalTimer (false);
                turnOnButtonControl ();
                drawGUI ();
            }
        }
        
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
            gameTerminal.write ("want to overwrite",
                                (extraMenuRect.x + (extraMenuRect.width / 2)) - 8,
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
    
    private void setupAnimatedBorder ()
    {
        if (!showExtraMenu) {
            animateExtraMenu = true;
            animatedExtraMenuRect = new Rect (extraMenuRect.x + (extraMenuRect.width / 2),
                                              extraMenuRect.y + (extraMenuRect.height / 2),
                                              2,
                                              2);
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
        } else if (showFuncButtonSelected){
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
    
    public void activateSelectedButton ()
    {
        //Check which buttons are being controlled
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
            setupAnimatedBorder ();
        } else if (controlSaveButtons) {
            //Show the extra menu to overwrite the save
            turnOffButtonControl ();
            setupAnimatedBorder ();
            extraMenu = 0;
        } else {
            if (funcButtonSelected == 0) {
                //TODO make a new save file
            } else if (funcButtonSelected == 1) {
                //Show the extra menu to delete the save
                turnOffButtonControl ();
                setupAnimatedBorder ();
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
