import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/**
 *This method is the Instructions.
 * 
 * @author Richard Dang and Benson Guo
 * @version 1.3, May 22th, 2014
 */
public class Instructions extends JPanel implements ActionListener
{ 
  /**
   *backgroundImage-BufferedImage variable for background image of panel.
   **/
  private BufferedImage backgroundImage;
  /**
   *backgroundImage2-BufferedImage variable for 2nd background image of panel.
   **/
  private BufferedImage backgroundImage2;
  /**
   *backgroundImage2-BufferedImage variable for 2nd background image of panel.
   **/
  private BufferedImage backgroundImage3;
  /**
   * returnButton-reference variable for JLabel used for return to menu button.
   **/
  private JButton returnButton;
  /**
   * nextButton-reference variable for JLabel used for next page button.
   **/
  private JButton nextButton;
  /**
   * previousButton-reference variable for JLabel used for previous page button.
   **/
  private JButton previousButton;
  /**
   * changeBackground-boolean variable to store whether or not to change background.
   **/
  private int changeBackground =1;
  
  /**
   *Constructor which sets up the JPanel for the level selection.
   * The layout is set to null.
   */
  public Instructions ()
  {
    setLayout (null);
    drawBackground ();
    backOption ();
  }
  
  /**
   *This method reads the image used for the background of the panel.
   * 
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */ 
  public void drawBackground ()
  {
    try
    {
      backgroundImage = ImageIO.read(new File( "../Images/IBackground.png"));
      backgroundImage2 = ImageIO.read(new File( "../Images/IBackground2.png"));
      backgroundImage3 = ImageIO.read(new File( "../Images/IBackground3.png"));
    }
    catch (IOException e) 
    {}
  }
  
  /**
   *This method sets up the buttons on the panel. The images for the buttons are read, set to the button, and the buttons are added onto the panel.
   * 
   * @param returns-BufferedImage variable for return button image.
   * @param next-BufferedImage variable for next button image.
   * @param previous-BufferedImage variable for previous button image.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */ 
  public void backOption ()
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
   * Implemented abstract method that moves user to next page of instructions or back to main menu based on if statements.
   * 
   * @param ae-ActionEvent variable passed in.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource() == returnButton) 
      TetristryApp.menuPanel();
    if (ae.getSource () == nextButton)
    {
      if (changeBackground >1)
        add(previousButton); 
      changeBackground++;
      if (changeBackground == 3)
        remove (nextButton);
    }
    else
    {
      if (changeBackground <3)
        add(nextButton);
      changeBackground--;
      if (changeBackground == 1)
        remove (previousButton);
    }
    repaint();
    revalidate();
  }
  
  /**
   * Overridden method that draws a background image onto the the JPanel.
   * 
   * @param g-Graphics variable passed in as parameter.
   */
  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g); 
    if (backgroundImage != null )
    {
      if (changeBackground == 1)
        g.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
      else if (changeBackground ==2 )
        g.drawImage(backgroundImage2, 0,0,this.getWidth(),this.getHeight(),this);
      else
        g.drawImage(backgroundImage3, 0,0,this.getWidth(),this.getHeight(),this);
    }
  }
  
}