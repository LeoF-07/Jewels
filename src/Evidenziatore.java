import javax.swing.*;
import java.awt.*;

public class Evidenziatore extends SwingWorker<Object, Object> {

    private int row;
    private int col;
    private int lunghezzaSequenza;
    private JButton[][] caselle;
    private Tabellone tabellone;
    private Direzione direzione;

    public Evidenziatore(int row, int col, int lunghezzaSequenza, JButton[][] caselle, Tabellone tabellone, Direzione direzione){
        this.row = row;
        this.col = col;
        this.lunghezzaSequenza = lunghezzaSequenza;
        this.caselle = caselle;
        this.tabellone = tabellone;
        this.direzione = direzione;
    }

    @Override
    protected Object doInBackground() throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tabellone.setVisible(false);
                if(direzione == Direzione.ORIZZONTALE) for (int i = col; i < col + lunghezzaSequenza; i++) caselle[row][i].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                else for (int i = row; i < row + lunghezzaSequenza; i++) caselle[i][col].setBorder(BorderFactory.createLineBorder(Color.GREEN));
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
                if(direzione == Direzione.ORIZZONTALE) for (int i = col; i < col + lunghezzaSequenza; i++) caselle[row][i].setBorder(tabellone.DEFAULT_BORDER);
                else for (int i = row; i < row + lunghezzaSequenza; i++) caselle[i][col].setBorder(tabellone.DEFAULT_BORDER);
                tabellone.setVisible(true);

                // TesterJewels.semaforoScala.release();

                if(direzione == Direzione.ORIZZONTALE) TesterJewels.semaforoScalaOrizzontale.release();
                else TesterJewels.semafororeScalaVerticale.release();
            }
        });

        //TesterJewels.scalaGemmeOrizzontali(row, col);

        return null;
    }

}