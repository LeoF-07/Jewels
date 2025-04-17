import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;

public class TesterJewels {

    private static final int WIDTH = 820, HEIGHT = 900; // grandezza del Jframe
    private static final int ROWS = 10, COLS = 8; // dimensioni della matrice

    private static int punteggio;

    private static Gemma[][] gemme;
    private static FinestraDiGiGioco finestraDiGiGioco;
    private static Tabellone tabellone;
    private static JLabel intestazione;

    public static Semaphore semaforoScala = new Semaphore(0);
    public static Semaphore semaforoScalaOrizzontale = new Semaphore(0);
    public static Semaphore semafororeScalaVerticale = new Semaphore(0);

    private static void inizializzaVariabili(){
        gemme = generaMatriceGemme();

        tabellone = Tabellone.getTabellone(ROWS, COLS); // Singleton
        tabellone.setVisible(false);

        punteggio = 0;
        intestazione = new JLabel("Punteggio: " + punteggio);

        finestraDiGiGioco = FinestraDiGiGioco.getFinestraDiGiGioco("Jewels", WIDTH, HEIGHT, tabellone, intestazione); // Singleton
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

    public static void scalaGemmeOrizzontali(int row, int col, int lunghezzaOrizzontale){
        for(int j = 0; j < lunghezzaOrizzontale; j++){
            for(int i = row; i > 0; i--){
                gemme[i][col + j] = gemme[i - 1][col + j];
            }

            gemme[0][col + j] = Gemma.values()[(int) (Math.random() * Gemma.values().length)];

            tabellone.update(gemme, j == lunghezzaOrizzontale - 1);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //tabellone.update(gemme);
    }

    public static void scalaGemmeVerticali(int row, int col, int lunghezzaVerticale){
        for(int j = 0; j < lunghezzaVerticale; j++){
            /*for(int i = row; i > -j; i--){
                gemme[i + j][col] = gemme[i - 1 + j][col];
                System.out.print((i + j) + " ");
            }

            System.out.println();*/

            for(int i = row + j; i > 0; i--){
                gemme[i][col] = gemme[i - 1][col];
            }

            gemme[0][col] = Gemma.values()[(int) (Math.random() * Gemma.values().length)];

            tabellone.update(gemme, j == lunghezzaVerticale - 1);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //tabellone.update(gemme);
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
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                int lunghezzaSequenzaOrizzontale = sequenzaOrizzontale(i, j);
                int lunghezzaSequenzaVerticale = sequenzaVerticale(i, j);

                /* Una combinazione di questo tipo ovviamente me le segna entrambe, teoricamente dopo
                   aver trovato la combinazione fa scendere le gemme, quindi non dovrebbero esserci problemi

                        G G G
                            G
                            G

                */

                if(lunghezzaSequenzaOrizzontale >= 3){
                    punteggio += lunghezzaSequenzaOrizzontale;
                    intestazione.setText("Punteggio: " + punteggio);
                    tabellone.evidenzia(i, j, lunghezzaSequenzaOrizzontale, Direzione.ORIZZONTALE);
                    tabellone.scala(i, j, lunghezzaSequenzaOrizzontale, Direzione.ORIZZONTALE);
                    return;
                }

                if(lunghezzaSequenzaVerticale >= 3){
                    punteggio += lunghezzaSequenzaVerticale;
                    intestazione.setText("Punteggio: " + punteggio);
                    tabellone.evidenzia(i, j, lunghezzaSequenzaVerticale, Direzione.VERTICALE);
                    tabellone.scala(i, j, lunghezzaSequenzaVerticale, Direzione.VERTICALE);
                    return;
                }
            }
        }
    }

}