package com.CabinEscape;

import java.util.ArrayList;

public class PlayerData {
    /*
     * This class will hold all the players data regarding:
     *      Their inventory
     *      Their progress in the game
     *
     * All of the users data (save file) will be stored as an xml file that will be encrypted so the
     * player can't easily edit any of the data
     *      How to parse xml: https://stackoverflow.com/questions/5059224/which-is-the-best-library-for-xml-parsing-in-java
     *      How to encrypt/decrypt: https://stackoverflow.com/questions/8395877/encrypting-and-decrypting-xml
     */
     
    public String directionLooking = "North";   //The current direction the player is looking
    
    public ArrayList<ItemData> journalEntries = new ArrayList<ItemData> ();
    public ArrayList<ItemData> inventory = new ArrayList<ItemData> ();
    
    //Add the method that will handle adding items to the players inventory
    public void addItemToInventory (ItemData item)
    {
        item.playerHasItem = true;
        item.playerHadItem = true;
        inventory.add (item);
    }
    
    //The method that will remove an item from the players inventory that they've used
    public void removeItemFromInventory (ItemData item)
    {
        item.playerHasItem = false;
        inventory.remove (item);
    }
    
}
