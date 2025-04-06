import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tabellone extends JPanel implements Runnable {

    private int width;
    private int height;
    private int rows;
    private int cols;

    private JButton[][] caselle; //matrice delle gemme

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
                caselle[i][j].addActionListener(new PulsanteGemma());
                add(caselle[i][j]);
            }
        }
    }

    public static Tabellone getTabellone(int larghezza, int altezza, int rows, int cols) {
        if(tabellone == null) tabellone = new Tabellone(larghezza, altezza, rows, cols);
        return tabellone;
    }

    @Override
    public void run() {
        // QUi presumo che ci debba mettere le azioni da effettuare in caso si prema un pulsante
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
            }
        }
    }

    public void testUpdate(String s, int i, int j){
        caselle[i][j].setText(s);
    }

}
