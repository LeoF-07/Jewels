import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    private Integer[] tempi = {1, 2, 5};

    public Menu(String titolo, int larghezza, int altezza, Classifica classifica){
        super(titolo);
        this.setBounds(10, 10, larghezza, altezza);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel panelTitolo = new JPanel();
        panelTitolo.setPreferredSize(new Dimension(larghezza, 50));
        JLabel labelTitolo = new JLabel("JEWELS");
        panelTitolo.add(labelTitolo);
        this.add(panelTitolo);


        String[] attributi = {"Punteggio", "Tempo"};

        JPanel panelTabelle = new JPanel();
        panelTabelle.setPreferredSize(new Dimension(larghezza, 100));
        panelTabelle.setLayout(new BoxLayout(panelTabelle, BoxLayout.X_AXIS));

        JPanel panelTabella1 = new JPanel();
        panelTabella1.setPreferredSize(new Dimension(200, 100));
        JTable tabella1 = new JTable(classifica.unMinuto, attributi);
        panelTabella1.add(tabella1);
        panelTabelle.add(panelTabella1);

        JPanel panelTabella2 = new JPanel();
        panelTabella2.setPreferredSize(new Dimension(200, 100));
        JTable tabella2 = new JTable(classifica.dueMinuti, attributi);
        panelTabella2.add(tabella2);
        panelTabelle.add(panelTabella2);

        JPanel panelTabella3 = new JPanel();
        panelTabella3.setPreferredSize(new Dimension(200, 100));
        JTable tabella3 = new JTable(classifica.cinqueMinuti, attributi);
        panelTabella3.add(tabella3);
        panelTabelle.add(panelTabella3);

        this.add(panelTabelle);

        JComboBox<Integer> selettoreTempo = new JComboBox<>(tempi);
        JPanel panelSelettoreTempo = new JPanel();
        selettoreTempo.setBounds(50, 20, 50, 40);
        panelSelettoreTempo.add(selettoreTempo);
        JLabel descrizioneTempo = new JLabel("Scegli i minuti di durata della partita");
        descrizioneTempo.setBounds(120, 20, 400, 40);
        panelSelettoreTempo.add(descrizioneTempo);

        JButton pulsanteAvvio = new JButton("Gioca");
        JPanel panelPulsanteAvvio = new JPanel();
        pulsanteAvvio.setBounds(0, 20, 100, 40);
        panelPulsanteAvvio.add(pulsanteAvvio);

        pulsanteAvvio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TesterJewels.gioca(selettoreTempo.getItemAt(selettoreTempo.getSelectedIndex()));
            }
        });

        JPanel panelAvvioGioco = new JPanel(new FlowLayout());
        panelAvvioGioco.setPreferredSize(new Dimension(larghezza, 500));

        panelAvvioGioco.add(panelSelettoreTempo);
        panelAvvioGioco.add(panelPulsanteAvvio);

        this.add(panelAvvioGioco);
        this.setVisible(true);
    }

}
