import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.Serializable;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.Duration;

public class Manager{

    String filename = "assignments.ser";

    List<Assignment> assignments;
    Scanner scanner;
    Assignment currentAssignment;
    Random rnd;
    LocalTime start;
    int assignmentIndex;

    Manager(){
        this.init();
        this.menuRun();
    }

    public void init(){
        this.scanner = new Scanner(System.in);
        if(!assignments_ser_exists()){
            this.loadFromScrachAssignments();
        }
        this.loadAssignments();
        this.currentAssignment = new Assignment(-1, "", 0, Duration.ofHours(99));
        this.rnd = new Random();
    }

    public boolean assignments_ser_exists(){
        return SerializeAssignments.fileExists(filename);
    }

    public static ArrayList<Assignment> getAssignmentsFromDirectory(){
        ArrayList<String> assignmentsName = CommandHandler.getAssignments();
        ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();

        for(int i = 0; i < assignmentsName.size(); i++){
            assignmentList.add(new Assignment(i, assignmentsName.get(i), 0, Duration.ofHours(99)));
        }

        return assignmentList;
    }

    public void printTasksAll(){
        // Ausgabe der geladenen Assignments
        for (Assignment assignment : assignments) {
            System.out.println(assignment.toString());
        }
        System.out.println("Current Assignments:");
        System.out.println(this.currentAssignment.toString());
    }

    public void loadFromScrachAssignments(){
        ArrayList<Assignment> assignmentList = getAssignmentsFromDirectory();
        SerializeAssignments.saveAssignments(assignmentList, filename);

        // Lesen der gespeicherten Assignments
        this.loadAssignments();
    }


    public void saveAssignments(){
        SerializeAssignments.saveAssignments(this.assignments, filename);
    }

    public void loadAssignments(){
        this.assignments = DeserializeAssignments.readAssignments(filename);
    }

    public void menuRun(){
        printTasksAll(); // Consider removing this if it's not needed on every command, as it might clutter the output.
        this.printMenu();
        while(true) {
            String userInput = this.scanner.nextLine();
            switch (userInput) {
                case "all":
                    this.printTasksAll();
                    break;
                case "sel":
                    try {
                        System.out.println("Geben Sie das Index von Assignment:");
                        int i = Integer.parseInt(scanner.nextLine()); // Using nextLine and parsing to avoid Scanner bug
                        this.selectAssignment(i);
                        this.assignmentRun();
                    } catch (NumberFormatException e) {
                        System.out.println("Bitte geben Sie eine gültige Zahl ein.");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Das gewählte Assignment existiert nicht. Bitte wählen Sie einen gültigen Index.");
                    }
                    break;
                case "r":
                    getRandomTask();
                    break;
                case "men":
                    this.printMenu();
                    break;
                default:
                    System.out.println("Unbekannter Befehl. Bitte versuchen Sie es erneut.");
                    break;
            }
        }
    }

    public void selectAssignment(int i) {
        if (i >= 0 && i < assignments.size()) {
            this.currentAssignment = assignments.get(i);
            this.assignmentIndex = i;
            System.out.println("Selected Assignment: " + this.currentAssignment.toString());
        } else {
            throw new IndexOutOfBoundsException("Index außerhalb des gültigen Bereichs.");
        }
    }

    public void printMenu(){
        System.out.println("[r]andom task, [sel]ect task, [all]");
    }


    public void assignmentRun(){

        this.printTasksAll();
        System.out.println("Current Assignment" + this.currentAssignment.toString());
        System.out.println("[r]andom task, [sta]rt ,[inc]rease task counter,[sel]ect assignment, [men]u");

        String input = this.scanner.nextLine();

        System.out.println(input);
        if(input.compareTo("r") == 0){
            this.getRandomTask();  
        } else if(input.compareTo("sta") == 0){
            this.currentAssignment.startTask();
            this.assignments.set(this.assignmentIndex, this.currentAssignment);
            this.saveAssignments();
            this.printTasksAll();
            assignmentRun();
        } else if(input.compareTo("inc") == 0){
            this.currentAssignment.versuche++;
            this.assignments.set(this.assignmentIndex, this.currentAssignment);
            this.saveAssignments();
            this.printTasksAll();
            assignmentRun();
        } else if(input.compareTo("res") == 0){
            this.currentAssignment.resetTask();
            this.assignments.set(this.assignmentIndex, this.currentAssignment);
            this.saveAssignments();
            this.printTasksAll();
            assignmentRun();
        } else if(input.compareTo("sel") == 0){
            try {
                System.out.println("Geben Sie das Index von Assignment:");
                int i = Integer.parseInt(scanner.nextLine()); // Using nextLine and parsing to avoid Scanner bug
                this.selectAssignment(i);
                this.assignmentRun();
            } catch (NumberFormatException e) {
                System.out.println("Bitte geben Sie eine gültige Zahl ein.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Das gewählte Assignment existiert nicht. Bitte wählen Sie einen gültigen Index.");
            }
        } else {
            this.menuRun();
        }
        this.assignmentRun();
    }


    public void getRandomTask(){
        List<Assignment> listToChoseFrom = getListOfAssignmentsWithLowestVersuch();
        int length = listToChoseFrom.size();
        int nr = this.rnd.nextInt(length);
        nr = Math.abs(nr);
        this.currentAssignment = listToChoseFrom.get(nr);
        this.assignmentIndex = this.assignments.indexOf(this.currentAssignment);
        System.out.println(this.currentAssignment.toString());
        this.assignmentRun();
    }

    public List<Assignment> getListOfAssignmentsWithLowestVersuch(){
        List<Assignment> lowestVersuch = new ArrayList<Assignment>();
        int minVersuch = Integer.MAX_VALUE;
        for (Assignment assignment : assignments) {
            if(assignment.versuche < minVersuch){
                minVersuch = assignment.versuche;
            }
        }

        for (Assignment assignment : assignments) {
            if(assignment.versuche == minVersuch){
                lowestVersuch.add(assignment);
            }
        }

        return lowestVersuch;
    }


}



































