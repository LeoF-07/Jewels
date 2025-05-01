import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedHashSet;

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tabellone.disabilita();

                tabellone.setVisible(false);
                Iterator<Integer> iterator = caselleDaScalare.iterator();
                while(iterator.hasNext()){
                    int posizione = iterator.next();
                    int row = posizione / TesterJewels.COLS;
                    int col = posizione % TesterJewels.COLS;
                    caselle[row][col].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                }
                tabellone.setVisible(true);
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tabellone.setVisible(false);
                Iterator<Integer> iterator = caselleDaScalare.iterator();
                while(iterator.hasNext()){
                    int posizione = iterator.next();
                    int row = posizione / TesterJewels.COLS;
                    int col = posizione % TesterJewels.COLS;
                    caselle[row][col].setBorder(tabellone.DEFAULT_BORDER);
                }
                tabellone.setVisible(true);
                TesterJewels.semaforoScala.release();
            }
        });

        return null;
    }

}