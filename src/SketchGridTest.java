import processing.core.*;

public class SketchGridTest extends PApplet
{
  class Cell 
  {
    float x,y;
    float w,h; 
    float c;

    // Cell Constructor
    Cell(float tempX, float tempY, float tempW, float tempH, float tempC)
    {
      x = tempX;
      y = tempY;
      w = tempW;
      h = tempH;
      c = tempC;
    } 
    void setColor(int tempC)
    {
      c = tempC;
    }
    
    void darken(float darkeningFactor)
    {
      c = max(0, c-darkeningFactor);
    }
    
    void lighten(float lightenFactor)
    {
      c = min(255, c+lightenFactor);
    }
    
    void display() 
    {
      stroke(0);
      fill(c);
      rect(x,y,w,h); 
    }
  } 

  class SketchGrid
  {
    Cell[][] grid;
    int rows, cols, size, darkeningFactor;
    
    SketchGrid(int numRows, int numCols, int tempSize, int darkenAmt)
    {
      rows = numRows;
      cols = numCols;
      size = tempSize;
      darkeningFactor = darkenAmt;
      grid = new Cell[rows][cols];
      
      for (int i = 0; i < cols; i++) 
      {
        for (int j = 0; j < rows; j++) 
        {
          grid[i][j] = new Cell(i*size,j*size,20,20,255);
        }
      }
    }
    
    void display()
    {
      for (int i = 0; i < cols; i++) 
      {
        for (int j = 0; j < rows; j++) 
        {
          grid[i][j].display();
        }
      }
    }
    
    void clearGrid()
    {
      for (Cell[] col: grid)
      {
        for (Cell c: col)
        {
          c.setColor(255);
        }      
      }
    }
    
    void fillIn(float x, float y)
    {
      grid[findCellX(x)][findCellY(y)].darken(darkeningFactor);
    }
    
    void erase(float x, float y)
    {
      grid[findCellX(x)][findCellY(y)].lighten(darkeningFactor);
    }
    
    int findCellX(float x)
    {
      return (int)(x/size);
    }
    
    int findCellY(float y)
    {
      return (int)(y/size);
    }
  }
  
  int rows = 40;
  int cols = 40;
  int size = 15;
  int darkeningFactor = 12;

  SketchGrid sketch = new SketchGrid(cols,rows,size,darkeningFactor);

  public void setup()
  {
    size(size*cols,size*rows);
    background(0);
  }

  public void draw()
  {
    sketch.display();
    if (mousePressed && mouseButton == LEFT)
    {
      sketch.fillIn(mouseX, mouseY);
    }
    
    if (mousePressed && mouseButton == RIGHT)
    {
      sketch.erase(mouseX, mouseY);
    }
    
    if (keyPressed && key == BACKSPACE)
    {
      sketch.clearGrid();
    }
  }

  public static void main(String[] args)
  {    
    PApplet.main(new String[] {"--present", "SketchGridTest"});
  }

}
