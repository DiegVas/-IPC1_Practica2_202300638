package Classes;

import java.io.*;

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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/src/data/" + filename + ".dat"))) {
            data = ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}