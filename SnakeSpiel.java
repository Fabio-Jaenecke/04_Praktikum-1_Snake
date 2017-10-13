import java.awt.Point;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Spielklasse des Spiels Snake.
 *
 * Ziel dieses Spiels ist es alle Goldstuecke einzusammeln und 
 * die Tuer zu erreichen, ohne sich selber zu beissen oder in 
 * die Spielfeldbegrenzung reinzukriechen. 
 */
public class SnakeSpiel {
  private Schlange schlange;
  private Tuer tuer;
  private Spielfeld spielfeld;
  private Goldstueck goldStueck;
  private int anzahlGoldStücke = 0;
  private boolean spielLaeuft = true;

  /**
   * Startet das Spiel mit 10 oder mit der Anzahl definierter Goldstücke. 
   */
  public void spielen() {
    setAnzahlGoldstueckeValidator();
    spielInitialisieren();
    while (spielLaeuft) {
      zeichneSpielfeld();
      ueberpruefeSpielstatus();
      fuehreSpielzugAus();
    }   
  }
  
  /**
   * Fragt den Benutzer nach Einstellungen und startet das Spiel.
   * Die Anzahl der Goldstücke können eingegeben werden.
   */
  public void spielenMitMenu() {
    setAnzahlGoldstuecke();
    setAnzahlGoldstueckeValidator();
    try {
        System.out.println("Das Spiel startet nun. Bitte warten...");
        Thread.sleep(1000);
    }
    catch (InterruptedException e) {
         System.out.println("Thread was interrupted while sleeping.");
    }
    spielInitialisieren();
    while (spielLaeuft) {
      zeichneSpielfeld();
      ueberpruefeSpielstatus();
      fuehreSpielzugAus();
    }   
  }
  
  public static void main(String[] args) {
    new SnakeSpiel().spielenMitMenu();
  }
  
  private void spielInitialisieren() {
    tuer = new Tuer(0, 5);
    spielfeld = new Spielfeld(40, 10);
    goldStueck = new Goldstueck();
    //Setzt die Anzahl Goldstücke, die definiert wurden:
    for(int index=0; index < setAnzahlGoldstueckeValidator(); index++){
       goldStueck.setGoldstueck();
    }
    schlange = new Schlange(30, 2);
  }
  
  /**
   * Fragt den Benutzer nach der gewünschten Anzahl Goldstücke und setzt
   * den neuen Wert in den Integer anzahlGoldStücke.
   */
  
  public int setAnzahlGoldstuecke(){ 
    Scanner numberScanner = new Scanner(System.in);
    do {
        try {
           System.out.println("Bitte geben Sie eine Anzahl Goldstücke ein und drücken Sie 'Enter'.");
           System.out.println("Für die Standard-Einstellung geben Sie bitte '0' ein und drücken Sie Enter.");
           int erhalte = numberScanner.nextInt();
           return anzahlGoldStücke = erhalte;   
        } catch (InputMismatchException e) {
           System.out.println("BITTE NUR ZAHLEN EINGEBEN:");
        }
        numberScanner.nextLine(); // reinigt den Buffer
    } while (anzahlGoldStücke == 0);
    return anzahlGoldStücke;   
  }
    
  /**
   * Validiert die eingestellte Anzahl Goldstücke.
   * 
   * Wurde keine Anzahl definiert, wird der Standard von 10 gesetzt.
   */
  
  private int setAnzahlGoldstueckeValidator(){ 
    if (anzahlGoldStücke > 0){
        return anzahlGoldStücke;
    }
    else {
        return 10;
    }
  }
  
  private void zeichneSpielfeld() {
    char ausgabeZeichen;
    for (int y = 0; y < spielfeld.gibHoehe(); y++) {
      for (int x = 0; x < spielfeld.gibBreite(); x++) {
        Point punkt = new Point(x, y);
        ausgabeZeichen = '.';
        if (schlange.istAufPunkt(punkt)) {
          ausgabeZeichen = '@';
        } else if (goldStueck.istAufPunkt(punkt)) {
          ausgabeZeichen = '$';
        } else if (tuer.istAufPunkt(punkt)) {
          ausgabeZeichen = '#';
        } 
        if(schlange.istKopfAufPunkt(punkt)) {
          ausgabeZeichen = 'S';         
        }
        System.out.print(ausgabeZeichen);
      }
      System.out.println();
    }
  }
  
  private void ueberpruefeSpielstatus() {   
    if (goldStueck.istAufPunkt(schlange.gibPosition())) {
      goldStueck.entferneGoldstueck(schlange.gibPosition());
      goldStueck.setGoldstueck();
      schlange.wachsen(goldStueck.setGoldWert()); //Lässt die Schlange um den GoldWert-mal wachsen.
      System.out.println("Goldstueck vom Wert " + (goldStueck.setGoldWert() + 1) + " eingesammelt.");  //informiert Goldwert     
    }
    if (istVerloren()){
      System.out.println("Verloren!");
      spielLaeuft = false;
    }
    if (istGewonnen()){
      System.out.println("Gewonnen!");
      spielLaeuft = false;
    }
  }
  
  private boolean istGewonnen() {
    return goldStueck == null && 
      tuer.istAufPunkt(schlange.gibPosition());
  }

  private boolean istVerloren() {
    return schlange.istKopfAufKoerper() || 
         !spielfeld.istPunktInSpielfeld(schlange.gibPosition());
  }
  
  private void fuehreSpielzugAus() {
    char eingabe = liesZeichenVonTastatur();
    schlange.bewege(eingabe);
  }

  private char liesZeichenVonTastatur() {
    Scanner scanner = new Scanner(System.in);
    char konsolenEingabe = scanner.next().charAt(0);
    return konsolenEingabe;
  }
}