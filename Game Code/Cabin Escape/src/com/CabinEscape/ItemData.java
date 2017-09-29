package com.CabinEscape;

public class ItemData {
    
    /*
     * This class will be the skeleton for all the items within the game.
     * This class will hold the following data:
     *      Item UID
     *      Item name
     *      Item description
     *      Item type (the specific type of item this is ex. Key, note, etc...)
     *      If the player has/had this item
     *      If this item can be combined
     *      What item this can be combined with
     *      What item is gained by combining
     */
    
    //All the possible types of items in the game
    public enum ItemType {
        AMMO,
        DOOR_KEY,
        FOOD,
        MISC,
        NOTE,
        TOOL,
        WEAPON
    }
    
    public String UID;                  //The items unique identifier
    public String itemName;             //The items in game name
    public String itemDescription;      //The items description that will be displayed if ever examined
    public String messageText;          //The text to display on the screen if the player reads it
    public String itemToCombineWith;    //The UID of the item this can be combined with
    public String combinationOutput;    //The UID of the item the player gets by combining this item
    
    public ItemType itemType;           //The type of item this is
    
    public boolean playerHasItem;       //Stores if the player currently has the item in their inventory
    public boolean playerHadItem;       //Stores if the player has previously had this item before
    public boolean canBeCombined;       //Stores whether or not this item can be combined with anything
    
    //Create all the needed constructors for this class
    
    //The most basic item
    public ItemData (String UID, String itemName, String itemDescription, ItemType itemType)
    {
        this.UID = UID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.messageText = "";
        this.itemToCombineWith = "";
        
        this.itemType = itemType;
        
        this.playerHasItem = false;
        this.playerHadItem = false;
        this.canBeCombined = false;
    }
    
    //An item that has a message to be displayed
    public ItemData (String UID, String itemName, String itemDescription, String messageText, ItemType itemType)
    {
        this.UID = UID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.messageText = messageText;
        this.itemToCombineWith = "";
        
        this.itemType = itemType;
        
        this.playerHasItem = false;
        this.playerHadItem = false;
        this.canBeCombined = false;
    }
    
    //A basic item the player has
    public ItemData (String UID, String itemName, String itemDescription, ItemType itemType, boolean playerHasItem)
    {
        this.UID = UID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.messageText = "";
        this.itemToCombineWith = "";
    
        this.itemType = itemType;
    
        this.playerHasItem = playerHasItem;
        this.playerHadItem = playerHasItem;
        this.canBeCombined = false;
    }
    
    //A basic item the player has or had
    public ItemData (String UID, String itemName, String itemDescription, ItemType itemType, boolean playerHasItem, boolean playerHadItem)
    {
        this.UID = UID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.messageText = "";
        this.itemToCombineWith = "";
        
        this.itemType = itemType;
        
        this.playerHasItem = playerHasItem;
        this.playerHadItem = playerHadItem;
        this.canBeCombined = false;
    }
    
    //An item that can be combined and the player might have/had
    public ItemData (String UID, String itemName, String itemDescription, boolean canBeCombined, String itemToCombineWith, String combinationOutput, ItemType itemType, boolean playerHasItem, boolean playerHadItem)
    {
        this.UID = UID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.messageText = "";
        this.itemToCombineWith = itemToCombineWith;
        this.combinationOutput = combinationOutput;
        
        this.itemType = itemType;
        
        this.playerHasItem = playerHasItem;
        this.playerHadItem = playerHadItem;
        this.canBeCombined = canBeCombined;
    }
}
