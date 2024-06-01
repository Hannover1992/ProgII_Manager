import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class DeserializeAssignments {
    public static List<Assignment> readAssignments(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Assignment>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Fehler beim Lesen der Assignments: " + e.getMessage());
            return null;
        }
    }
}
