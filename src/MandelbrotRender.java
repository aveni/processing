import processing.core.PApplet;

/**
 * This program creates an interactive Mandelbrot Set.
 * <p>
 * To move around, left-click and drag the Mandelbrot with your mouse.<br>
 * To center the Mandelbrot on your mouse location, right-click.<br>
 * To zoom in, hit the 'Z' key.<br>
 * To zoom out, hit the 'X' key.<br>
 * To center on a preset special point of the Mandelbrot, hit the 'S' key.
 * </p>
 * The size of the Mandelbrot can be set within the application, 
 * by clicking the Small, Medium, or Large buttons.<br>
 * The coloring of the Mandelbrot can also be set by clicking the Grayscale
 * or Color buttons.
 *  
 * @author Abhinav Venigalla
 */
public class MandelbrotRender extends PApplet
{
  
  //Background inputs
  int bWidth = 700;
  int bHeight = 600;
  int originX = width/2;
  int originY = height/2;  
  int curX = 0;
  int curY = 0;
  boolean held = false;
  int curSize = 0;
  int curColor = 0;
  
  //Mandelbrot sets:
  int sWidth = 140;
  int sHeight = 120;
  int mWidth = 350;
  int mHeight = 300;
  int lWidth = 700;
  int lHeight = 600;
  
  //small, high speed
  Mandelbrot m1 = new Mandelbrot(sWidth, sHeight, (bWidth - sWidth)/2, (bHeight - sHeight)/2, -2.5, 1.5, 3.5/sWidth, 3.0/sHeight, 500, 1);
  Mandelbrot cm1 = new ColorMandelbrot(sWidth, sHeight, (bWidth - sWidth)/2, (bHeight - sHeight)/2, -2.5, 1.5, 3.5/sWidth, 3.0/sHeight, 500, 2);
 
  //medium, medium speed
  Mandelbrot m2 = new Mandelbrot(mWidth, mHeight, (bWidth - mWidth)/2, (bHeight - mHeight)/2, -2.5, 1.5, 3.5/mWidth, 3.0/mHeight, 500, 1);
  Mandelbrot cm2 = new ColorMandelbrot(mWidth, mHeight, (bWidth - mWidth)/2, (bHeight - mHeight)/2, -2.5, 1.5, 3.5/mWidth, 3.0/mHeight, 500, 2);
  
  //large, slow speed
  Mandelbrot m3 = new Mandelbrot(lWidth, lHeight, (bWidth - lWidth)/2, (bHeight - lHeight)/2, -2.5, 1.5, 3.5/lWidth, 3.0/lHeight, 500, 1);
  Mandelbrot cm3 = new ColorMandelbrot(lWidth, lHeight, (bWidth - lWidth)/2, (bHeight - lHeight)/2, -2.5, 1.5, 3.5/lWidth, 3.0/lHeight, 500, 2);
  
  //Mandelbrot initially starts with small size and grayscale
  DynamicMandelbrot dm = new DynamicMandelbrot(m1);

  
  public void setup()
  {
    frameRate(100);
    size(bWidth, bHeight);
    background(255);
    dm.display();
    resetButtons(curSize, curColor);
  }
  

