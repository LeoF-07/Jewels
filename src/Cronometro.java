import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Cronometro extends SwingWorker<Object, Object> {

    private JLabel labelTempo;
    private int tempoTotale;

    public Cronometro(JLabel labelTempo, int tempoTotale){
        this.labelTempo = labelTempo;
        this.tempoTotale = tempoTotale;
    }

    @Override
    protected Object doInBackground() throws Exception {
        TimerTask task = new TimerTask() {
            int min = 0;
            int sec = 0;

            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if(min == 0) labelTempo.setText("Tempo: " + sec + "s");
                        else labelTempo.setText("Tempo: " + min + "m " + sec + "s");
                        sec++;
                        if(sec == 60){
                            min++;
                            sec = 0;
                        }

                        if(min == tempoTotale) {
                            cancel();
                            TesterJewels.terminaPartita();
                        }
                    }
                });
            }
        };
        java.util.Timer timer = new Timer();

        timer.schedule(task, 0, 1000);

        return null;
    }
}
