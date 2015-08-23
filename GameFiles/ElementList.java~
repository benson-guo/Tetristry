import java.util.*;
import java.io.*;

/**
 *This class sets up the arraylist for all compounds and elements, which are read in from a text file.
 * 
 * @author Benson Guo
 * @version 1.5, May 22th, 2014
 */
public class ElementList
{
  /*
   * ArrayList of Strings to store metal symbols.
   */
  private ArrayList <String> metals=new ArrayList <String>();
  /*
   * ArrayList of Strings to store non-metal symbols.
   */
  private ArrayList <String> nonMetals=new ArrayList <String>();
  /*
   * Hashmap that maps compound symbol strings to compound names.
   */
  private Map<String,String> compounds = new HashMap<String,String>();
  /*
   * Scanner reference variable 1.
   */
  private Scanner s;
  /*
   * Scanner reference variable 2.
   */
  private Scanner t;  
  /*
   * Constructor that calls readFiles method.
   */
  public ElementList ()
  {
    readFiles();
  }
  
  /*
   * This method reads the text files of the compounds and elements and stores them in the instance level arraylists.
   * 
   * @param e-variable to catch for IOException, NoSuchElementException.
   * @throws IOException Thrown to indicate an error when reading from text file.
   * @throws NoSuchElementException Thrown to indicate an error when reading string from textfile.
   */
  private void readFiles ()
  {
    try
    {
      s=new Scanner (new File("../ElementsAndCompounds/Metals.txt"));
      while (s.hasNextLine())
      {
        metals.add (s.nextLine());
      }
      s=new Scanner (new File("../ElementsAndCompounds/NonMetals.txt"));
      while (s.hasNextLine())
      {
        nonMetals.add (s.nextLine());
      }
      s=new Scanner (new File("../ElementsAndCompounds/Compounds.txt"));
      t=new Scanner (new File("../ElementsAndCompounds/CompoundSymbols.txt"));
      while (s.hasNextLine())
      {
        compounds.put (t.nextLine(),s.nextLine());
      }
    }
    catch (IOException e){}
    catch (NoSuchElementException e) {}
  }
  
  /*
   * This method generates a random element for the gameboard. Math.random () is used to pick either a metal or a non-metal, and select one of them at random.
   * 
   * @param symbol-String variable for the compound symbol name.
   * @param rand-int variable for random element.
   * @return a new random element object
   */
  public Element randElement ()
  {
    String symbol="";
    int rand=0;
    if (Math.random()>0.5)
    {
      rand=(int)(Math.random()*metals.size());
      symbol=metals.get(rand);
    }
    else
    {
      rand=(int)(Math.random()*nonMetals.size());
      symbol=nonMetals.get(rand);
    }
    return new Element(symbol);
  }
  
  /*
   * String return method that returns the correct compound name that corresponds to a symbol.
   * 
   * @param compoundSymbol-String variable for the symbol of the compound the user is naming.
   * @return the compound name corresponding to the passed in symbol
   */
  public String checkCompound(String compoundSymbol)
  {
    return compounds.get(compoundSymbol);
  }
  
  /*
   * Boolean return method that returns whether or not two elements can form a compound. One element must be a non-metal, and one a metal.The if statements also checks to make sure the elements are not null and are not barriers.
   * 
   * @param a-parameter passed in for 1st element.
   * @param b-parameter passed in for 2nd element.
   * @return true if a compound can be formed, otherwise false.
   */
  public boolean canForm(Element a, Element b)
  {
    if (a==null||b==null)
      return false;
    if (metals.contains(a.getName())&&metals.contains(b.getName())||nonMetals.contains(a.getName())&&nonMetals.contains(b.getName()))
      return false;
    if (a.getName().equals ("Barrier") ||b.getName().equals ("Barrier"))
      return false;
    return true;
  }
  
  /*
   * Boolean return method that returns whether or not an element is a metal. It checks the metal list for the element name.
   * 
   * @param e-parameter passed in for element.
   * @return true if the element is a metal, otherwise false.
   */
  public boolean isMetal(Element e)
  {
    if (metals.contains (e.getName()))
      return true;
    else 
      return false;
  }
}
