import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Creates a panel that is used for the splash screen of the program. The company name and software
 * title along with an animation is added to the splashscreen. A progression bar is also incorperated.
 * 
 * @author Richard Dang
 * @version 2.2, May 15, 2014
 */
public class SplashScreen extends JPanel implements Runnable
{
  /**
   * Allows access to BufferedImage methods.
   */
  BufferedImage backgroundImage;
  /**
   * Allows access to JLabel methods.
   */
  JLabel label;
  /**
   * Allows access to JLabel methods.
   */
  JLabel element;
  /**
   * Allows access to JLabel methods.
   */
  JLabel element2;
  /**
   * Allows access to JLabel methods.
   */
  JLabel element3;
  /**
   * Allows access to JProgressBar methods.
   */
  JProgressBar progressBar;
  
  /**
   * Class constructor that draws the background and progress bar of the splashscreen.
   */
  public SplashScreen ()
  {
    setLayout (null);
    drawBackground();
    drawProgressBar();
  }
  
  /**
   * Draws the progress bar on the splashscreen.
   */
  public void drawProgressBar ()
  {
    progressBar = new JProgressBar(0, 816);
    progressBar.setBounds (20, 730, 760, 30);
    add(progressBar);
  }
  
  /**
   * Draws the background picture on the splashscreen.
   * 
   * @param elementPic reference: Allows access to BufferedImage methods.
   * @param elementPic2 reference: Allows access to BufferedImage methods.
   * @param elementPic3 reference: Allows access to BufferedImage methods.
   * @param e reference: Allows access to IOException methods.
   * @throws IOException Thrown to indicate an error when reading an image.
   */
  public void drawBackground ()
  {
    try
    {
      backgroundImage = ImageIO.read(new File( "../Images/SSBackground.png"));
      
      BufferedImage elementPic = ImageIO.read(new File( "../Images/SmallElement.png"));
      element = new JLabel (new ImageIcon (elementPic));
      add (element);
      
      BufferedImage elementPic2 = ImageIO.read(new File( "../Images/BigElement.png"));
      element2 = new JLabel (new ImageIcon (elementPic2));
      add (element2);
      
      BufferedImage elementPic3 = ImageIO.read(new File( "../Images/Tristry.png"));
      element3 = new JLabel (new ImageIcon (elementPic3));
      add (element3);
    }
    catch (IOException e) 
    {}
  }
  
  /**
   * Thread method that animates the splashscreen. Runs through a for loop
   * in order to increase the progress bar and uses conditionals in order
   * to move images around on the screen in different directions.
   * 
   * @param i int: number used as a counter in the for loop.
   * @param e reference: Allows access to Exception methods.
   * @throws Exception Thrown to indicate an error when running the thread.
   */
  public void run()
  {
    try
    {
      Thread.sleep (500);
    }
    catch (Exception e)
    {
    }
    for (int i=0; i<=816; i++)
    {    
      progressBar.setValue(i);
      
      if (i<55)
        element.setBounds(new Rectangle(new Point(152, 270+i),  element.getPreferredSize())); 
      else if (i<170)
        element.setBounds(new Rectangle(new Point(97+i, 325),  element.getPreferredSize())); 
      else if (i<283)
        element.setBounds(new Rectangle(new Point(267, 155+i),  element.getPreferredSize())); 
      else if (i<815)
        element3.setBounds(new Rectangle(new Point(1100-i, 268),  element3.getPreferredSize())); 
      else
      {
        element2.setBounds(new Rectangle(new Point(90, 268),  element2.getPreferredSize())); 
        remove (element);
      }
      try
      {
        Thread.sleep (5);
      }
      catch (Exception e)
      {
      }
    }
    try
    {
      Thread.sleep (1500);
    }
    catch (Exception e)
    {
    }
  }
  
  /**
   * Overridden method that draws a background image onto the the JPanel.
   * 
   * @param g Graphics: Allows components to be drawn onto the panel.
   */
  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g); 
    if (backgroundImage != null)
      g.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
  }
}