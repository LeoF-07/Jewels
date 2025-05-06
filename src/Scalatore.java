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
        for(int i = 0; iterator.hasNext(); i++){
            int posizione = iterator.next();
            int row = posizione / TesterJewels.COLS;
            int col = posizione % TesterJewels.COLS;
            if(i == caselleDaScalare.size() - 1) TesterJewels.scalaGemme(row, col, true);
            else TesterJewels.scalaGemme(row, col, false);
        }

        TesterJewels.semaforoControllo.release();
    }

}