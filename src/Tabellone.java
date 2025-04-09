import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tabellone extends JPanel {

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
                caselle[i][j].addActionListener(new PulsanteGemma(i, j));
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
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                caselle[i][j].setIcon(new ImageIcon(tabellone[i][j].path));
                caselle[i][j].setText("");
                PulsanteGemma pulsanteGemma = (PulsanteGemma) caselle[i][j].getActionListeners()[0];
                pulsanteGemma.setGemma(tabellone[i][j]);
            }
        }
    }

    public void testUpdate(String s, int i, int j){
        caselle[i][j].setText(s);
    }

    public void aggiungiGemmaDaScambiare(JButton pulsanteGemma){
        
    }

}
