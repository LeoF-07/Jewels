import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.Semaphore;

public class TesterJewels {

    public static final int WIDTH = 820, HEIGHT = 900; // grandezza del Jframe
    public static final int ROWS = 10, COLS = 8; // dimensioni della matrice

    private static int punteggio;

    private static Gemma[][] gemme;
    private static FinestraDiGiGioco finestraDiGiGioco;
    private static Tabellone tabellone;
    private static JLabel intestazione;

    public static Semaphore semaforoScala = new Semaphore(0);

    public static LinkedHashSet<Integer> caselleDaScalare;

    private static void inizializzaVariabili(){
        gemme = generaMatriceGemme();

        tabellone = Tabellone.getTabellone(ROWS, COLS); // Singleton
        tabellone.setVisible(false);

        punteggio = 0;
        intestazione = new JLabel("Punteggio: " + punteggio);

        finestraDiGiGioco = FinestraDiGiGioco.getFinestraDiGiGioco("Jewels", WIDTH, HEIGHT, tabellone, intestazione); // Singleton

        caselleDaScalare = new LinkedHashSet<>();
    }

    public static void main(String[] args){
        inizializzaVariabili();
        tabellone.update(gemme, false);
    }

    private static Gemma[][] generaMatriceGemme(){
        Gemma[][] matrice = new Gemma[ROWS][COLS];

        for (int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                matrice[i][j] = Gemma.values()[(int) (Math.random() * Gemma.values().length)];
            }
        }

        controllaCombinazioni(matrice);

