import java.awt.Point;
import java.util.ArrayList;

/**
 * Repraesentiert ein Goldstück.
 */
public class Goldstueck {
  private ArrayList<Point> goldPunkt;
  private ArrayList<Integer> goldWert;

  /**
   * Konstruiert ein Gold mit Standort
   * bei den angegebenen Koordinaten. 
   * 
   * @param x x-Koordinate der Goldpunkt
   * @param y y-Koordinate der Goldpunkt
   * 
   * Und Setzt einen GoldWert zwischen 1 und 5.
   */
  public Goldstueck() {
    goldPunkt = new ArrayList<Point>();
    goldWert = new ArrayList<Integer>();
  }
  
  public void setGoldstueck() {
    //Setzt einen neuen Zufallspunkt auf dem Spielfeld.
    int x = (int)(Math.random() * 40);
    int y = (int)(Math.random() * 10);
    goldPunkt.add(new Point(x, y));
  }
  
  public int setGoldWert() {
    //Setzt einen neuen Zufallswert.
    int wert = (int)(Math.random() * 5); 
    goldWert.add(new Integer(wert));
    return wert;
  } 
  
  public Point gibGoldStueckPosition() {
      return goldPunkt.get(0);
  }
  
  public boolean entferneGoldstueck(Point standort) {
     return goldPunkt.remove(standort);
    }
  
    /**
   * Prueft, ob Gold am bezeichneten Standort ist
   * 
   * @param standort
   * @return wahr, falls die Gold am bezeichneten Standort ist
  */
  public boolean istAufPunkt(Point standort) {
    return goldPunkt.contains(standort);
  }
   
 
}