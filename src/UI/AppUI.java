package UI;

import Classes.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

import static Classes.Data.pilotList;

public class AppUI extends JFrame {
    private JPanel Manager;
    private JButton travelsButton;
    private JPanel panelLayout;
    private JPanel destinyPanel;
    private JPanel generateTrips;
    private JPanel tripsPanel;
    private JButton editDestinyButton;
    private JButton tripButton;
    private JPanel destiny_File;
    private JTable destinyTable;
    private JPanel editPanel;
    private JButton generateButton;
    private JComboBox<String> starTripCombo, endTripCombo, typeVehicleCombo;
    private JLabel Pilots;
    private JLabel startLabel;
    private JLabel endLabel;
    private JLabel distanceLabel;
    private JButton initAllTripsButton;
    private JLabel tripStartLabel;
    private JLabel tripEndLabel;
    private JLabel tripVehicle1;
    private JLabel tripGasoline1;
    private JButton startTripButton1;
    private JButton recargeTrip1;
    private JLabel vehicleLabel1;
    private JButton RegisterButton;
    private JButton startTripButton2;
    private JButton recargeTrip2;
    private JButton startTripButton3;
    private JButton recargeTrip3;
    private JLabel vehicleLabel2;
    private JLabel vehicleLabel3;
    private JLabel tripGasoline2;
    private JLabel tripGasoline3;
    private JLabel tripVehicle2;
    private JLabel tripVehicle3;
    private JLabel tripDistance1;
    private JLabel tripDistance2;
    private JLabel tripDistance3;
    private final Data baseData = new Data();
    public static DefaultTableModel model = new DefaultTableModel();

    public static DefaultComboBoxModel<String> vehicleComboModel = new DefaultComboBoxModel<>(), starTripModel = new DefaultComboBoxModel<>(), endTripModel = new DefaultComboBoxModel<>();

    public java.util.List<TripAnimated> tripsAnimated = java.util.List.of(
            new TripAnimated(vehicleLabel1, tripDistance1, tripGasoline1, tripVehicle1, startTripButton1, recargeTrip1, new Trip(new Destines("A", "B", "100"), new vehicle("Motocicleta 1", typeVehicle.Motorcycle))),
            new TripAnimated(vehicleLabel2, tripDistance2, tripGasoline2, tripVehicle2, startTripButton2, recargeTrip2, new Trip(new Destines("A", "B", "100"), new vehicle("Motocicleta 1", typeVehicle.Motorcycle))),
            new TripAnimated(vehicleLabel3, tripDistance3, tripGasoline3, tripVehicle3, startTripButton3, recargeTrip3, new Trip(new Destines("A", "B", "100"), new vehicle("Motocicleta 1", typeVehicle.Motorcycle)))
    );


