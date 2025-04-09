import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PulsanteGemma implements ActionListener {

    private Gemma gemma;
    private int row;
    private int col;
    private Tabellone tabellone;

    public PulsanteGemma(int row, int col, Tabellone tabellone){
        this.row = row;
        this.col = col;
        this.tabellone = tabellone;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*JButton pulsante = (JButton) e.getSource();
        System.out.println();*/
        System.out.println(getRow() + " " + getCol() + " " + getGemma());
        tabellone.aggiungiGemmaDaScambiare(this);
    }

    public void setGemma(Gemma gemma) {
        this.gemma = gemma;
    }

    public Gemma getGemma() {
        return gemma;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}