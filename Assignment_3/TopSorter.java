import java.util.*;
public class TopSorter {
  public static ArrayList<Integer> topSortGenerator(String filename) {
    GraphStart graph = new GraphStart();
    graph.readfile_graph(filename);

  }
  private static int [] indegrees(int nvertices, ArrayList<Integer> [] graph) {
    int [] indegree_count = new int[nedges + 1];
    for (int i = 0; i < indegree_count.length; i++) {
      indegree_count[i] = 0;
    }
    for (int i = 1; i <= nvertices; i++) {
      for (int j = 0; j < graph[i].size(); j++) {
        indegree_count[graph[i].get(j)]++;
      }
    }
    return indegree_count;
  }
}
