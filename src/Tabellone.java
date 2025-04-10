import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Tabellone extends JPanel implements Runnable {

    private int width;
    private int height;
    private int rows;
    private int cols;

    private JButton[][] caselle; //matrice delle gemme
    private PulsanteGemma[] gemmeDaScambiare;

    private static Tabellone tabellone;

    private Tabellone(int larghezza, int altezza, int rows, int cols) {
        this.width = larghezza;
        this.height = altezza;
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
                caselle[i][j].addActionListener(new PulsanteGemma(i, j, this));
                add(caselle[i][j]);
            }
        }

        gemmeDaScambiare = new PulsanteGemma[2];
    }

    public static Tabellone getTabellone(int larghezza, int altezza, int rows, int cols) {
        if(tabellone == null) tabellone = new Tabellone(larghezza, altezza, rows, cols);
        return tabellone;
    }

    @Override
    public void run() {
        while(true){}
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
        this.setVisible(false);

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                caselle[i][j].setIcon(new ImageIcon(tabellone[i][j].path));
                caselle[i][j].setText("");
                PulsanteGemma pulsanteGemma = (PulsanteGemma) caselle[i][j].getActionListeners()[0];
                pulsanteGemma.setGemma(tabellone[i][j]);
            }
        }

        this.setVisible(true);
    }

    public void evidenziaOrizzontali(int i, int j){
        caselle[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN));
        caselle[i][j - 1].setBorder(BorderFactory.createLineBorder(Color.GREEN));
        caselle[i][j + 1].setBorder(BorderFactory.createLineBorder(Color.GREEN));


        System.out.println("colorazione completata");
    }

    public void evidenzia(int i, int j){
        caselle[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN));
    }

    public void freeze(int millis){
        System.out.println("sleep");

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void testUpdate(String s, int i, int j){
        caselle[i][j].setText(s);
    }

    public void aggiungiGemmaDaScambiare(PulsanteGemma pulsanteGemma){
        if(gemmeDaScambiare[0] == null && gemmeDaScambiare[1] == null){
            gemmeDaScambiare[0] = pulsanteGemma;
        } else if(gemmeDaScambiare[0] != null && gemmeDaScambiare[1] == null){
            gemmeDaScambiare[1] = pulsanteGemma;
            TesterJewels.cercaCombinazioneEAggiorna(gemmeDaScambiare);
        } else {
            gemmeDaScambiare[0] = pulsanteGemma;
            gemmeDaScambiare[1] = null;
        }
    }
}