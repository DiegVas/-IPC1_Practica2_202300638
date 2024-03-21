package Classes;

public enum typeVehicle {
    Motorcycle(0.1, 6, "./src/vehiclesImage/motocicle.png"), Standard(0.3, 10, "./src/vehiclesImage/vehicleStandar.png"), Premium(0.45, 12, "./src/vehiclesImage/vehiclePremium.png");

    typeVehicle(double gasoline, int tanks, String pathImage) {
    }
}
