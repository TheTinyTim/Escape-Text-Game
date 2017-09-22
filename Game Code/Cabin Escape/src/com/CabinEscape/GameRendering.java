package com.CabinEscape;

import com.asciiPanel.AsciiFont;
import com.asciiPanel.AsciiPanel;
import com.sun.istack.internal.Nullable;
import structs.Rect;
import structs.Vector2D;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
//    public static void drawBorder (Rect bounds, AsciiPanel gameTerminal, @Nullable Color foreground, @Nullable Color background)
//    {
//        //Get the needed variables to loop through the area needed to make the border
//        int xMax = bounds.x + bounds.width;
//        int yMax = bounds.x + bounds.height;
//
//        //Now loop through all the x and y points
//        for (int y = 0; y <= yMax; y++) {
//            //Get the actual spot on the terminal that this will be on
//            int yPos = bounds.y + y;
//
//            for (int x = 0; x <= xMax; x++) {
//                //Get the actual spot on the terminal that this will be on
//                int xPos = bounds.x + x;
//
//                //Set up the variable that will be drawn onto the screen
//                int thingToDraw = -1;
//
//                //Now find out what kind of pipe needs to be used based on the current x/y location
//                //being drawn to.
//                if (y == 0) {
//                    //The top of the border
//                    if (x == 0)
//                        thingToDraw = PIPE_DOWN_RIGHT;
//                    else if (x == xMax)
//                        thingToDraw = PIPE_DOWN_LEFT;
//                    else
//                        thingToDraw = PIPE_HORIZONTAL;
//                } else if (y > bounds.y && y < yMax) {
//                    //Everything in the middle of the border
//                    if (x == 0 || x == xMax)
//                        thingToDraw = PIPE_VERTICAL;
//                } else if (y == yMax) {
//                    //The bottom of the border
//                    if (x == 0)
//                        thingToDraw = PIPE_UP_RIGHT;
//                    else if (x == xMax)
//                        thingToDraw = PIPE_UP_LEFT;
//                    else
//                        thingToDraw = PIPE_HORIZONTAL;
//                }
//
//                //Now draw what needs to be drawn in the spot only if something was set on that spot
//                if (thingToDraw != -1)
//                    gameTerminal.write ((char)thingToDraw, xPos, yPos, foreground, background);
//            }
//        }
//    }
    
    //This will draw a border onto the game terminal with a title
    public static void drawBorder (Rect bounds, AsciiPanel gameTerminal, @Nullable Color foreground, @Nullable Color background, String title)
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
                    if (x == 0) {
                        thingToDraw = PIPE_DOWN_RIGHT;
                    } else if (x == 1 && title != "") {
                        gameTerminal.write (title, xPos, yPos, foreground, background);
                        //Add the length of the title to the x loop index
                        x += title.length () - 1;
                    } else if (x == xMax) {
                        thingToDraw = PIPE_DOWN_LEFT;
                    } else {
                        thingToDraw = PIPE_HORIZONTAL;
                    }
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
    
    //Create the function that will handle drawing menu titles from text files
    public static void drawMenuTitle (Vector2D startPos, AsciiPanel gameTerminal, InputStream file, Color foreground)
    {
        //Make a list that will store all the data from the text file
        ArrayList<String> fileText = new ArrayList<String> ();
        
        //Create a scanner to read the input stream of the file
        Scanner scanner = new Scanner (file);
        //Loop through until the scanner doesn't have any more lines to read
        while (scanner.hasNextLine ()) {
            //Add the next line of the file to the list
            fileText.add (scanner.nextLine ());
        }

        //Make sure to close the scanner to not take up any memory
        scanner.close ();
        try {
            //Do the same thing for the input stream
            file.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        
        //Go through all the lines from the file and print out their corresponding character
        for (int y = 0; y < fileText.size (); y++) {
            //Get the current index's y position on the terminal based on the starting position given.
            int yPos = startPos.y + y;
            
            //Loop through the characters in the current row
            for (int x = 0; x < fileText.get (y).length (); x++) {
                //Get the current index's x position on the terminal based on the starting position given.
                int xPos = startPos.x + x;
                
                //Get the character at the current index
                char currChar = fileText.get (y).charAt (x);
                char charToWrite;
                //Figure out what the character is supposed to represent
                if (currChar == 'v')
                    charToWrite = 219;
                else
                    charToWrite = 32;
                
                //Now place the character on the terminal in it's position
                gameTerminal.write (charToWrite, xPos, yPos, foreground);
            }
        }
    }
}
