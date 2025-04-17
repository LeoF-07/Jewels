import javax.swing.*;

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
    protected Object doInBackground() throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tabellone.setVisible(false);
                for(int i = 0; i < rows; i++){
                    for(int j = 0; j < cols; j++){
                        caselle[i][j].setIcon(new ImageIcon(gemme[i][j].path));
                        caselle[i][j].setText("");
                        ListenerPulsanteGemma listenerPulsanteGemma = (ListenerPulsanteGemma) caselle[i][j].getActionListeners()[0];
                        listenerPulsanteGemma.setGemma(gemme[i][j]);
                    }
                }
                tabellone.setVisible(true);

                if(updateScalatura) TesterJewels.controlloTutteLeCombinazioni();
            }
        });
        return null;
    }

}
