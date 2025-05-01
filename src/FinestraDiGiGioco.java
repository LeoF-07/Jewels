import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FinestraDiGiGioco extends JFrame {

    private static FinestraDiGiGioco finestraDiGiGioco;

    private FinestraDiGiGioco(String titolo, int larghezza, int altezza, Tabellone tabellone, JLabel labelPunteggio, JLabel labelTempo){
        super(titolo);
        this.setBounds(10, 10, larghezza, altezza);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel panelIntestazione = new JPanel();
        panelIntestazione.setLayout(new BoxLayout(panelIntestazione, BoxLayout.X_AXIS));
        panelIntestazione.setPreferredSize(new Dimension(larghezza, 70));
        panelIntestazione.setBackground(Color.ORANGE);

        panelIntestazione.add(labelPunteggio);
        panelIntestazione.add(Box.createHorizontalGlue());
        panelIntestazione.add(labelTempo);

        labelPunteggio.setBorder(new EmptyBorder(0, 30, 0, 0));
        labelTempo.setBorder(new EmptyBorder(0, 0, 0, 30));

        this.add(panelIntestazione, BorderLayout.NORTH);
        this.add(tabellone);
    }

    public static FinestraDiGiGioco getFinestraDiGiGioco(String titolo, int larghezza, int altezza, Tabellone tabellone, JLabel labelPunteggio, JLabel labelTempo) {
        if(finestraDiGiGioco == null) finestraDiGiGioco = new FinestraDiGiGioco(titolo, larghezza, altezza, tabellone, labelPunteggio, labelTempo);
        return finestraDiGiGioco;
    }
}