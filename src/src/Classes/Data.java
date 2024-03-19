package src.Classes;

import UI.AppUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.awt.image.ImageObserver.ERROR;
import static javax.swing.JOptionPane.showMessageDialog;

public class Data {
    public static List<Destines> destinesList;
    public static List<Register> registerList;


    public List<Destines> loadDestines(String path) {

        BufferedReader reader;
        List<Destines> destinesList = new ArrayList<>();

        try {
            String row;
            reader = new BufferedReader(new FileReader(path));
            AppUI.model.setRowCount(0);

            while ((row = reader.readLine()) != null) {
                String[] cell = row.split("[;,]");
                destinesList.add(new Destines(cell[0], cell[1], cell[2]));
                Object[] tableRow = {destinesList.size(), cell[0], cell[1], cell[2]};

                AppUI.model.addRow(tableRow);
            }


        } catch (FileNotFoundException e) {
            showMessageDialog(null, "No se pudo leer el mensaje", "Error de lectura", ERROR);
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return destinesList;
    }


}
