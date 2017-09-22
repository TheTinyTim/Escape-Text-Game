package com.CabinEscape;

import com.asciiPanel.AsciiFont;
import com.asciiPanel.AsciiPanel;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
    
    public static GameMain gameRenderer;
    
    //Initialize the variable that will store the ASCII Terminal for later use and creation
    private AsciiPanel gameTerminal;
    
    //Initialize the GameSettings class variable
    private GameSettings gameSettings;
    
    private MainMenu mainMenu;
    
    private String onScreenTyping = "";
    
    public static void main(String[] args) {
        //Create a new GameMain class to start the game
        gameRenderer = new GameMain ();
    }

    public GameMain ()
    {
        //Invoke the overridden superclass method for JFrame
        super ();
        
        //Create the class that holds all the settings for the game
        gameSettings = new GameSettings ();
        
        //Create a new ASCII Terminal with the default 80x24 width/height
        gameTerminal = new AsciiPanel (gameSettings.gameWindowWidth,
                                       gameSettings.gameWindowHeight,
                                       AsciiFont.CP437_9x16);
        
        //Set the windows title name
        this.setTitle (gameSettings.gameWindowsTitle);
    
        //Add the key listener
        addKeyListener (this);
        
        //Add the game terminal to JFrame to later use
        add (gameTerminal);
        //Now pack the JFrame to set the needed space for the windows created
        pack ();
        
        startGame (this);
    }
    
    public void startGame (GameMain gameRenderer)
    {
        this.gameRenderer = gameRenderer;
    
        //Set up the title letters in the game rendering class for later use.
        ClassLoader classLoader = getClass ().getClassLoader ();
        InputStream file = classLoader.getResourceAsStream ("title_letters.txt");
        GameRendering.setupTitleCharacters (file);
        
        //Create the class that holds all the data for the main menu
        mainMenu = new MainMenu (gameSettings, gameTerminal);
        mainMenu.drawMenu ();
    
        //Set how the application closes
        gameRenderer.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        //Now set the JFrame window visible
        gameRenderer.setVisible (true);
    }
    
    public void changeMenu ()
    {
        //Make sure to clear the terminal so nothing will be left over
        //gameTerminal.clear ();
        
        //Draw the menu
        //mainMenu.drawMenu ();
        
        //Now repaint the terminal so the changes will actually display
        //gameTerminal.repaint ();
    }
    
    //Set up all the methods needed to extend KeyListener
    public void keyPressed (KeyEvent event)
    {
        if (event.getKeyChar () == 'c' || event.getKeyChar () == 'C')
            changeMenu ();
    }
    
    public void keyReleased (KeyEvent event)
    {
        //System.out.println ("Key released");
    }
    
    public void keyTyped (KeyEvent event)
    {
//        if (event.getKeyChar () == KeyEvent.VK_BACK_SPACE)
//            onScreenTyping = onScreenTyping.substring (0, onScreenTyping.length () - 1);
//        else if (!event.isActionKey () && event.getKeyChar () != KeyEvent.VK_ENTER)
//            onScreenTyping += event.getKeyChar ();
        
        //gameTerminal.write (onScreenTyping, 1, 1);
        //gameTerminal.updateUI ();
    }
}
