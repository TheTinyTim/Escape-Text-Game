package com.CabinEscape;

import javax.swing.*;
import com.asciiPanel.AsciiPanel;
import com.asciiPanel.AsciiFont;
import structs.Rect;
import structs.Vector2D;
import sun.misc.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

public class MainMenu {
    
    private GameSettings gameSettings;
    
    private AsciiPanel gameTerminal;
    
    public MainMenu (GameSettings gameSettings, AsciiPanel gameTerminal)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
    }
    
    //Draw the menu
    public void drawMenu ()
    {
//        GameRendering.drawBorder (new Rect (0, 0, 30, 30),
//                                  gameTerminal,
//                                  AsciiPanel.brightGreen,
//                                  null,
//                                  "Main Menu");
        
        //Draw the title of the game on the screen
        //First get the classloader to get the text file from the resource folder
        ClassLoader classLoader = getClass ().getClassLoader ();
        
//        File file = new File (classLoader.getResource ("main_menu_title.txt").getFile ());
        
        InputStream file = classLoader.getResourceAsStream ("main_menu_title.txt");
//
//        File f = new File (classLoader.getResource ("main_menu_title.txt").getFile ());
//        System.out.println (f.getAbsolutePath ());
        
        //Now call the function to draw the title
        GameRendering.drawMenuTitle (new Vector2D (5, 5),
                                     gameTerminal,
                                     file,
                                     AsciiPanel.red);
    }
}
