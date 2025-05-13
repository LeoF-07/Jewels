import javax.swing.*;
import java.util.List;

public class TabelloneUpdater extends SwingWorker<Object, Object> {

    private int rows;
    private int cols;
    private Gemma[][] gemme;
    private JButton[][] caselle;
    private Tabellone tabellone;
    private boolean updateScalatura;

    public TabelloneUpdater(int rows, int cols, Gemma[][] gemme, JButton[][] caselle, Tabellone tabellone, boolean updateScalatura){
        this.rows = rows;
        this.cols = cols;
        this.gemme = gemme;
        this.caselle = caselle;
        this.tabellone = tabellone;
        this.updateScalatura = updateScalatura;
    }

    @Override
    protected Object doInBackground() throws Exception { // Serve a caricare le immagini nella GUI
        publish(0);
        return null;
    }

    @Override
    protected void process(List<Object> chunks) {
        tabellone.setVisible(false);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                caselle[i][j].setIcon(new ImageIcon(gemme[i][j].path));
                caselle[i][j].setText("");

                if(caselle[i][j].getActionListeners().length != 0){
                    ListenerPulsanteGemma listenerPulsanteGemma = (ListenerPulsanteGemma) caselle[i][j].getActionListeners()[0];
                    listenerPulsanteGemma.setGemma(gemme[i][j]);
                }
            }
        }
        tabellone.setVisible(true); // Una volta fatto l'update rende visibile il tabellone

        if(updateScalatura) TesterJewels.controlloTutteLeCombinazioni(); // Finito l'update e dopo aver scalato le gemme (class Scalatore) ricontrolla le combinazioni
    }

}