  public void draw()
  {  
    //Key Functions
    if (keyPressed)
    {
      switch(key)
      {
      case 'z': dm.zoom(2); break;
      case 'x': dm.zoom(.5); break;
      case 'r': dm.reset(); break;
      case 's': dm.setCoordinateCenter(-1.786440783, 0); dm.display(); break;
      default: break;
      }
      dm.display();
    }

    //Mouse Interaction    
    if (mousePressed && mouseButton == RIGHT)
    {
      dm.setGridCenter((int)mouseX, (int)mouseY); dm.display();
    }
       
    if (!held)
    {
      if (mousePressed && mouseButton == LEFT)
      {
        curX = (int)mouseX;
        curY = (int)mouseY;
        held = true;
      }
    }
    else
    {
      if (mousePressed && mouseButton == LEFT)
      {
        dm.mouseMove((int)mouseX - curX, (int)mouseY - curY);      
        curX = (int)mouseX;
        curY = (int)mouseY;
        dm.display();
      }      
      else if (!mousePressed)
      {
        curX = 0;
        curY = 0;
        held = false;
      }
    }
    
    //Display Information
    stroke(0); fill(255);
    rect(bWidth-250, bHeight-70, 250, 70);
    double[] coords = dm.getCoordinates(mouseX, mouseY);
    textSize(10); fill(0);
    text("X-Coordinate: " + coords[0], bWidth-240, bHeight-50);
    text("Y-Coordinate: " + coords[1], bWidth-240, bHeight-30);
    text("Zoom Level: " + dm.getZoomLevel() + "x", bWidth-240, bHeight-10);
    
    //Adjust buttons
    GUISelect();
  }
  

//------------------------------GUI methods---------------------------------------------
  
  
  //Adjusts the coloring of the Size buttons based on which one is clicked
  public void resetSizeButtons(int n)
  {
    fill(255); stroke(0); 
    rect(0, bHeight-80, 50, 30); 
    rect(50, bHeight-80, 50, 30);
    rect(100, bHeight-80, 50, 30);    
    textSize(10); fill(0);
    text("SMALL", 10, bHeight - 60);
    text("MEDIUM", 55, bHeight - 60);
    text("LARGE", 110, bHeight - 60); 
    
    //darken selected button
    if (n==0)
    {
      fill(0); rect(0, bHeight-80, 50, 30);
      fill(255); text("SMALL", 10, bHeight - 60);
    }
    else if(n==1)
    {
      fill(0); rect(50, bHeight-80, 50, 30);
      fill(255); text("MEDIUM", 55, bHeight - 60);
    }
    else
    {
      fill(0); rect(100, bHeight-80, 50, 30);
      fill(255); text("LARGE", 110, bHeight - 60); 
    }
  }
  
  //Adjusts the coloring of the Color buttons based on which one is clicked
  public void resetColorButtons(int n)
  {
    fill(255); stroke(0); 
    rect(0, bHeight- 25, 75, 25);
    rect(75, bHeight- 25, 75, 25);    
    textSize(10); fill(0);
    text("GRAYSCALE", 10, bHeight - 5);
    text("COLOR", 90, bHeight - 5);
    
    //darken selected button
    if (n==0)
    {
      fill(0); rect(0, bHeight- 25, 75, 25);
      fill(255); text("GRAYSCALE", 10, bHeight - 5);
    }    
    else
    {
      fill(0); rect(75, bHeight- 25, 75, 25);
      fill(255); text("COLOR", 90, bHeight - 5);
    }
  }
  
  //Resets all buttons based on which ones have been clicked
  public void resetButtons(int curSize, int curColor)
  {
    fill(255); stroke(0);   
    rect(0, bHeight-100, 150, 100);   
    textSize(15); fill(0);
    text("Select Size", 20, bHeight-84);
    text("Select Color", 20, bHeight-32);
    resetSizeButtons(curSize);
    resetColorButtons(curColor);
  }

