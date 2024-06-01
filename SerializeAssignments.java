
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class SerializeAssignments {
    public static void saveAssignments(List<Assignment> assignments, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(assignments);
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Assignments: " + e.getMessage());
        }
    }

    public static boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }
}
