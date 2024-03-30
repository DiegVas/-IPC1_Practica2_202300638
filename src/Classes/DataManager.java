package Classes;

import java.io.*;
import java.net.URL;
import java.util.Objects;

import static java.lang.ClassLoader.getSystemResource;

public class DataManager {

    public static void saveData(String filename, Object data) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/src/data/" + filename + ".dat"))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadData(String filename) {
        Object data = null;
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream("src/src/data/" + filename + ".dat"))) {
            data = oos.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}