import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * This is the driver class for Tetristry.
 * This will set up the frame and all the panel classes.
 * Extends JFrame, implements ActionListener.
 * 
 * @author Richard Dang and Benson Guo
 * @version 2.5, May 15th, 2014
 */
public class TetristryApp extends JFrame implements ActionListener
{
  /*
   * Stores user choice when asked to quit.
   **/
  static int result; 
  /**
   * Allows access to TetristryApp methods.
   */
  static TetristryApp t;
  /**
   * Allows access to Menu methods.
   */
  static Menu m;
  /**
   * Allows access to LevelSelectiontryApp methods.
   */
  static LevelSelection l;
  /**
   * Allows access to HighScorepp methods.
   */
  static HighScore h;
  /**
   * Allows access to Instructions methods.
   */
  static Instructions i;
  /**
   * Allows access to Tutorial methods.
   */
  static Tutorial u;
  /**
   * Allows access to GameBoard methods.
   */
  static GameBoard g;
  /**
   * Keeps track of the current panel the player is on.
   */
  static char currentPanel =' ' ;
  /**
   * Checks if the gameboard is paused or not.
   */
  static boolean pauseGB;
  
  
  /**
   * Constructor which sets up the file and help menubar, and the frame.
   * Shortcut keys are added to allow access to menu items.
   * An image is loaded into the background.
   * A frame is created, with size 800 by 800, and is not resizable.
   * An icon of the program is added.
   * The menubar items are instantiated and added.
   * The menubar is instatiated.
   * ActionListeners are added to the menubar items.
   * The frame is set visible.
   * A windowlistener is added.
   *
   * @param quitItem-reference variable for JMenuItem for "Quit".
   * @param highscoreItem-reference variable for JMenuItem for "Highscore".
   * @param printScoreItem-reference variable for JMenuItem for "Print Highscore".
   * @param helpMeItem-reference variable for JMenuItem for "Help Me".
   * @param instructionsItem-reference variable for JMenuItem for "Instructions".
   * @param tutorialItem-reference variable for JMenuItem for "Tutorial".
   * @param aboutItem-reference variable for JMenuItem for "About".
   * @param fileMenu-reference variable for JMenu item "File".
   * @param helpMenu-reference variable for JMenu item "Help".
   * @param myMenus-reference variable for JMenuBar item.
   * @param myIcon-reference variable for BufferedImage from File (".\\Images/Images\Icon.png").
   * @param windowListener-reference varaible for WindowListener.
   */
  public TetristryApp ()
  {
    super ("Tetristry");
    
    JMenuItem quitItem = new JMenuItem ("Quit");
    JMenuItem printScoreItem = new JMenuItem ("Print Highscore");
    JMenuItem highscoreItem = new JMenuItem ("Highscore");
    JMenuItem helpMeItem = new JMenuItem ("Help Me");
    JMenuItem instructionsItem = new JMenuItem ("Instructions");
    JMenuItem tutorialItem = new JMenuItem ("Tutorial");
    JMenuItem aboutItem = new JMenuItem ("About");
    
    JMenu fileMenu = new JMenu ("File");
    JMenu helpMenu = new JMenu ("Help");
    
    fileMenu.add (highscoreItem);
    fileMenu.add (printScoreItem);
    fileMenu.add (quitItem);
    helpMenu.add (helpMeItem);
    helpMenu.add (instructionsItem);
    helpMenu.add (tutorialItem);
    helpMenu.add (aboutItem);
    
    JMenuBar myMenus = new JMenuBar ();
    
    aboutItem.setAccelerator (KeyStroke.getKeyStroke ('A', KeyEvent.CTRL_DOWN_MASK));
    highscoreItem.setAccelerator (KeyStroke.getKeyStroke ('H', KeyEvent.CTRL_DOWN_MASK));
    printScoreItem.setAccelerator (KeyStroke.getKeyStroke ('P', KeyEvent.CTRL_DOWN_MASK));
    helpMeItem.setAccelerator (KeyStroke.getKeyStroke ('M', KeyEvent.CTRL_DOWN_MASK));
    tutorialItem.setAccelerator (KeyStroke.getKeyStroke ('T', KeyEvent.CTRL_DOWN_MASK));
    instructionsItem.setAccelerator (KeyStroke.getKeyStroke ('I', KeyEvent.CTRL_DOWN_MASK));
    quitItem.setAccelerator (KeyStroke.getKeyStroke ('Q', KeyEvent.CTRL_DOWN_MASK));
    
    highscoreItem.addActionListener (this);
    instructionsItem.addActionListener (this);
    quitItem.addActionListener (this);
    printScoreItem.addActionListener (this);
    helpMeItem.addActionListener (this);
    aboutItem.addActionListener (this);
    tutorialItem.addActionListener (this);
    
    try
    {
      BufferedImage myIcon = ImageIO.read (new File ("../Images/Icon.png"));
      setIconImage (myIcon);
      
    }
    catch (IOException e)
    {
    }
    
    myMenus.add (fileMenu);
    myMenus.add (helpMenu);
    setJMenuBar (myMenus);
    setSize (800, 825);
    setVisible (true);
    setResizable (false);
    this.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
    
    WindowListener windowListener = new WindowAdapter ()
    {
      
      /**
       *The windowClose method overrides the default close operation. It asks if the user really wants to quit.
       *
       * @param e-reference variable for WindowEvent.
       */
      public void windowClosing (WindowEvent e)
      {
        result = JOptionPane.showConfirmDialog (TetristryApp.m, "Are you sure?", "Quit?", JOptionPane.YES_NO_OPTION);
        if (result == 0)
          System.exit (0);
      }
    }
    ;
    this.addWindowListener (windowListener);
  }
  
  
  /** The actionPerformed method calls methods based on the menubar choices clicked by the user through an if statement.
    * If the "Quit" option is clicked the program will exit.
    * If the "Tutorial" option is clicked the program will display the tutorial panel.
    * If the "About" option is clicked the program will pause the game and display the about dialog.
    * If the "Instructions" option is clicked the program will display the instructions panel.
    * If the "Highscore" option is clicked the program will display the highscore panel.
    * If the "Print Highscore" option is clicked the program will pause the game and ask the user to confirm.
    * If the "Help Me" option is clicked the program will display the CHM Help file.
    * 
    * @param ae-reference variable for ActionEvent.
    */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getActionCommand ().equals ("Quit"))
    {
      result = JOptionPane.showConfirmDialog (TetristryApp.m, "Are you sure?", "Quit?", JOptionPane.YES_NO_OPTION);
      if (result == 0)
        System.exit (0);
    }
    else if (ae.getActionCommand ().equals ("Tutorial"))
      tutorialPanel ();
    
    else if (ae.getActionCommand ().equals ("About"))
    {
      if (currentPanel == 'g')
      {
        GameBoard.t.stop ();
        GameBoard.l.t.stop();
        new About ();
      }
      else 
        new About ();
    }
    
    else if (ae.getActionCommand ().equals ("Instructions"))
      instructionsPanel();
    
    else if (ae.getActionCommand ().equals ("Highscore"))
      highScorePanel();
    
    else if (ae.getActionCommand ().equals ("Print Highscore"))
    {
      if (currentPanel =='g')
      {
        GameBoard.t.stop ();
        GameBoard.l.t.stop();
        h.printHighscore();
        GameBoard.t.start ();
        GameBoard.l.t.start();
      }
      else 
        h.printHighscore ();
    }
    
    else
    {
      File file = new File("../HelpFile/Tetristry Help-Me.chm");
      try
      {
        Runtime.getRuntime().exec("HH.EXE ms-its:" + file.getAbsolutePath() + "");
      } 
      catch (IOException e)
      { 
      }
    }
  }
  
  
  /**
   * Static method used by the Menu class to remove the current panel, 
   * access the level selection panel and set the current panel as the level panel. 
   */
  static public void levelPanel ()
  {
    changePanel (currentPanel);
    l = new LevelSelection ();
    currentPanel = 'l';
    t.add (l);
    l.repaint ();
    l.revalidate ();
  }
  
  
  /**
   * Static method used by the Menu class to remove the current panel, 
   * access the game panel and set the current panel as the game panel. 
   */
  static public void gamePanel ()
  {
    changePanel (currentPanel);
    g = new GameBoard ();
    currentPanel = 'g';
    t.add (g);
    g.repaint ();
    g.revalidate ();
  }
  
  /**
   * Static method that pauses the gameboard if the user tries to access a menu item.
   * If they select 'no' on the confirmation dialog, the game resumes.
   */
  static public void pauseResumeGameBoard ()
  {
    if (pauseGB==true)
    {
      GameBoard.wasPaused=GameBoard.paused;
      GameBoard.t.stop ();
      GameBoard.l.t.stop();
      GameBoard.paused=true;
    }
    else
    {
      if (!GameBoard.wasPaused)
      {
        GameBoard.t.start ();
        GameBoard.l.t.start();
        GameBoard.paused=false;
      }
    }
  }
  
  
  /**
   * Static method used by the Menu class to remove the current panel, 
   * access the highscore panel and set the current panel as the highscore panel. 
   * Pauses the game if the player is current in the game panel and asks them
   * if they want to end their current game. If not, the game resumes.
   */
  static public void highScorePanel ()
  { 
    if (currentPanel == 'g')
    {
      pauseGB = true;
      pauseResumeGameBoard ();
      result = JOptionPane.showConfirmDialog (TetristryApp.t, "Go to highscores?", "End Game?", JOptionPane.YES_NO_OPTION);
      if (result == 0)
      {
        Time.t.stop ();
        changePanel (currentPanel);
        t.add (h);
        currentPanel = 'h';
        h.repaint ();
        h.revalidate ();
      }
      else
      {
        pauseGB = false;
        pauseResumeGameBoard ();
      }
    }
    else 
    {
      changePanel (currentPanel);
      t.add (h);
      currentPanel = 'h';
      h.repaint ();
      h.revalidate ();
    }
  }
  
  
  /**
   * Static method used by the Menu class to remove the current panel, 
   * access the instructions panel and set the current panel as the instructions panel. 
   * Pauses the game if the player is current in the game panel and asks them
   * if they want to end their current game. If not, the game resumes.
   */
  static public void instructionsPanel ()
  {
    if (currentPanel == 'g')
    {
      pauseGB = true;
      pauseResumeGameBoard ();
      result = JOptionPane.showConfirmDialog (TetristryApp.t, "Go to instructions?", "End Game?", JOptionPane.YES_NO_OPTION);
      if (result == 0)
      {
        Time.t.stop ();
        changePanel (currentPanel);
        i = new Instructions ();
        currentPanel = 'i';
        t.add (i);
        i.repaint ();
        i.revalidate ();
      }
      else
      {
        pauseGB = false;
        pauseResumeGameBoard ();
      }
    }
    else
    {
      changePanel (currentPanel);
      i = new Instructions ();
      currentPanel = 'i';
      t.add (i);
      i.repaint ();
      i.revalidate ();
    }
  }
  
  /**
   * Static method used by the Menu class to remove the current panel, 
   * access the tutorial panel and set the current panel as the tutorial panel. 
   * Pauses the game if the player is current in the game panel and asks them
   * if they want to end their current game. If not, the game resumes.
   */
  static public void tutorialPanel ()
  {
    if (currentPanel == 'g')
    {
      pauseGB = true;
      pauseResumeGameBoard ();
      result = JOptionPane.showConfirmDialog (TetristryApp.t, "Go to tutorial?", "End Game?", JOptionPane.YES_NO_OPTION);
      if (result == 0)
      {
        Time.t.stop ();
        changePanel (currentPanel);
        u = new Tutorial ();
        currentPanel = 'u';
        t.add (u);
        u.repaint ();
        u.revalidate ();
      }
      else
      {
        pauseGB = false;
        pauseResumeGameBoard ();
      }
    }
    else
    {
      changePanel (currentPanel);
      u = new Tutorial ();
      currentPanel = 'u';
      t.add (u);
      u.repaint ();
      u.revalidate ();
    }
  }
  
  
  /**
   * Static method used to remove the current panel.
   */
  static public void changePanel (char panel)
  {
    if (panel == 'l')
      t.remove (l);
    else if (panel == 'h')
      t.remove (h);
    else if (panel == 'i')
      t.remove (i);
    else if (panel == 'u')
      t.remove (u);
    else if (panel == 'g')
      t.remove (g);
    else 
      t.remove (m);
  }
  
  /**
   * Static method used by panel classes to remove the current panel, 
   * access the menu panel and set the current panel as the menu panel. 
   */
  static public void menuPanel ()
  {
    if (currentPanel != ' ')
      changePanel (currentPanel);
    m = new Menu ();
    currentPanel = 'm';
    t.add (m);
    m.repaint ();
    m.revalidate ();
  }
  
  
  /**
   * Static method used display the splashscreen on the 
   * intial launch of the program.
   */
  static public void splashScreen ()
  {
    SplashScreen s = new SplashScreen();
    t.add(s);
    s.repaint();
    s.revalidate();
    s.run();
    t.remove (s);
  }
  
  /**
   * Constructs the Driver class and all the panel classes
   * which begins with the splash screen then the main menu.
   */
  public static void main (String[] args)
  {
    t = new TetristryApp ();
    h = new HighScore ();
    //splashScreen ();
    menuPanel (); 
  }
}
