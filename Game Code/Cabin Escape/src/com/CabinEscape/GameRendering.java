package com.CabinEscape;

import com.asciiPanel.AsciiFont;
import com.asciiPanel.AsciiPanel;
import com.sun.istack.internal.Nullable;
import structs.Rect;

import java.awt.*;

public class GameRendering {
    
    //Set up some chars to be used for rendering borders
    private static final int PIPE_DOWN_RIGHT = 218;
    private static final int PIPE_DOWN_LEFT = 191;
    private static final int PIPE_UP_RIGHT = 192;
    private static final int PIPE_UP_LEFT = 217;
    private static final int PIPE_VERTICAL = 179;
    private static final int PIPE_HORIZONTAL = 196;
    
    
    //This class will hold all te functions needed to help with rendering onto the ASCII Terminal
    
    //This will draw a border onto the game terminal with no title
    public static void drawBorder (Rect bounds, AsciiPanel gameTerminal, @Nullable Color foreground, @Nullable Color background)
    {
        //Get the needed variables to loop through the area needed to make the border
        int xMax = bounds.x + bounds.width;
        int yMax = bounds.x + bounds.height;
        
        //Now loop through all the x and y points
        for (int y = 0; y <= yMax; y++) {
            //Get the actual spot on the terminal that this will be on
            int yPos = bounds.y + y;
            
            for (int x = 0; x <= xMax; x++) {
                //Get the actual spot on the terminal that this will be on
                int xPos = bounds.x + x;
                
                //Set up the variable that will be drawn onto the screen
                int thingToDraw = -1;
                
                //Now find out what kind of pipe needs to be used based on the current x/y location
                //being drawn to.
                if (y == 0) {
                    //The top of the border
                    if (x == 0)
                        thingToDraw = PIPE_DOWN_RIGHT;
                    else if (x == xMax)
                        thingToDraw = PIPE_DOWN_LEFT;
                    else
                        thingToDraw = PIPE_HORIZONTAL;
                } else if (y > bounds.y && y < yMax) {
                    //Everything in the middle of the border
                    if (x == 0 || x == xMax)
                        thingToDraw = PIPE_VERTICAL;
                } else if (y == yMax) {
                    //The bottom of the border
                    if (x == 0)
                        thingToDraw = PIPE_UP_RIGHT;
                    else if (x == xMax)
                        thingToDraw = PIPE_UP_LEFT;
                    else
                        thingToDraw = PIPE_HORIZONTAL;
                }
                
                //Now draw what needs to be drawn in the spot only if something was set on that spot
                if (thingToDraw != -1)
                    gameTerminal.write ((char)thingToDraw, xPos, yPos, foreground, background);
            }
        }
    }
    
    //This will draw a border onto the game terminal with a title
//    public static void drawBorder (Rect bounds, String title, AsciiPanel gameTerminal)
//    {
//
//    }
}
