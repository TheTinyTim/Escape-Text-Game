package com.CabinEscape;

import com.asciiPanel.AsciiFont;
import com.asciiPanel.AsciiPanel;
import com.menus.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;

public class GameMain extends JFrame implements KeyListener {
    
    /*
     * This will be a old school style text based adventure/escape room game. It will be built off using Trystan's ASCIIPanel java
     * library. A blog tutorial by Trystan can be found at:
     * http://trystans.blogspot.com/2011/08/roguelike-tutorial-01-java-eclipse.html
     *
     * Info on the game can be found within the GitHub Escape-Text-Game\Game Documents folder. That will be where anything story wise
     * and idea wise for the game will be stored.
     */
    
    //Help prevent show-stopping failures if we need to serialize this class later in te future.
    //Explanation why this is helpful here:
    //https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
    private static final long serialVersionUID = 1060623638149583738L;
    
    //Enums
    public enum Menu {
        MAIN,
        GAME,
        SETTINGS,
        SAVE,
        LOAD
    }
    
    //Public variables
    public static GameMain gameRenderer;
    public boolean gameIsGoing = false;     //Stores if the player is playing the actual game or not.
    
    //AsciiPanel Variables
    private AsciiPanel gameTerminal;        //This is the actual terminal that will display everything for the game
    
    //Initialize the GameSettings class variable
    private GameSettings gameSettings;      //This stores all the important settings needed fro te game
    
    //All the menus
    private MainMenuHandler mainMenuHandler;
    private SettingsMenuHandler settingsMenuHandler;
    private MainGameHandler gameMenu;
    private SaveFileHandler saveFileHandler;
    private LoadSaveHandler loadSaveHandler;
    private Menu currentMenu = Menu.MAIN;                //The current menu being displayed
    
    private Timer timer;
    
    public static void main(String[] args) {
        //Create a new GameMain class to start the game
        new GameMain ();
    }

    public GameMain ()
    {
        //Invoke the overridden superclass method for JFrame
        super ();
    
        //Store this class in a variable for later use
        this.gameRenderer = this;
        
        //Create the class that holds all the settings for the game
        gameSettings = new GameSettings ();
        //TODO grab settings from file and place it in the settings class
        
        //Create a new ASCII Terminal with the default 80x24 width/height
        gameTerminal = new AsciiPanel (gameSettings.gameWindowWidth,
                                       gameSettings.gameWindowHeight,
                                       AsciiFont.CP437_9x16);
        
        //Set the windows title name
        this.setTitle (gameSettings.gameWindowsTitle);
        
        //Don't allow the user to resize the window
        setResizable (false);
    
        //Add the key listener
        addKeyListener (this);
        
        //Add the game terminal to JFrame to later use
        add (gameTerminal);
        //Now pack the JFrame to set the needed space for the windows created
        pack ();
        
        startGame ();
    }
    
