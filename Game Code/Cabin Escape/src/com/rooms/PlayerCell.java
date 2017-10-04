package com.rooms;

import com.CabinEscape.GameItems;
import com.CabinEscape.GameSettings;
import com.CabinEscape.ItemData;
import com.CabinEscape.PlayerData;
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
    
    //These variables will store everything about Sam and where he's located
    public boolean isSamDownstairs = false;
    public boolean isSamDead = false;
    private boolean didPlayerYell = false;
    
    //User Input control
    public boolean allowUserInput = true;
    
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
                          "There's also scattered pieces of paper and other junk that you've accumulated over the weeks...or months. It's hard to tell at this point.";
        
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
        
        //Make sure the user can input anything
        if (allowUserInput) {
            //Check if sam is currently downstairs and not dead
            if (isSamDownstairs) {
                //If he is the player can't do much
                if (checkMovementSynonyms (userInput)) {
                    //Have Sam yell at the player for trying to move
                    mainGame.addToGameLog ("#r \"What the fuck do ya think yer doing!?\", Sam yells, " +
                            "\"Don't even think about turning away from me when I'm talking to ya boy\"");
            
                    //Restart the delayed text timer so it doesn't look so weird
                    mainGame.restartDelayedTextTimer ();
                } else if (checkLookSynonyms (userInput) && userInput.contains ("sam")) {
                    //Give a description of Sam
                    mainGame.addToGameLog ("You see the younger of your kidnappers, Sam. He's quite the piece of " +
                            "work wearing a torn up shirt with plenty of stains and a disgusting unkempt beard. As he talks " +
                            "you get a whiff of probably the worst thing you've ever smelled coming from a human being, " +
                            "if you can call him that, from what you can only assume is his breath. You also notice something " +
                            "shiny in his shirt pocket.");
            
                } else if (userInput.contains ("throw") || userInput.contains ("toss") || userInput.contains ("splash") || userInput.contains ("use")) {
                    //The player is trying to use something from their inventory. Try to find out what it is they're trying to use
                    if (userInput.contains ("bucket")) {
                        //The player is trying to use the cell_bucket from their inventory on sam
                        //But first check to see if the user has the cell_bucket in their inventory
                        if (GameItems.cellBucket.playerHasItem) {
                            //Have the player throw the contents of the bucket at Sam
                            mainGame.startDelayedText (new String[] {
                                            "You take the bucket you picked up and while Sam is yelling you splash the contents " +
                                                    "right on him. If someone didn't think he smelled bad now there's no way in hell " +
                                                    "they don't think that now.",
                                    
                                            "#r Sam yells, \"WHAT IN THE ABSOLUTE FU-\" Sam suddenly starts violently coughing and gasping for air.",
                                    
                                            "Yup you definitely got some in his mouth. You can tell Sam is choking on the...waste. " +
                                                    "Sam desperately tries to grab you but can't quite reach you knocking " +
                                                    "an item from his shirt pocket onto the ground.",
                                    
                                            "#r \"Mother...Will...Teach...You...\", Sam gasps out as he falls to the ground dying.",
                                    
                                            "#g After a moment of twitching and convulsions you gather that Sam has finally died. \"This is it.\", " +
                                                    "you say aloud \"Now's my chance to get out of here...\""},
                            
                                    "kill sam");
    
                            //Don't let the user input anything while this is going on
                            allowUserInput = false;
                        }
                    }
                } else if (((userInput.contains ("break") || userInput.contains ("snap") || userInput.contains ("crack") || userInput.contains ("split")) && userInput.contains ("arm"))
                        || userInput.contains ("attack") || userInput.contains ("charge") || userInput.contains ("strike") || userInput.contains ("rush") || userInput.contains ("storm")) {
                    //The player is trying to attack Sam
                    mainGame.startDelayedText (new String[] {
                                    "As Sam thrusts his arm through the cell gate you take your opportunity. You grab his arm and, with all your " +
                                            "strength you snap his arm against the cell bars. You see bone shoot out from his arm and blood " +
                                            "violently squirting out. Sam makes out shriek so loud and haunting you don't think you'll ever forget it.",
                            
                                    "#r \"Mother...Will...Teach...You...\", Sam faintly says as he's falling to the ground.",
                            
                                    "You notice an object fall from his pocket as he's falling. You can tell as he lays on the ground he " +
                                            "slowly starts fading out of consciousness, and by the amount of blood he's losing you don't think " +
                                            "he's getting back up."},
                    
                            "kill sam");
                    
                    //Don't let the user input anything while this is going on
                    allowUserInput = false;
                }
            } else {
                //Try to find out the base thing the player is trying to do by seeing if it contains any specific keywords
                if (userInput.contains ("scream") || userInput.contains ("yell") || userInput.contains ("screech") || userInput.contains ("shriek")
                        || userInput.contains ("howl") || userInput.contains ("shout")) {
                    //The user is trying to scream at the top of their lungs. This will jump start the other kidnapper
                    //coming downstairs if the player hasn't already done this.
                    if (!didPlayerYell) {
                        //The player hasn't screamed yet so do everything that needs to be done
                        mainGame.startDelayedText (new String[] {
                                        "You scream at the top of your lungs, either out of frustration or insanity you're not really " +
                                                "sure, so loud you freaked yourself out. " +
                                                "However someone else was freaked out by this. You immediately hear someone upstairs walking and " +
                                                "shortly after an angry voice shouting most likely at you. The door to the upstairs loudly crashes " +
                                                "open and slammed shut.",
                                
                                        "#r \"What the fuck was that!?\", you hear as Sam, one of your kidnappers, is flying down the stairs. ",
                                
                                        "Sam is now right up at your cell's gate visibly angry with a red hot face wildly swinging his arms in " +
                                                "your direction between the bars.",
                                
                                        "He adds, \"You know if it wasn't for mother I woullda had you killed the day we found you! " +
                                                "You have no idea how lucky you are.\"",
                                
                                        "There she is again, mother. Without thinking about it you blurt out in a desperate tone, \"Who is mother!?\"",
                                
                                        "#r \"You don't need to worry about that...now just stay in that cell an look pretty will ya?\", Sam says.",
                                
                                        "After Sam just angrily stares at you for a few seconds he turns around and starts heading back up the stairs " +
                                                "again slamming the door behind him."},
                        
                                "sam leaves");
                
                        //Force the player to look north (Sam is standing at the gate)
                        mainGame.playerData.directionLooking = "North";
                
                        //Set some crucial variables for later use
                        didPlayerYell = true;
                        isSamDownstairs = true;
                    } else {
                        //The player has already screamed so don't let them do this again
                        mainGame.addToGameLog ("You don't think annoying Sam again would help you at this time");
                    }
            
                } else if (userInput.contains ("room") || userInput.contains ("surroundings") || userInput.contains ("location")) {
            
                    //See what te user is trying to do exactly
                    if (checkLookSynonyms (userInput)) {
                
                        //The user is trying to get the description of the room
                        mainGame.addToGameLog (roomDescription);
                
                    } else {
                
                        //Tell the player they can't do that
                        mainGame.addToGameLog ("I don't know how to do that.");
                
                    }
            
                } else if (checkMovementSynonyms (userInput)) {
            
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
                    //No update the direction the player is looking in the player data even if it wasn't changed
                    changePlayersDirection (mainGame.playerData);
            
                    //Now after checking all of this if nothing happens see if the user is trying to do something based on the side they're facing
                } else if (currentDirection == 0) {
            
                    //Try to figure out what the player is trying to do
                    if (checkLookSynonyms (userInput) || userInput.contains ("area")) {
                
                        //The user is trying to get the description of the side of the room they're looking at
                        mainGame.addToGameLog (northSideDescription);
                
                    } else {
                
                        //Tell the player they can't do that
                        mainGame.addToGameLog ("I don't know how to do that.");
                
                    }
            
                } else if (currentDirection == 1) {
            
                    //Try to figure out what the player is trying to do
                    if (userInput.contains ("mattress") || userInput.contains ("bed")) {
                        //The user is trying to do something with the mattress so find out what they're trying to do
                        if (checkLookSynonyms (userInput)) {
                            //The user is trying to examine the mattress now see if they're trying to look under it
                            if (userInput.contains ("under") || userInput.contains ("beneath") || userInput.contains ("bottom")) {
                        
                                //Only do this if the player doesn't already have the notes
                                if (!GameItems.playerNoteOne.playerHasItem) {
                                    //Write to the gamelog that the player looks under the mattress and give them the specific items
                                    //that are hidden under it
                                    mainGame.addToGameLog ("You look under your mattress and a slight feeling of peace greats you " +
                                            "as you see all the different trinkets and papers you've accumulated since you've been here. You pick up the " +
                                            "papers, fold them, and put them in your pocket.");
                                    mainGame.addToGameLog ("#g Entries added to your journal!");
                            
                                    //Now add the messages to the player journals
                                    mainGame.playerData.journalEntries.add (GameItems.playerNoteOne);
                                    GameItems.playerNoteOne.playerHasItem = true;
                                    mainGame.playerData.journalEntries.add (GameItems.playerNoteTwo);
                                    GameItems.playerNoteTwo.playerHasItem = true;
                                    mainGame.playerData.journalEntries.add (GameItems.playerNoteThree);
                                    GameItems.playerNoteThree.playerHasItem = true;
                                } else {
                                    //The player already looked under the bed
                                    mainGame.addToGameLog ("There's no need to look under the bed again.");
                                }
                            } else {
                        
                                //Write to the gamelog the description of the mattress
                                mainGame.addToGameLog ("It's the disgusting bug and stain ridden mattress you've been forced to sleep on " +
                                        "since you were kidnapped. But even then you still find yourself coming back to it for safety because of everything " +
                                        "underneath it you hide.");
                            }
                        }
                
                    } else if (userInput.contains ("wall")) {
                        //The user is trying to do something with the wall on this side
                        if (checkLookSynonyms (userInput)) {
                            //The user is trying to inspect the wall so write to the gamelog the description of the wall
                            mainGame.addToGameLog ("On the wall you see a bunch of different drawings of random people, " +
                                    "objects, areas, and other things that you draw when you get bored. You also see in its own area a " +
                                    "large number of tallies that you've been scratching out counting the number of days you've been " +
                                    "here. After counting them all you total a number of 42 tallies.");
                        }
                
                    } else if (checkLookSynonyms (userInput) || userInput.contains ("area")) {
                        //The user is trying to get the description of the side of the room they're looking at
                        mainGame.addToGameLog (eastSideDescription);
                
                    } else {
                        //Tell the player they can't do that
                        mainGame.addToGameLog ("I don't know how to do that.");
                
                    }
            
                } else if (currentDirection == 2) {
            
                    //Try to figure out what the player is trying to do
                    if (checkLookSynonyms (userInput) || userInput.contains ("area")) {
                
                        //The user is trying to get the description of the side of the room they're looking at
                        mainGame.addToGameLog (southSideDescription);
                
                    } else {
                
                        //Tell the player they can't do that
                        mainGame.addToGameLog ("I don't know how to do that.");
                
                    }
            
                } else if (currentDirection == 3) {
            
                    //Try to figure out what the player is trying to do
                    if (checkLookSynonyms (userInput) || userInput.contains ("area")) {
                
                        //The user is trying to get the description of the side of the room they're looking at
                        mainGame.addToGameLog (westSideDescription);
                
                    } else {
                
                        //Tell the player they can't do that
                        mainGame.addToGameLog ("I don't know how to do that.");
                
                    }
            
                }
            }
        }
    }

    //This function will check if the users input has anything to do with looking at something
    private boolean checkLookSynonyms (String userInput)
    {
        //Check to see if the users input has any words that are synonyms to look
        if (userInput.contains ("examine") || userInput.contains ("look") || userInput.contains ("looking") || userInput.contains ("review") || userInput.contains ("inspect")
                || userInput.contains ("gander") || userInput.contains ("observe") || userInput.contains ("survey") || userInput.contains ("see")) {
            return true;
        } else {
            return false;
        }
    }
    
    //This function will check if the users input has anything to do with movement
    private boolean checkMovementSynonyms (String userInput)
    {
        if (userInput.contains ("turn") || (userInput.contains ("change") && userInput.contains ("direction")) || userInput.contains ("rotate")
                || userInput.contains ("face")) {
            return true;
        } else {
            return false;
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
    
    //This method will change the players direction they're looking in the player data
    private void changePlayersDirection (PlayerData playerData)
    {
        if (currentDirection == 0)
            playerData.directionLooking = "North";
        else if (currentDirection == 1)
            playerData.directionLooking = "East";
        else if (currentDirection == 2)
            playerData.directionLooking = "South";
        else if (currentDirection == 3)
            playerData.directionLooking = "West";
    }
}
