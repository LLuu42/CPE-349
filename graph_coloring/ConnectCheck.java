import java.util.*;
public class ConnectCheck {
    private static boolean [] visited;
    public static ArrayList<String> checkAllConnections(ArrayList<Integer> [] graph, int numVertices) {
        ArrayList<String> connections = new ArrayList<String>();
        visited = new boolean[numVertices + 1];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
        for (int i = 1; i <= numVertices; i++) {
            if (!visited[i]) {
                ArrayList<Integer> sequence = bfs(graph, i);
                String s = "{ ";
                for (int vertex: sequence) {
                    s += vertex + " ";    
                }
                connections.add(s + "}");
            }
        }
        return connections;
    }
    private static ArrayList<Integer> bfs(ArrayList<Integer> [] adjList, int v) {
        ArrayList<Integer> sequence = new ArrayList<Integer>();   
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(v);
        visited[v] = true;
        sequence.add(v);
        while (queue.size() != 0) {
            int root = queue.poll();
            for (int i = 0; i < adjList[root].size(); i++) {
                if (!visited[adjList[root].get(i)]) {
                    visited[adjList[root].get(i)] = true;
                    sequence.add(adjList[root].get(i));
                    queue.add(adjList[root].get(i));
                }
            }
        }
        return sequence;
    }
}
