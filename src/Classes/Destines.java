package Classes;

import java.io.Serializable;

public class Destines implements Serializable {

    public String start, end, distance;

    public Destines(String start, String end, String distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

}
