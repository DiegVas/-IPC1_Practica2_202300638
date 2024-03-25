package Classes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import static java.lang.ClassLoader.getSystemResource;

public class TripAnimated {
    private static final int TOTAL_DISTANCE = 350;
    private double INITIAL_DISTANCE;
    private double distance;
    private double xvelocity;
    public double x;
    private boolean reverse = false;
    private double thank;
    Trip trip;
    public Timer tripTimer = new Timer(100, null);
    private JLabel distanceLabel;
    private JLabel vehicleLabel;
    private JLabel gasolineLabel;
    private JButton inittripButton;
    private JButton recargeGasolineButton;


    public TripAnimated(JLabel vehicleLabel, JLabel distanceLabel, JLabel gasolineLabel, JLabel tripVehicle, JButton inittripButton, JButton recargeGasolineButton, Trip trip) {

        this.trip = trip;
        this.vehicleLabel = vehicleLabel;
        this.distanceLabel = distanceLabel;
        this.gasolineLabel = gasolineLabel;
        this.inittripButton = inittripButton;
        this.recargeGasolineButton = recargeGasolineButton;
        this.INITIAL_DISTANCE = Double.parseDouble(trip.destines.distance);
        this.thank = trip.vehicle.typeVehicle.tanks;


        distance = INITIAL_DISTANCE;
        xvelocity = TOTAL_DISTANCE / distance;

        tripVehicle.setText(trip.vehicle.name);
        vehicleLabel.setIcon(new ImageIcon(getSystemResource(trip.vehicle.typeVehicle.pathImage)));


        tripTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tripTimer.isRunning() && thank > 0) x = 5;

                updateDistanceAndPosition();
                distanceLabel.setText(String.valueOf(distance));
                gasolineLabel.setText(String.format("%.2f", thank));
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        vehicleLabel.setLocation((int) x, 20);
                        for (TripAnimated trip : Data.tripsAnimated) trip.vehicleLabel.setLocation((int) trip.x, 20);

                    }
                });
            }
        });

        inittripButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (thank > 0) tripTimer.start();
                else JOptionPane.showMessageDialog(null, "Tanque vacio");

            }
        });

        recargeGasolineButton.addActionListener(e -> {
            thank = trip.vehicle.typeVehicle.tanks;
            gasolineLabel.setText(String.valueOf(thank));
            recargeGasolineButton.setEnabled(false);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    for (TripAnimated trip : Data.tripsAnimated) trip.vehicleLabel.setLocation((int) trip.x, 20);

                }
            });
            
        });
    }

    private void updateDistanceAndPosition() {
        if (thank <= 0) {
            tripTimer.stop();
            recargeGasolineButton.setEnabled(true);
            return;
        }

        distance -= 1;
        x += reverse ? -xvelocity : xvelocity;
        thank -= trip.vehicle.typeVehicle.oilConsume;

        if (thank < 0) thank = 0;

        if (distance == 0) {
            tripTimer.stop();
            inittripButton.setText(reverse ? "Iniciar" : "Volver");
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
