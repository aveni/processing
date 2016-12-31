import processing.core.PApplet;

public class GameOfLife extends PApplet
{
  int h = 50;
  int w = 50;
  int s = 10;
  boolean run;
  LifeGame lifegame = new LifeGame(h, w, s);

  public void setup()
  {
    size(w*s, h*s);
    background(0);
    frameRate(10);
    run = false;
  }
  
  public void draw()
  {
    if (keyPressed && key == 'c') lifegame.clear();    
    if (keyPressed && key == ENTER) run = true;     
    if (keyPressed && key == 'p') run = false;
    if (keyPressed && key == 'e') lifegame.clearEdges();
    
    if (mousePressed && mouseButton == LEFT)
    {
      lifegame.turnOn(lifegame.findTileX(mouseX), lifegame.findTileY(mouseY));
    }
    
    if (mousePressed && mouseButton == RIGHT)
    {
      lifegame.turnOff(lifegame.findTileX(mouseX), lifegame.findTileY(mouseY));
    }
    
    lifegame.display();
    
    if (!run)
    {
      fill(255,0,0,60);
      rect(0,0,w*s, h*s);
    }
    else
    {
      lifegame.update();
    }    
  }
  
  public class Tile
  {
    private int x, y, s;
    private boolean cstate, nstate;
    
    public Tile(int x, int y, int s)
    {
      this.x = x;
      this.y = y;
      this.s = s;
      cstate = false;
      nstate = false;
    }
    
    public boolean getCState()
    {
      return cstate;
    }
    
    public void setNState(boolean b)
    {
      nstate = b;
    }
    
    public void setCState(boolean b)
    {
      cstate = b;
    }
    
    public void updateState()
    {
      cstate = nstate;
    }
    
    public void display()
    {
      stroke(0);
      if (cstate)
      {
        fill(0);
      }
      else
      {
        fill(255);
      }
      
      rect(x,y,s,s);
    }    
  }
  
  public class LifeGame
  {
    private Tile[][] tiles;
    int h, w, s;
    
    public LifeGame(int h, int w, int s)
    {
      this.h = h;
      this.w = w;
      this.s = s;
      tiles = new Tile[w][h];
      
      for (int i=0; i<w; i++)
      {
        for (int j=0; j<h; j++)
        {
          tiles[i][j] = new Tile(i*s,j*s,s);
        }
      }     
    }
    
    public boolean isValid(int i, int j)
    {
      return i>=0 && i<w && j>=0 && j<h;
    }
    
    public void turnOn(int x, int y)
    {    
      if (isValid(x,y)) tiles[x][y].setCState(true);
    }
    
    public void turnOff(int x, int y)
    {    
      if (isValid(x,y)) tiles[x][y].setCState(false);
    }
    
    public int findTileX(float x)
    {
      return (int)(x/s);
    }
    
    public int findTileY(float y)
    {
      return (int)(y/s);
    }
    
    public void clearEdges()
    {
      for (int i=0; i<w; i++)
      {
        turnOff(i, 0);
        turnOff(i, h-1);
      }
      
      for (int j=0; j<h; j++)
      {
        turnOff(0,j);
        turnOff(w-1, j);
      }
    }
    
    public int numNeighbors(int i, int j)
    {
      if (!isValid(i, j)) return -1;

      int numAlive=0;
      
      for (int a = i-1; a <= i+1; a++)
      {
        for (int b = j-1; b <= j+1; b++)
        {
          if (isValid(a,b) && !(a==i && b==j))
          {
            if (tiles[a][b].getCState()) numAlive++;
          }
        }
      }
      return numAlive;
    }
    
    
    
    public void update()
    {
      for (int i=0; i<w; i++)
      {
        for (int j=0; j<h; j++)
        {
          int n = numNeighbors(i,j);
          
          if (tiles[i][j].getCState())
          {
            if (n==2 || n==3) tiles[i][j].setNState(true);
            else tiles[i][j].setNState(false);
          }
          
          else
          {
            if (n==3) tiles[i][j].setNState(true);
            else tiles[i][j].setNState(false);
            
          }
        }     
      }
      
      for (int i=0; i<w; i++)
      {
        for (int j=0; j<h; j++)
        {
          tiles[i][j].updateState();
        }
      }

    }  
    
    public void display()
    {
      for (int i=0; i<w; i++)
      {
        for (int j=0; j<h; j++)
        {
          tiles[i][j].display();
        }
      }
    }
    
    public void clear()
    {
      for (int i=0; i<w; i++)
      {
        for (int j=0; j<h; j++)
        {
          tiles[i][j].setCState(false);
        }
      }
    }    
  }
  
  public static void main(String[] args)
  {
    PApplet.main(new String[] {"--present", "Test"});
  }

}
