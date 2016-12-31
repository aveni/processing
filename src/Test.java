import processing.core.*;

public class Test extends PApplet
{
  public void setup()
  {
    size(200,200);
    background(0);
  }
  
  public void draw()
  {
    if (mousePressed && mouseButton == LEFT)
    {
      background(255,0,0);
    }
    else
    {
      background(0);
    }
    
  }
  
  public static void main(String[] args)
  {
    PApplet.main(new String[] {"--present", "Test"});
  }

}
