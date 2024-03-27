package Classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Register implements Serializable {

    public LocalDateTime initTrip;
    public LocalDateTime endTrip;
    public int distance;

    public String nameVehicle;
    public double consumeGasoline;

    Register(LocalDateTime initTrip, LocalDateTime endTrip, int distance, String nameVehicle, double consumeGasoline) {
        this.initTrip = initTrip;
        this.endTrip = endTrip;
        this.distance = distance;
        this.nameVehicle = nameVehicle;
        this.consumeGasoline = consumeGasoline;
    }
}
