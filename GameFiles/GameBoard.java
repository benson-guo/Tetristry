import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import javax.swing.Timer;

/**
 *This class is the main game panel and in-game actions are processed here.
 * Extends JPanel
 * 
 * @author Richard Dang and Benson Guo
 * @version 3.4, May 22th, 2014
 */
public class GameBoard extends JPanel implements ActionListener, KeyListener
{  
  /*
   * BufferedImage variable for background image.
   */
  private BufferedImage backgroundImage;
  /*
   *backgroundImage2-BufferedImage variable for 2nd background image of panel.
   **/
  private BufferedImage backgroundImage2;
  /*
   *backgroundImage2-BufferedImage variable for 2nd background image of panel.
   **/
  private BufferedImage backgroundImage3;
  /*
   * Array of JButtons for gameboard buttons.
   */
  JButton [] menuItems = new JButton [4];
  /*
   * JButton for name input button.
   */
  private JButton okButton;
  /*
   * JLabel displayed when compound is named correctly.
   */
  private JLabel correctLabel;
  /*
   * JLabel displayed when compound is named incorrectly.
   */
  private JLabel incorrectLabel;
  /*
   * JTextField for user to input compound name.
   */
  private JTextField tField;
  /*
   * JLabel to ask the user to name the compound.
   */
  private JLabel compoundLabel;
  /*
   * JLabel to display user score.
   */
  private JLabel scoreLabel;
  /*
   * String for the correct compound Name.
   */
  private String compoundName;
  /*
   * String for the difficulty.
   */
  private String level;
  /*
   * Static int variable to store user score.
   */
  static int score;
  /*
   * boolean variable to store whether the game is paused or not.
   */
  static boolean paused;
  /*
   * boolean variable to store whether the game was paused or not.
   */
  static boolean wasPaused; 
  /*
   * boolean variable to store whether the game is asking the user to name a compound.
   */
  private boolean askingCompound=false;
  /*
   *int variable to store the column of the element adjacent to controlLabel.
   */
  private int elementx2;
  /*
   *int variable to store the row of the element adjacent to controlLabel.
   */
  private int elementy2;
  /*
   *double variable to store the multiplier for score, based of level difficulty.
   */
  private double scoreMultiplier;
  /*
   *reference variable for JDialog
   */
  private JDialog d;
  /*
   *int variable to store whether or not the game is over.
   */
  private boolean gameOver;
  /*
   * 2d array of Element objects, which represents the board.
   */
  private Element[][] elementsOnBoard=new Element [12][9];
  /*
   * 2d array of JLabel objects, which represents the pictures of the elements on the board.
   */
  private JLabel [][] labelsOnBoard=new JLabel [12][9]; 
  /*
   * reference variable for the JLabel that is being moved by the arrow keys.
   */
  private JLabel controlLabel;
  /*
   * int variable for column of the JLabel that is being moved by the arrow keys.
   */
  private int controlLabelCol=4;
  /*
   * int variable for column of the JLabel that is being moved by the arrow keys.
   */
  private int controlLabelRow=11;
  /*
   * static reference variable for Timer object.
   */
  static Timer t;
  /*
   * static reference variable for Time object.
   */
  static Time l;
  /*
   * reference variable for ElementList object.
   */
  private ElementList e;
  /*
   * reference variable for KeyAdapter object for enter key.
   */
  private KeyAdapter keyAdapter;
  /*
   * String variable for compound symbol of element.
   */
  private String compoundSymbol;
  /*
   * int variable for number of questions answered correctly.
   */
  private int numCorrect;
  /*
   * int variable for number of questions answered incorrectly.
   */
  private int numWrong;
  /*
   * int variable for user choice.
   */
  private int result;
  /*
   * JButton variable for continue button.
   */
  private JButton continueButton;
  
