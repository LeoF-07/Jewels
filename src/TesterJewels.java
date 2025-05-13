import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.concurrent.Semaphore;

public class TesterJewels {

    public static final int WIDTH_MENU = 600, HEIGHT_MENU = 200; // Grandezza del Menu
    public static final int WIDTH = 800, HEIGHT = 870; // Grandezza del Tabellone
    public static final int ROWS = 10, COLS = 8; // Righe e colonne del tabellone

    private static int punteggio;
    private static int tempo;

    private static Gemma[][] gemme;
    public static Semaphore semaforoScala = new Semaphore(0); // Semaforo per coordinare lo scalatore (può scalare solo dopo che le gemme sono state evidenziate in verde)
    public static Semaphore semaforoControllo = new Semaphore(0); // Semaforo per coordinare i controlli (possono essere fatti solo dopo che è avvenuta la scalatura precedente)
    public static Semaphore semaforoFineScalatura = new Semaphore(0); // Semaforo per la fine della partita (la partita finisce quando l'ultima scalatura è terminata)
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
        tabellone.setVisible(false); // Il tabellone inizialmente è invisibile e verrà mostrato solo quando verrà premuto il pulsante Gioca nel Menu

        classifica = new Classifica();

        menu = new Menu("Menu", WIDTH_MENU, HEIGHT_MENU, classifica);

        punteggio = 0;
        labelPunteggio = new JLabel("Punteggio: " + punteggio);

        tempo = 0;
        labelTempo = new JLabel("Tempo: " + tempo + "s");

        finestraDiGiGioco = FinestraDiGiGioco.getFinestraDiGiGioco("Jewels", WIDTH, HEIGHT, tabellone, labelPunteggio, labelTempo); // Singleton
        finestraDiGiGioco.setVisible(false);

        caselleDaScalare = new LinkedHashSet<>(); // Serve a memorizzare le gemme che vanno scalate una volta trovata la combinazione
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
        avviaCronometro();
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

    private static void avviaCronometro(){
        cronometro.execute();
    }

    private static Gemma[][] generaMatriceGemme(){
        Gemma[][] matrice = new Gemma[ROWS][COLS];

        for (int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                matrice[i][j] = Gemma.values()[(int) (Math.random() * Gemma.values().length)]; //in ogni cella viene inserita una gemma casuale tra quelle presenti nell'ENUM
            }
        }

        controllaCombinazioni(matrice); // Doppio controllo per assicurarsi che non ci siano combinazioni fin da subito
        controllaCombinazioni(matrice);

        return matrice;
    }

    public static void controllaCombinazioni(Gemma[][] matrice){ // Esegue il controllo delle combinazioni
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
                } while(matrice[i][j] == gemma); // Genera una nuova gemma finchè la gemma generata è ugugale a quella presente nella matrice, quando è diversa si ferma
                matrice[i][j] = gemma;
                controllaCombinazioneOrizzontale(matrice, i, j); // Rieseguire i controlli per controllare che non vengano generate ulteriori combinazioni
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
                } while(matrice[i][j] == gemma); // Genera una nuova gemma finchè la gemma generata è ugugale a quella presente nella matrice, quando è diversa si ferma
                matrice[i][j] = gemma;
                controllaCombinazioneVerticale(matrice, i, j);
                controllaCombinazioneOrizzontale(matrice, i, j); // Rieseguire i controlli per controllare che non vengano generate ulteriori combinazioni
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


    public static void cercaCombinazioneEAggiorna(ListenerPulsanteGemma[] pulsantiPremuti){ // Metodo richiamato quando si premono due gemme
        ListenerPulsanteGemma listenerPulsanteGemma1 = pulsantiPremuti[0];
        ListenerPulsanteGemma listenerPulsanteGemma2 = pulsantiPremuti[1];

        if(((listenerPulsanteGemma1.getRow() == listenerPulsanteGemma2.getRow()) && (listenerPulsanteGemma1.getCol() == listenerPulsanteGemma2.getCol() + 1 || listenerPulsanteGemma1.getCol() == listenerPulsanteGemma2.getCol() - 1)) ||
                (listenerPulsanteGemma1.getCol() == listenerPulsanteGemma2.getCol()) && (listenerPulsanteGemma1.getRow() == listenerPulsanteGemma2.getRow() + 1 || listenerPulsanteGemma1.getRow() == listenerPulsanteGemma2.getRow() - 1)){ // Controlla se le 2 gemme selezionate sono adiacenti

            gemme[listenerPulsanteGemma1.getRow()][listenerPulsanteGemma1.getCol()] = listenerPulsanteGemma2.getGemma(); // Se le gemme sono adiacenti vengono scambiate
            gemme[listenerPulsanteGemma2.getRow()][listenerPulsanteGemma2.getCol()] = listenerPulsanteGemma1.getGemma();

            if(!combinazioneOrizzontale(listenerPulsanteGemma1.getRow(), listenerPulsanteGemma1.getCol(), true) && !combinazioneOrizzontale(listenerPulsanteGemma2.getRow(), listenerPulsanteGemma2.getCol(), true)
                    && !combinazioneVerticale(listenerPulsanteGemma1.getRow(), listenerPulsanteGemma1.getCol(), true) && !combinazioneVerticale(listenerPulsanteGemma2.getRow(), listenerPulsanteGemma2.getCol(), true)){ // Una volta scambiate le gemme si controlla che ci sia una combinazione
                gemme[listenerPulsanteGemma1.getRow()][listenerPulsanteGemma1.getCol()] = listenerPulsanteGemma1.getGemma();
                gemme[listenerPulsanteGemma2.getRow()][listenerPulsanteGemma2.getCol()] = listenerPulsanteGemma2.getGemma(); // Se non c'è una combinazione le gemme tornano al loro posto originario
            }else{
                tabellone.update(gemme, false); // Se la combinazione c'è si fa l'update del tabellone (scambio delle gemme)
            }

            controlloTutteLeCombinazioni(); // Alla fine del metodo si ricontrollano tutte le combinazioni e quelle trovate vengono evidenziate e scalate
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

        if(semaforoFineScalatura.availablePermits() != 0) { // Se non mettessi questa condizione, il Thread si blocherebbe dopo il primo controllo ricorsivo delle combinazioni. Voglio assicurarmi che funzioni come un ping pong
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

        if(caselleDaScalare.isEmpty()){ // Se non trova nuove combinazioni e il semaforoFineScalatura è uguale a 0 rilascia il semaforoFineScalatura (devo controllare che sia uguale a 0 altrimenti ogni volta che trova delle combinazioni e scala ricorsivamente aumenta i posti liberi)
                                        // Se finisce il tempo, si aspetta che la scalatura sia finita, quindi si fa l'acquire di questo semaforo (vedi metodo terminaPartita())
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
                    semaforoControllo.acquire(); // Prima di controllare altre combinazioni aspetta che siano avvenuti tutti i controlli precedenti con evidenziature e scalature
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
                if(cercaCombinazione(i, j, i + 1, j) || cercaCombinazione(i, j, i - 1, j) // La combinazione viene cercata spostando la gemma a Nord, Sud, Est e Ovest
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

    public static void aggiungiAllaLista(int row, int col, Direzione direzione){ // Aggiunge alla LinkedHashSet le coordinate della sequenza di gemme da scalare sottoforma del risultato di una formula
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