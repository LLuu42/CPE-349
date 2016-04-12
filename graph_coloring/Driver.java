import java.util.*;
import java.io.*;
public class Driver {
    public static void main(String [] argv) {
        Scanner scan = new Scanner(System.in);
        System.out.print("What is the name of your file: ");
        String file_name = scan.next();
        GraphStart graph = new GraphStart();
        try {
            graph.readfile_graph(file_name);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        graph.print_graph();
        ArrayList<String> connections = ConnectCheck.checkAllConnections(graph.edges, graph.nvertices);
        boolean bicolorable = BipartiteCheck.isBipartite(graph.edges, graph.nvertices);
        if (bicolorable) {
            System.out.println("Graph has (" + graph.nvertices + 
                    " vertices, " + graph.nedges + " edges) and is " + 
                    "BiColorable.");
        } else {
            System.out.println("Graph has (" + graph.nvertices +
                    " vertices, " + graph.nedges + " edges) and is " + 
                    "not BiColorable.");
        }
        System.out.println("It has " + connections.size() + " connected " +
                        "components: ");
        for (String s : connections) {
            System.out.println(s);
        }
    }
}
