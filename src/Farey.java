import processing.core.PApplet;

public class Farey extends PApplet
{

  int width=1600;
  int height=900;
  int counter=0;

  public void setup()
  {
    frameRate(15);
    size(width, height);
    background(255);  
    stroke(0);
  }

  public void drawStats(int counter)
  {
    stroke(0);
    fill(255); rect(0,height-20,140,20);
    textSize(18); fill(0);
    text("N-Value: " + counter, 15, height-3);
  }

  public void draw()
  {
    if (keyPressed && keyCode==RIGHT)
    {
      counter++;
      drawSet(counter, width, height-20);
    }
    
    if (keyPressed && keyCode==LEFT)
    {
      counter--;
      background(255);
      drawFarey(counter, width, height-20);
    }
    
    if (keyPressed && key==BACKSPACE)
    {
      background(255);
      counter=0;
    }
    
    //AutoPlay
    //counter++; drawSet(counter, width, height-20);
    
    
    drawStats(counter);
  }

  public void drawFarey(int n, int width, int height)
  {
    for (int i=1; i<=n; i++)
    {
      drawSet(i, width, height);
    }
  }

  
  public void drawSet(int n, int width, int height)
  {
    stroke((int)(0.3*n));
    for (int x=0; x<=n; x++)
    {
      for (int y=0; y<=n; y++)
      {
        double xfrac = ((double)x) / n;
        double yfrac = ((double)y) / n;
        point((int)(xfrac*(width-1)), (int)(yfrac*(height-1)));
      }     
    }
  }

  public static void main(String[] args)
  {
    PApplet.main(new String[] {"--present", "Test"});
  }

}
