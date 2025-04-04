import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PulsanteGemma implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton pulsante = (JButton) e.getSource();
        System.out.println(pulsante.getText());
    }

}