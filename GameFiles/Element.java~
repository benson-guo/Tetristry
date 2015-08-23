import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *This class is a template for an Element object.
 * 
 * @author Benson Guo
 * @version 1.2, May 22th, 2014
 */
public class Element
{
  /*
   *BufferedImage variable for picture of element. 
   */
  private BufferedImage pic;
  /*
   *String variable for element symbol. 
   */
  private String name;
  
  /**
   *Constructor creates an element object with the passed in string name and sets up the correct image.
   * 
   * @param compoundName-String variable that is passed in for compound symbol.
   * @param e-variable to catch for IOException.
   * @throws IOException Thrown to indication a error when reading image file.
   */
  public Element (String compoundName)
  {
    name = compoundName;
    try
    {  
      pic = ImageIO.read(new File("../Images/Elements/"+name+".png"));
    }
    catch (IOException e) 
    {} 
  }
  
  /*
   *Accessor method for element name.
   * 
   * @return the name of the element
   */
  public String getName()
  {
    return name;
  }
  
  /*
   *Accessor method for element BufferedImage. 
   * 
   * @return the image of the element
   */
  public BufferedImage getImage()
  {
    return pic;
  }
}
