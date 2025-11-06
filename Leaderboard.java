/**
 * This is my leaderboard class
 * @author Nelle Baker
**/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;

//This creates the class and defines the arrays
public class Leaderboard {
    public static ArrayList<String> Name = new ArrayList<>();
    public static ArrayList<Integer> Score = new ArrayList<>();
    public static ArrayList<String> Adding = new ArrayList<>();

    // This will add the name that the user inputted into the Name array
    public static void AddName(String name) {
        Name.add(name);
    }

    //This will add the Score that the user got into the Score Array
    public static void AddScore(int score) {
        Score.add(score);
    }

    // THis will add the Score and the Name of the user who just played to the Score.txt file
    // If there is a problem with writing to the file it will throw an exception
    public static void AddLeaderboard() {
        Path path = Paths.get("C:\\Uni work\\Year 1\\OOP\\Project\\patience-template\\out\\production\\patience-template\\Score.txt");
        for(int i = 0; i < Name.size(); i++ ){
            Adding.add(Score.get(i) + "\t" + Name.get(i));
            try{
                Files.write(path,Adding, StandardCharsets.UTF_8);
            }

            catch(IOException ex){
                System.out.println("Error writing to file");
            }
        }

    }

    // This will print the First 10 lines of the score.txt file and if it cannot
    //Read the file then it will throw an exception
    public static void GetLeaderboard() {
       try( BufferedReader br = new BufferedReader(new FileReader("C:\\Uni work\\Year 1\\OOP\\Project\\patience-template\\out\\production\\patience-template\\Score.txt"))){
            String line;
            for(int i = 0; i < 10; i++) {
                if((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
       catch(IOException ex){
           System.out.println("Error reading file");
       }
    }
}