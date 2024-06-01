import java.io.IOException;
import java.io.Serializable;
import java.lang.StringBuilder;
import java.util.Scanner;

import java.time.Duration;
import java.time.format.DateTimeFormatter;


public class Assignment implements Serializable{ 

    private static final long serialVersionUID = 6L; // Ändern, wenn die Klasse sich ändert
    int index;
    public String name;
    int versuche;
    Duration bestTime;
    private boolean isPaused = false;

    Assignment(int index, String name, int versuche, Duration bestTime){
        this.index = index;
        this.name = name;
        this.versuche = versuche;
        this.bestTime = bestTime;
    }

    @Override
    public String toString(){
        int lengthOfFirstWord = 46;
        int lengthOfCurrentWord = name.length();


        StringBuilder sb = new StringBuilder(this.name);
        //fill 
        for(int i = 0; i < lengthOfFirstWord - lengthOfCurrentWord; i++){
            sb.append(" ");
        }


        sb.append("Anzahl Versuche: " + versuche + "\t\tBest Time so Far: " + formatDuration(bestTime));
        String indexString = "Ind:" + Integer.toString(this.index)  + "\t\t";
        sb.insert( 0 , indexString);


        return sb.toString();
    }

public void startTask() {
    Scanner scanner = new Scanner(System.in);
    long startTime = System.currentTimeMillis();
    boolean isCancelled = false;

    Thread timerThread = new Thread(() -> {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (!isPaused) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedSeconds = (currentTime - startTime) / 1000;
                    System.out.print("\rElapsed Time: " + formatDuration(Duration.ofSeconds(elapsedSeconds)));
                    Thread.sleep(1000);  // Aktualisiert jede Sekunde
                } else {
                    Thread.sleep(100);  // Wartet kurz, um CPU-Zeit zu sparen, wenn pausiert
                }
            }
        } catch (InterruptedException e) {
            // Thread wird unterbrochen, wenn der Task endet oder abgebrochen wird
            System.out.println("\nTimer stopped.");
        }
    });

    timerThread.start();  // Startet den Timer-Thread

    while (true) {
        System.out.println("\nCommands: [f] Finish, [p] Pause, [r] Resume, [x] Cancel");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "p":  // Pause
                isPaused = true;
                break;
            case "r":  // Fortsetzen
                isPaused = false;
                break;
            case "f":  // Beenden und speichern
                timerThread.interrupt();
                long finishTime = System.currentTimeMillis();
                Duration duration = Duration.ofMillis(finishTime - startTime);
                if (duration.compareTo(bestTime) < 0) {
                    bestTime = duration;
                    System.out.println("\nNew best time!!!\n");
                }
                this.versuche++;
                return;  // Beendet die Methode
            case "x":  // Abbrechen ohne Speichern
                timerThread.interrupt();
                isCancelled = true;
                return;  // Beendet die Methode
            default:
                System.out.println("Invalid command. Please enter [f], [p], [r], or [x].");
                break;
        }
    }
}


    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long hours = absSeconds / 3600;
        long minutes = (absSeconds % 3600) / 60;
        long secs = absSeconds % 60;

        // Farbe basierend auf der Zeit festlegen
        String color;
        if (hours >= 1) {
            color = "\033[31m"; // Rot für mehr als eine Stunde
        } else if (minutes < 20) {
            color = "\033[32m"; // Grün, wenn weniger als 20 Minuten
        } else if (minutes < 40) {
            color = "\033[33m"; // Gelb, wenn zwischen 20 und 40 Minuten
        } else {
            color = "\033[31m"; // Rot, wenn 40 Minuten oder mehr, aber weniger als eine Stunde
        }

        // Farbigen String zusammenstellen
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, secs);
        return color + timeString + "\033[0m"; // Reset am Ende
    }
    
    public void resetTask(){
        this.versuche = 0;
        this.bestTime = Duration.ofHours(99);
    }

}