        return matrice;
    }

    public static void controllaCombinazioni(Gemma[][] matrice){
        for(int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                controllaCombinazioneVerticale(matrice, i, j);
                controllaCombinazioneOrizzontale(matrice, i, j);
            }
        }
    }

    public static void controllaCombinazioneVerticale(Gemma[][] matrice, int i, int j){
        try{
            if(matrice[i][j] == matrice[i - 1][j] && matrice[i][j] == matrice[i + 1][j]){
                Gemma gemma;
                do {
                    gemma = Gemma.values()[(int) (Math.random() * Gemma.values().length)];
                } while(matrice[i][j] == gemma);
                matrice[i][j] = gemma;
                controllaCombinazioneOrizzontale(matrice, i, j);
                controllaCombinazioneVerticale(matrice, i, j);
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
    }

    public static void controllaCombinazioneOrizzontale(Gemma[][] matrice, int i, int j){
        try {
            if(matrice[i][j] == matrice[i][j - 1] && matrice[i][j] == matrice[i][j + 1]){
                Gemma gemma;
                do {
                    gemma = Gemma.values()[(int) (Math.random() * Gemma.values().length)];
                } while(matrice[i][j] == gemma);
                matrice[i][j] = gemma;
                controllaCombinazioneVerticale(matrice, i, j);
                controllaCombinazioneOrizzontale(matrice, i, j);
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
    }

    public static boolean combinazioneOrizzontale(int i, int j, boolean controlloRicorsivo){
        try {
            if(gemme[i][j] == gemme[i][j - 1] && gemme[i][j] == gemme[i][j + 1]){
                return true;
            }
            if(controlloRicorsivo) return combinazioneOrizzontale(i, j - 1, false) | combinazioneOrizzontale(i, j + 1, false);
        }catch (ArrayIndexOutOfBoundsException e){
            if(controlloRicorsivo) return combinazioneOrizzontale(i, j - 1, false) | combinazioneOrizzontale(i, j + 1, false);
        }

        return false;
    }

    public static boolean combinazioneVerticale(int i, int j, boolean controlloRicorsivo){
        try {
            if(gemme[i][j] == gemme[i - 1][j] && gemme[i][j] == gemme[i + 1][j]){
                return true;
            }
            if(controlloRicorsivo) return combinazioneVerticale(i - 1, j, false) | combinazioneVerticale(i + 1, j, false);
        }catch (ArrayIndexOutOfBoundsException e){
            if(controlloRicorsivo) return combinazioneVerticale(i - 1, j, false) | combinazioneVerticale(i + 1, j, false);
        }

        return false;
    }


    public static void cercaCombinazioneEAggiorna(ListenerPulsanteGemma[] pulsantiPremuti){
        ListenerPulsanteGemma listenerPulsanteGemma1 = pulsantiPremuti[0];
        ListenerPulsanteGemma listenerPulsanteGemma2 = pulsantiPremuti[1];

        if(((listenerPulsanteGemma1.getRow() == listenerPulsanteGemma2.getRow()) && (listenerPulsanteGemma1.getCol() == listenerPulsanteGemma2.getCol() + 1 || listenerPulsanteGemma1.getCol() == listenerPulsanteGemma2.getCol() - 1)) ||
                (listenerPulsanteGemma1.getCol() == listenerPulsanteGemma2.getCol()) && (listenerPulsanteGemma1.getRow() == listenerPulsanteGemma2.getRow() + 1 || listenerPulsanteGemma1.getRow() == listenerPulsanteGemma2.getRow() - 1)){

            gemme[listenerPulsanteGemma1.getRow()][listenerPulsanteGemma1.getCol()] = listenerPulsanteGemma2.getGemma();
            gemme[listenerPulsanteGemma2.getRow()][listenerPulsanteGemma2.getCol()] = listenerPulsanteGemma1.getGemma();

            if(!combinazioneOrizzontale(listenerPulsanteGemma1.getRow(), listenerPulsanteGemma1.getCol(), true) && !combinazioneOrizzontale(listenerPulsanteGemma2.getRow(), listenerPulsanteGemma2.getCol(), true)
                    && !combinazioneVerticale(listenerPulsanteGemma1.getRow(), listenerPulsanteGemma1.getCol(), true) && !combinazioneVerticale(listenerPulsanteGemma2.getRow(), listenerPulsanteGemma2.getCol(), true)){
                gemme[listenerPulsanteGemma1.getRow()][listenerPulsanteGemma1.getCol()] = listenerPulsanteGemma1.getGemma();
                gemme[listenerPulsanteGemma2.getRow()][listenerPulsanteGemma2.getCol()] = listenerPulsanteGemma2.getGemma();
            }else{
                tabellone.update(gemme, false);
            }

            controlloTutteLeCombinazioni();
        }
    }

    public static void scalaGemme(int row, int col, boolean updateScalatura){
        for(int i = row; i > 0; i--){
            gemme[i][col] = gemme[i - 1][col];
        }

        gemme[0][col] = Gemma.values()[(int) (Math.random() * Gemma.values().length)];

        tabellone.update(gemme, updateScalatura);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int sequenzaOrizzontale(int row, int col){
        int n = 0;
        for(int i = col; i < COLS && gemme[row][col] == gemme[row][i]; i++, n++){}
        return n;
    }

    public static int sequenzaVerticale(int row, int col){
        int n = 0;
        for(int i = row; i < ROWS && gemme[row][col] == gemme[i][col]; i++, n++){}
        return n;
    }

    public static void controlloTutteLeCombinazioni(){
        caselleDaScalare.clear();

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                int lunghezzaSequenzaOrizzontale = sequenzaOrizzontale(i, j);
                int lunghezzaSequenzaVerticale = sequenzaVerticale(i, j);

                if(lunghezzaSequenzaOrizzontale >= 3) aggiungiAllaLista(i, j, Direzione.ORIZZONTALE);

                if(lunghezzaSequenzaVerticale >= 3) aggiungiAllaLista(i, j, Direzione.VERTICALE);
            }
        }

        if(caselleDaScalare.isEmpty()) return;

        punteggio += caselleDaScalare.size();
        intestazione.setText("Punteggio: " + punteggio);

        tabellone.evidenzia(caselleDaScalare);
        tabellone.scala(caselleDaScalare);
    }

    public static void aggiungiAllaLista(int row, int col, Direzione direzione){
        switch (direzione){
            case ORIZZONTALE:
                for(int i = col; i < COLS && gemme[row][col] == gemme[row][i]; i++){
                    caselleDaScalare.add(row * COLS + i);
                }
                break;
            case VERTICALE:
                for(int i = row; i < ROWS && gemme[row][col] == gemme[i][col]; i++){
                    caselleDaScalare.add(i * COLS + col);
                }
                break;
        }
    }

}