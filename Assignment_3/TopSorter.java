import java.util.*;
public class TopSorter {
  public static ArrayList<Integer> topSortGenerator(String filename) {
    //creates the graph to be used
    GraphStart graph = new GraphStart();
    //initializes arraylist to contain topological order of graph
    ArrayList<Integer> order = new ArrayList<Integer>();
    //reads in the graph
    try {
      graph.readfile_graph(filename);
    } catch (Exception e) {
      System.out.println(e);
    }
    //array to hold the amount of indegrees for each vertex
    int [] indegrees = indegrees(graph.nvertices, graph.edges);
    //creates a queue to store vertices with indegree 0
    LinkedList<Integer> queue = new LinkedList<Integer>();
    //finds all vertices with indegree of 0, and adds them to the queue
    for (int i = 1; i < indegrees.length; i++) {
      if (indegrees[i] == 0) {
        queue.add(i);
      }
    }
    //if the queue isn't empty, then we have some topological ordering
    //this entire if statement runs in O(V + E) time
    if (queue.size() != 0) {
      //while there are still vertices with indegree 0
      while (queue.size() > 0) {
        //pop the queue
        int source = queue.poll();
        //add it to the topological ordering
        order.add(source);
        //goes through adjacency list of that vertex
        for (int i = 0; i < graph.edges[source].size(); i++) {
          //gets the target of the directed edge
          int target = graph.edges[source].get(i);
          //decrease the amount of indegrees of that vertex
          //we can modify the graph since we are only using it for sorting
          indegrees[target]--;
          //if has no indegrees, then add it to the queue
          if (indegrees[target] == 0)
            queue.add(target);
        }
      }
      //if there are vertices that still have an indegrees
      //then replace them with -1 and add to topological ordering
      for (int i = 1; i < indegrees.length; i++) {
        if (indegrees[i] != 0) {
          order.add(-1);
        }
      }
    //if no vertex has indegree 0
    } else {
      //add -1 to the order for every vertex there is
      for (int i = 0; i < graph.nvertices; i++) {
        order.add(-1);
      }
    }
    //returns the topological ordering
    return order;
  }
  //private method to calculate the number of indegrees
  //runs in O(V + E) time
  private static int [] indegrees(int nvertices, ArrayList<Integer> [] graph) {
    //creates the array of indegrees
    int [] indegree_count = new int[nvertices + 1];
    //goes through each vertex
    for (int i = 1; i <= nvertices; i++) {
      //goes through each directed edge
      for (int j = 0; j < graph[i].size(); j++) {
        //increments the indegree of that vertex
        indegree_count[graph[i].get(j)]++;
      }
    }
    //returns the indegrees
    return indegree_count;
  }
}
