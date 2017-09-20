package com.CabinEscape;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import java.awt.*;

public class GameMain extends JFrame {
    
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
    
    //Initialize the variable that will store the ASCII Terminal for later use and creation
    private AsciiPanel gameTerminal;

    public GameMain ()
    {
        //Invoke the overridden superclass method for JFrame
        super ();
        
        //Create a new ASCII Terminal with the default 80x24 width/height
        gameTerminal = new AsciiPanel ();
        
        //gameFont = gameFont.deriveFont (30);
        //this.setFont (gameFont);
        
        //Write to the terminal with an x/y coordinate
        gameTerminal.write ("Hello World!", 1, 1);
        
        
        //Set the windows title name
        this.setTitle ("Project Cabin Escape");
        
        //Add the game terminal to JFrame to later use
        add (gameTerminal);
        //Now pack the JFrame to set the needed space for the windows created
        pack ();
        
    }
    
    public static void main(String[] args) {
        //Create a new GameMain class to create the ASCII Terminal
        GameMain app = new GameMain ();
    
        Font gameFont = app.getFont ();
        app.setFont (new Font (gameFont.getFontName (), Font.PLAIN, 40));
        
        //Set how the application closes
        app.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        //Now set the JFrame window visible
        app.setVisible (true);
    }
}
