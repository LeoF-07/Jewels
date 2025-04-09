import javax.swing.*;
import java.awt.*;
import java.util.EventListener;

public class TesterJewels {

    private static final int WIDTH = 800, HEIGHT = 800; // grandezza del Jframe
    private static final int ROWS = 10, COLS = 8; // dimensioni della matrice

    private static Gemma[][] gemme;
    private static FinestraDiGiGioco finestraDiGiGioco;
    static Tabellone tabellone;

    private static void inizializzaVariabili(){
        gemme = generaMatriceGemme();

        tabellone = Tabellone.getTabellone(WIDTH, HEIGHT, ROWS, COLS);
        finestraDiGiGioco = FinestraDiGiGioco.getFinestraDiGiGioco("Jewels", WIDTH, HEIGHT, tabellone); // Singleton
    }

    public static void main(String[] args){
        inizializzaVariabili();
        tabellone.update(gemme);
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

        }

        return false;
    }

    public static void cercaCombinazioneEAggiorna(PulsanteGemma[] pulsantiPremuti){
        PulsanteGemma pulsanteGemma1 = pulsantiPremuti[0];
        PulsanteGemma pulsanteGemma2 = pulsantiPremuti[1];

        if((pulsanteGemma1.getRow() == pulsanteGemma2.getRow()) && (pulsanteGemma1.getCol() == pulsanteGemma2.getCol() + 1 || pulsanteGemma1.getCol() == pulsanteGemma2.getCol() - 1)){

        }

        if((pulsanteGemma1.getCol() == pulsanteGemma2.getCol()) && (pulsanteGemma1.getRow() == pulsanteGemma2.getRow() + 1 || pulsanteGemma1.getRow() == pulsanteGemma2.getRow() - 1)){
            gemme[pulsanteGemma1.getRow()][pulsanteGemma1.getCol()] = pulsanteGemma2.getGemma();
            gemme[pulsanteGemma2.getRow()][pulsanteGemma2.getCol()] = pulsanteGemma1.getGemma();
            System.out.println("scambia gemme");
            if(combinazioneOrizzontale(pulsanteGemma1.getRow(), pulsanteGemma1.getCol(), true)){
                System.out.println("combinazione orizzontale");
                tabellone.update(gemme);
            } else if(combinazioneOrizzontale(pulsanteGemma2.getRow(), pulsanteGemma2.getCol(), true)){
                System.out.println("combinazione orizzontale");
                tabellone.update(gemme);
            } else{
                gemme[pulsanteGemma1.getRow()][pulsanteGemma1.getCol()] = pulsanteGemma1.getGemma();
                gemme[pulsanteGemma2.getRow()][pulsanteGemma2.getCol()] = pulsanteGemma2.getGemma();
            }
        }
    }
}