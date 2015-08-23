import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/**
 * Creates the level selection screen where the user will have a choice 
 * to choose their difficulty or return to the main menu.
 * 
 * @author Richard Dang
 * @version 1.3, May 22th, 2014
 */
public class LevelSelection extends JPanel implements ActionListener
{
  /*
   *BufferedImage variable for background image of panel.
   **/
  BufferedImage backgroundImage;
  /*
   * Array of JButtons for gameboard buttons.
   */
  JButton [] menuItems = new JButton [4];
  
  
  /*
   * static int variable for the difficulty selected.
   **/
  static int difficulty;
  
  
  /**
   *Constructor which sets up the JPanel for the level selection.
   * The layout is set to null.
   */
  public LevelSelection ()
  {
    setLayout (null);
    drawBackground ();
    levelOptions ();   
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
      backgroundImage = ImageIO.read(new File( "../Images/LSBackground.png"));
    }
    catch (IOException e) 
    {}
  }
  
  /**
   *This method reads the images for the JButtons and adds them onto the screen.
   * 
   * @param easy-BufferedImage variable for easy button.
   * @param easy-BufferedImage variable for medium button.
   * @param easy-BufferedImage variable for hard button.
   * @param easy-BufferedImage variable for return to menu button.
   * @param spacing-int variable for spacing.
   * @param x-int counter variable for each loop.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */
  public void levelOptions ()
  {
    int spacing = 0;
    try
    {  
      BufferedImage easy = ImageIO.read(new File( "../Images/EasyButton.png"));
      BufferedImage medium = ImageIO.read(new File( "../Images/MediumButton.png"));
      BufferedImage hard = ImageIO.read(new File( "../Images/HardButton.png"));
      BufferedImage returns = ImageIO.read(new File( "../Images/ReturnButton.png"));
      
      
      menuItems[0] = new JButton(new ImageIcon(easy));
      menuItems[1] = new JButton(new ImageIcon(medium));
      menuItems[2] = new JButton(new ImageIcon(hard));
      menuItems[3] = new JButton(new ImageIcon(returns));
      
      for (int x = 0; x<4; x++)
      {
        menuItems[x].setBounds(new Rectangle(new Point(230, 100+spacing), menuItems[x].getPreferredSize())); 
        menuItems[x].setBorder(BorderFactory.createEmptyBorder());
        menuItems[x].setContentAreaFilled(false);
        add(menuItems[x]);
        menuItems[x].addActionListener (this);
        spacing +=150;
      }
    }
    catch (IOException e) 
    {} 
  }
  
  /**
   * Implemented abstract method that stores the current difficulty based on user's selection, through an if statement, and opens a player name dialog box.
   * 
   * @param ae-ActionEvent variable passed in.
   */
  public void actionPerformed (ActionEvent ae)
  {
    
    if (ae.getSource() == menuItems[0]) 
      difficulty = 1; 
    else if (ae.getSource() == menuItems[1]) 
      difficulty = 2; 
    else if (ae.getSource() == menuItems[2]) 
      difficulty = 3; 
    else
      TetristryApp.menuPanel();
    
    if (ae.getSource() != menuItems[3]) 
    {
      for (JButton x: menuItems)
      {
        x.setEnabled (false);
      }
      new PlayerName();
    }
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
    if (backgroundImage != null)
      g.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
  }
}