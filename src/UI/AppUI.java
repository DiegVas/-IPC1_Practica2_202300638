package UI;

import Classes.Data;
import Classes.Destines;

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
import java.util.Objects;

import static java.lang.ClassLoader.getSystemResource;

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
    private JLabel tripDistance1;
    private JLabel tripVehicle1;
    private JLabel tripGasoline1;
    private JButton startTripButton1;
    private JButton recargeTrip1;
    private JLabel vehicleLabel1;
    private JLabel tripvehicle2;
    private final Data baseData = new Data();
    public static DefaultTableModel model = new DefaultTableModel();

    public static DefaultComboBoxModel<String> vehicleComboModel = new DefaultComboBoxModel<>(), starTripModel = new DefaultComboBoxModel<>(), endTripModel = new DefaultComboBoxModel<>();

    public AppUI() {

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
        vehicleLabel1.setIcon(new ImageIcon(getSystemResource("./src/vehiclesImage/vehiclePremium.png")));

        //Destines
        DestinesUI destinesUI = new DestinesUI();
        //Generate routes
        GenerateRoutesUi routesUi = new GenerateRoutesUi();
        //Trips
        TripsUI tripsUI = new TripsUI();
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

            Pilots.setText(String.valueOf(Data.Pilots));

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
                Data.Pilots -= 1;
                Pilots.setText(String.valueOf(Data.Pilots));
                if (Data.Pilots == 0) {
                    generateButton.setEnabled(false);
                    Pilots.setForeground(Color.red);
                    generateButton.setBackground(Color.red);
                    generateButton.setForeground(Color.black);
                }
            });

        }
    }

    private class TripsUI {
        private double distance = 100;
        private int x = 5;
        private double distanceRes = distance / (350 - x);
        private Timer tripTimer1;

        TripsUI() {
            tripTimer1 = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(distanceRes);

                    distance -= distanceRes;
                    x += 1;
                    if (x == 350) tripTimer1.stop();
                    tripGasoline1.setText(String.format("%.2f", distance));
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            vehicleLabel1.setLocation(x, 20);
                            vehicleLabel1.repaint();
                        }
                    });
                }
            });

            startTripButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tripTimer1.start();
                }
            });

            recargeTrip1.addActionListener(e -> {
                distance = 20;
                tripGasoline1.setText(String.valueOf(distance));
            });
        }
    }


    public static void setBorderColorOfComboBoxPopup(JComboBox<String> comboBox) {
        Object child = comboBox.getAccessibleContext().getAccessibleChild(0);
        BasicComboPopup popup = (BasicComboPopup) child;
        popup.setBorder(new LineBorder(new Color(0x12372A)));
    }

}
