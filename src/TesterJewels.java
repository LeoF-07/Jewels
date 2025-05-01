import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.concurrent.Semaphore;

public class TesterJewels {

    public static final int WIDTH_MENU = 600, HEIGHT_MENU = 200; // grandezza del Jframe
    public static final int WIDTH = 800, HEIGHT = 870; // grandezza del Jframe
    public static final int ROWS = 10, COLS = 8; // dimensioni della matrice

    private static int punteggio;
    private static int tempo;

    private static Gemma[][] gemme;
    public static Semaphore semaforoScala = new Semaphore(0);
    public static Semaphore semaforoControllo = new Semaphore(0);
    public static Semaphore semaforoFineScalatura = new Semaphore(0);
    public static LinkedHashSet<Integer> caselleDaScalare;
    private static int tempoScelto;
    private static Cronometro cronometro;
    private static Classifica classifica;

    private static Menu menu;

    private static FinestraDiGiGioco finestraDiGiGioco;
    private static Tabellone tabellone;
    private static JLabel labelPunteggio;
    private static JLabel labelTempo;

    private static void inizializzaVariabili(){
        gemme = generaMatriceGemme();

        tabellone = Tabellone.getTabellone(ROWS, COLS); // Singleton
        tabellone.setVisible(false);

        classifica = new Classifica();

        menu = new Menu("Menu", WIDTH_MENU, HEIGHT_MENU, classifica);

        punteggio = 0;
        labelPunteggio = new JLabel("Punteggio: " + punteggio);

        tempo = 0;
        labelTempo = new JLabel("Tempo: " + tempo + "s");

        finestraDiGiGioco = FinestraDiGiGioco.getFinestraDiGiGioco("Jewels", WIDTH, HEIGHT, tabellone, labelPunteggio, labelTempo); // Singleton
        finestraDiGiGioco.setVisible(false);

        caselleDaScalare = new LinkedHashSet<>();
    }

    public static void main(String[] args){
        inizializzaVariabili();
    }

    public static void gioca(int tempoTotale){
        finestraDiGiGioco.setVisible(true);
        menu.setVisible(false);
        tabellone.update(gemme, false);
        controllaCombinazioniDisponibili();
        tempoScelto = tempoTotale;
        cronometro = new Cronometro(labelTempo, tempoTotale);
        avviaCronometro(tempoTotale);
    }

    public static void terminaPartita(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaforoFineScalatura.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                tabellone.disabilita();
                JOptionPane.showMessageDialog(null, "Fine partita");
                classifica.aggiorna(tempoScelto, punteggio);
            }
        }).start();
    }

    private static void avviaCronometro(int tempoTotale){
        cronometro.execute();
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

        if(semaforoFineScalatura.availablePermits() != 0) {
            try {
                semaforoFineScalatura.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                int lunghezzaSequenzaOrizzontale = sequenzaOrizzontale(i, j);
                int lunghezzaSequenzaVerticale = sequenzaVerticale(i, j);

                if(lunghezzaSequenzaOrizzontale >= 3) aggiungiAllaLista(i, j, Direzione.ORIZZONTALE);

                if(lunghezzaSequenzaVerticale >= 3) aggiungiAllaLista(i, j, Direzione.VERTICALE);
            }
        }

        if(caselleDaScalare.isEmpty()){
            if(semaforoFineScalatura.availablePermits() == 0) semaforoFineScalatura.release();
            return;
        }

        punteggio += caselleDaScalare.size();
        labelPunteggio.setText("Punteggio: " + punteggio);

        tabellone.evidenzia(caselleDaScalare);
        tabellone.scala(caselleDaScalare);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaforoControllo.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                controllaCombinazioniDisponibili();
            }
        }).start();
    }

    public static boolean cercaCombinazione(int i1, int j1, int i2, int j2){
        try{
            Gemma backup = gemme[i1][j1];
            gemme[i1][j1] = gemme[i2][j2];
            gemme[i2][j2] = backup;

            if(combinazioneOrizzontale(i1, j1, true) || combinazioneOrizzontale(i2, j2, true)
                    || combinazioneVerticale(i1, j1, true) || combinazioneVerticale(i2, j2, true)){
                backup = gemme[i1][j1];
                gemme[i1][j1] = gemme[i2][j2];
                gemme[i2][j2] = backup;
                return true;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }

        Gemma backup = gemme[i1][j1];
        gemme[i1][j1] = gemme[i2][j2];
        gemme[i2][j2] = backup;

        return false;
    }

    public static void controllaCombinazioniDisponibili(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++) {
                if(cercaCombinazione(i, j, i + 1, j) || cercaCombinazione(i, j, i - 1, j)
                        || cercaCombinazione(i, j, i, j + 1) || cercaCombinazione(i, j, i, j - 1)) {
                    //System.out.println(i + " " + j);
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Nessuna combinazione disponibile, shuffle");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gemme = generaMatriceGemme();
        tabellone.update(gemme, false);
        controllaCombinazioniDisponibili();
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