import javax.swing.*;

public class FinestraDiGiGioco extends JFrame implements Runnable {

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


    @Override
    public void run() {
        Thread threadTabellone = new Thread(tabellone);
        threadTabellone.start();

        /*
            Secondo me posso:
            o far partire i Thread di tabellone, intestazione ecc. sul costruttore (o fare un metodo che le fa partire);
            oppure far partire solo il thread di questa finestra, anche credo che nel run dovrei gestire tutti i click sia sull'intestazione che sul tabellone...
            se nell'intestazione decido di mettere solo cose non cliccabili allora potrei rendere anche quella un subscriber del Tester e far partire solo il Thread del tabellone
        */
    }

}
