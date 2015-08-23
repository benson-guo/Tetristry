import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * This manages and manipulates the highscore of the game.
 * The highscore will be dispalyed on the JPanel through a JLabel.
 *
 * @author Richard Dang and Benson Guo 
 * @version 2.3, May 16th, 2014
 */

public class HighScore extends JPanel implements ActionListener
{
  /**
   * String ArrayList to store the names of the highscores from highest to lowest.
   **/
  private ArrayList < String > highscoreNames = new ArrayList < String > ();
  /**
   * Integer ArrayList to store the highscore values from highest to lowest.
   **/
  private ArrayList < Integer > highscoreValues = new ArrayList < Integer > ();
  /**
   * String ArrayList to store the highscore levels from highest to lowest.
   **/
  private ArrayList < String > highscoreLevels = new ArrayList < String > ();
  /**
   * Reference variable for BufferedImage used for background.
   **/
  private BufferedImage backgroundImage;
  /**
   * Reference variable for JButton used to print highscores.
   **/
  private JButton printButton;
  /**
   * Reference variable for JButton used to return to main menu.
   **/
  private JButton returnButton;
  /**
   * Reference variable for JButton used to reset highscores.
   **/
  private JButton resetButton;
  /**
   * Reference variable for PrintWriter used to save highscores to text file.
   **/
  private PrintWriter out;
  /**
   * int variable used to store user choice.
   **/
  private int result;
  /**
   * Reference variable for JLabel for highscore information.
   **/
  private JLabel scoreName;
  
