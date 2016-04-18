import java.util.*;
public class Driver {
  public static void main(String [] argv) {
    //check if this works by making your own graph in graph.txt
    ArrayList<Integer> order = TopSorter.topSortGenerator("graph.txt");
    for (int n : order) {
      System.out.println(n);
    }
  }
}
