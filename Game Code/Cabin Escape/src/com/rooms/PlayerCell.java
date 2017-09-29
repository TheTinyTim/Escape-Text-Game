package com.rooms;

import com.CabinEscape.GameSettings;
import com.CabinEscape.ItemData;
import com.menus.MainGameHandler;

import java.util.ArrayList;

public class PlayerCell {
    
    /*
     * This class will hold all the necessary information needed for a specific room.
     * Data this class will hold:
     *      Room name
     *      Descriptions for the main room and all the different sides
     *      All the different items located in this room and where they're located
     *      All the different typed functions the player can do
     *
     */
    
    //Important variables needed
    private GameSettings gameSettings;
    private MainGameHandler mainGame;
    
    //Main room variables
    private String roomName;                                                    //The rooms name
    private String roomDescription;                                             //The rooms main description
    private int currentDirection = 0;                                           //The current direction the player is facing in the room
    
    //North side of room variables
    private String northSideDescription;                                        //The north side of the room description
    private ArrayList<ItemData> northSideItems = new ArrayList<ItemData> ();    //All the items that are located in the north side of the room
    
    //East side of room variables
    private String eastSideDescription;                                         //The east side of the room description
    private ArrayList<ItemData> eastSideItems = new ArrayList<ItemData> ();     //All the items that are located in the east side of the room
    
    //South side of room variables
    private String southSideDescription;                                        //The south side of the room description
    private ArrayList<ItemData> southSideItems = new ArrayList<ItemData> ();    //All the items that are located in the south side of the room
    
    //West side of room variables
    private String westSideDescription;                                         //The west side of the room description
    private ArrayList<ItemData> westSideItems = new ArrayList<ItemData> ();     //All the items that are located in the west side of the room
    
    
    //The constructor for this class
    public PlayerCell (GameSettings gameSettings, MainGameHandler mainGame)
    {
        this.gameSettings = gameSettings;
        this.mainGame = mainGame;
        
        //Now set up all the variables for this room
        roomName = "Dark Cell";
        roomDescription = "The room you've called home for what seems like an eternity now. A small, bug infested cell with a tiny mattress in the corner. " +
                          "There's also scattered pieces of paper and other junk that you've accumulated over the days...or weeks. It's hard to tell at this point.";
        
        northSideDescription = "You see a metal gate with the door that's keeping you captive in this hell. On the floor you see a bunch of junk that hasn't been cleaned in ages.";
        
        eastSideDescription = "There's a small mattress that you've been sleeping on. You've come to treat this as your safe haven, the only place you can go to and dream of a better future " +
                              "no matter how slim that chance seems. There's also some markings on the wall that you've scratch when you're bored...which is most of the day.";
        
        southSideDescription = "There's a large pile of dirty dishes that you get your so called \"meals\" on. They've been sitting there for days, maybe weeks, now attracting all kinds " +
                               "of bugs.";
        
        westSideDescription = "All you see is a bunch of trash on the ground and a small bucket in the corner against the wall.";
        
        //Now set up all the needed items for this room
        setupItems ();
    }
    
    //This method will set up all the necessary items within the room
    private void setupItems ()
    {
        //North side items
        
        
        //East side items
        
        
        //South side items
        
        
        //West side items
        westSideItems.add (new ItemData ("cell_bucket", "Dirty Bucket",
                           "A bucket filled with...waste. You're not sure why you're carrying it",
                           ItemData.ItemType.MISC));
    }
    
    //This method will go through all the items in this room and find the one that the player had or has
    //so the player can't get it again. This will be called when the game loads a players save and finds
    //an item they have that belongs to this room.
    public void setItemPossession (String UID, boolean playerHasItem, boolean playerHadItem)
    {
        //Loop through all the item lists until we find the specific item and change the possession data on it
        boolean foundItem = false;
        //Make a list of all the item lists
        ArrayList<ArrayList<ItemData>> roomItems = new ArrayList<ArrayList<ItemData>> ();
        roomItems.add (northSideItems);
        roomItems.add (eastSideItems);
        roomItems.add (southSideItems);
        roomItems.add (westSideItems);
        
        for (int side = 0; side < roomItems.size (); side++) {
            if (!foundItem) {
                for (int i = 0; i < roomItems.get (side).size (); i++) {
                    if (roomItems.get (side).get (i).UID == UID) {
                        //This is the item so change the possession of it
                        roomItems.get (side).get (i).playerHasItem = playerHasItem;
                        roomItems.get (side).get (i).playerHadItem = playerHadItem;
                        //Now tell the program that we found the item
                        foundItem = true;
                        //And break out of the loop
                        break;
                    }
                }
            }
        }
    }
    
