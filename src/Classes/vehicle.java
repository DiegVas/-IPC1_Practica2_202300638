package Classes;

import java.io.Serializable;

public class vehicle implements Serializable {
    typeVehicle typeVehicle;
    public String name;

    public vehicle(String name, typeVehicle typeVehicle) {
        this.name = name;
        this.typeVehicle = typeVehicle;
    }
}


