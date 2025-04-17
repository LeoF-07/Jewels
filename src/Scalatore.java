import javax.swing.*;
import java.awt.*;

public class Scalatore extends SwingWorker<Object, Object> {

    private int row;
    private int col;
    private int lunghezzaSequenza;
    private Direzione direzione;

    public Scalatore(int row, int col, int lunghezzaSequenza, Direzione direzione){
        this.row = row;
        this.col = col;
        this.lunghezzaSequenza = lunghezzaSequenza;
        this.direzione = direzione;
    }

    @Override
    protected Object doInBackground() throws Exception {
        TesterJewels.semaforoScala.acquire();
        if(direzione == Direzione.ORIZZONTALE) TesterJewels.scalaGemmeOrizzontali(row, col, lunghezzaSequenza);
        else TesterJewels.scalaGemmeVerticali(row, col, lunghezzaSequenza);
        return null;
    }

}