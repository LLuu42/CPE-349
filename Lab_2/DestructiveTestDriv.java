//imports the util library, a core library necessary to get the scanner class
import java.util.*;
public class DestructiveTestDriv {
    public static void main(String [] argv) {
        //declares the scanner to take in user input
        Scanner scan = new Scanner(System.in);
        //prompts user for the height of the ladder
        System.out.print("What is the height of the ladder: ");
        //gets the user input as an int and assigns it to the height variable
        int height = scan.nextInt();
        //prompts user for the safe rung
        System.out.print("Where is the safe rung: ");
        //gets the user input as an int and assign it to the safe_rung variable
        int safe_rung = scan.nextInt();
        //creates an array of ints initialized by calling the algorithm
        int [] output = DestructiveTest.findHighestSafeRung(height, safe_rung);
        //outputs the results
        System.out.println("Height of the ladder " + output[0]);
        System.out.println("Actual highest safe run " + output[1]);
        System.out.println("Highest safe rung determined by this algorithm "
                            + output[2]);
        System.out.println("Rung where the first test device broke "
                            + output[3]);
        System.out.println("Rung where the second test device broke "
                            + output[4]);
        System.out.println("Total number of drops required to find the "
                            + "highest safe rung " + output[5]);
    }
}
