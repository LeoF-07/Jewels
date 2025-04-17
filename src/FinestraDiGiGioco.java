import javax.swing.*;
import java.awt.*;

public class FinestraDiGiGioco extends JFrame {

    private Tabellone tabellone;
    private JLabel intestazione;

    private static FinestraDiGiGioco finestraDiGiGioco;

    private FinestraDiGiGioco(String titolo, int larghezza, int altezza, Tabellone tabellone, JLabel intestazione){
        super(titolo);

        JPanel jPanel = new JPanel();
        jPanel.setBounds(100, 100, larghezza, altezza);
        //jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        intestazione.setPreferredSize(new Dimension(800, 40));

        JPanel pT = new JPanel();
        pT.add(tabellone);


        jPanel.add(intestazione);
        jPanel.add(pT);

        add(jPanel);

        setBounds(80, 80, larghezza, altezza);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static FinestraDiGiGioco getFinestraDiGiGioco(String titolo, int larghezza, int altezza, Tabellone tabellone, JLabel intestazione) {
        if(finestraDiGiGioco == null) finestraDiGiGioco = new FinestraDiGiGioco(titolo, larghezza, altezza, tabellone, intestazione);
        return finestraDiGiGioco;
    }
}