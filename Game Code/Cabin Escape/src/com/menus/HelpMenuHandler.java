package com.menus;

import com.CabinEscape.GameMain;
import com.CabinEscape.GameRendering;
import com.CabinEscape.GameSettings;
import com.asciiPanel.AsciiPanel;
import com.structs.Rect;
import com.structs.Vector2D;

import java.awt.event.KeyEvent;

public class HelpMenuHandler {
    
    //These are all the variables that are used within every menu
    private GameSettings gameSettings;  //This stores all the settings for the game
    private AsciiPanel gameTerminal;    //This is the terminal that will display everything for the game
    private GameMain gameMain;          //This is the main class for the game that will handle updating the gui and what not
    
    //-------------------GUI Variables-------------------\\
    //Help text variables
    private String helpMessage;
    private Rect messageBorder;
    
    public HelpMenuHandler (GameSettings gameSettings, AsciiPanel gameTerminal, GameMain gameMain)
    {
        this.gameSettings = gameSettings;
        this.gameTerminal = gameTerminal;
        this.gameMain = gameMain;
        
        //Set up the rect position of the message border
        messageBorder = new Rect (10,
                                    3,
                                    60,
                                    gameSettings.gameWindowHeight - 5);
        
        //Set up the actual message that will be displayed
        helpMessage = "In this game you take control of the main character trying to escape from their captives. You do this by telling the character what to do with specific words." +
                " \\n Each room is comprised of 4 different sides; North, East, South, and West. You can have your character face these directions with specific words like \"Turn East\" " +
                "or \"Turn Left\"/\"Turn Right\". In the upper right corner of the screen in the \"Map\" area you'll see what current direction you're facing as well as where you're at in " +
                "the cabin at the given time. \\n Throughout the game there will be various items scattered around the many different rooms that you'll need to pick up to progress in the " +
                "game. So for example if there was a screwdriver in one of the rooms and you wanted to pick it up you could tell the character \"Pick up the screwdriver\" and if it's " +
                "something you can pick up it will say so in the Game Log and place it in your Inventory. \\n It is extremely important that you pay close attention to the environment " +
                "you're currently in to find items. In order to do this every room and side of the room you can get a description of what the character is looking at. If you want to do " +
                "this tell your character to \"Inspect the area you're in\" or \"What do you currently see?\" to get the descriptions. Now pay close attention to these descriptions as " +
                "they'll reveal some key locations and items in the room for you to inspect. \\n There's a lot to see so make sure you pay attention and use specific wording so the " +
                "character knows what you're telling them what to do. Good luck and have fun!";
    }
    
    //-------------------Key Input Section-------------------\\
    public void keyPressed (KeyEvent event)
    {
        //Check to see if the user presses the escape or enter key to close the menu
        if (event.getKeyChar () == KeyEvent.VK_ENTER || event.getKeyChar () == KeyEvent.VK_ESCAPE)
            gameMain.changeMenu (GameMain.Menu.GAME);
    }
    
    //-------------------GUI Section-------------------\\
    public void drawGUI ()
    {
        //First draw the title for the menu
        drawTitles ();
        
        //Now draw the help message
        drawMessage ();
    }
    
    private void drawTitles ()
    {
        GameRendering.drawMenuTitle (new Vector2D (gameSettings.gameWindowWidth - GameRendering.titleLength ("Help"), 5),
                                     "Help",
                                     gameTerminal,
                                     AsciiPanel.red);
    }
    
    private void drawMessage ()
    {
        GameRendering.displayMessage (messageBorder,
                                        "",
                                        helpMessage,
                                        gameMain,
                                        gameTerminal,
                                        true,
                                        null,
                                        null);
    }
}
