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
        switch (tempoScelto){
            case 1:
                if(punteggio > Integer.parseInt(unMinuto[1][0])){
                    unMinuto[3][0] = unMinuto[2][0];
                    unMinuto[2][0] = unMinuto[1][0];
                    unMinuto[1][0] = Integer.toString(punteggio);
                }

                else if(punteggio > Integer.parseInt(unMinuto[2][0])){
                    unMinuto[3][0] = unMinuto[2][0];
                    unMinuto[2][0] = Integer.toString(punteggio);
                }

                else if(punteggio > Integer.parseInt(unMinuto[3][0])){
                    unMinuto[3][0] = Integer.toString(punteggio);
                }
                try {
                    FileWriter fileWriter = new FileWriter(".\\Classifica\\UnMinuto.txt");
                    PrintWriter printWriter = new PrintWriter(fileWriter);

                    for(int i = 0; i < 4; i++){
                        if(i == 3) printWriter.write(unMinuto[i][0] + ";" + unMinuto[i][1]);
                        else printWriter.write(unMinuto[i][0] + ";" + unMinuto[i][1] + "\n");
                        printWriter.flush();
                    }

                    printWriter.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;

            case 2:
                if(punteggio > Integer.parseInt(dueMinuti[1][0])){
                    dueMinuti[3][0] = dueMinuti[2][0];
                    dueMinuti[2][0] = dueMinuti[1][0];
                    dueMinuti[1][0] = Integer.toString(punteggio);
                }

                else if(punteggio > Integer.parseInt(dueMinuti[2][0])){
                    dueMinuti[3][0] = dueMinuti[2][0];
                    dueMinuti[2][0] = Integer.toString(punteggio);
                }

                else if(punteggio > Integer.parseInt(dueMinuti[3][0])){
                    dueMinuti[3][0] = Integer.toString(punteggio);
                }
                try {
                    FileWriter fileWriter = new FileWriter(".\\Classifica\\DueMinuti.txt");
                    PrintWriter printWriter = new PrintWriter(fileWriter);

                    for(int i = 0; i < 4; i++){
                        if(i == 3) printWriter.write(dueMinuti[i][0] + ";" + dueMinuti[i][1]);
                        else printWriter.write(dueMinuti[i][0] + ";" + dueMinuti[i][1] + "\n");
                        printWriter.flush();
                    }

                    printWriter.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;

            case 5:
                if(punteggio > Integer.parseInt(cinqueMinuti[1][0])){
                    cinqueMinuti[3][0] = cinqueMinuti[2][0];
                    cinqueMinuti[2][0] = cinqueMinuti[1][0];
                    cinqueMinuti[1][0] = Integer.toString(punteggio);
                }

                else if(punteggio > Integer.parseInt(cinqueMinuti[2][0])){
                    cinqueMinuti[3][0] = cinqueMinuti[2][0];
                    cinqueMinuti[2][0] = Integer.toString(punteggio);
                }

                else if(punteggio > Integer.parseInt(cinqueMinuti[3][0])){
                    cinqueMinuti[3][0] = Integer.toString(punteggio);
                }
                try {
                    FileWriter fileWriter = new FileWriter(".\\Classifica\\CinqueMinuti.txt");
                    PrintWriter printWriter = new PrintWriter(fileWriter);

                    for(int i = 0; i < 4; i++){
                        if(i == 3) printWriter.write(cinqueMinuti[i][0] + ";" + cinqueMinuti[i][1]);
                        else printWriter.write(cinqueMinuti[i][0] + ";" + cinqueMinuti[i][1] + "\n");
                        printWriter.flush();
                    }

                    printWriter.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

}
