package Classes;

import java.io.Serializable;

public class Trip implements Serializable {
    public Destines destines;
    public vehicle vehicle;

    public Trip(Destines destines, vehicle vehicle) {
        this.destines = destines;
        this.vehicle = vehicle;

    }
}
