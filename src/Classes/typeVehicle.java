package Classes;

import java.io.Serializable;

public enum typeVehicle implements Serializable {

    Motorcycle(0.1, 6, "/src/vehiclesImage/motocicle.png"),
    Standard(0.3, 10, "/src/vehiclesImage/vehicleStandar.png"),
    Premium(0.45, 12, "/src/vehiclesImage/vehiclePremium.png"), None(0, 0, "");
    public String pathImage;
    public double oilConsume;
    public int tanks;

    typeVehicle(double gasoline, int tanks, String pathImage) {
        this.oilConsume = gasoline;
        this.tanks = tanks;
        this.pathImage = pathImage;
    }
}
