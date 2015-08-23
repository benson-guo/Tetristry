import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/**
 * Creates a panel that is used as the tutorial to teach the player the basics of chemistry nomenclature.
 * 
 * @author Richard Dang and Benson 
 * @version 1.2, May 20, 2014
 */
public class Tutorial extends JPanel implements ActionListener
{ 
  /**
   * Allows access to BufferedImage methods .
   */
  private BufferedImage backgroundImage;
  /**
   * Allows access to BufferedImage methods .
   */
  private BufferedImage backgroundImage2;
  /**
   * Allows access to JButton methods .
   */
  private JButton returnButton;
  /**
   * Allows access to JButton methods .
   */
  private JButton nextButton;
  /**
   * Allows access to JButton methods .
   */
  private JButton previousButton;
  /**
   * Used to change the background image.
   */
  private boolean changeBackground;
  
  /**
   * Class constructor that sets the layout to null, draws the background image and creates
   * the button options of the tutorial.
   */
  public Tutorial ()
  {
    setLayout (null);
    drawBackground ();
    tutorialOptions ();
  }
  
  /**
   * Draws the background of the tutorial panel.
   * 
   * @param e reference: Allows access to IOException methods.
   * @throws IOException Thrown to indicate an error when reading an image.
   */
  public void drawBackground ()
  {
    try
    {
      backgroundImage = ImageIO.read(new File( "../Images/TBackground.png"));
      backgroundImage2 = ImageIO.read(new File( "../Images/TBackground2.png"));
    }
    catch (IOException e) 
    {}
  }
  
  /**
   * Creates the button options for the tutorial using imported images.
   * Each are set to their respective location and added to the panel.
   * 
   * @param returns BufferedImage: Allows access to BufferedImage methods.
   * @param next BufferedImage: Allows access to BufferedImage methods.
   * @param previous BufferedImage: Allows access to BufferedImage methods.
   * @param e reference: Allows access to IOException methods.
   * @throws IOException Thrown to indicate an error when reading an image.
   */
  public void tutorialOptions ()
  {
    try
    {  
      BufferedImage returns = ImageIO.read(new File( "../Images/ReturnButton.png"));
      returnButton = new JButton(new ImageIcon(returns));
      returnButton.setBounds(new Rectangle(new Point(230, 600), returnButton.getPreferredSize())); 
      returnButton.setBorder(BorderFactory.createEmptyBorder());
      returnButton.setContentAreaFilled(false);
      add(returnButton);
      returnButton.addActionListener (this);
      
      BufferedImage next = ImageIO.read(new File( "../Images/NextButton.png"));
      nextButton = new JButton(new ImageIcon(next));
      nextButton.setBounds(new Rectangle(new Point(560, 680), nextButton.getPreferredSize())); 
      nextButton.setBorder(BorderFactory.createEmptyBorder());
      nextButton.setContentAreaFilled(false);
      add(nextButton);
      nextButton.addActionListener (this);
      
      BufferedImage previous = ImageIO.read(new File( "../Images/PreviousButton.png"));
      previousButton = new JButton(new ImageIcon(previous));
      previousButton.setBounds(new Rectangle(new Point(20, 680), previousButton.getPreferredSize())); 
      previousButton.setBorder(BorderFactory.createEmptyBorder());
      previousButton.setContentAreaFilled(false);
      previousButton.addActionListener (this);
    }
    catch (IOException e) 
    {} 
  }
  
  /**
   * Uses action listener to check if one of the buttons are pressed
   * and performs the action assosiated with it.
   * Return allows the player to return to the menu
   * Next moves the player to the next screen of the tutorial
   * Previous moves the player to the previous screen of the tutorial
   * 
   * @param ae ActionEvent: allows access to ActionEvent methods
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource() == returnButton) 
      TetristryApp.menuPanel ();
    
    if (ae.getSource () == nextButton)
    {
      changeBackground = true;
      remove (nextButton);
      add (previousButton);
    }
    else
    {
      changeBackground = false;
      remove (previousButton);
      add (nextButton);
    }
    repaint();
    revalidate();
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
    if (backgroundImage != null && changeBackground == false)
      g.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
    else g.drawImage(backgroundImage2, 0,0,this.getWidth(),this.getHeight(),this);
  }
}