    //This method will handle actually taking the users input and trying to find out what they're trying to do.
    //And if it's something they can do make the appropriate stuff happen
    public void checkUserInput (String input)
    {
        //First make the users input lowercase
        String userInput = input.toLowerCase ();
        //Try to find out the base thing the player is trying to do by seeing if it contains any specific keywords
        if (userInput.contains ("room") || userInput.contains ("surroundings") || userInput.contains ("location")) {
            
            //See what te user is trying to do exactly
            if ((userInput.contains ("examine") || userInput.contains ("look") || userInput.contains ("review")|| userInput.contains ("inspect")
                    || userInput.contains ("gander") || userInput.contains ("observe") || userInput.contains ("survey") || userInput.contains ("see"))) {
                
                //The user is trying to get the description of the room
                mainGame.addToGameLog (roomDescription);
                
            } else {
                
                //Tell the player they can't do that
                mainGame.addToGameLog ("I don't know how to do that.");
                
            }
            
        } else if (userInput.contains ("turn") || (userInput.contains ("change") && userInput.contains ("direction")) || userInput.contains ("rotate")
                || userInput.contains ("face")) {
            
            //The user is trying to change the direction they're looking
            //Now find out what direction they're trying to look at
            if (userInput.contains ("left") || userInput.contains ("counterclockwise") || userInput.contains ("counter clockwise")
                    || userInput.contains ("counter-clockwise") || userInput.contains ("counter clock wise") || userInput.contains ("counter-clock-wise")) {
                
                //Subtract the current direction by one so the program knows the user is turning left
                currentDirection--;
                //Now make sure the current direction is within the bounds of the room
                checkCurrentDirection ();
                //Now display on the log what the player did
                mainGame.addToGameLog ("You turn left.");
                
            } else if (userInput.contains ("right") || userInput.contains ("clockwise") || userInput.contains ("clock wise")
                    || userInput.contains ("clock-wise")) {
               
                //Add to the current direction so the program knows the player is turning right
                currentDirection++;
                checkCurrentDirection ();
                //Now display on the log what the player did
                mainGame.addToGameLog ("You turn right.");
                
            } else if (userInput.contains ("north")) {
                
                //Face the player north
                currentDirection = 0;
                //Now display on the log what the player did
                mainGame.addToGameLog ("You face the north side of the room.");
                
            } else if (userInput.contains ("east")) {
                
                //Face the player east
                currentDirection = 1;
                //Now display on the log what the player did
                mainGame.addToGameLog ("You face the east side of the room.");
                
            } else if (userInput.contains ("south")) {
                
                //Face the player south
                currentDirection = 2;
                //Now display on the log what the player did
                mainGame.addToGameLog ("You face the south side of the room.");
                
            } else if (userInput.contains ("west")) {
                
                //Face the player west
                currentDirection = 3;
                //Now display on the log what the player did
                mainGame.addToGameLog ("You face the west side of the room.");
                
            } else if (userInput.contains ("around") || userInput.contains ("behind") || userInput.contains ("back") || userInput.contains ("opposite")) {
                
                //Turn the player around twice to have them facing the opposite direction while checking between each turn
                currentDirection--;
                checkCurrentDirection ();
                currentDirection--;
                checkCurrentDirection ();
                //Now display on the log what the player did
                mainGame.addToGameLog ("You turn around.");
                
            } else {
                
                //If the player entered an incorrect direction tell them
                mainGame.addToGameLog ("I don't know how to do that.");
                
            }
        //Now after checking all of this if nothing happens see if the user is trying to do something based on the side they're facing
        } else if (currentDirection == 0) {
        
            //Try to figure out what the player is trying to do
            if (userInput.contains ("examine") || userInput.contains ("look") || userInput.contains ("review") || userInput.contains ("inspect")
                    || userInput.contains ("gander") || userInput.contains ("observe") || userInput.contains ("survey") || userInput.contains ("see") || userInput.contains ("area")) {
                
                //The user is trying to get the description of the side of the room they're looking at
                mainGame.addToGameLog (northSideDescription);
                
            } else {
    
                //Tell the player they can't do that
                mainGame.addToGameLog ("I don't know how to do that.");
    
            }
        
        } else if (currentDirection == 1) {
    
            //Try to figure out what the player is trying to do
            if (userInput.contains ("examine") || userInput.contains ("look") || userInput.contains ("looking") || userInput.contains ("review") || userInput.contains ("inspect")
                    || userInput.contains ("gander") || userInput.contains ("observe") || userInput.contains ("survey") || userInput.contains ("see") || userInput.contains ("area")) {
        
                //The user is trying to get the description of the side of the room they're looking at
                mainGame.addToGameLog (eastSideDescription);
        
            } else {
    
                //Tell the player they can't do that
                mainGame.addToGameLog ("I don't know how to do that.");
    
            }
        
        } else if (currentDirection == 2) {
    
            //Try to figure out what the player is trying to do
            if (userInput.contains ("examine") || userInput.contains ("look") || userInput.contains ("looking") || userInput.contains ("review") || userInput.contains ("inspect")
                    || userInput.contains ("gander") || userInput.contains ("observe") || userInput.contains ("survey") || userInput.contains ("see") || userInput.contains ("area")) {
        
                //The user is trying to get the description of the side of the room they're looking at
                mainGame.addToGameLog (southSideDescription);
        
            } else {
    
                //Tell the player they can't do that
                mainGame.addToGameLog ("I don't know how to do that.");
    
            }
        
        } else if (currentDirection == 3) {
    
            //Try to figure out what the player is trying to do
            if (userInput.contains ("examine") || userInput.contains ("look") || userInput.contains ("looking") || userInput.contains ("review") || userInput.contains ("inspect")
                    || userInput.contains ("gander") || userInput.contains ("observe") || userInput.contains ("survey") || userInput.contains ("see") || userInput.contains ("area")) {
        
                //The user is trying to get the description of the side of the room they're looking at
                mainGame.addToGameLog (westSideDescription);
        
            } else {
    
                //Tell the player they can't do that
                mainGame.addToGameLog ("I don't know how to do that.");
    
            }
        
        }
    }
    
    //This function will check and make sure that the current direction is within the bounds of how many sides the room has
    private void checkCurrentDirection ()
    {
        //Make sure the current direction is between 0-3
        if (currentDirection < 0)
            currentDirection = 3;
        else if (currentDirection > 3)
            currentDirection = 3;
    }
}
