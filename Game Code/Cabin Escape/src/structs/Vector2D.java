package structs;

public class Vector2D {
    
    public int x = 0;
    public int y = 0;
    
    public Vector2D () {}
    
    public Vector2D (int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public static Vector2D zero ()
    {
        return new Vector2D (0, 0);
    }
    
    public static Vector2D one ()
    {
        return new Vector2D (1, 1);
    }
    
    public boolean equals (Vector2D e)
    {
        if (x == e.x && y == e.y)
            return true;
        else
            return false;
    }
}