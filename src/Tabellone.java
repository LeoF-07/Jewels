import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Tabellone extends JPanel {

    private int rows;
    private int cols;

    private JButton[][] caselle; //matrice delle gemme
    private ListenerPulsanteGemma[] gemmeDaScambiare;

    public final Border DEFAULT_BORDER = new JButton().getBorder();

    private static Tabellone tabellone;

    private Tabellone(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        setLayout(new GridLayout(rows, cols));

        this.caselle = new JButton[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                caselle[i][j] = new JButton(i + " " + j);
                caselle[i][j].setOpaque(false);
                caselle[i][j].setContentAreaFilled(false);
                caselle[i][j].addActionListener(new ListenerPulsanteGemma(i, j, this));
                add(caselle[i][j]);
            }
        }

        gemmeDaScambiare = new ListenerPulsanteGemma[2];
    }

    public static Tabellone getTabellone(int rows, int cols) {
        if(tabellone == null) tabellone = new Tabellone(rows, cols);
        return tabellone;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //setBackground(Color.LIGHT_GRAY);

        try {
            BufferedImage sfondo = ImageIO.read(new File(".\\Immagini\\Sfondo\\sfondoJewels.png"));
            g.drawImage(sfondo, -300, 0, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Gemma[][] tabellone, boolean updateScalatura) {
        if(updateScalatura) this.abilita();
        new TabelloneUpdater(rows, cols, tabellone, caselle, this, updateScalatura).execute();
    }

    public void evidenzia(LinkedHashSet<Integer> caselleDaScalare){
        new Evidenziatore(caselleDaScalare, caselle, this).execute();
    }

    public void scala(LinkedHashSet<Integer> caselleDaScalare){
        new Scalatore(caselleDaScalare).start();
    }

    public void aggiungiGemmaDaScambiare(ListenerPulsanteGemma listenerPulsanteGemma){
        if(gemmeDaScambiare[0] == null && gemmeDaScambiare[1] == null){
            gemmeDaScambiare[0] = listenerPulsanteGemma;
        } else if(gemmeDaScambiare[0] != null && gemmeDaScambiare[1] == null){
            gemmeDaScambiare[1] = listenerPulsanteGemma;
            gemmeDaScambiare[0].getPulsante().setBorder(DEFAULT_BORDER);
            gemmeDaScambiare[1].getPulsante().setBorder(DEFAULT_BORDER);
            TesterJewels.cercaCombinazioneEAggiorna(gemmeDaScambiare);
        } else {
            gemmeDaScambiare[0] = listenerPulsanteGemma;
            gemmeDaScambiare[1] = null;
        }
    }

    public void disabilita(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(caselle[i][j].getActionListeners().length != 0){
                    ActionListener[] actionListener = caselle[i][j].getActionListeners();
                    caselle[i][j].removeActionListener(actionListener[0]);
                }
            }
        }
    }

    public void abilita(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                caselle[i][j].addActionListener(new ListenerPulsanteGemma(i, j, this));
            }
        }
    }
}