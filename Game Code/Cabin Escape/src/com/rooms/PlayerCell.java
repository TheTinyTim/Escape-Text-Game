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
    
    //North side of room variables
    private String northSideDescription;                                        //The north side of the room description
    private ArrayList<ItemData> northSideItems = new ArrayList<ItemData> ();    //All the items that are located in the north side of the room
    
    //East side of room variables
    private String eastSideDescription;                                         //The east side of the room description
    private ArrayList<ItemData> EastSideItems = new ArrayList<ItemData> ();     //All the items that are located in the east side of the room
    
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
        roomDescription = "The room you've called home for what seems like an eternity now. A small, bug infested cell with a tiny mattress in the corner." +
                          "There's also scattered pieces of paper and other junk that you've accumulated over the days...or weeks. It's hard to tell at this point.";
        
        northSideDescription = "You see a metal gate with the door that's keeping you captive in this hell. On the floor you see a bunch of junk that hasn't been cleaned in ages.";
        
        eastSideDescription = "There's a small mattress that you've been sleeping on. You've come to treat this as your safe haven, the only place you can go to and dream of a better future" +
                              "no matter how slim that chance seems. There's also some markings on the wall that you've scratch when you're bored...which is most of the day.";
        
        southSideDescription = "There's a large pile of dirty dishes that you get your so called \"meals\" on. They've been sitting there for days, maybe weeks, now attracting all kinds" +
                               "of bugs.";
        
        eastSideDescription = "All you see is a bunch of trash on the ground and a small bucket in the corner against the wall.";
    }
    
    //This method will set up all the necessary items within the room
    private void setupItems ()
    {
    
    }
    
    //This class will handle actually taking the users input and trying to find out what they're trying to do.
    //And if it's something they can do make the appropriate stuff happen
    public void checkUserInput (String input)
    {
        //First make the users input lowercase
        String userInput = input.toLowerCase ();
        //Try to find out the base thing the player is trying to do by seeing if it contains any specific keywords
        if ((userInput.contains ("room") || userInput.contains ("surroundings")) && (userInput.contains ("examine") || userInput.contains ("look") || userInput.contains ("review")
                || userInput.contains ("inspect") || userInput.contains ("gander") || userInput.contains ("observe") || userInput.contains ("survey"))) {
            mainGame.addToGameLog (roomDescription);
        }
    }
}
