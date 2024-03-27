package Classes;

import UI.AppUI;
import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static Classes.Data.pilotList;
import static java.lang.ClassLoader.getSystemResource;

public class TripAnimated implements Serializable {
    private static final int TOTAL_DISTANCE = 350;
    private double INITIAL_DISTANCE;
    private double distance;
    private double xvelocity;
    private double thank;
    private double consumeGasoline;
    public double x;
    private Trip trip;
    public JButton recargeGasolineButton;
    public JButton inittripButton;
    public JButton generateButton;
    public JLabel distanceLabel;
    public JLabel vehicleLabel;
    public JLabel gasolineLabel;
    public JLabel tripVehicle;
    public JLabel pilotsLabel;
    private int indexFound;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int lap = 0;
    public Timer tripTimer = new Timer(100, null);
    private boolean reverse = false;

    public TripAnimated(JLabel vehicleLabel, JLabel distanceLabel, JLabel gasolineLabel, JLabel tripVehicle, JLabel pilotsLabel, JButton inittripButton, JButton recargeGasolineButton, JButton generateButton, Trip trip, int indexFound) {

        this.INITIAL_DISTANCE = Double.parseDouble(trip.destines.distance);
        this.recargeGasolineButton = recargeGasolineButton;
        this.inittripButton = inittripButton;
        this.gasolineLabel = gasolineLabel;
        this.distanceLabel = distanceLabel;
        this.vehicleLabel = vehicleLabel;
        this.tripVehicle = vehicleLabel;
        this.generateButton = generateButton;
        this.indexFound = indexFound;
        this.pilotsLabel = pilotsLabel;
        this.thank = trip.vehicle.typeVehicle.tanks;
        this.trip = trip;

        distance = INITIAL_DISTANCE;
        xvelocity = TOTAL_DISTANCE / distance;

        tripVehicle.setText(trip.vehicle.name);
        distanceLabel.setText(String.valueOf(distance));
        gasolineLabel.setText(String.valueOf(thank));
        vehicleLabel.setIcon(new ImageIcon(getSystemResource(trip.vehicle.typeVehicle.pathImage)));

        tripTimer.addActionListener(e -> {
            updateDistanceAndPosition();
            distanceLabel.setText(String.valueOf(distance));
            gasolineLabel.setText(String.format("%.2f", thank));
            SwingUtilities.invokeLater(() -> {
                vehicleLabel.setLocation((int) x, 20);
                for (TripAnimated trip1 : Data.tripsAnimated) trip1.vehicleLabel.setLocation((int) trip1.x, 20);
            });
        });

        inittripButton.addActionListener(e -> {
            if (thank > 0) tripTimer.start();
        });

        recargeGasolineButton.addActionListener(e -> {
            thank = trip.vehicle.typeVehicle.tanks;
            recargeGasolineButton.setEnabled(false);
            gasolineLabel.setText(String.valueOf(thank));

            SwingUtilities.invokeLater(() -> {
                vehicleLabel.setLocation((int) x, 20);
                for (TripAnimated trip12 : Data.tripsAnimated) {
                    trip12.vehicleLabel.setLocation((int) trip12.x, 20);
                    trip12.gasolineLabel.setText(String.valueOf(trip12.thank));
                }
            });

        });
    }

    private void updateDistanceAndPosition() {
        if (thank <= 0) {
            tripTimer.stop();
            recargeGasolineButton.setEnabled(true);
            thank = 0;
            return;
        }

        distance -= 1;
        x += reverse ? -xvelocity : xvelocity;
        thank -= trip.vehicle.typeVehicle.oilConsume;
        consumeGasoline += trip.vehicle.typeVehicle.oilConsume;

        if (distance == 0) {
            tripTimer.stop();
            inittripButton.setText(reverse ? "Iniciar" : "Volver");
            reverse = !reverse;
            lap += 1;
            distance = INITIAL_DISTANCE;
            flipImage();

            if (lap == 2) {
                TripAnimated newAnimated = new TripAnimated(vehicleLabel, distanceLabel, gasolineLabel, tripVehicle, pilotsLabel, inittripButton, recargeGasolineButton, generateButton, new Trip(new Destines("", "", "0"), new vehicle("", typeVehicle.None)), indexFound);
                Data.tripsAnimated.set(indexFound, newAnimated);
                Data.pilotList.set(indexFound, new Trip(new Destines("", "", ""), new vehicle("", typeVehicle.None)));

                startTime = LocalDateTime.now();
                endTime = startTime.plusMinutes((long) (distance / xvelocity * 60));
                Data.registerList.add(new Register(startTime, endTime, Integer.parseInt(trip.destines.distance), trip.vehicle.name, consumeGasoline));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedStartTime = startTime.format(formatter);
                String formattedEndTime = endTime.format(formatter);

                Object[] tableRow = {Data.registerList.size(), formattedStartTime, formattedEndTime, trip.destines.distance, trip.vehicle.name, String.valueOf((int) consumeGasoline)};
                AppUI.registerModel.addRow(tableRow);

                generateButton.setEnabled(true);
                generateButton.setForeground(Color.white);
                generateButton.setBackground(new Color(25, 76, 64));

                List<Trip> availablePilots = pilotList.stream().filter(pilot -> pilot.destines.distance.isEmpty()).toList();
                pilotsLabel.setText(String.valueOf(availablePilots.size()));

                SwingUtilities.invokeLater(() -> {
                    vehicleLabel.setLocation((int) 5, 20);
                    distanceLabel.setText("0");
                    gasolineLabel.setText("0");
                });

            }

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
