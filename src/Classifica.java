import javax.swing.*;
import java.io.*;

public class Classifica implements Serializable {

    public String[][] unMinuto;

    public String[][] dueMinuti;

    public String[][] cinqueMinuti;

    public Classifica(){
        unMinuto = prelevaClassifica(".\\Classifica\\unMinuto.txt");
        dueMinuti = prelevaClassifica(".\\Classifica\\dueMinuti.txt");
        cinqueMinuti = prelevaClassifica(".\\Classifica\\cinqueMinuti.txt");
    }

    private String[][] prelevaClassifica(String path){
        String[][] classifica = new String[4][2];

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for(int i = 0; bufferedReader.ready(); i++){
                String line = bufferedReader.readLine();
                classifica[i] = line.split(";");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return classifica;
    }

    public void aggiorna(int tempoScelto, int punteggio){
        boolean recordEffetuato = false;

        switch (tempoScelto){
            case 1:
                if(punteggio > Integer.parseInt(unMinuto[1][0])){
                    unMinuto[3][0] = unMinuto[2][0];
                    unMinuto[2][0] = unMinuto[1][0];
                    unMinuto[1][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                else if(punteggio > Integer.parseInt(unMinuto[2][0])){
                    unMinuto[3][0] = unMinuto[2][0];
                    unMinuto[2][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                else if(punteggio > Integer.parseInt(unMinuto[3][0])){
                    unMinuto[3][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                if(recordEffetuato){
                    JOptionPane.showMessageDialog(null, "Il tuo punteggio è nel podio!");
                    salvaSulFile(unMinuto, ".\\Classifica\\UnMinuto.txt");
                }

                break;

            case 2:
                if(punteggio > Integer.parseInt(dueMinuti[1][0])){
                    dueMinuti[3][0] = dueMinuti[2][0];
                    dueMinuti[2][0] = dueMinuti[1][0];
                    dueMinuti[1][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                else if(punteggio > Integer.parseInt(dueMinuti[2][0])){
                    dueMinuti[3][0] = dueMinuti[2][0];
                    dueMinuti[2][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                else if(punteggio > Integer.parseInt(dueMinuti[3][0])){
                    dueMinuti[3][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                if(recordEffetuato){
                    JOptionPane.showMessageDialog(null, "Il tuo punteggio è nel podio!");
                    salvaSulFile(dueMinuti, ".\\Classifica\\DueMinuti.txt");
                }

                break;

            case 5:
                if(punteggio > Integer.parseInt(cinqueMinuti[1][0])){
                    cinqueMinuti[3][0] = cinqueMinuti[2][0];
                    cinqueMinuti[2][0] = cinqueMinuti[1][0];
                    cinqueMinuti[1][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                else if(punteggio > Integer.parseInt(cinqueMinuti[2][0])){
                    cinqueMinuti[3][0] = cinqueMinuti[2][0];
                    cinqueMinuti[2][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                else if(punteggio > Integer.parseInt(cinqueMinuti[3][0])){
                    cinqueMinuti[3][0] = Integer.toString(punteggio);
                    recordEffetuato = true;
                }

                if(recordEffetuato){
                    JOptionPane.showMessageDialog(null, "Il tuo punteggio è nel podio!");
                    salvaSulFile(cinqueMinuti, ".\\Classifica\\CinqueMinuti.txt");
                }

                break;
        }
    }

    private void salvaSulFile(String[][] classifica, String path){
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for(int i = 0; i < 4; i++){
                if(i == 3) printWriter.write(classifica[i][0] + ";" + classifica[i][1]);
                else printWriter.write(classifica[i][0] + ";" + classifica[i][1] + "\n");
                printWriter.flush();
            }

            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
