import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

/**
 *This class is used as the timer for the game. It counts down starting
 * from 3 minutes until the time runs out and the timer is stopped.
 * 
 * @author Richard Dang 
 * @version 1.5, May 15th, 2014
 */
public class Time implements ActionListener
{
  /**
   * Allows access to JLabel methods and presets the label to '3:00'.
   */
  JLabel time = new JLabel ("3:00");
  /**
   * Allows access to Timer methods.
   */
  static Timer t;
  /**
   * Stores the minutes remaining in the game preset to 3.
   */
  int minutes = 0;
  /**
   * Stores the minutes remaining in the game preset to 1.
   */
  int seconds = 30;
  
  /**
   * Class constructor which begins the timer with a 1 second delay inbetween
   * and an initial delay of 0.
   */
  public Time ()
  {
    t = new Timer (1000,this);
    t.setInitialDelay (0);
    t.start ();
  }
  
  /**
   * Used to display the time remaining to the player. Stops the timer when
   * both minutes and seconds is equal to 0. The time decreases like a regular
   * timer where each minute contains 60 seconds. 
   */
  public void displayTime ()
  {
    time.setFont (new Font ("Arial", Font.BOLD, 22));
    time.setForeground (Color.WHITE);
    time.setBounds(new Rectangle(new Point(670, 160), time.getPreferredSize())); 
    
    if (minutes <= 0 && seconds <=0)
    {
      minutes=0;
      seconds=0;
      return;
    }
    
    if (--seconds < 0&&minutes>0)
    {
      seconds = 59;
      minutes--;
    }
    
    if (seconds < 10)
      time.setText (String.valueOf (minutes)+ ":0"+String.valueOf (seconds));
    else
      time.setText (String.valueOf (minutes)+ ":"+String.valueOf (seconds));
    
  }
  
  /**
   * Uses action listener to continous display the updated time to the player. 
   * 
   * @param ae ActionEvent: allows access to ActionEvent methods.
   */
  public void actionPerformed (ActionEvent ae)
  {
    displayTime ();    
  }
}