    public AppUI() {
        Data data = new Data();
        data.chargeAnimated(tripsAnimated);
        Pilots.setText(String.valueOf(pilotList.size()));

        //Tabs
        CardLayout cardLayout = (CardLayout) (panelLayout.getLayout());
        panelLayout.setLayout(cardLayout);

        setContentPane(Manager);
        setTitle("Navigo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();


        UIManager.put("Button.select", new Color(0x436850));

        //Destines
        new DestinesUI();
        //Generate routes
        new GenerateRoutesUi();
        //Trips
        new TripUI();

    }


    private class DestinesUI {
        DestinesUI() {

            Object[] headers = {"id", "Inicio", "Final", "Distancia"};
            destinyTable.setModel(model);
            model.setColumnIdentifiers(headers);

            destinyTable.setFont(new Font("Robot", Font.PLAIN, 14));
            destinyTable.setForeground(new Color(0x12372A));
            destinyTable.setRowHeight(25);

            JTableHeader header = destinyTable.getTableHeader();
            header.setBackground(new Color(0x436850));
            header.setForeground(Color.white);
            header.setPreferredSize(new Dimension(100, 30));
            header.setFont(new Font("Roboto", Font.BOLD, 15));


            travelsButton.addActionListener(e -> ((CardLayout) panelLayout.getLayout()).show(panelLayout, "destiny"));
            editDestinyButton.addActionListener(e -> ((CardLayout) panelLayout.getLayout()).show(panelLayout, "editDestiny"));
            tripButton.addActionListener(e -> ((CardLayout) panelLayout.getLayout()).show(panelLayout, "trips"));
            RegisterButton.addActionListener(e -> ((CardLayout) panelLayout.getLayout()).show(panelLayout, "register"));
            destiny_File.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                             UnsupportedLookAndFeelException ex) {
                        throw new RuntimeException(ex);
                    }

                    JFileChooser chooser = new JFileChooser();
                    FileFilter filter = new FileNameExtensionFilter("files", "csv");
                    chooser.setFileFilter(filter);

                    int select = chooser.showDialog(Manager, "Seleccionar CSV");

                    if (select == JFileChooser.APPROVE_OPTION) {
                        File fileCSV = chooser.getSelectedFile();
                        Data readCSV = new Data();
                        readCSV.loadDestines(fileCSV.getAbsolutePath());
                    }

                    super.mouseClicked(e);
                }
            });
            editPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (destinyTable.getSelectedRow() == -1) return;
                    EditDistance dialog = new EditDistance(Data.destinesList.get(destinyTable.getSelectedRow()));
                    dialog.pack();
                    dialog.setVisible(true);
                    super.mouseClicked(e);
                }
            });
        }
    }

    private class GenerateRoutesUi {
        GenerateRoutesUi() {
            vehicleComboModel.addAll(baseData.vehicleList.stream().map((e) -> e.name).toList());
            typeVehicleCombo.setModel(vehicleComboModel);
            starTripCombo.setModel(starTripModel);
            endTripCombo.setModel(endTripModel);

            setBorderColorOfComboBoxPopup(starTripCombo);
            setBorderColorOfComboBoxPopup(endTripCombo);
            setBorderColorOfComboBoxPopup(typeVehicleCombo);

            // Pilots.setText(String.valueOf(Data.Pilots));

            starTripCombo.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectTrip = Objects.requireNonNull(starTripCombo.getSelectedItem()).toString();
                    Destines destineInfo = Data.destinesList.stream().filter(destines -> destines.start.equals(selectTrip) || destines.end.equals(selectTrip)).toList().getFirst();
                    String endTrip = selectTrip.equals(destineInfo.start) ? destineInfo.end : destineInfo.start;

                    endTripModel.removeAllElements();
                    endTripModel.addElement(endTrip);
                    startLabel.setText(selectTrip);
                    endLabel.setText(endTrip);
                    distanceLabel.setText(destineInfo.distance);

                }
            });

            generateButton.addActionListener(e -> {

                String start = startLabel.getText();
                String end = endLabel.getText();
                String distance = distanceLabel.getText();
                Destines destines = new Destines(start, end, distance);

                vehicle vehicle = baseData.vehicleList.stream().filter(vehicle1 -> vehicle1.name.equals(Objects.requireNonNull(typeVehicleCombo.getSelectedItem()).toString())).toList().getFirst();

                int indexFound;
                List<Trip> availablePilots = pilotList.stream().filter(pilot -> pilot.destines.distance.isEmpty()).toList();
                indexFound = !availablePilots.isEmpty() ? pilotList.indexOf(availablePilots.getFirst()) : -1;

                Pilots.setText(String.valueOf(availablePilots.size() - 1));

                if (availablePilots.size() - 1 == 0) {
                    Pilots.setForeground(Color.DARK_GRAY);
                    generateButton.setBackground(Color.gray);
                    generateButton.setForeground(Color.DARK_GRAY);
                    JOptionPane.showMessageDialog(null, "No hay pilotos disponibles");
                    return;
                }

                pilotList.set(indexFound, new Trip(destines, vehicle));

            });

        }


    }

    private class TripUI {
        TripUI() {
            initAllTripsButton.addActionListener(e -> {
                for (TripAnimated trip : tripsAnimated) trip.tripTimer.start();
            });
        }
    }

    public static void setBorderColorOfComboBoxPopup(JComboBox<String> comboBox) {
        Object child = comboBox.getAccessibleContext().getAccessibleChild(0);
        BasicComboPopup popup = (BasicComboPopup) child;
        popup.setBorder(new LineBorder(new Color(0x12372A)));
    }

}