  /**
   *Constructor which sets up the JPanel for the highscores.
   * The layout is set to null.
   * Sets up the background, guis, highscore ArrayLists, and diaplays the highscores.
   */
  public HighScore ()
  {
    setLayout (null);
    drawBackground ();
    highscoreOptions ();
    readHighscore ();
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
      backgroundImage = ImageIO.read (new File ("../Images/HSBackground.png"));
    }
    catch (IOException e)
    {
    }
  }
  
  
  /**
   * The readHighscore method reads the highscore list from a text file ("Highscore.rbcs") with Scanner.
   * The text is read within a try block to catch for IOExceptions and NoSuchElementExceptions.
   * If the header is not valid, a new highscore file will be created.
   *
   * @param s-reference variable for Scanner.
   * @param x-int variable for loop to read text.
   * @param e-variable to catch for IOException and NoSuchElementException.
   * @throws IOException Thrown to indication a error when reading from highscore file.
   * @throws NoSuchElementException Thrown to indication a error when reading from highscore file.
   */
  public void readHighscore ()
  {
    try
    {
      Scanner s = new Scanner (new File ("../Highscore/Highscore.rbcs"));
      if (s.nextLine ().equals ("Richard Benson Chemistry Software Inc."))
      {
        for (int x = 0 ; x < 10 ; x++)
        {
          highscoreNames.add (s.next ());
          highscoreLevels.add (s.next ());
          highscoreValues.add (s.nextInt ());
        }
      }
      else
        createHighscoreFile ();
    }
    catch (IOException e)
    {
      createHighscoreFile ();
    }
    catch (NoSuchElementException e)
    {
    }
  }
  
  /**
   * Creates a new highscore with extension rbcs, and a header.
   * 
   *  @param e-variable to catch for IOException and NoSuchElementException.
   *  @throws IOException Thrown to indication a error when writing to file.
   */
  public void createHighscoreFile ()
  {
    try
    {
      out = new PrintWriter (new FileWriter ("../Highscore/Highscore.rbcs"));
      out.println ("Richard Benson Chemistry Software Inc.");
      out.close ();
    }
    catch (IOException e)
    {
    }
  }
  
  
  /**
   * The saveHighscore method saves the highscore list to a text file ("Highscore.rbcs") with PrintWriter.
   * The text is written within a try block to catch for IOExceptions.
   *
   * @param out-reference variable for PrintWriter.
   * @param x-int variable for loop to write text.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when writing to highscore file.
   */
  public void saveHighscore ()
  {
    try
    {
      out = new PrintWriter (new FileWriter ("../Highscore/Highscore.rbcs"));
      out.println ("Richard Benson Chemistry Software Inc.");
      for (int x = 0 ; x < highscoreNames.size () ; x++)
      {
        out.println (highscoreNames.get (x) + " " + highscoreLevels.get (x) + " " + highscoreValues.get (x));
      }
      out.close ();
    }
    catch (IOException e)
    {
    }
  }
  
  
  /**
   * The insertScore method inserts a score into the highscores, if the score is high enough, which is checked with an if statement.
   * The loop goes through the ArrayList of scores from highest to lowest, and inserts at the index if the new score is higher.
   * If the score is lower than the 10th score, than no action is done.
   * The panel is updated with the new highscores.
   *
   * @param x-int variable for loop to go through score ArrayList.
   * @param score-int variable passed in for score.
   * @param name-String variable passed in for player name.
   * @param level-String varaible passed in for level.
   */
  public void insertScore (int score, String name, String level)
  {
    for (int x = 0 ; x < highscoreValues.size () ; x++)
    {
      if (score > highscoreValues.get (x))
      {
        highscoreValues.add (x, score);
        highscoreLevels.add (x, level);
        highscoreNames.add (x, name);
        break;
      }
      if (highscoreValues.size () < 10 && x==highscoreValues.size()-1&&score < highscoreValues.get (x))
      {
        highscoreValues.add (score);
        highscoreLevels.add (level);
        highscoreNames.add (name);
      }
    }
    if ( highscoreValues.size() == 0)
    {
      highscoreValues.add (score);
      highscoreLevels.add (level);
      highscoreNames.add (name);
    }
    
    saveHighscore();
    removeAll ();
    updateUI ();
    highscoreOptions ();
    displayHighscore ();
    repaint ();
  }
  
  
  /**
   * The displayHighscore method displays the highscore list on the panel.
   * The title and header JLabels and set to the required locations.
   * A for loop goes through the 3 instance ArrayLists and displays the score information on the screen through a JLabel.
   * An if statement checks to ensure that the highscore file is not corrupted. 
   *
   * @param scoreName-reference variable for JLabel for score information.
   * @param rank-int varaible for rank.
   * @param font-reference variable for Font of score information.
   * @param x-int variable for for loop.
   * @param y-int variable for for loop.
   * @param z-int variable for for loop.
   */
  public void displayHighscore ()
  {
    if (highscoreValues.size()!=highscoreNames.size()||highscoreLevels.size()!=highscoreValues.size())
    {
      JOptionPane.showMessageDialog (TetristryApp.h, "The highscore file is corrupted. A new file has been created.", "File Corrupted", JOptionPane.ERROR_MESSAGE);
      highscoreValues.clear();
      highscoreNames.clear();
      highscoreLevels.clear();
    }
    scoreName = null;
    Font font = new Font ("Arial", Font.PLAIN, 30);
    int rank = 1;
    for (int x = 0 ; x < 10 ; x++)
    {
      if (x < highscoreNames.size ())
      {
        for (int z = 0 ; z < 4 ; z++)
        {
          if (z == 0)
          {
            scoreName = new JLabel ("       " + rank);
            rank++;
          }
          else if (z == 1)
            scoreName = new JLabel (highscoreNames.get (x));
          else if (z == 2)
            scoreName = new JLabel ("" + highscoreLevels.get (x));
          else
            scoreName = new JLabel ("" + highscoreValues.get (x));
          
          scoreName.setFont (font);
          scoreName.setForeground (Color.WHITE);
          scoreName.setBounds (new Rectangle (new Point (20 + z * 210, 208 + 47 * x), scoreName.getPreferredSize ()));
          add (scoreName);
        }
      }
      else
      {
        for (int y = 0 ; y < 4 ; y++)
        {
          if (y == 0)
          {
            scoreName = new JLabel ("       " + rank);
            rank++;
          }
          else
            scoreName = new JLabel ("   -------");
          
          scoreName.setFont (font);
          scoreName.setForeground (Color.WHITE);
          scoreName.setBounds (new Rectangle (new Point (20 + 210 * y, 208 + 47 * x), scoreName.getPreferredSize ()));
          add (scoreName);
        }
        repaint();
      }
    }
  }
  
  
  /**
   * The highscoreOptions method sets up the background and buttons.
   *
   * @param print-BufferedImage variable for print button.
   * @param returns-BufferedImage variable for return to menu button.
   * @param reset-BufferedImage variable for reset highscore button.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */
  public void highscoreOptions ()
  {
    try
    {
      BufferedImage print = ImageIO.read (new File ("../Images/PrintButton.png"));
      BufferedImage returns = ImageIO.read (new File ("../Images/ReturnButton2.png"));
      BufferedImage reset = ImageIO.read (new File ("../Images/ResetButton.png"));
      
      printButton = new JButton (new ImageIcon (print));
      returnButton = new JButton (new ImageIcon (returns));
      resetButton = new JButton (new ImageIcon (reset));
      
      printButton.setBounds (new Rectangle (new Point (40, 680), printButton.getPreferredSize ()));
      returnButton.setBounds (new Rectangle (new Point (520, 680), returnButton.getPreferredSize ()));
      resetButton.setBounds (new Rectangle (new Point (280, 680), resetButton.getPreferredSize ()));
      
      printButton.setBorder (BorderFactory.createEmptyBorder ());
      returnButton.setBorder (BorderFactory.createEmptyBorder ());
      resetButton.setBorder (BorderFactory.createEmptyBorder ());
      
      printButton.setContentAreaFilled (false);
      returnButton.setContentAreaFilled (false);
      resetButton.setContentAreaFilled (false);
      
      add (printButton);
      add (returnButton);
      add (resetButton);
      
      printButton.addActionListener (this);
      returnButton.addActionListener (this);
      resetButton.addActionListener (this);
    }
    catch (IOException e)
    {
    }
  }
  
  
  /**
   * Implemented abstract method that prints the highscore or returns to menu based on the button that is clicked, through and if statement.
   *
   * @param ae-ActionEvent variable passed in.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource () == printButton)
      printHighscore ();
    else if (ae.getSource () == resetButton)
      resetHighscores ();
    else
      TetristryApp.menuPanel ();
  }
  
  /**
   * The resetHighscores method resets the highscore list and panel.
   */
  public void resetHighscores ()
  {
    if (highscoreNames.size () != 0)
    {
      result = JOptionPane.showConfirmDialog (TetristryApp.h, "Are you sure?", "Reset Highscores?", JOptionPane.YES_NO_OPTION);
      if (result == 0)
      {
        createHighscoreFile ();
        highscoreNames.clear ();
        highscoreValues.clear ();
        highscoreLevels.clear ();
        removeAll ();
        updateUI ();
        highscoreOptions ();
        displayHighscore ();
        repaint ();
        revalidate ();
      }
    }
    else
      JOptionPane.showMessageDialog (TetristryApp.h, "There are no highscores to reset!", "No Highscores", JOptionPane.WARNING_MESSAGE);
  }
  
  
  /**
   *This method prints the highscore list. It creates a Print class coded by Daniel Chen and prints a list of the highscores.
   * The printed text is formatted for for loops.
   *
   * @param p-reference variable for Print class.
   * @param x-int counter variable for loop.
   * @param y-int counter variable for loop.
   * @param line-String variable to store a line to be printed.
   * @param rank-String variable to store the rank of a score.
   */
  public void printHighscore ()
  {
    if (highscoreNames.size () != 0)
    {
      result = JOptionPane.showConfirmDialog (TetristryApp.h, "Are you sure?", "Print Highscores?", JOptionPane.YES_NO_OPTION);
      if (result == 0)
      {
        Print p = new Print ();
        p.println ("                Tetristry Highscores");
        p.println ("____________________________________________________");
        p.println (" Rank       Name           Level          Score");
        p.println ("____________________________________________________");
        String line = "";
        String rank = "";
        for (int x = 0 ; x < highscoreNames.size () ; x++)
        {
          rank = "  " + (x + 1) + "";
          line = rank;
          for (int y = 0 ; y < 10 - rank.length () ; y++)
            line += " ";
          line += highscoreNames.get (x);
          for (int y = 0 ; y < 17 - highscoreNames.get (x).length () ; y++)
            line += " ";
          line += highscoreLevels.get (x);
          for (int y = 0 ; y < 15 - highscoreLevels.get (x).length () ; y++)
            line += " ";
          line += highscoreValues.get (x);
          p.println (line);
        }
        p.startPrinting ();
      }
    }
    else
      JOptionPane.showMessageDialog (TetristryApp.h, "There are no highscores to print!", "No Highscores", JOptionPane.WARNING_MESSAGE);
    
    
  }
  
  /**
   * Overridden method that draws a background image onto the the JPanel.
   *
   * @param g-Graphics variable passed in as parameter.
   */
  @ Override
  protected void paintComponent (Graphics g)
  {
    super.paintComponent (g);
    if (backgroundImage != null)
      g.drawImage (backgroundImage, 0, 0, this.getWidth (), this.getHeight (), this);
  }
}