    public void startGame ()
    {
        //Set up the title letters in the game rendering class for later use.
        ClassLoader classLoader = getClass ().getClassLoader ();
        InputStream file = classLoader.getResourceAsStream ("title_letters.txt");
        GameRendering.setupTitleCharacters (file);
        
        //Instantiate all the menus
        mainMenuHandler = new MainMenuHandler (gameSettings, gameTerminal, this);
        settingsMenuHandler = new SettingsMenuHandler (gameSettings, gameTerminal, this);
        gameMenu = new MainGameHandler (gameSettings, gameTerminal, this);
        saveFileHandler = new SaveFileHandler (gameSettings, gameTerminal, this);
        loadSaveHandler = new LoadSaveHandler (gameSettings, gameTerminal, this);
        
        //Set up the timer to continually update if needed by the program
        ActionListener actionListener = new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                updateTerminal ();
            }
        };
        timer = new Timer (30, actionListener);
        
        updateTerminal ();
    
        //Set how the application closes
        gameRenderer.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        //Now set the JFrame window visible
        gameRenderer.setVisible (true);
    }
    
    //-----------------------GUI Control-----------------------\\
    //The main method that will draw what's needed on the terminal
    public void updateTerminal ()
    {
        //Make sure to clear the terminal so nothing will be left over
        gameTerminal.clear ();
        
        //Draw the menu
        if (currentMenu == Menu.MAIN)
            mainMenuHandler.drawGUI ();
        else if (currentMenu == Menu.SETTINGS)
            settingsMenuHandler.drawGUI ();
        else if (currentMenu == Menu.GAME)
            gameMenu.drawGUI ();
        else if (currentMenu == Menu.SAVE)
            saveFileHandler.drawGUI ();
        else if (currentMenu == Menu.LOAD)
            loadSaveHandler.drawGUI ();
        
        //Now repaint the terminal so the changes will actually display
        gameTerminal.repaint ();
    }
    
    //This will start the timer to keep updating the terminal
    public void setUpdateTerminalTimer (boolean on)
    {
        if (on)
            timer.start ();
        else
            timer.stop ();
    }
    
    //This will handle switching menus in the game
    public void changeMenu (Menu newMenu)
    {
        currentMenu = newMenu;
    }
    
    //-----------------------User Input Control-----------------------\\
    //Set up all the methods needed to extend KeyListener
    public void keyPressed (KeyEvent event)
    {
        /*
         * Keycodes:
         *      40 = Up Arrow
         *      38 = Down Arrow
         *      39 = Right Arrow
         *      37 = Left Arrow
         */
        
        //Check to see if the user has typed anything while in the game menu
        if (currentMenu == Menu.GAME) {
            //Check to see if the user is trying to pause the menu or not
            if (event.getKeyChar () == KeyEvent.VK_ESCAPE) {
                //Pause the game
                gameMenu.escapePressed ();
            //Check to see if the user is tyring to press the enter key
            } else if (event.getKeyChar () == KeyEvent.VK_ENTER) {
                gameMenu.enterPressed ();
            //Check to see if the user is trying to delete a character from what they typed.
            } else if (event.getKeyChar () == KeyEvent.VK_BACK_SPACE) {
                if (!gameMenu.userInput.equals (""))
                    gameMenu.userInput = gameMenu.userInput.substring (0, gameMenu.userInput.length () - 1);
            } else if (!event.isActionKey () && event.getKeyChar () != KeyEvent.VK_ENTER) {
                //First make sure that the user string isn't longer then how many characters can fit into the border
                if (gameMenu.checkUserInputLength ()) {
                    //Now only write something to the user input if the user is either not holding the shift key or when they press the shift key while pressing another key
                    if (event.isShiftDown () && event.getKeyCode () != 16) {
                        gameMenu.userInput += event.getKeyChar ();
                    } else if (!event.isShiftDown ()) {
                        gameMenu.userInput += event.getKeyChar ();
                    }
                }
            }
        }
        
        if (event.getKeyCode () == 40)
            changeSelectedButton (1);
        
        else if (event.getKeyCode () == 38)
            changeSelectedButton (-1);
        
        else if (event.getKeyCode () == 39)
            incrementSelectedButton (1);
        
        else if (event.getKeyCode () == 37)
            incrementSelectedButton (-1);
        
        else if (event.getKeyCode () == KeyEvent.VK_ENTER)
            activateButton ();
        
        //Make sure to update the terminal to display the new information
        updateTerminal ();
    }
    
    //Function that will call the correct button activation method based on the current menu open
    public void activateButton ()
    {
        if (currentMenu == Menu.MAIN)
            mainMenuHandler.activateSelectedButton ();
        else if (currentMenu == Menu.SETTINGS)
            settingsMenuHandler.activateSelectedButton ();
        else if (currentMenu == Menu.GAME)
            gameMenu.activateSelectedButton ();
        else if (currentMenu == Menu.SAVE)
            saveFileHandler.activateSelectedButton ();
        else if (currentMenu == Menu.LOAD)
            loadSaveHandler.activateSelectedButton ();
    }
    
    //Function that will call the correct button change selection method based on the current menu open
    public void changeSelectedButton (int change)
    {
        if (currentMenu == Menu.MAIN)
            mainMenuHandler.changeSelectedButton (change);
        else if (currentMenu == Menu.SETTINGS)
            settingsMenuHandler.changeSelectedButton (change);
        else if (currentMenu == Menu.GAME)
            gameMenu.changeSelectedButton (change);
        else if (currentMenu == Menu.SAVE)
            saveFileHandler.changeSelectedButton (change);
        else if (currentMenu == Menu.LOAD)
            loadSaveHandler.changeSelectedButton (change);
    }
    
    //Function that will call te correct button incrementation method based on the current menu open
    public void incrementSelectedButton (int change)
    {
        if (currentMenu == Menu.SETTINGS)
            settingsMenuHandler.changeSoundLevel (change);
        else if (currentMenu == Menu.SAVE)
            saveFileHandler.changeButtonControl (change);
        else if (currentMenu == Menu.LOAD)
            loadSaveHandler.changeButtonControl (change);
    }
    
    public void keyReleased (KeyEvent event)
    {
        //System.out.println ("Key released");
    }
    
    public void keyTyped (KeyEvent event)
    {
        //System.out.println ("Key Typed");
    }
}
