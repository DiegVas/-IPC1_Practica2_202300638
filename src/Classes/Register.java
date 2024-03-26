package Classes;

import java.time.LocalDate;

public class Register {

    public LocalDate initTrip;
    public LocalDate endTrip;
    public int distance;

    public String nameVehicle;
    public double consumeGasoline;

    Register(LocalDate initTrip, LocalDate endTrip, int distance, String nameVehicle, double consumeGasoline) {
        this.initTrip = initTrip;
        this.endTrip = endTrip;
        this.distance = distance;
        this.nameVehicle = nameVehicle;
        this.consumeGasoline = consumeGasoline;
    }
}
