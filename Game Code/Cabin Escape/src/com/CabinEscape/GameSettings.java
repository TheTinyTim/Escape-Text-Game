package com.CabinEscape;

import structs.Rect;

public class GameSettings {
    
    
    
    //Initialize all the variables for the games settings
    
    //Ints
    public final int gameWindowWidth = 120;                                      //The width of the games window (amount of rows in the ASCII Terminal)
    public final int gameWindowHeight = 50;                                     //The height of the games window (amount of columns in the ASCII Terminal)
    
    //Rects
    public final Rect gameWindowRect = new Rect (0, 0, 120, 50);
    
    //Strings
    public final String gameWindowsTitle = "Cabin Escape";                      //The game windows title
    
    //Bools
    public boolean gameRunning = true;
    
    
    public GameSettings ()
    {
    
    }
}
