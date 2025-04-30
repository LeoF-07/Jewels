import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Scalatore extends SwingWorker<Object, Object> {

    private LinkedHashSet<Integer> caselleDaScalare;

    public Scalatore(LinkedHashSet<Integer> caselleDaScalare){
        this.caselleDaScalare = caselleDaScalare;
    }

    @Override
    protected Object doInBackground() throws Exception {
        // Attenzione, questo codice non posso metterlo sul tester perché lavora in un Thread apposito dello SwingWorker
        // Posso vederelo con sout(Thread.currentThread().getName());

        TesterJewels.semaforoScala.acquire();

        Iterator<Integer> iterator = caselleDaScalare.iterator();
        for(int i = 0; iterator.hasNext(); i++){
            int posizione = iterator.next();
            int row = posizione / TesterJewels.COLS;
            int col = posizione % TesterJewels.COLS;
            if(i == caselleDaScalare.size() - 1) TesterJewels.scalaGemme(row, col, true);
            else TesterJewels.scalaGemme(row, col, false);
        }
        return null;
    }

}