  /**
   * Adjusts the buttons within the application based on mouse input,
   * and also changes the size/color of the Mandelbrot accordingly
   */
  public void GUISelect()
  {
    resetButtons(curSize, curColor);
    
    //mouse clicked inside selection panel
    if (mousePressed && mouseButton == LEFT && mouseX>0 && mouseX<150 && mouseY>0 && mouseY>bHeight-100)
    {      
      if (mouseY>bHeight-80 && mouseY<=bHeight-50) //size selection
      {
        if (mouseX<=50 && curSize != 0)
        {
          background(255);
          dm.reset();
          if (curColor == 0) dm = new DynamicMandelbrot(m1);
          else dm = new DynamicMandelbrot(cm1);
          dm.display();
          curSize = 0;
        }
        
        else if (mouseX>50 && mouseX<=100 && curSize != 1)
        {
          background(255);
          dm.reset();        
          if (curColor == 0) dm = new DynamicMandelbrot(m2);
          else dm = new DynamicMandelbrot(cm2);
          dm.display();
          curSize = 1;        
        }
        
        else if (mouseX>100 && mouseX<150 && curSize != 2)
        {
          background(255);
          dm.reset();
          if (curColor == 0) dm = new DynamicMandelbrot(m3);
          else dm = new DynamicMandelbrot(cm3);
          dm.display();
          curSize = 2;
        }
      }
      
      else if(mouseY>bHeight-25) //color selection
      {
        if (mouseX<=75 && curColor != 0)
        {
          background(255);
          dm.reset();
          if (curSize == 0) dm = new DynamicMandelbrot(m1);
          else if (curSize == 1) dm = new DynamicMandelbrot(m2);
          else dm = new DynamicMandelbrot(m3);
          dm.display();
          curColor = 0;
        }
        
        else if (mouseX>75 && mouseX<=150 && curColor != 1)
        {
          background(255);
          dm.reset();
          if (curSize == 0) dm = new DynamicMandelbrot(cm1);
          else if (curSize == 1) dm = new DynamicMandelbrot(cm2);
          else dm = new DynamicMandelbrot(cm3);
          dm.display();
          curColor = 1;
        }
      }
    }
  }

  
//---------------------------BEGINNING OF MANDELBROT RELATED CLASSES-----------------------------
  
  /**
   * This class creates Dynamic Mandelbrot Set, that allows user interaction/alteration
   * @param m - the Mandelbrot object that will be changed and displayed
   * @author Abhinav
   */
  public class DynamicMandelbrot
  {
    private Mandelbrot m;
    private double zoomLevel;
    private double originalMinX;
    private double originalMaxY;
    private double originalDx;
    private double originalDy;
    
    public DynamicMandelbrot(Mandelbrot m)
    {
      this.m = m;
      zoomLevel = 1;
      originalMinX = m.getMinX();
      originalMaxY = m.getMaxY();
      originalDx = m.getDx();
      originalDy = m.getDy();     
    }
    
    //resets to original conditions of Mandelbrot m
    public void reset()
    {
      m.setMinX(originalMinX);
      m.setMaxY(originalMaxY);
      m.setDx(originalDx);
      m.setDy(originalDy);
    }
    
    //zooms in on the center of the Mandelbrot image by the scale amount
    public void zoom(double scale)
    {
      double xRange = m.getMaxX() - m.getMinX();
      double yRange = m.getMaxY() - m.getMinY();
      double xCenter = (m.getMaxX() + m.getMinX())/2;
      double yCenter = (m.getMaxY() + m.getMinY())/2;
      
      m.setMinX(xCenter - xRange/(2*scale));
      m.setMaxY(yCenter + yRange/(2*scale));
      
      m.setDx(m.getDx()/scale);
      m.setDy(m.getDy()/scale);
      zoomLevel*=scale;
    }
    
    //sets the center of the Mandelbrot on the coordinates x, y
    public void setCoordinateCenter(double x, double y)
    {
      double xCenter = (m.getMaxX() + m.getMinX())/2;
      double yCenter = (m.getMaxY() + m.getMinY())/2;      
      translate(xCenter - x, yCenter - y);     
    }
    
    //sets the center of the Mandelbrot based on the grid coordinates x, y
    public void setGridCenter(int gridX, int gridY)
    {
      double newX = m.getMinX() + (gridX-m.getStartX())*m.getDx();
      double newY = m.getMaxY() - (gridY-m.getStartY())*m.getDy();      
      setCoordinateCenter(newX, newY);
    }
    
    //moves the Mandelbrot based on mouse movement
    public void mouseMove(int gridDx, int gridDy)
    {
      translate(gridDx*m.getDx(), -gridDy*m.getDy());
    }
    
