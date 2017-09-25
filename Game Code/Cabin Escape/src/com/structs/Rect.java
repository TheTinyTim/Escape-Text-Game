package com.structs;

public class Rect {
    
    //The variables used in this struct
    public int x = 0;
    public int y = 0;
    public int width = 0;
    public int height = 0;
    
    //The constructors of the class
    
    public Rect () {
    
    }
    
    public Rect (int x) {
        this.x = x;
    }
    
    public Rect (int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Rect (int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }
    
    public Rect (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Rect (Vector2D pos) {
        this.x = pos.x;
        this.y = pos.y;
    }
    
    public Rect (Vector2D pos, int width, int height) {
        this.x = pos.x;
        this.y = pos.y;
        this.width = width;
        this.height = height;
    }
    
    public Rect (int x, int y, Vector2D size) {
        this.x = x;
        this.y = y;
        this.width = size.x;
        this.height = size.y;
    }
    
    public Rect (Vector2D pos, Vector2D size) {
        this.x = pos.x;
        this.y = pos.y;
        this.width = size.x;
        this.width = size.y;
    }
    
    //Getters and setters
    public Vector2D getPos ()
    {
        return new Vector2D (x, y);
    }
    
    public void setPos (Vector2D pos)
    {
        this.x = pos.x;
        this.y = pos.y;
    }
    
    public Vector2D getSize ()
    {
        return new Vector2D (width, height);
    }
    
    public void setSize (Vector2D size)
    {
        this.width = size.x;
        this.height = size.y;
    }
    
    //Create the functions for the rect class
    
    //This will return if a vector2d position is within the Rect
    public boolean containsPoint (Vector2D point)
    {
        //Get the necessary variables/values to check if the point is in the Rect
        int xMin = x;
        int yMin = y;
        int xMax = x + width;
        int yMax = y + height;
        
        //Now check the point is within all these restraints
        if (point.x >= xMin && point.x <= xMax && point.y >= yMin && point.y <= yMax) {
            return true;
        } else {
            return false;
        }
    }
    
    //Check to see if this rect is the same as the passed rect
    public boolean equals (Rect rect)
    {
        if (x == rect.x && y == rect.y && width == rect.width && height == rect.height)
            return true;
        else
            return false;
    }
    
    //Clone this rect
    public Rect clone ()
    {
        return new Rect (this.x, this.y, this.width, this.height);
    }
}
