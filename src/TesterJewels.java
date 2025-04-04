import javax.swing.*;
import java.awt.*;
import java.util.EventListener;

public class TesterJewels {

    private static final int WIDTH = 1000, HEIGHT = 800; // grandezza del Jframe
    private static final int ROWS = 10, COLS = 8; // dimensioni della matrice

    private static Gemma[][] gemme;
    private static FinestraDiGiGioco finestraDiGiGioco;
    static Tabellone tabellone;
    private static Thread GUI;

    private static void inizializzaVariabili(){
        gemme = new Gemma[ROWS][COLS];

        tabellone = Tabellone.getTabellone(WIDTH, HEIGHT, ROWS, COLS);
        finestraDiGiGioco = FinestraDiGiGioco.getFinestraDiGiGioco("Jewels", WIDTH, HEIGHT, tabellone); // Singleton

        GUI = new Thread(finestraDiGiGioco);
        GUI.start();
    }

    public static void main(String[] args){
        inizializzaVariabili();
        tabellone.testUpdate("ciao", 0, 0);
    }
}