  /*
   * Constructor for GameBoard which sets up the background, guis,and level.
   * ElementList object is instantiated and the key listener is added.
   * score, numCorrect, and numWrong are intialized to 0.
   */
  public GameBoard ()
  {
    numCorrect=0;
    numWrong=0;
    setLayout (null);
    addKeyListener (this);
    drawBackground ();
    gameOptions ();   
    displayInfo ();
    rightOrWrong();
    
    e=new ElementList ();
    l = new Time ();
    add(l.time);
    newElement();
    repaint ();
    
    if (LevelSelection.difficulty == 1)
      scoreMultiplier=0.8;
    else if (LevelSelection.difficulty == 2)
      scoreMultiplier=1;
    else 
      scoreMultiplier=1.2;
    if (LevelSelection.difficulty == 1)
      t = new Timer(500, this);
    else if (LevelSelection.difficulty == 2)
      t = new Timer(250, this);
    else
      t = new Timer(125, this);
    
    t.setInitialDelay(0);
    t.start(); 
    score=0;
    updateScore();
    paused=false;
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
      backgroundImage = ImageIO.read(new File( "../Images/GBackground.png"));
      backgroundImage2 = ImageIO.read(new File( "../Images/GBackground2.png"));
      backgroundImage3 = ImageIO.read(new File( "../Images/GBackground3.png"));
    }
    catch (IOException e) 
    {}
  }
  
  /**
   *This method reads the images used for correct and wrong naming of compounds.The labels are hidden on the panel until the user answers a question.
   * 
   * @param correct-BufferedImage for correct picture.
   * @param incorrect-BufferedImage for incorrect picture.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */ 
  public void rightOrWrong ()
  {
    try
    {
      BufferedImage correct = ImageIO.read(new File( "../Images/Correct.png"));
      BufferedImage incorrect = ImageIO.read(new File( "../Images/Incorrect.png"));
      correctLabel = new JLabel (new ImageIcon (correct));
      incorrectLabel = new JLabel (new ImageIcon (incorrect));
      correctLabel.setBounds(new Rectangle(new Point(20, 690), correctLabel.getPreferredSize())); 
      incorrectLabel.setBounds(new Rectangle(new Point(20,690), incorrectLabel.getPreferredSize()));     
      add(correctLabel);
      add(incorrectLabel);
      correctLabel.setVisible (false);
      incorrectLabel.setVisible (false);
    }
    catch (IOException e)
    {}
  }
  
  /**
   *This method sets up the gui buttons for the GameBoard. They are stored in the arrayList menuItems.
   * 
   * @param spacing-int variabel for button spacing.
   * @param counter-counter variable for button spacing.
   * @param news-BufferedImage for new game button.
   * @param resume-BufferedImage for resume button.
   * @param pause-BufferedImage for pause button.
   * @param end-BufferedImage for end button.
   * @param x-JButton variable for for loop.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */ 
  public void gameOptions ()
  {
    int spacing = 0;
    int counter = 0;
    try
    {  
      //create images and resize
      BufferedImage news = ImageIO.read(new File( "../Images/NewGameButton.png"));
      BufferedImage resume = ImageIO.read(new File( "../Images/ResumeButton.png"));
      BufferedImage pause = ImageIO.read(new File( "../Images/PauseButton.png"));
      BufferedImage end = ImageIO.read(new File( "../Images/EndGameButton.png"));
      
      menuItems[0]=new JButton(new ImageIcon(news));
      menuItems[1]=new JButton(new ImageIcon(resume));
      menuItems[2]=new JButton(new ImageIcon(pause));
      menuItems[3]=new JButton(new ImageIcon(end));
      
      
      for (int x = 0; x<4;x++)
      {
        menuItems[x].setBounds(new Rectangle(new Point(540, 410+spacing),  menuItems[x].getPreferredSize())); 
        menuItems[x].setBorder(BorderFactory.createEmptyBorder());
        menuItems[x].setContentAreaFilled(false);
        add( menuItems[x]);
        menuItems[x].addActionListener (this);
        counter++;
        if (counter == 2)
        {
          menuItems[x].setVisible (false);
          menuItems[x].setEnabled (false);
        }
        else
          spacing +=80;
      }
    }
    catch (IOException e) 
    {} 
  }
  
  /**
   *This method sets up the labels on the gameboard, which display information to user about the level, time left, and score.
   * The JTextField is also set up.
   * The labels are stored in an array.
   * 
   * @param spacing-int variabel for label spacing
   * @param labels-array of JLabels to store labels.
   * @param ok-BufferedImage for ok button.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */ 
  public void displayInfo ()
  {
    int spacing = 0;
    JLabel [] labels = new JLabel [3];
    tField = new JTextField (27);
    tField.setBounds(new Rectangle(new Point(90, 705), tField.getPreferredSize())); 
    add (tField);
    
    if (LevelSelection.difficulty == 1)
      level = "Easy";
    else if (LevelSelection.difficulty == 2)
      level = "Medium";
    else 
      level = "Hard";
    
    labels[0]=new JLabel ("Difficulty: " + level);
    labels[1]=new JLabel ("Time Left: " );
    labels[2]=new JLabel ("Score: ");
    
    for (int x = 0; x<3; x++)
    {
      labels[x].setFont (new Font ("Arial", Font.BOLD, 22));
      labels[x].setForeground (Color.WHITE);
      labels[x].setBounds(new Rectangle(new Point(550, 100+spacing), labels[x].getPreferredSize())); 
      add (labels[x]);
      spacing +=60;
    } 
    try{ 
      BufferedImage ok = ImageIO.read(new File( "../Images/OKButton.png")); 
      okButton = new JButton(new ImageIcon(ok)); 
      okButton.setBounds(new Rectangle(new Point(390, 695), okButton.getPreferredSize()));  
      okButton.setBorder(BorderFactory.createEmptyBorder()); 
      okButton.setContentAreaFilled(false); 
      add(okButton); 
      okButton.addActionListener (this); 
      okButton.setEnabled (false);
      tField.setEditable (false);
    } 
    catch (IOException e) 
    {} 
    repaint();
  }
  
  /*
   * This method updates the score label with the new score value.
   */
  public void updateScore()
  {
    if (scoreLabel!=null)
      remove (scoreLabel);
    scoreLabel=new JLabel (""+score);
    scoreLabel.setFont (new Font ("Arial", Font.BOLD, 22));
    scoreLabel.setForeground (Color.WHITE);
    scoreLabel.setBounds(new Rectangle(new Point(630, 220), scoreLabel.getPreferredSize())); 
    add (scoreLabel);
  }
  
  /*
   * This method inserts the user score into the highscores.
   */
  public void saveScore ()
  {
    TetristryApp.h.insertScore(score, PlayerName.playerName,level);
  }
  
  /**
   * This method drops the controlLabel by one row if possible.
   * It is a boolean return method and returns true if the element has been dropped, else if returns false.
   * 
   * @return true if the there is an open location below the dropping piece, otherwise false
   */
  public boolean drop ()
  {
    if (controlLabel==null)
      return false;
    if (controlLabelRow>0&&labelsOnBoard[controlLabelRow-1][controlLabelCol]==null)
    {
      labelsOnBoard[controlLabelRow-1][controlLabelCol]=labelsOnBoard[controlLabelRow][controlLabelCol];
      labelsOnBoard[controlLabelRow][controlLabelCol]=null;
      elementsOnBoard[controlLabelRow-1][controlLabelCol]=elementsOnBoard[controlLabelRow][controlLabelCol];
      elementsOnBoard[controlLabelRow][controlLabelCol]=null;
      controlLabelRow--;
      repaintBoard();
      return true;
    }
    return false;
  }
  
  /**
   * This method adds two elements barriers by the passed in co-ordinates.
   * 
   * @param barrier1-JLabel for 1st barrier.
   * @param barrier2-JLabel for 2nd barrier.
   * @param x1-int variable for 1st barrier's column passed in.
   * @param y1-int variable for 1st barrier's row passed in.
   * @param x2-int variable for 2nd barrier's column passed in.
   * @param y2-int variable for 2nd barrier's row passed in.
   */
  public void addBarriers (int x1, int y1, int x2, int y2)
  {
    elementsOnBoard[y1][x1]=new Element ("Barrier");
    elementsOnBoard[y2][x2]=new Element ("Barrier");
    JLabel barrier1 = new JLabel (new ImageIcon (elementsOnBoard[y1][x1].getImage()));
    JLabel barrier2 = new JLabel (new ImageIcon (elementsOnBoard[y2][x2].getImage()));
    remove(labelsOnBoard [y1][x1]);
    remove(labelsOnBoard [y2][x2]);
    labelsOnBoard [y1][x1]= barrier1;
    labelsOnBoard [y2][x2]= barrier2;
    add (labelsOnBoard [y1][x1]);
    add (labelsOnBoard [y2][x2]);    
  }
  
  /**
   * This method removes the two elements specified by the passed in co-ordinates. Elements above the removed elements are shifted down accordingly.
   * 
   * @param end-int variable for row to start shifting elements down from.
   * @param temp-temporary int variable to store col/row value.
   * @param y-int counter variable for loop representing row.
   * @param x1-int variable for 1st element's column passed in.
   * @param y1-int variable for 1st element's row passed in.
   * @param x2-int variable for 2nd element's column passed in.
   * @param y2-int variable for 2nd element's row passed in.
   */
  public void removeElements(int x1, int y1, int x2, int y2)
  {   
    int temp=0;
    int end=0;
    if (y1<y2)
    {
      temp=x1;
      x1=x2;
      x2=temp;
      temp=y1;
      y1=y2;
      y2=temp;
    }
    
    elementsOnBoard[y1][x1]=null;
    elementsOnBoard[y2][x2]=null;
    remove(labelsOnBoard [y1][x1]);
    remove(labelsOnBoard [y2][x2]);
    labelsOnBoard [y1][x1]=null;
    labelsOnBoard [y2][x2]=null;
    
    end =y1;
    for (int y=end+1;y<12;y++)
    {
      if (labelsOnBoard[y][x1]!=null)
      {
        if (elementsOnBoard[y][x1].getName().equals("Barrier"))
          break;
        labelsOnBoard[y-1][x1]=labelsOnBoard[y][x1];
        labelsOnBoard[y][x1]=null;
        elementsOnBoard[y-1][x1]=elementsOnBoard[y][x1];
        elementsOnBoard[y][x1]=null;
      }
    }
    end=y2;
    for (int y=end+1;y<12;y++)
    {
      if (labelsOnBoard[y][x2]!=null)
      {
        if (elementsOnBoard[y][x2].getName().equals("Barrier"))
          break;
        labelsOnBoard[y-1][x2]=labelsOnBoard[y][x2];
        labelsOnBoard[y][x2]=null;
        elementsOnBoard[y-1][x2]=elementsOnBoard[y][x2];
        elementsOnBoard[y][x2]=null;
      }
    }
  }
  
  /**
   * This method repaints the whole board based on the 2d JLabel array.
   * A nested for loop goes through and sets the position of the label if it is not null.
   * 
   * @param y-int counter variable for loop representing row.
   * @param x-int counter variable for loop representing column.
   */
  public void repaintBoard()
  {
    for (int y=0;y<labelsOnBoard.length;y++)
    {
      for (int x=0;x<labelsOnBoard[0].length;x++)
      {
        if (labelsOnBoard[y][x]!=null)
          labelsOnBoard[y][x].setBounds(new Rectangle(new Point(48+50*x, -20+50*(12-y)),  labelsOnBoard[y][x].getPreferredSize()));         
        
      }
    }
    repaint();
  }
  
  /**
   * This method focuses the Key Listener on the panel.
   */
  public void addNotify()
  {
    super.addNotify();
    requestFocus();
  }
  
  /**
   * This method pauses or resumes the game based on the current state through an if statement.
   */
  public void pauseResume ()
  {
    if (!paused)
    {
      tField.setEditable (false);
      okButton.setEnabled (false);
      t.stop ();
      l.t.stop();
      paused=true;
    }
    else 
    {
      tField.setEditable (true);
      okButton.setEnabled (true);
      t.start();
      l.t.start();
      paused=false;
    }
    addNotify();
  }
  
  
  /**
   * Overridden method that moves the controlLabel based on the arrow keys that are pressed.
   * If the down key is pressed the element drops to the end of the column.
   * The 2d Element and JLabel array are changed according to the keyboard input.
   * The board is repainted.
   * 
   * @param e-KeyEvent variable passed in.
   * @param x-int counter variable for for loop.
   */
  public void keyPressed(KeyEvent e) 
  { 
    if (controlLabel==null|| paused || askingCompound)
      return;
    if (e.getKeyCode()== KeyEvent.VK_DOWN&&controlLabelRow>0)
    {
      for (int x = controlLabelRow-1;x>=0;x--)
      {
        if (labelsOnBoard [x][controlLabelCol] == null)
        {
          labelsOnBoard[x][controlLabelCol]=labelsOnBoard[controlLabelRow][controlLabelCol];
          labelsOnBoard[controlLabelRow][controlLabelCol]=null;
          elementsOnBoard[x][controlLabelCol]=elementsOnBoard[controlLabelRow][controlLabelCol];
          elementsOnBoard[controlLabelRow][controlLabelCol]=null;
          controlLabelRow=x;
        }
        else 
          break;
      }       
    }
    if (e.getKeyCode()== KeyEvent.VK_LEFT&&controlLabelCol>0&&labelsOnBoard[controlLabelRow][controlLabelCol-1]==null)
    {
      labelsOnBoard[controlLabelRow][controlLabelCol-1]=labelsOnBoard[controlLabelRow][controlLabelCol];
      labelsOnBoard[controlLabelRow][controlLabelCol]=null;
      elementsOnBoard[controlLabelRow][controlLabelCol-1]=elementsOnBoard[controlLabelRow][controlLabelCol];
      elementsOnBoard[controlLabelRow][controlLabelCol]=null;
      controlLabelCol--;
    }
    if (e.getKeyCode()== KeyEvent.VK_RIGHT&&controlLabelCol<8&&labelsOnBoard[controlLabelRow][controlLabelCol+1]==null)
    {
      labelsOnBoard[controlLabelRow][controlLabelCol+1]=labelsOnBoard[controlLabelRow][controlLabelCol];
      labelsOnBoard[controlLabelRow][controlLabelCol]=null;
      elementsOnBoard[controlLabelRow][controlLabelCol+1]=elementsOnBoard[controlLabelRow][controlLabelCol];
      elementsOnBoard[controlLabelRow][controlLabelCol]=null;
      controlLabelCol++;
    }
    repaintBoard();
  }
  
  /**
   * Overridden method for releasing keys.
   * 
   * @param e-KeyEvent variable passed in.
   */
  public void keyReleased(KeyEvent e) 
  { 
  }
  /**
   * Overridden method for typing keys.
   * 
   * @param e-KeyEvent variable passed in.
   */
  public void keyTyped(KeyEvent e) 
  {
  }
  
  /**
   * This method asks the user to name the compound formed. A keylistener is added to the textfield to moniter the enter key. 
   */
  public void askCompound ()
  {
    tField.setEditable (true);
    tField.setText ("");
    tField.requestFocusInWindow();
    okButton.setEnabled (true);
    incorrectLabel.setVisible (false);
    correctLabel.setVisible (false);
    askingCompound=true;
    compoundLabel = new JLabel ("What is the compound name for '" + compoundSymbol+"'?");
    compoundLabel.setFont (new Font ("Arial", Font.BOLD, 16));
    compoundLabel.setForeground (Color.WHITE);
    compoundLabel.setBounds(new Rectangle(new Point(90, 670), compoundLabel.getPreferredSize())); 
    add (compoundLabel);
    keyAdapter=new KeyAdapter()
    {    
      /**
       * This method overrides the keyPressed method to checkInput when the enter key is pressed.
       * 
       * @param f-variable passed in for KeyEvent
       */
      public void keyPressed(KeyEvent f)
      {  
        if(f.getKeyCode() == KeyEvent.VK_ENTER&&!paused)
          checkInput();
      }
    };
    tField.addKeyListener(keyAdapter);
    repaint();
  }
  
  /**
   * This method looks for nearby elements that can form compounds with the element the user is controlling. If possible, the askCompound method will be called to ask the user to name the compound.
   * 
   * @param adjacent-variable for adjacent element being checked.
   */
  public void checkCompound ()
  {
    //check if compound can be formed
    Element adjacent=null;
    if (controlLabelCol<8&&e.canForm(elementsOnBoard[controlLabelRow][controlLabelCol],elementsOnBoard[controlLabelRow][controlLabelCol+1]))
    {
      elementx2=controlLabelCol+1;
      elementy2=controlLabelRow;
    }
    else if (controlLabelRow>0&&e.canForm(elementsOnBoard[controlLabelRow][controlLabelCol],elementsOnBoard[controlLabelRow-1][controlLabelCol]))
    {
      elementx2=controlLabelCol;
      elementy2=controlLabelRow-1;
    }
    else if (controlLabelCol>0&&e.canForm(elementsOnBoard[controlLabelRow][controlLabelCol],elementsOnBoard[controlLabelRow][controlLabelCol-1]))
    {
      elementx2=controlLabelCol-1;
      elementy2=controlLabelRow;
    }
    else
    {
      return;
    }
    adjacent=elementsOnBoard[elementy2][elementx2];
    if (e.isMetal(elementsOnBoard[controlLabelRow][controlLabelCol]))
      compoundSymbol=elementsOnBoard[controlLabelRow][controlLabelCol].getName()+adjacent.getName();
    else 
      compoundSymbol=adjacent.getName()+elementsOnBoard[controlLabelRow][controlLabelCol].getName();
    askCompound();
  }
  
  /**
   * This method checks for the input of the user to see if they have named the compound correctly. Feedback will be given back to the user on the JTextField.
   * A time deduction is given if the user has named the compound incorrectly. The scores are updated.
   * 
   * @param compoundName-String variable for correct name of compound.
   */
  public void checkInput()
  { 
    String compoundName=e.checkCompound(compoundSymbol);
    if (tField.getText().equals(""))
      return;
    if (compoundName.replaceAll (" ","").equalsIgnoreCase(tField.getText().replaceAll (" ","")))
    {
      tField.setText ("That's correct, good job!");
      incorrectLabel.setVisible (false);
      correctLabel.setVisible (true);
      removeElements (controlLabelCol,controlLabelRow,elementx2,elementy2);
      repaintBoard();
      numCorrect++;
    }
    else 
    {
      tField.setText ("That's incorrect, '" +compoundName+ "' is the answer.");
      correctLabel.setVisible (false);      
      incorrectLabel.setVisible (true);
      addBarriers (controlLabelCol,controlLabelRow,elementx2,elementy2);
      repaintBoard();
      if (numWrong < numCorrect)
        numWrong++;
      
      if (LevelSelection.difficulty == 1)
        l.seconds-=3;
      else if (LevelSelection.difficulty == 2)
        l.seconds-=6;
      else 
        l.seconds-=9;
    }
    score=(int)(scoreMultiplier*(100*numCorrect-50*numWrong));
    if (score<0)
      score=0;
    
    updateScore();
    remove (compoundLabel);
    newElement();
    askingCompound=false;
    tField.removeKeyListener(keyAdapter);
    tField.setEditable (false);
    addNotify();    
    okButton.setEnabled (false);
  }
  
  /**
   * Implemented abstract method that processes ActionEvents.
   * If the continue button has been clicked, the game progresses the next level.
   * If the minutes and seconds has reached 0, the game will end and progress to the next level.
   * If the new game button has been clicked, the user will be asked to start a new game.
   * If the pause/resume buttons are clicked, the game will pause/resume.
   * If actionPerformed is triggered by the Timer, the current controlled element will drop or a new element will be created if the current element cannot drop any further. 
   * If the screen is filled with elements, the game will end.
   * 
   * @param ae-ActionEvent variable passed in.
   */
  public void actionPerformed (ActionEvent ae)
  {
    
    if (ae.getSource() == continueButton) 
    {
      d.dispose();
      if (gameOver)
      {
        TetristryApp.menuPanel ();
        gameOver =false;
      }
      else
      {
        LevelSelection.difficulty +=1;
        TetristryApp.gamePanel ();
      }
      return;
    }
    
    if (l.minutes==0 && l.seconds==0)
    {
      endGame ();
      nextLevel ();
    }
    
    if (ae.getSource() ==  menuItems[0]) 
    {
      t.stop ();
      l.t.stop ();
      result= JOptionPane.showConfirmDialog (TetristryApp.g, "Are you sure?", "New Game?", JOptionPane.YES_NO_OPTION);
      if (result==0)
        TetristryApp.gamePanel ();
      else
      {
        t.start ();
        l.t.start ();
        addNotify();
      }
      
    }
    else if (ae.getSource() ==  menuItems[1]) 
    {
      pauseResume();
      menuItems[1].setVisible (false);
      menuItems[1].setEnabled (false);
      menuItems[2].setVisible (true);
      menuItems[2].setEnabled (true);
    }
    else if (ae.getSource() ==  menuItems[2]) 
    {
      pauseResume();
      menuItems[2].setVisible (false);
      menuItems[2].setEnabled (false);
      menuItems[1].setVisible (true);
      menuItems[1].setEnabled (true);
    } 
    else if (ae.getSource() == okButton&&!paused) 
    {
      checkInput();
    } 
    else if (ae.getSource() ==  menuItems[3]) 
    {
      wasPaused=paused;
      if (!paused)
        pauseResume();
      result= JOptionPane.showConfirmDialog (TetristryApp.g, "Are you sure?", "Quit Game?", JOptionPane.YES_NO_OPTION);
      if (result==0)
      {
        t.stop();
        controlLabel=null;
        TetristryApp.menuPanel ();
      }
      else
      {
        if (!wasPaused)
          pauseResume();
        addNotify();
      }
    }
    else
    {
      if (askingCompound)
        return;
      
      if (!drop())
      {
        if (labelsOnBoard [11][4]!=null)
        {
          endGame();
          dialogBoxes ("Game Over", "Thanks for playing Tetristry!");
          gameOver = true;
        }
        else
        {
          checkCompound ();
          if (!askingCompound)
          {
            newElement();
          }
        }
      }
    }
  }
  
  /*
   * This method advances the game to the next level if not already on the highest difficulty. Else, it displays a congradulatory message.
   */
  public void nextLevel ()
  {    
    if (LevelSelection.difficulty < 3)
    {
      dialogBoxes ("Level Completed", "Let's continue to the next level.");
    }
    else
    {
      gameOver = true;
      dialogBoxes ("You Win!", "Thanks for playing Tetristry!");
    }
    tField.setEditable (false);
    okButton.setEnabled (false);
  }
  
  /*
   * This method creates a dialog box based on the String variables passed in as parameters.
   * 
   * @param dLabel-JLabel for text on dialog box.
   * @param continues-BufferedImage for continue button.
   * @param title-String variable for dialog box title.
   * @param text-String variable for dialog box info.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   * 
   */
  public void dialogBoxes (String title, String text)
  {
    d = new JDialog (TetristryApp.t, title);
    d.setSize (290,150);
    d.setResizable(false);
    d.setLayout (null);
    d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    
    JLabel dLabel = new JLabel (text);
    dLabel.setFont (new Font ("Arial", Font.BOLD, 16));
    dLabel.setBounds(new Rectangle(new Point(35, 15), dLabel.getPreferredSize())); 
    d.add (dLabel);
    d.setLocationRelativeTo (TetristryApp.t);
    d.setVisible (true);
    
    try
    {     
      BufferedImage continues = ImageIO.read(new File( "../Images/ContinueButton.png"));
      continueButton = new JButton(new ImageIcon(continues));
      continueButton.setBounds(new Rectangle(new Point(50, 60), continueButton.getPreferredSize())); 
      continueButton.setBorder(BorderFactory.createEmptyBorder());
      continueButton.setContentAreaFilled(false);
      d.add(continueButton);
      continueButton.addActionListener (this);
    }
    catch (IOException e)
    {}
  }
  
  /*
   * This method ends the correct game by stopping the timer and saving the score.
   */
  public void endGame()
  {
    t.stop();
    l.t.stop ();
    controlLabel=null;
    if (score>0)
      saveScore();
  }
  
  /*
   * This element creates a new element for the user to control. The element will start dropping from the 12th column and 5th row. The new element is added onto the label and element 2d arrays.
   * 
   * @param newElement-element variable for the new element.
   * @param elementPic-BufferedImage variable for element's picture.
   */
  public void newElement()
  {
    controlLabelCol=4;
    controlLabelRow=11;
    Element newElement =e.randElement();
    elementsOnBoard [11][4]=newElement;
    BufferedImage elementPic = newElement.getImage();
    labelsOnBoard [11][4]=new JLabel(new ImageIcon( elementPic));
    controlLabel =labelsOnBoard[11][4];
    add (labelsOnBoard[11][4]);
    repaintBoard();        
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
    {
      if (LevelSelection.difficulty ==3)
        g.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
      else if (LevelSelection.difficulty ==2)
        g.drawImage(backgroundImage2, 0,0,this.getWidth(),this.getHeight(),this);
      else 
        g.drawImage(backgroundImage3, 0,0,this.getWidth(),this.getHeight(),this);
      
    }
  }
}