import java.awt.print.PrinterJob;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PageFormat;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Used to create methods that will print text for the highscore menu.
 * 
 * @author Daniel Chen modified by Richard Dang and Benson Guo
 * @version 1.0, May 15, 2014
 */
public class Print implements Printable
{
  /*
   * Stores the a list of string to print.
   */
  private ArrayList <String> list;
  /*
   * Checks if a new line should be added.
   */
  private boolean newLine;
  /*
   * Used in the printable interface.
   */
  private int y;
  
  /**
   * Adds the string passed into the parameter to the 
   * list that will be printed.
   * Use like System.out.print (String)
   * 
   * @param text String: stores the string to print
   */
  public void print (String text)
  {
    if (newLine)
      list.add (text);
    else
      list.add (list.remove (list.size () - 1) + text);
    newLine = false;
  }
  
  /**
   * Adds the string and spacing passed into the parameter to the 
   * list that will be printed.
   * Use like System.out.print (String)
   * 
   * @param text String: stores the string to print
   * @param spacing int: stores the string to print
   */
  public void print (String text, int spacing)
  {
    for (int x=0;x<spacing;x++)
    {
      text+=" ";
    }
    if (newLine)
      list.add (text);
    else
      list.add (list.remove (list.size () - 1) + text);
    newLine = false;
  }
  
  /**
   * Adds a blank line to the list of string
   */
  public void println ()
  {
    list.add ("");
    newLine = true;
  }
  
  /**
   * Adds the string passed into the parameter to the 
   * list that will be printed and a blank line.
   * Use like System.out.print (String)
   * 
   * @param text String: stores the string to print
   */
  public void println (String text)
  {
    print (text);
    newLine = true;
  }
  
  /**
   * Use to start the actual printer printing
   * 
   * @param p PrinterJob: Allows access to PrinterJob methods.
   * @param e reference: Allows access to PrinterException methods.
   * @throws PrinterException Thrown to indicate an error when printing.
   * @return true if there is no print exception, false otherwise.
   */
  public boolean startPrinting ()
  {
    PrinterJob p = PrinterJob.getPrinterJob ();
    p.setPrintable (this);
    try
    {
      p.print ();    
    }
    catch (PrinterException e)
    {
      return false;
    }
    return true;
  }
  
  /**
   * Required for the Printable interface. Prints the text in Courier font and adds a logo.
   * 
   * @param g-Graphics variable passed in as parameter.
   * @param pf-PageFormat variable passed in as parameter.
   * @param page-int variable passed in for page.
   * @param border-Rectangle2D variable for border.
   * @param s-String variable for for each loop.
   * @param g2d-Graphics2D variable for drawing.
   * @param y-integer used for text spacing.
   * @param e-variable to catch for IOException and PrinterException.
   * @param tetristryName-BufferedImage variable for Tetristry Name image.
   * @param tetristrLogo-BufferedImage variable for Tetristry Logo.
   * @throws PrinterException Thrown to indicate an error when printing.
   * @throws IOException Thrown to indicate an error when reading image file.
   * @return final int instance to check if there is or is not a page to print.
   */
  public int print(Graphics g, PageFormat pf, int page) throws PrinterException 
  {
    if (page > 0)
      return NO_SUCH_PAGE;
    
    
    Graphics2D g2d = (Graphics2D)g;
    g2d.translate(pf.getImageableX(), pf.getImageableY());
    y = (int) pf.getImageableY();
    
    Rectangle2D.Double border = new Rectangle2D.Double(0, 0, pf.getImageableWidth(), pf.getImageableHeight());
    g2d.draw(border);
    
    g2d.setFont (new Font ("Courier", Font.PLAIN, 12));
    
    for (String s : list)
    {
      g2d.drawString(s, (int) pf.getImageableX()-30, y+65);
      y+= g.getFontMetrics ().getHeight ();
    }
    try
    {
      BufferedImage tetristryName = ImageIO.read(new File( "../Images/TetristrySmall.png"));
      g2d.drawImage(tetristryName, (int)pf.getImageableWidth()/2-140, (int) pf.getImageableY()-50, null);
      BufferedImage tetristryLogo = ImageIO.read(new File( "../Images/RBCSLogo.png"));
      g2d.drawImage(tetristryLogo, (int)pf.getImageableWidth()/2-112, 585, null);
    }
    catch (IOException e)
    {
    }   
    return PAGE_EXISTS;
  }
  
  /*
   * Constructor that sets up the ArrayList.
   */
  public Print ()
  {
    list = new ArrayList <String> ();
    newLine = true;
  }
}