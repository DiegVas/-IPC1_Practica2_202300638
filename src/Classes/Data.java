package Classes;

import UI.AppUI;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.awt.image.ImageObserver.ERROR;
import static javax.swing.JOptionPane.showMessageDialog;

public class Data {
    public static List<Destines> destinesList = new ArrayList<>();
    public static List<Register> registerList = new ArrayList<>();
    public static List<Trip> pilotList = new ArrayList<>(List.of(
            new Trip(new Destines("", "", ""), new vehicle("", typeVehicle.Standard)),
            new Trip(new Destines("", "", ""), new vehicle("", typeVehicle.Standard)),
            new Trip(new Destines("", "", ""), new vehicle("", typeVehicle.Standard)))
    );
    public static java.util.List<TripAnimated> tripsAnimated;

    public final List<vehicle> vehicleList = List.of(new vehicle("Motocicleta 1", typeVehicle.Motorcycle),
            new vehicle("Motocicleta 1", typeVehicle.Motorcycle),
            new vehicle("Motocicleta 2", typeVehicle.Motorcycle),
            new vehicle("Motocicleta 3", typeVehicle.Motorcycle),
            new vehicle("Vehiculo estandar 1", typeVehicle.Standard),
            new vehicle("Vehiculo estandar 2", typeVehicle.Standard),
            new vehicle("Vehiculo estandar 3", typeVehicle.Standard),
            new vehicle("Vehiculo premium 1", typeVehicle.Premium),
            new vehicle("Vehiculo premium 2", typeVehicle.Premium),
            new vehicle("Vehiculo premium 3", typeVehicle.Premium)
    );


    public void chargeAnimated(java.util.List<TripAnimated> tripsAnimated) {
        Data.tripsAnimated = tripsAnimated;
    }

    public void loadDestines(String path) {

        BufferedReader reader;
        destinesList = new ArrayList<>();

        try {
            String row;
            reader = new BufferedReader(new FileReader(path));
            AppUI.starTripModel.removeAllElements();
            AppUI.endTripModel.removeAllElements();
            AppUI.model.setRowCount(0);
            List<String> nameRoutres = new ArrayList<String>();

            while ((row = reader.readLine()) != null) {
                String[] cell = row.split("[;,]");
                destinesList.add(new Destines(cell[0], cell[1], cell[2]));
                Object[] tableRow = {destinesList.size(), cell[0], cell[1], cell[2]};
                AppUI.model.addRow(tableRow);
                nameRoutres.add(cell[0]);
                nameRoutres.add(cell[1]);
            }

            AppUI.starTripModel.addAll(nameRoutres);

        } catch (FileNotFoundException e) {
            showMessageDialog(null, "No se pudo leer el mensaje", "Error de lectura", ERROR);
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
