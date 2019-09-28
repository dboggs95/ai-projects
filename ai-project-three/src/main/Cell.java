package main;

//import java.util.ArrayList;

public class Cell
{

	private int x;
    private int y;
    private int value;
    private boolean INITIAL_CELL;
    
	public Cell(int x, int y)
	{
		this.value = 0;
		this.x = x;
        this.y = y;
        INITIAL_CELL = false;
	}
    
	public Cell(int x, int y, int val)
	{
		this.value = val;
		this.x = x;
        this.y = y;
        INITIAL_CELL = true;
	}
	
	public void setX(int newX)
	{
		x = newX;
	}
	
	public void setY(int newY)
	{
		y = newY;
	}
	
	public void setValue(int val)
	{
		if(INITIAL_CELL)
		{
			System.out.println("Error: Cannot change initial cell.");
			return;
		}
		this.value = val;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getValue()
	{
		return this.value;
	}

    public int hashCode() 
    {
        return Integer.parseInt(x + "" + y);
    }

    public boolean equals(Object o) {
        Cell incoming = (Cell) o;
        return this.x == incoming.getX() && this.y == incoming.getY();
    }
}
