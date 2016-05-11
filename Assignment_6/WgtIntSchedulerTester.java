import java.util.Scanner;
import java.io.File;

public class WgtIntSchedulerTester {
  public static void main(String [] argv) {
    Scanner scan = new Scanner(System.in);
    System.out.print("What is the name of your file: ");
    String filename = scan.next();
    try {
      Scanner fileScanner = new Scanner(new File(filename));
      int testCount = Integer.parseInt(fileScanner.nextLine().split(" ")[0]);
      for (int i = 1; i <= testCount; i++) {
        System.out.println("Test " + i + " -------------------------");
        int length = Integer.parseInt(fileScanner.nextLine().split(" ")[0]);
        int [] stime = new int[length];
        //remove the braces and comma from each, and get each number thereafter
        String [] string_stime = fileScanner.nextLine().
                                 replace("{", "").replace("}", "").
                                 replace(",", "").split(" ");
        int [] ftime = new int[length];
        String [] string_ftime = fileScanner.nextLine().
                                 replace("{", "").replace("}", "").
                                 replace(",", "").split(" ");
        int [] weight = new int[length];
        String [] string_weight = fileScanner.nextLine().
                                  replace("{", "").replace("}", "").
                                  replace(",", "").split(" ");
        //convert each into an array of integers
        for (int j = 0; j < length; j++) {
          stime[j] = Integer.parseInt(string_stime[j]);
          ftime[j] = Integer.parseInt(string_ftime[j]);
          weight[j] = Integer.parseInt(string_weight[j]);
        }
        //find the schedule
        int [] schedule = WgtIntScheduler.getOptSet(stime, ftime, weight);
        int totalWeight = 0;
        String jobNumbers = "";
        //calculates the total weight and creates a string to format
        //the job numbers nicely
        for (int j = 0; j < schedule.length; j++) {
          totalWeight += weight[schedule[j] - 1];
          jobNumbers += schedule[j] + "";
          if (j == schedule.length - 1) {
            jobNumbers += " ";
          } else {
            jobNumbers += ", ";
          }
        }
        //prints out the job numbers
        System.out.println("Case " + i + ": Maximum compatible set contains " +
                           schedule.length + " jobs: jobs " + jobNumbers +
                           "with total weight " + totalWeight);
      }
    } catch (Exception e) {
      System.out.print(e);
    }
  }
}
