import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class Evidenziatore extends SwingWorker<Object, Object> {

    private LinkedHashSet<Integer> caselleDaScalare;
    private JButton[][] caselle;
    private Tabellone tabellone;

    public Evidenziatore(LinkedHashSet<Integer> caselleDaScalare, JButton[][] caselle, Tabellone tabellone){
        this.caselleDaScalare = caselleDaScalare;
        this.caselle = caselle;
        this.tabellone = tabellone;
    }

    @Override
    protected Object doInBackground() throws Exception {
        publish(0);

        try {
            Thread.sleep(1000); // Attendere un secondo
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    protected void process(List<Object> chunks) {
        tabellone.disabilita();

        tabellone.setVisible(false);
        Iterator<Integer> iterator = caselleDaScalare.iterator();
        while(iterator.hasNext()){
            int posizione = iterator.next();
            int row = posizione / TesterJewels.COLS; // Formula inversa per ottenere le coordinate della gemma da scalare
            int col = posizione % TesterJewels.COLS;
            caselle[row][col].setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Mette il bordo a verde
        }
        tabellone.setVisible(true);
    }

    @Override
    protected void done() {
        tabellone.setVisible(false);
        Iterator<Integer> iterator = caselleDaScalare.iterator();
        while(iterator.hasNext()){
            int posizione = iterator.next();
            int row = posizione / TesterJewels.COLS;
            int col = posizione % TesterJewels.COLS;
            caselle[row][col].setBorder(tabellone.DEFAULT_BORDER); // Ripristia i bordi originari
        }
        tabellone.setVisible(true);
        TesterJewels.semaforoScala.release();
    }

}