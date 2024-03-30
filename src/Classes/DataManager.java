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
        URL resource = DataManager.class.getResource("/src/data/" + filename + ".dat");
        if (resource == null) {
            System.out.println("No se ha encontrado el archivo");
            return null;
        }
        Object data = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(resource.getPath()))) {
            data = ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}