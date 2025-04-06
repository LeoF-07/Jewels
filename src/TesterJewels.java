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
        gemme = generaMatriceGemme(ROWS, COLS);

        tabellone = Tabellone.getTabellone(WIDTH, HEIGHT, ROWS, COLS);
        finestraDiGiGioco = FinestraDiGiGioco.getFinestraDiGiGioco("Jewels", WIDTH, HEIGHT, tabellone); // Singleton

        GUI = new Thread(finestraDiGiGioco);
        GUI.start();
    }

    public static void main(String[] args){
        inizializzaVariabili();
        tabellone.update(gemme);
    }

    private static Gemma[][] generaMatriceGemme(int rows, int cols){
        Gemma[][] matrice = new Gemma[rows][cols];

        for (int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                matrice[i][j] = Gemma.values()[(int) (Math.random() * Gemma.values().length)];
            }
        }

        return matrice;
    }
}