import javax.swing.*;
import java.awt.*;

public class Evidenziatore extends SwingWorker<Object, Object> {

    private int row;
    private int col;
    private int lunghezzaSequenza;
    private JButton[][] caselle;
    private Tabellone tabellone;

    public Evidenziatore(int row, int col, int lunghezzaSequenza, JButton[][] caselle, Tabellone tabellone){
        this.row = row;
        this.col = col;
        this.lunghezzaSequenza = lunghezzaSequenza;
        this.caselle = caselle;
        this.tabellone = tabellone;
    }

    @Override
    protected Object doInBackground() throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tabellone.setVisible(false);
                for (int i = col; i < col + lunghezzaSequenza; i++){
                    caselle[row][i].setBorder(BorderFactory.createLineBorder(Color.GREEN));
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
                for (int i = col; i < col + lunghezzaSequenza; i++){
                    caselle[row][i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                }
                tabellone.setVisible(true);
            }
        });

        return null;
    }

}