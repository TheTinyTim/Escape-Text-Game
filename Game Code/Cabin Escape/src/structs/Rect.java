package structs;

public class Rect {
    
    //The variables used in this struct
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    
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
    
    //Getters and setters
    public int getX ()
    {
        return x;
    }
    
    public void setX (int x)
    {
        this.x = x;
    }
    
    public int getY ()
    {
        return y;
    }
    
    public void setY (int y)
    {
        this.y = y;
    }
    
    public int getWidth ()
    {
        return width;
    }
    
    public void setWidth (int width)
    {
        this.width = width;
    }
    
    public int getHeight ()
    {
        return height;
    }
    
    public void setHeight (int height)
    {
        this.height = height;
    }
}