    //moves the Mandelbrot by dx and dy
    public void translate(double dx, double dy)
    {
      m.setMinX(m.getMinX() - dx);
      m.setMaxY(m.getMaxY() - dy);
    }
    
    public double getZoomLevel()
    {
      return zoomLevel;
    }
    
    //returns coordinates based on mouse position
    public double[] getCoordinates(int gridX, double gridY)
    {
      double newX = m.getMinX() + (gridX-m.getStartX())*m.getDx();
      double newY = m.getMaxY() - (gridY-m.getStartY())*m.getDy();      
      return new double[]{newX, newY};      
    }
    
    public void display()
    {
      m.display();
    }
  }
  
  /**
   * Extends the Mandelbrot class, uses RGB coloring to produce a fire gradient Mandelbrot set.<br>
   * Coloring based off of escape-time.
   * @param width - the pixel width of the image
   * @param height - the pixel height of the image
   * @param startX - the left most pixel value of the image
   * @param startY - the uppermost pixel value of the image
   * @param minX - the least x-coordinate in the complex plane
   * @param maxY - the largest y-coordinate in the complex plane
   * @param dx - the step value of the x-coordinates from pixel to pixel
   * @param dy - the step value of the y-coordinates from pixel to pixel
   * @param contrast -  the contrast of the coloring algorithm, must be >=1
   * @param maxIterations -the maximum number of iterations of the coloring algorithm, must be >=1
   * @author Abhinav
   */
  public class ColorMandelbrot extends Mandelbrot
  {
    
    public ColorMandelbrot(int width, int height, int startX, int startY,
        double minX, double maxY, double dx, double dy, int maxIterations,
        int contrast)
    {
      super(width, height, startX, startY, minX, maxY, dx, dy, maxIterations,
          contrast);
    }
    
    public int[] fireColor(int n)
    {
      if ((n/256)%2 == 0) return new int[]{255, n%256,0};
      else return new int[]{255, 255 - n%256, 0};
    }
    
    public void setColor(int n)
    {
      if (n == -1) stroke(0);
      else
      {
        int[] rgb = fireColor(n);        
        stroke(rgb[0], rgb[1], rgb[2]);
      }   
    }    
  }
  
  
  /**
   * Creates an instance of a grayscale Mandelbrot set. <br>
   * Coloring based off of escape-time.
   * @param width - the pixel width of the image
   * @param height - the pixel height of the image
   * @param startX - the left most pixel value of the image
   * @param startY - the uppermost pixel value of the image
   * @param minX - the least x-coordinate in the complex plane
   * @param maxY - the largest y-coordinate in the complex plane
   * @param dx - the step value of the x-coordinates from pixel to pixel
   * @param dy - the step value of the y-coordinates from pixel to pixel
   * @param contrast -  the contrast of the coloring algorithm, must be >=1
   * @param maxIterations -the maximum number of iterations of the coloring algorithm, must be >=1
   * @author Abhinav
   */  
  public class Mandelbrot
  {
    private int startX, startY, height, width, maxIterations, contrast;
    private double minX, dx, maxY, dy;
    private Complex[][] coordinates;
    
    public Mandelbrot(int width, int height, int startX, int startY,
        double minX, double maxY, double dx, double dy, int maxIterations, int contrast)
    {      
      this.width = width;
      this.height = height;
      this.startX = startX;
      this.startY = startY;
      this.maxIterations = maxIterations;
      this.contrast = contrast;
      
      this.minX = minX;
      this.maxY = maxY;
      this.dx = dx;     
      this.dy = dy;
      
      coordinates = new Complex[width][height];
      for (int i=0; i<width; i++)
      {
        for (int j=0; j<height; j++)
        {
          coordinates[i][j] = new Complex();
        }
      }
    }
    
    public int getStartX()
    {
      return startX;
    }
    
