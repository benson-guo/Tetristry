import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;


/**
 * Creates the main menu panel which consists of the game name and button options
 * that the player can access. 
 * 
 * @author Richard Dang
 * @version 1.2, May 20, 2014
 * 
 * Number of hours: 1.5
 */
public class Menu extends JPanel implements ActionListener
{
  
  /**
   * Allows access to BufferedImage methods.
   */
  BufferedImage backgroundImage;
  
  /*
   * Array of JButtons for gameboard buttons.
   */
  JButton [] menuItems = new JButton [5];
  
  
  
  /**
   * Class constructor that sets the layout of the panel to null, 
   * draws the background image and creates the menu option buttons.
   */
  public Menu ()
  {
    setLayout (null);
    drawBackground ();
    menuOptions ();
  }
  
  /**
   * Draws the background image onto the panel
   */
  public void drawBackground ()
  {
    try
    {
      backgroundImage = ImageIO.read(new File( "../Images/MenuBackground.png"));
    }
    catch (IOException e) 
    {}
  }
  
  /**
   * Creates the menu option buttons including play, instructions, highscores
   * tutorial and quit.
   */
  public void menuOptions ()
  {
    int spacing = 0;
    try
    {  
      BufferedImage play = ImageIO.read(new File( "../Images/PlayButton.png"));
      BufferedImage instructions = ImageIO.read(new File( "../Images/InstructionsButton.png"));
      BufferedImage highscores = ImageIO.read(new File( "../Images/HighscoresButton.png"));
      BufferedImage tutorial = ImageIO.read(new File( "../Images/TutorialButton.png"));
      BufferedImage quit = ImageIO.read(new File( "../Images/QuitButton.png"));
      
      menuItems[0]=new JButton(new ImageIcon(play));
      menuItems[1]=new JButton(new ImageIcon(instructions));
      menuItems[2]=new JButton(new ImageIcon(highscores));
      menuItems[3]=new JButton(new ImageIcon(tutorial));
      menuItems[4]=new JButton(new ImageIcon(quit));
      
      for (int x = 0; x<5; x++)
      {
        menuItems[x].setBounds(new Rectangle(new Point(230, 200+spacing),  menuItems[x].getPreferredSize())); 
        menuItems[x].setBorder(BorderFactory.createEmptyBorder());
        menuItems[x].setContentAreaFilled(false);
        add( menuItems[x]);
        menuItems[x].addActionListener (this);
        spacing +=100;
      }
    }
    catch (IOException e) 
    {} 
  }
  
  /**
   * Uses action listener to check if one of the buttons are pressed
   * and performs the action assosiated with it.
   * Play button redirects user to the level selection screen
   * Instructions button redirects user to instructions screen
   * Highscore button redirects user to the highscore screen
   * Tutorial button redirects user to tutorial screen
   * Quit button quit displays a confirmation dialog to quit, if yes, exits game
   * 
   *@param ae ActionEvent: allows access to ActionEvent methods
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource() ==  menuItems[0]) 
      TetristryApp.levelPanel();
    else if (ae.getSource() ==  menuItems[1]) 
      TetristryApp.instructionsPanel();
    else if (ae.getSource() ==  menuItems[2]) 
    {
      TetristryApp.highScorePanel();
      TetristryApp.h.displayHighscore();
    }
    else if (ae.getSource() ==  menuItems[3]) 
      TetristryApp.tutorialPanel ();
    else
    {
      JOptionPane.showMessageDialog (TetristryApp.m, "Thanks for playing Tetristry!", "Good Bye", JOptionPane.INFORMATION_MESSAGE);
      System.exit (0);
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