package com.CabinEscape;

import com.asciiPanel.AsciiFont;
import com.asciiPanel.AsciiPanel;
import com.sun.istack.internal.Nullable;
import structs.Rect;
import structs.Vector2D;

import java.awt.*;
import java.io.*;
import java.util.*;

public class GameRendering {
    
    //Set up some chars to be used for rendering borders
    private static final int PIPE_DOWN_RIGHT = 218;
    private static final int PIPE_DOWN_LEFT = 191;
    private static final int PIPE_UP_RIGHT = 192;
    private static final int PIPE_UP_LEFT = 217;
    private static final int PIPE_VERTICAL = 179;
    private static final int PIPE_HORIZONTAL = 196;
    
    //Set up the dictionary that will hold all the letters/numbers/characters to display on the terminal for titles
    public static Map<String, ArrayList<String>> terminalTitleLetters = new HashMap<String, ArrayList<String>> ();
    private static final int LETTER_SPACE = 2;
    
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
    
    //This function will go through the title_letters.txt file and grab all the letters in arrays for later use
    public static void setupTitleCharacters (InputStream file)
    {
        //Create a list that will temporarily hold the letter until the full letter is stored (break is when the # shows up)
        ArrayList<String> tempLetter = new ArrayList<String> ();
        
        //Create a scanner to read the file
        Scanner scanner = new Scanner (file);
        //Now loop through the file to get all the letters. When a # is found this marks the end of the letter and the next character after that is the key
        while (scanner.hasNextLine ()) {
            String currLine = scanner.nextLine ();
            //Check to make sure this line doesn't have a # in it as this is the end of the letter
            if (currLine.contains ("#")) {
                //System.out.println (tempLetter.size ());
                //Now that the end of the letter has been found and the full letter is stored in the temp array store it in the dictionary with the key (second character on this line)
                terminalTitleLetters.put (currLine.substring (1, currLine.length ()), (ArrayList<String>) tempLetter.clone ());
                //Make sure to empty out the temp letter array list to use again
                tempLetter.clear ();
            } else {
                //Add the line to the temp array list
                tempLetter.add (currLine);
            }
        }
    }
    
    //Create the function that will handle drawing menu titles from text given and then draw the separate letters based on the string given
    public static void drawMenuTitle (Vector2D startPos, String title, AsciiPanel gameTerminal, Color foreground)
    {
        //First make sure to make the title all the uppercase
        title = title.toUpperCase ();
        
        //Make a temp of start pos so that if the user passes a already stored one it won't change that in memory
        Vector2D tempStartPos = new Vector2D (startPos.x, startPos.y);
        
        //Setup the variables needed for the loop
        
        //Now loop through all the letters in the title
        for (int titleIndex = 0; titleIndex < title.length (); titleIndex++) {
            //Get the letter at the current index
            String currLetter = title.substring (titleIndex, titleIndex + 1);
            //This will store the largest line in the letter array for use later
            int largestLine = 0;
            
            if (!currLetter.equals (" ")) {
                //Get the array from the dictionary that holds the correct letter
                ArrayList<String> letterList = terminalTitleLetters.get (currLetter);
                
                //Now go through the letter list and display it on the terminal
                for (int y = 0; y < letterList.size (); y++) {
                    //Get the position of the letter based on the starting pos
                    int yPos = startPos.y + y;
                    
                    //Check to see if this line is the largest one. And if so set it as the largest one
                    if (letterList.get (y).length () > largestLine)
                        largestLine = letterList.get (y).length ();
                    
                    //Now go through all the characters in the string
                    for (int x = 0; x < letterList.get (y).length (); x++) {
                        //Get the position of the letter based on the starting pos
                        int xPos = startPos.x + x;
                        
                        //Get the character at the current index
                        char currChar = letterList.get (y).charAt (x);
                        char charToWrite;
                        //Figure out what the character is supposed to represent
                        if (currChar == '*')
                            charToWrite = 219;
                        else
                            charToWrite = 32;
                        
                        //Now place the character on the terminal in it's position
                        gameTerminal.write (charToWrite, xPos, yPos, foreground);
                    }
                }
            } else {
                largestLine = 2;
            }
            
            //Now update the start pos for the next letter based on the largest line and another added 2 for the letter space
            startPos.x += largestLine + LETTER_SPACE;
        }
        //Set start pos back to what it originally was
        startPos.set (tempStartPos);
    }
    
    //Create a function that will give the total length of a string for a title plus spaces
    public static int titleLength (String title)
    {
        //Set up some variables that will be later used
        int length = 0;
        
        //Upper case the title
        title = title.toUpperCase ();
        
        //Set up some variables to denote how much the length that character will be
        String twoLength = "!., ";
        String fourLength = "()";
        String sixLength = "1I";
        String eightLength = "234567890ABCDEFGHJKLOPRSUVXZ?";
        String tenLength = "MNQTWY";
        
        for (int i = 0; i < title.length (); i++) {
            //Get the character at the current index
            String currChar = title.substring (i, i + 1);
            
            //Find out the length of the character
            if (twoLength.contains (currChar))
                length += 2 + LETTER_SPACE;
            else if (fourLength.contains (currChar))
                length += 4 + LETTER_SPACE;
            else if (sixLength.contains (currChar))
                length += 6 + LETTER_SPACE;
            else if (eightLength.contains (currChar))
                length += 8 + LETTER_SPACE;
            else if (tenLength.contains (currChar))
                length += 10 + LETTER_SPACE;
        }
        
        return length;
    }
    
    //Create a function that will draw a list of selectable buttons
    public static void drawButtons (Vector2D startPos, ArrayList<String> buttonNames, int buttonSpace, int selected, AsciiPanel gameTerminal, Color bracketColor, Color foreground)
    {
        //Go through all the buttons and display them on screen
        for (int i = 0; i < buttonNames.size (); i++){
            //Determine if this button is currently the selected button
            if (selected == i) {
                gameTerminal.write ("[", startPos.x - 2, startPos.y, bracketColor);
                gameTerminal.write ("]", startPos.x + buttonNames.get (i).length () + 1, startPos.y, bracketColor);
            }
            
            gameTerminal.write (buttonNames.get (i), startPos.x, startPos.y, foreground);
            
            startPos.y += buttonSpace;
        }
    }
}