import java.util.Iterator;
import java.util.LinkedHashSet;

public class Scalatore extends Thread {

    private LinkedHashSet<Integer> caselleDaScalare;

    public Scalatore(LinkedHashSet<Integer> caselleDaScalare){
        this.caselleDaScalare = caselleDaScalare;
    }

    @Override
    public void run() {
        try {
            TesterJewels.semaforoScala.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Iterator<Integer> iterator = caselleDaScalare.iterator();
        for(int i = 0; iterator.hasNext(); i++){ // Scorre tutte le gemme dell'hashset
            int posizione = iterator.next();
            int row = posizione / TesterJewels.COLS;
            int col = posizione % TesterJewels.COLS;
            if(i == caselleDaScalare.size() - 1) TesterJewels.scalaGemme(row, col, true); // Chiama la funzione scalaGemme del tester e se è l'ultima dell'hashset rifaccio i controlli delle combinazioni (passando true)
            else TesterJewels.scalaGemme(row, col, false); // Se non è l'ultima gemma semplicemente chiamo il metodo del tester
        }

        TesterJewels.semaforoControllo.release();
    }

}