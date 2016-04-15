import java.util.*;
import java.io.*;
public class BipartiteCheck {
    private final static int BLACK = 0;
    private final static int RED = 1;
    private static int [] graphColor;
    public static boolean isBipartite(ArrayList<Integer> [] graph, int numVertices) {
        graphColor = new int [numVertices + 1];
        for (int i = 0; i < graphColor.length; i++) {
            graphColor[i] = -1;
        }
        for (int i = 1; i <= numVertices; i++) {
            if (graphColor[i] == -1) {
                if (!bfs(graph, i)) {
                    return false;
                }
            }
        }
        return true;
    }
    private static boolean bfs(ArrayList<Integer> [] adjList, int v) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(v);
        graphColor[v] = RED;
        while (queue.size() != 0) {
            int root = queue.poll();
            for (int i = 0; i < adjList[root].size(); i++) {
                int neighbor = adjList[root].get(i);
                if (graphColor[neighbor] == -1) {
                    if (graphColor[root] == RED) {
                        graphColor[neighbor] = BLACK;
                    } else {
                        graphColor[neighbor] = RED;
                    }
                    queue.add(neighbor);
                } else if (graphColor[root] == graphColor[neighbor]) {
                    return false;
                }
            }
        }
        return true;
    }
}
