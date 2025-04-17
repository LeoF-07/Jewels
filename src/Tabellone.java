import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tabellone extends JPanel {

    private int rows;
    private int cols;

    private JButton[][] caselle; //matrice delle gemme
    private ListenerPulsanteGemma[] gemmeDaScambiare;

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
                //caselle[i][j].setBorderPainted(false);
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

    public void update(Gemma[][] tabellone) {
        new TabelloneUpdater(rows, cols, tabellone, caselle, this).execute();
    }

    public void evidenziaOrizzontali(int row, int col, int lunghezzaSequenza){
        new Evidenziatore(row, col, lunghezzaSequenza, caselle, this).execute();
    }

    public void aggiungiGemmaDaScambiare(ListenerPulsanteGemma listenerPulsanteGemma){
        if(gemmeDaScambiare[0] == null && gemmeDaScambiare[1] == null){
            gemmeDaScambiare[0] = listenerPulsanteGemma;
        } else if(gemmeDaScambiare[0] != null && gemmeDaScambiare[1] == null){
            gemmeDaScambiare[1] = listenerPulsanteGemma;
            TesterJewels.cercaCombinazioneEAggiorna(gemmeDaScambiare);
        } else {
            gemmeDaScambiare[0] = listenerPulsanteGemma;
            gemmeDaScambiare[1] = null;
        }
    }
}