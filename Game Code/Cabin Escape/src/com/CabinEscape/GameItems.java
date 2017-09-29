package com.CabinEscape;

public class GameItems {
    
    //This class will hold and create all of the items in the game
    
    //All the items that will be inside the player cell room
    
    //North side items
    
    
    //East side items
    public static ItemData playerNoteOne =
            new ItemData ("player_note_one", "I can't belive this",
                    "A hand written note",
                    "I can't believe this. I can't belive this happened to me. It all happened so fast, I was out " +
                            "on a camping trip when they attacked me. I had no time to react, it's like they've been watching " +
                            "me these past few weeks planning it all out...Oh what's the point anymore, this speculation doesn't even matter " +
                            "they've already captured me. At least they didn't find this pen in my pockets. Maybe I can try to keep " +
                            "track of everything in case someone finds this place.",
                    ItemData.ItemType.NOTE);
    
    public static ItemData playerNoteTwo =
            new ItemData ("player_note_two", "What's taking them so long",
                    "A hand written note",
                    "It's been a week since the last time I've written anything and they still haven't killed me! " +
                            "Any time they're down here they're either bringing me \"food\" or just grabbing something. " +
                            "Every now and then I catch something about \"mother\" but it's so hard to understand them because " +
                            "all they do is mumble. Whatever it is they apparently have a different reason for keeping me here " +
                            "then just to kill me. Maybe because they're taking so long it will give me some time to escape. " +
                            "I'll just have to find out how.",
                    ItemData.ItemType.NOTE);
    
    public static ItemData playerNoteThree =
            new ItemData ("player_note_three", "I'm going insane",
                    "A hand written note",
                    "I've been trying to keep track of time by scratching the amount of days in the wall but " +
                            "there must be something wrong. Based on my marks it's been over a month! That can't be right! " +
                            "The days have been going by like a blur, my days are all blending together. I'm barely being fed " +
                            "and when they do bring food it's the most vile thing I've ever had but it's food. " +
                            "\\n I'm not sure why I'm even trying anymore. Why won't I just give up and die? Is " +
                            "it because I feel like I can escape from this hell or am I just curious why they've " +
                            "been keeping me alive for so long and who this \"mother\" is? I don't know maybe I just " +
                            "wont keep eating and starve my self to death...",
                    ItemData.ItemType.NOTE);
    
    //South side items
    
    
    //West side items
    public static ItemData cellBucket =
            new ItemData ("cell_bucket", "Dirty Bucket",
                  "A bucket filled with...waste. You're not sure why you're carrying it.",
                  ItemData.ItemType.MISC);
        
    
}
