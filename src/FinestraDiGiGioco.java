import javax.swing.*;

public class FinestraDiGiGioco extends JFrame {

    private Tabellone tabellone;

    private static FinestraDiGiGioco finestraDiGiGioco;

    private FinestraDiGiGioco(String titolo, int larghezza, int altezza, Tabellone tabellone){
        super(titolo);

        this.tabellone = tabellone;
        add(tabellone);

        setBounds(80, 80, larghezza, altezza);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static FinestraDiGiGioco getFinestraDiGiGioco(String titolo, int larghezza, int altezza, Tabellone tabellone) {
        if(finestraDiGiGioco == null) finestraDiGiGioco = new FinestraDiGiGioco(titolo, larghezza, altezza, tabellone);
        return finestraDiGiGioco;
    }

}