    public int getStartY()
    {
      return startY;
    }
    
    public double getMinX()
    {
      return minX;
    }
    
    public double getMaxX()
    {
      return minX + width*dx;
    }
       
    public double getMaxY()
    {
      return maxY;
    }
    
    public double getMinY()
    {
      return maxY - height*dy;
    }
    
    public void setMinX(double x)
    {
      minX = x;
    }
    
    public void setMaxY(double y)
    {
      maxY = y;
    }
    
    public double getDx()
    {
      return dx;
    }
    
    public void setDx(double dx)
    {
      this.dx = dx;
    }
    
    public double getDy()
    {
      return dy;
    }
    
    public void setDy(double dy)
    {
      this.dy = dy;
    }
    
    //tests whether a complex number c is within the Mandelbrot Set
    public int testComplex(Complex c)
    {
      Complex temp = new Complex(c);
      for (int i=0; i<maxIterations; i++)
      {
        if (c.getMag() > 2) return i; //not in Set
        
        c.square();
        c.add(temp);
      }      
      return -1; //in Set
    }
    
    //sets the color of the pixel based off of escape time
    public void setColor(int n)
    {
      if (n == -1) stroke(0);
      
      else if ((n/256)%2 == 0) stroke((n*contrast)%256);
      else stroke(255 - (n*contrast)%256);
    }
    
    //displays the image of the Mandelbrot
    public void display()
    {
      for (int i=0; i<width; i++)
      {
        for (int j=0; j<height; j++)
        {
          coordinates[i][j].setRe(minX + i*dx);
          coordinates[i][j].setIm(maxY - j*dy);
          
          int n = testComplex(coordinates[i][j]);          
          setColor(n);
          
          point(startX+i ,startY+j);            
        }        
      }
    }
    
    public String toString()
    {
      return ("Height: "+ height + " Width: " + width + " startX: " + startX
          + " startY:" + startY + " minX: " + minX + " maxY: " + maxY + " dx: "+ dx + " dy: " + dy);
    }    
  }
  
  /**
   * Complex number class taken from http://en.literateprograms.org/Complex_numbers_(Java)
   * @param re - the real term in the complex number
   * @param im - the coefficient of the imaginary term in the complex number
   */
  public class Complex
  {
    double re, im;
    
    public Complex ()
    {
      this.re = 0;
      this.im = 0;
    }
    
    public Complex(double re, double im)
    {
      this.re = re;
      this.im = im;
    }
    
    public Complex(Complex input) 
    {
      this.re = input.getRe();
      this.im = input.getIm();
    }
    
    public void setRe(double re)
    {
      this.re = re;
    }
    
    public void setIm(double im) 
    {
      this.im = im;
    }
    
    public double getRe() 
    {
      return this.re;
    }
    
    public double getIm() 
    {
      return this.im;
    }
    
    public double getMag()
    {
      return Math.pow((this.im*this.im) + (this.re*this.re), .5);
    }
    
    public void add(Complex op) 
    {
      double newRe = this.re + op.getRe();
      double newIm = this.im + op.getIm();
      setRe(newRe); setIm(newIm);
    }
    
    public void square()
    {
      double newRe = re*re - im*im;
      double newIm = 2*re*im;
      setRe(newRe); setIm(newIm);
    }
        
    public String toString() 
    {
      if (this.re == 0) 
      {
        if (this.im == 0) 
        {
          return "0";
        }         
        else 
        {
          return (this.im + "i");
        }
      } 
      
      else 
      {
        if (this.im == 0) 
        {
          return String.valueOf(this.re);
        } 
        else if (this.im < 0) 
        {
          return(this.re + " " + this.im + "i");
        } 
        else
        {
          return(this.re + " +" + this.im + "i");
        }
      }
    }      
  }
  
  public static void main(String[] args)
  {
    PApplet.main(new String[] {"--present", "Test"});
  }

}
