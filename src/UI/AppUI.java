package UI;

import Classes.Data;
import Classes.Destines;
import Classes.Trip;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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
    private JButton RegisterButton;
    private JButton startTripButton2;
    private JButton recargeTrip2;
    private JButton startTripButton3;
    private JButton recargeTrip3;
    private JLabel vehicleLabel2;
    private JLabel vehicleLabel3;
    private JLabel tripGasoline2;
    private JLabel tripGasoline3;
    private JLabel tripDistance2;
    private JLabel tripDistance3;
    private final Data baseData = new Data();
    public static DefaultTableModel model = new DefaultTableModel();

    public static DefaultComboBoxModel<String> vehicleComboModel = new DefaultComboBoxModel<>(), starTripModel = new DefaultComboBoxModel<>(), endTripModel = new DefaultComboBoxModel<>();

    public java.util.List<TripsAnimated> tripsAnimated = java.util.List.of(new AppUI.TripsAnimated("", vehicleLabel1, tripDistance1, startTripButton1, recargeTrip1, new Timer(100, null), 200)
            , new AppUI.TripsAnimated("", vehicleLabel2, tripDistance2, startTripButton2, recargeTrip2, new Timer(100, null), 50),
            new AppUI.TripsAnimated("", vehicleLabel3, tripDistance3, startTripButton3, recargeTrip3, new Timer(100, null), 90));


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

        //Destines
        DestinesUI destinesUI = new DestinesUI();
        //Generate routes
        GenerateRoutesUi routesUi = new GenerateRoutesUi();
        //Trips
        TripUI tripUI = new TripUI();

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



                Data.tripList.add(new Trip(new Destines(startLabel.getText(), endLabel.getText(), distanceLabel.getText()), baseData.vehicleList.get(starTripCombo.getSelectedIndex()),



                        ));

            });

        }
    }

    private class TripUI {
        TripUI() {


            initAllTripsButton.addActionListener(e -> {
                tripsAnimated.get(0).tripTimer.start();
                tripsAnimated.get(2).tripTimer .start();
                tripsAnimated.get(3).tripTimer.start();
            });
        }
    }

    public class TripsAnimated {
        private static final int TOTAL_DISTANCE = 350;
        private static final int INITIAL_X = 5;
        private double INITIAL_DISTANCE;

        private double distance;
        private double xvelocity;
        private double x = INITIAL_X;
        private Timer tripTimer;
        private boolean reverse = false;
        private String path;
        private JLabel vehicleLabel;
        private JLabel distanceLabel;
        private JButton inittripButton;
        private JButton recargeGasolineButton;

        TripsAnimated(String path, JLabel vehicleLabel, JLabel distanceLabel, JButton inittripButton, JButton recargeGasolineButton, Timer timer, int INITIAL_DISTANCE) {
            this.path = path;
            this.vehicleLabel = vehicleLabel;
            this.distanceLabel = distanceLabel;
            this.inittripButton = inittripButton;
            this.recargeGasolineButton = recargeGasolineButton;
            this.tripTimer = timer;
            this.INITIAL_DISTANCE = INITIAL_DISTANCE;
            distance = INITIAL_DISTANCE;
            xvelocity = TOTAL_DISTANCE / distance;

            vehicleLabel.setIcon(new ImageIcon(getSystemResource(path)));
            tripTimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateDistanceAndPosition();
                    distanceLabel.setText(String.valueOf(distance));
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            vehicleLabel.setLocation((int) x, 20);
                            vehicleLabel.repaint();
                        }
                    });
                }
            });

            inittripButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tripTimer.start();
                }
            });

            recargeGasolineButton.addActionListener(e -> {
                distance = INITIAL_DISTANCE;
                distanceLabel.setText(String.valueOf(distance));
            });
        }

        private void updateDistanceAndPosition() {
            distance -= 1;
            x += reverse ? -xvelocity : xvelocity;
            if (distance == 0) {
                tripTimer.stop();
                distanceLabel.setText(reverse ? "Iniciar" : "Volver");
                reverse = !reverse;
                distance = INITIAL_DISTANCE;
                flipImage();
            }
        }

        public void flipImage() {
            ImageIcon icon = (ImageIcon) vehicleLabel.getIcon();
            BufferedImage bufferedImage = new BufferedImage(
                    icon.getIconWidth(),
                    icon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            icon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-bufferedImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bufferedImage = op.filter(bufferedImage, null);
            vehicleLabel.setIcon(new ImageIcon(bufferedImage));
        }
    }

    public static void setBorderColorOfComboBoxPopup(JComboBox<String> comboBox) {
        Object child = comboBox.getAccessibleContext().getAccessibleChild(0);
        BasicComboPopup popup = (BasicComboPopup) child;
        popup.setBorder(new LineBorder(new Color(0x12372A)));
    }

}
