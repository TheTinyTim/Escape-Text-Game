package com.menus;

import com.CabinEscape.GameMain;
import com.CabinEscape.GameRendering;
import com.CabinEscape.GameSettings;
import com.asciiPanel.AsciiPanel;
import com.rooms.PlayerCell;
import com.structs.Rect;
import com.structs.Vector2D;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MainGameHandler {
    
    //These are all the variables that are used within every menu
    private GameSettings gameSettings;  //This stores all the settings for the game
    private AsciiPanel gameTerminal;    //This is the terminal that will display everything for the game
    private GameMain gameMain;          //This is the main class for the game that will handle updating the gui and what not
    
    //Room variables
    private String currentRoom = "Player Cell";
    private PlayerCell playerCellRoom;
    //------------------GUI variables------------------\\
    //These variables are needed to draw the game game log segment
    private Rect gameLogBorder;                                     //The border for the game log and its content
    private ArrayList<String> gameLog = new ArrayList<String> ();   //All the data that will be displayed on the game log

    //These variables are needed to draw the users input segment
    private Rect userInputBorder;   //The border where the player will type everything
    public String userInput = "";   //What the player has currently typed

    //These variables are needed to draw the inventory segment
    private Rect inventoryBorder;   //The inventories border

    //These variables are needed to draw and handle the pause menu
    private ArrayList<String> pauseMenuButtons = new ArrayList<String> ();  //All the buttons that go on the pause menu
    private Vector2D pauseMenuButtonPos;                                    //The location of the pause menu buttons
    private int selectedMenuButton = 0;                                     //The button that's currently being hovered over
    private Rect pauseMenuBorder;                                           //The pause menus border
    private boolean pauseMenuOpen = false;                                  //Stores if the pause menu is open or not
    public boolean animatePauseMenu = false;                                //Stores if the pause menu needs to be animated open
    public boolean hidePauseMenu = false;                                   //Stores if the pause menu needs to be animated closed
   
    //These variables are needed to draw and handle the message window
    private String message = "Hello there!";                            //The message to be displayed in the menu
    private ArrayList<String> messageParts = new ArrayList<String> ();  //The parts of a message if it needs the player to press space to continue it
    private boolean animateMessageWindow = false;                       //Stores if the message window needs to be animated open or not
    private boolean hideMessageWindow = false;                          //Stores if the message window needs to be animated closed or not
    private boolean displayMessage = false;                             //Stores if the message window needs to be shown or not
    private boolean showMessageCloseButton = true;
    private int segmentedMessageSpot = 0;                               //What the current spot is for the segmented message
    private Rect messageBorder;                                         //The messages border
 
    //These variables are needed to help with animating different windows
    private Rect animatedMenuRect;  //The Rect that stores the information for the being animated at the time
 
    
    //The main constructor of this class that will set up all the needed variables for GUI
    public MainGameHandler (GameSettings gameSettings, AsciiPanel gameTerminal, GameMain gameMain)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        this.gameMain = gameMain;
        
        //Set up the variables for the GUI
        gameLogBorder = new Rect (0,
                                  0,
                                  gameSettings.gameWindowWidth - 22,
                                  gameSettings.gameWindowHeight - 3);
        
        userInputBorder = new Rect (0,
                                    gameLogBorder.height,
                                    gameSettings.gameWindowWidth - 22,
                                    3);
        
        inventoryBorder = new Rect (gameLogBorder.width,
                                    0,
                                    22,
                                    gameSettings.gameWindowHeight);
        
        pauseMenuBorder = new Rect ((gameSettings.gameWindowWidth / 2) - 12,
                                    (gameSettings.gameWindowHeight / 2) - 5,
                                    25,
                                    13);
        
        pauseMenuButtons.add ("Save Game");
        pauseMenuButtons.add ("Load Game");
        pauseMenuButtons.add ("Settings");
        pauseMenuButtons.add ("Back");
        pauseMenuButtons.add ("Exit Game");
        
        pauseMenuButtonPos = new Vector2D (pauseMenuBorder.x + ((pauseMenuBorder.width / 2) - 4), pauseMenuBorder.y + ((pauseMenuBorder.height / 2) - 4));
        
        messageBorder = new Rect (30, 5, gameSettings.gameWindowWidth - 60, gameSettings.gameWindowHeight - 10);
        
        //Now load all the rooms within the game
        playerCellRoom = new PlayerCell (gameSettings, this);
    }
    
    //----------------------User Input Handling----------------------\\
    //This will be called whenever the user presses the escape button (mainly used to show/hide the pause menu)
    public void escapePressed ()
    {
        setupPauseMenuAnimation ();
        selectedMenuButton = 0;
    }
    
    //Function that makes sure the users input isn't longer then the border
    public boolean checkUserInputLength ()
    {
        int inputMax = userInputBorder.width - ("> ".length () + 2);
        
        if (userInput.length () != inputMax)
            return true;
        
        return false;
    }
    
    //Create the function that will handle changing the selected button
    public void changeSelectedButton (int change)
    {
        //Change the selection variable based on the change
        selectedMenuButton += change;
        
        //Now check if the selection has gone over/under the max/min
        if (selectedMenuButton < 0)
            selectedMenuButton = pauseMenuButtons.size () - 1;
        else if (selectedMenuButton > pauseMenuButtons.size () - 1)
            selectedMenuButton = 0;
    }
    
    public void activateSelectedButton ()
    {
        //Only open these if the pause menu is open
        if (pauseMenuOpen) {
            //Check to see what button is currently selected
            if (selectedMenuButton == 0) {
                //Display the save game menu
                gameMain.changeMenu (GameMain.Menu.SAVE);
            } else if (selectedMenuButton == 1) {
                //Display the load game menu
                gameMain.changeMenu (GameMain.Menu.LOAD);
            } else if (selectedMenuButton == 2) {
                //Display the settings game menu
                gameMain.changeMenu (GameMain.Menu.SETTINGS);
            } else if (selectedMenuButton == 3) {
                //Close the pause menu
                setupPauseMenuAnimation ();
            } else if (selectedMenuButton == 4) {
                //Close the pause menu
                pauseMenuOpen = false;
                //Tell the program the game won't be running any more
                gameMain.gameIsGoing = false;
                //Display the main menu
                gameMain.changeMenu (GameMain.Menu.MAIN);
            }
        }
    }
    
    public void enterPressed ()
    {
        //Check to see if the message window is open
        if (displayMessage) {
            //Only do this code if the messages close button is showing
            if (showMessageCloseButton) {
                //If it is open, close it
                displayMessage = false;                     //Don't let the message window display anymore
                hideMessageWindow = true;                   //Let the program know that it should animate the message window close
                gameMain.setUpdateTerminalTimer (true);     //Have the program continually loop through the drawGUI method
                animatedMenuRect = messageBorder.clone ();  //Clone the message borders rect so the animated window know where it starts
    
                //Check to see if the game is still set as a new game.
                if (gameSettings.isNewGame) {
                    //Make sure to tell the program it is no longer a new game so it doesn't show the new game message
                    gameSettings.isNewGame = false;
                }
            }
        } else {
            //Get the current room the player is in and what ever it is check to see if this input will do anything
            if (currentRoom == "Player Cell")
                playerCellRoom.checkUserInput (userInput);
            
            //Now remove everything from the users input
            userInput = "";
        }
    }
    
    public void keyPressed (KeyEvent event)
    {
        //Check to see if the user is trying to pause the menu or not
        if (event.getKeyChar () == KeyEvent.VK_ESCAPE) {
            //Pause the game
            escapePressed ();
            //Check to see if the user is tyring to press the enter key
        } else if (event.getKeyChar () == KeyEvent.VK_ENTER) {
            enterPressed ();
            //Check to see if the user is trying to delete a character from what they typed.
        } else if (event.getKeyChar () == KeyEvent.VK_BACK_SPACE) {
            if (!userInput.equals (""))
                userInput = userInput.substring (0, userInput.length () - 1);
        } else if (!event.isActionKey () && event.getKeyChar () != KeyEvent.VK_ENTER) {
            //See if the message window is open to record when the user presses the space bar
            if (displayMessage) {
                if (event.getKeyChar () == KeyEvent.VK_SPACE) {
                    if (messageParts.size () != 0 && messageParts.size () - 1 > segmentedMessageSpot) {
                        segmentedMessageSpot++;
                        //Tell the program to re draw the gui
                        gameMain.reDrawGUI = true;
                    }
                }
            //First make sure that the user string isn't longer then how many characters can fit into the border
            } else if (checkUserInputLength ()) {
                //Now only write something to the user input if the user is either not holding the shift key or when they press the shift key while pressing another key
                if (event.isShiftDown () && event.getKeyCode () != 16) {
                    userInput += event.getKeyChar ();
                } else if (!event.isShiftDown ()) {
                    userInput += event.getKeyChar ();
                }
            }
        }
    }
    
    //-----------------------------Everything GUI related-----------------------------\\
    
    //This is the main method that handles everything that needs to be draw to the terminal for this menu
    public void drawGUI ()
    {
        //First draw all the borders for the game
        drawGameSegments ();
        
        //Now draw the the message window if there is one being displayed
        drawMessageWindow ();
        
        //Finally draw the pause menu if one is trying to open/closed or if it's already open
        drawPauseMenu ();
        
        //Check to see if this is a new game
        if (gameSettings.isNewGame) {
            //If it is display the beginning game message
            if (!displayMessage && !animateMessageWindow) {
                displayMessage ();
                //Add the strings to message parts
                messageParts.clear ();
                messageParts.add ("You wake up to a loud crash. Then shortly after loud screaming, it's an argument. This is all too familiar.");
                messageParts.add ("\\n You look around and see the same thing you've seen every time you've awoken for the past few weeks...or is it months?");
                messageParts.add ("\\n You scratch your head in confusion. You're not really sure how long it's been since you were kidnapped. Everything has just been a blur ever since.");
                messageParts.add ("\\n All of a sudden you snap back from your thoughts and you hear someone yelling, \"You know what!? I don't fucking care! I'll be back later.\" " +
                        "then a loud slam of what you could only imagine being a door.");
                messageParts.add ("\\n You start wondering what all of that was about. But quickly shrug it off and realise it hopefully means some time to yourself.");
                messageParts.add ("\\n After all of that everything just goes quite. Even if only one of the two left it still gives you a sense of security. Which is something you crave even if it lasts for just a moment.");
            }
            
            //Set the message to show based on the segmented message spot
            message = "";
            for (int i = 0; i < segmentedMessageSpot + 1; i++) {
                if (i != 0)
                    message += " ";
                message += messageParts.get (i);
            }
            
            //Find out if this is the last line to be shown
            if (segmentedMessageSpot < messageParts.size () - 1) {
                //If it isn't write onto the terminal to prompt the player to press space to continue the message
                message += " \\n Press Space To Continue...";
                //Don't allow the player to close the message window
                showMessageCloseButton = false;
            } else {
                //Player has exhausted all the dialogue so let them close the message window
                showMessageCloseButton = true;
            }
        }
    }
    
    //This will draw all the different segments in the game menu like the game log and inventory screens
    private void drawGameSegments ()
    {
        //---------------Draw everything needed for the game log---------------\\
        GameRendering.drawBorder (gameLogBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Game Log");
        
        //Now draw the actual text that goes into the game log
        drawGameLogText ();
    
        //---------------Draw everything needed for the players input---------------\\
        GameRendering.drawBorder (userInputBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Input");
    
        //Draw what the user has typed
        gameTerminal.write ("> " + userInput,
                userInputBorder.x + 1,
                userInputBorder.y + 1,
                AsciiPanel.green);
    
        //---------------Draw everything needed for the players inventory---------------\\
        GameRendering.drawBorder (inventoryBorder,
                                  gameTerminal,
                                  AsciiPanel.white,
                                  null,
                                  "Inventory");
    }
    
    //This will draw all the log text
    private void drawGameLogText ()
    {
        //Set up all the variables needed for the loop
        int xPos = gameLogBorder.x + 3;
        int yPos = gameLogBorder.y + 1;
        int maxLineCharacterLength = gameLogBorder.width - 4;
        String lineToAdd = "";
        String wordToAdd = "";
        boolean drawLine = false;
        char bulletPoint = 254;
        //Go through all the items in the game log array and write them to the terminal
        for (int i = 0; i < gameLog.size (); i++)
        {
            //Get the current line that needs to be written to the terminal and split it by the spaces
            String[] currLine = gameLog.get (i).split (" ");
            //Draw the bullet point for this line to separate the liens
            gameTerminal.write (bulletPoint, xPos, yPos);
            
            //Now loop through all the words in this line
            for (int wordIndex = 0; wordIndex < currLine.length; wordIndex++) {
                //First off check to see if the current line should be drawn based on it's length
                if (drawLine || lineToAdd.length () > maxLineCharacterLength) {
                    //First write the line to the terminal
                    gameTerminal.write (lineToAdd, xPos + 2, yPos);
                    //Now add on to the y position for the next line
                    yPos++;
                    //Make sure to reset the line to add
                    lineToAdd = "";
                    //Same for the draw line boolean
                    drawLine = false;
                    
                    //Now check to see if there was a word that would have been skipped because a line being too long
                    if (!wordToAdd.equals ("")) {
                        //Add this word to the next line to be added
                        lineToAdd += wordToAdd + " ";
                        //And reset the word to add
                        wordToAdd = "";
                    }
                }
                
                //Now find out if the next word in the array should be added to the current line or if the line
                //would be larger then the max with this word added to it.
                if ((lineToAdd.length () + currLine[wordIndex].length() + " ".length ()) < maxLineCharacterLength) {
                    //Add this word to the line
                    lineToAdd += currLine[wordIndex] + " ";
                } else {
                    //Make sure the current word will be added to the next line
                    wordToAdd = currLine[wordIndex];
                    //And tell the program to write this line
                    drawLine = true;
                }
            }
            
            //Make sure to write the last line
            gameTerminal.write (lineToAdd, xPos + 2, yPos);
            //Now add on to the y position for the next line
            yPos++;
            //Make sure to reset the line to add
            lineToAdd = "";
            //Same for the draw line boolean
            drawLine = false;
        }
    }
    
    //This will allow the program to add text to the game log
    public void addToGameLog (String gameLogOutput)
    {
        gameLog.add (gameLogOutput);
    }
    
    //---------------Displaying Message Related Functions---------------\\
    //This will draw the message window if there is one that's being opened/closed/displayed
    private void drawMessageWindow ()
    {
        //Check to see if there is supposed to be a message to display
        if (animateMessageWindow) {
            if (GameRendering.displayAnimatedBorder (messageBorder, animatedMenuRect, gameTerminal, 2, "Message", false)) {
                animateMessageWindow = false;
                displayMessage = true;
                gameMain.setUpdateTerminalTimer (false);
            }
        }
    
        //Check to see if the message window should be hidden
        if (hideMessageWindow) {
            if (GameRendering.hideAnimatedBorder (messageBorder, animatedMenuRect, gameTerminal, 2, "Message", false)) {
                hideMessageWindow = false;
                gameTerminal.clear (' ',
                        animatedMenuRect.x,
                        animatedMenuRect.y,
                        animatedMenuRect.width,
                        animatedMenuRect.height);
                gameMain.setUpdateTerminalTimer (false);
                drawGUI ();
            }
        }
    
        //Now draw the message if it's opened
        if (displayMessage) {
            gameTerminal.clear (' ', messageBorder.x, messageBorder.y, messageBorder.width, messageBorder.height);
            GameRendering.displayMessage (messageBorder, "Message", message, gameMain, gameTerminal, showMessageCloseButton, null, null);
        }
    }
    
    //This will set everything up needed to animate the message window
    public void displayMessage ()
    {
        if (!hideMessageWindow && !animateMessageWindow) {
            if (!displayMessage) {
                animateMessageWindow = true;
                gameMain.setUpdateTerminalTimer (true);
                animatedMenuRect = new Rect (messageBorder.x + (messageBorder.width / 2),
                        messageBorder.y + (messageBorder.height / 2),
                        2,
                        2);
            }
        }
    }
    
    //---------------Displaying The Pause Menu Related---------------\\
    //This will draw the pause menu if it's being opened/closed/displayed
    private void drawPauseMenu ()
    {
        //Check to see if the user is trying to open the pause menu. If so animate it to open
        if (animatePauseMenu) {
            if (GameRendering.displayAnimatedBorder (pauseMenuBorder, animatedMenuRect, gameTerminal, 1, "Pause Menu", false)) {
                pauseMenuOpen = true;
                animatePauseMenu = false;
                gameMain.setUpdateTerminalTimer (false);
            }
        }
    
        //Check to see if the pause menu should be hidden
        if (hidePauseMenu) {
            if (GameRendering.hideAnimatedBorder (pauseMenuBorder, animatedMenuRect, gameTerminal, 1, "Pause Menu", false)) {
                hidePauseMenu = false;
                gameTerminal.clear (' ',
                        animatedMenuRect.x,
                        animatedMenuRect.y,
                        animatedMenuRect.width,
                        animatedMenuRect.height);
                gameMain.setUpdateTerminalTimer (false);
                drawGUI ();
            }
        }
    
        //Draw the pause menu if it's open
        if (pauseMenuOpen) {
            //Clear the area the menu will go in
            gameTerminal.clear (' ', pauseMenuBorder.x, pauseMenuBorder.y, pauseMenuBorder.width, pauseMenuBorder.height);
        
            //Draw the menus border
            GameRendering.drawBorder (pauseMenuBorder,
                    gameTerminal,
                    AsciiPanel.white,
                    null,
                    "Pause Menu");
        
            //Draw the menu buttons
            GameRendering.drawButtons (pauseMenuButtonPos.clone (),
                    pauseMenuButtons,
                    gameTerminal,
                    selectedMenuButton,
                    2,
                    AsciiPanel.yellow,
                    true,
                    true);
        }
    }
    
    //This will handle setting all the needed variables to animate the pause menu open/closed
    public void setupPauseMenuAnimation ()
    {
        //Find out if the pause menu is being opened or closed
        if (!pauseMenuOpen) {
            animatePauseMenu = true;
            animatedMenuRect = new Rect (pauseMenuBorder.x + (pauseMenuBorder.width / 2),
                    pauseMenuBorder.y + (pauseMenuBorder.height / 2),
                    2,
                    2);
        } else {
            hidePauseMenu = true;
            pauseMenuOpen = false;
            animatedMenuRect = pauseMenuBorder.clone ();
        }
        gameMain.setUpdateTerminalTimer (true);
    }
}
