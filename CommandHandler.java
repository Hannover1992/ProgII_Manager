import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandHandler{

    public static List<String> genericCommand(String commandEin){
        List<String> result = new ArrayList<String>();
        
        try{
            String[] commandArr = commandEin.split(" ");

            ArrayList<String> command = new ArrayList<String>();

            for(String currComand : commandArr){
                command.add(currComand);
            }


            ProcessBuilder processBuilder = new ProcessBuilder(command);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;


            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public static ArrayList<String> getAssignments() {
        ArrayList<String> result = new ArrayList<String>();
        try{
            ArrayList<String> command = new ArrayList<String>();
            command.add("bash");
            command.add("-c");
            command.add("cd ./Assignments/ && find . -mindepth 2 -maxdepth 2 -type d");


            ProcessBuilder processBuilder = new ProcessBuilder(command);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;


            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;

    }
}
