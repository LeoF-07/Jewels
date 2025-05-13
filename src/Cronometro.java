import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Cronometro extends SwingWorker<Object, Object> {

    private JLabel labelTempo;
    private int tempoTotale;

    int min;
    int sec;
    int i;

    public Cronometro(JLabel labelTempo, int tempoTotale){
        this.labelTempo = labelTempo;
        this.tempoTotale = tempoTotale;
    }

    TimerTask task;

    @Override
    protected Object doInBackground() throws Exception {
        min = 0;
        sec = 0;
        i = 0;

        task = new TimerTask() {
            public void run() {
                publish(i);
                i++;
            }
        };
        Timer timer = new Timer();

        timer.schedule(task, 0, 1000);

        return null;
    }

    @Override
    protected void process(List<Object> chunks) {
        if(min == 0) labelTempo.setText("Tempo: " + sec + "s");
        else labelTempo.setText("Tempo: " + min + "m " + sec + "s");

        sec++;
        if(sec == 60){
            min++;
            sec = 0;
        }

        if(min == tempoTotale) {
            task.cancel();
            TesterJewels.terminaPartita();
        }
    }

}
