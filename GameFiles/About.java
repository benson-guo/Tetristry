import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Creates an about dialog that will display information about the programmers of Tetristry.
 * 
 * @author Richard Dang
 * @version 1.1, May 22th, 2014
 */
public class About implements ActionListener
{
  /**
   * Reference variable for JDialog.
   */
  private JDialog d;
  /**
   * Reference variable for JLabel used for close dialog button.
   */
  private JButton continueButton;
  
  /**
   *Constructor calls the method to create the dialog.
   */
  public About ()
  {
    aboutDialog();
  }
  
  /**
   *This method creates and sets up the about dialog.The buttons and logo are read in from an image file.
   *The logo is enlarged and made transparent.
   * 
   * @param dLabel-reference variable for label to display text.
   * @param logo-BufferedImage variable for logo.
   * @param logoLabel-reference variable for JLabel for logo information. 
   * @param continues-BufferedImage variable for continue button.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */
  public void aboutDialog ()
  {
    d = new JDialog (TetristryApp.t, "About Us");
    d.setSize (500,300);
    d.setResizable(false);
    d.setLayout (null);
    
    JLabel dLabel = new JLabel ("Tetristry was created by Richard Dang & Benson Guo.");
    dLabel.setFont (new Font ("Arial", Font.BOLD, 16));
    dLabel.setBounds(new Rectangle(new Point(30, 20), dLabel.getPreferredSize())); 
    d.add (dLabel);
    d.setLocationRelativeTo (TetristryApp.t);
    d.setVisible (true);
    
    try
    {
      BufferedImage logo = ImageIO.read(new File( "../Images/RBCSLogo(Transparent).png"));
      JLabel logoLabel = new JLabel (new ImageIcon (logo));
      logoLabel.setBounds(new Rectangle(new Point(50, 60), logoLabel.getPreferredSize())); 
      d.add (logoLabel);
      
      BufferedImage continues = ImageIO.read(new File( "../Images/ContinueButton.png"));
      continueButton = new JButton(new ImageIcon(continues));
      continueButton.setBounds(new Rectangle(new Point(170, 200), continueButton.getPreferredSize())); 
      continueButton.setBorder(BorderFactory.createEmptyBorder());
      continueButton.setContentAreaFilled(false);
      d.add(continueButton);
      continueButton.addActionListener (this);
    }
    catch (IOException e)
    {}
  }
  
  /**
   * Implemented abstract method that closes the dialog when the continue button is clicked, through an if statement.
   * An if statement also checks if the user is in game, in which case the game will resume.
   * 
   * @param ae-ActionEvent variable passed in.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource() == continueButton) 
    {
      if (TetristryApp.currentPanel =='g')
      {
        GameBoard.t.start ();
        GameBoard.l.t.start();
      }
      d.dispose();
    }
  }
  
}