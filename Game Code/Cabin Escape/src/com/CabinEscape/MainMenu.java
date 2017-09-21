package com.CabinEscape;

import javax.swing.*;
import com.asciiPanel.AsciiPanel;
import com.asciiPanel.AsciiFont;
import structs.Rect;

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
        GameRendering.drawBorder (new Rect (0, 0, 10, 10),
                                  gameTerminal,
                                  AsciiPanel.brightGreen,
                                  null);
    }
}
