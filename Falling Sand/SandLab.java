import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int FIRE = 4;
  public static final int GLASS = 5;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    grid = new int[numRows][numCols];
    names = new String[6];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[FIRE] = "Fire";
    names[GLASS] = "Glass";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
   grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
    for(int row = 0; row < grid.length; row++)
    {
      for(int col = 0; col < grid[0].length; col++)
      {
        if(grid[row][col] == 0)
        {
          display.setColor(row, col, new Color(0, 0, 0));
        }
        if(grid[row][col] == 1)
        {
          display.setColor(row, col, new Color(192, 192, 192));
        }
        if(grid[row][col] == 2)
        {
          display.setColor(row, col, new Color(255, 255, 153));
        }
        if(grid[row][col] == 3)
        {
          display.setColor(row, col, new Color(0, 0, 255));
        }
        if(grid[row][col] == 4)
        {
          display.setColor(row, col, new Color(220, 20, 60));
        }
        if(grid[row][col] == 5)
        {
          display.setColor(row, col, new Color(162, 213, 235));
        }
      }
    }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
    int row = (int)(Math.random() * grid.length);
    int col = (int)(Math.random() * grid[0].length);
    if(row < grid.length - 1 && grid[row][col] == 2 && grid[row + 1][col] == 0)
    {
      grid[row + 1][col] = 2;
      grid[row][col] = 0;
    }
     if(row < grid.length - 1 && grid[row][col] == 4 && grid[row + 1][col] == 0)
    {
      grid[row + 1][col] = 4;
      grid[row][col] = 0;
    }
    if(grid[row][col] == 2 && row < grid.length - 1 && grid[row + 1][col] == 3)
    {
      grid[row + 1][col] = 2;
      grid[row][col] = 3;
    }
    if(row < grid.length - 1 && ((grid[row][col] == 4 && grid[row + 1][col] == 2) || (grid[row][col] == 2 && grid[row + 1][col] == 4)))
    {
      grid[row + 1][col] = 5;
      grid[row][col] = 5;
    }
    if(col < grid[0].length - 1 && ((grid[row][col] == 4 && grid[row][col + 1] == 2) || (grid[row][col] == 2 && grid[row][col + 1] == 4)))
    {
      grid[row][col + 1] = 5;
      grid[row][col] = 5;
    }
    if(col > 0 && ((grid[row][col] == 4 && grid[row][col - 1] == 2) || (grid[row][col] == 2 && grid[row][col - 1] == 4)))
    {
      grid[row][col - 1] = 5;
      grid[row][col] = 5;
    }
    if(grid[row][col] == 3)
    {
      int dir = (int)(Math.random() * 3);
      if(dir == 2 && row < grid.length - 1 && grid[row + 1][col] == 0)
      {
      grid[row + 1][col] = 3;
      grid[row][col] = 0;
      }
      if(dir == 1 && col < grid[0].length - 1 && grid[row][col + 1] == 0)
      {
      grid[row][col + 1] = 3;
      grid[row][col] = 0;
      }
      if(dir == 0 && col > 0 && grid[row][col - 1] == 0)
      {
      grid[row][col - 1] = 3;
      grid[row][col] = 0;
      }
    }
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
