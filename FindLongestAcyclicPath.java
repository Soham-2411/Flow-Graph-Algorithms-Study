import java.util.*;

/**
 * @author Soham
 */
public class FindLongestAcyclicPath {

    static ArrayList<Node> residualGraph;

    public static List<Integer> findLongestAcyclicPath(ArrayList<Node> graph, int source, int sink) {
        residualGraph = graph;
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, List<Integer>> longestPaths = new HashMap<>();
        List<Integer> topologicalOrder = topologicalSort(graph);

        // Initialize distances to negative infinity and add the source node
        for (Node node : graph) {
            distances.put(node.id, Integer.MIN_VALUE);
        }
        distances.put(source, 0);

        for (int currentNodeId : topologicalOrder) {
            Node currentNode = Node.findNodeById(graph, currentNodeId);

            assert currentNode != null;
            for (Node neighbor : currentNode.getNeighbors(residualGraph)) {
                int neighborId = neighbor.id;

                int newDistance = distances.get(currentNodeId) + 1; // Unit distance for each edge

                if (newDistance > distances.get(neighborId)) {
                    distances.put(neighborId, newDistance);

                    // Update the longest path to the neighbor
                    List<Integer> newPath = new ArrayList<>(longestPaths.getOrDefault(currentNodeId, Collections.emptyList()));
                    newPath.add(neighborId);
                    longestPaths.put(neighborId, newPath);
                }
            }
        }

        return longestPaths.get(sink);
    }

    private static List<Integer> topologicalSort(List<Node> graph) {
        // Perform topological sorting of the graph using DFS
        List<Integer> topologicalOrder = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        for (Node node : graph) {
            if (!visited.contains(node.id)) {
                dfsTopologicalSort(node, visited, topologicalOrder);
            }
        }

        Collections.reverse(topologicalOrder);
        return topologicalOrder;
    }

    private static void dfsTopologicalSort(Node node, Set<Integer> visited, List<Integer> topologicalOrder) {
        visited.add(node.id);

        for (Node neighbor : node.getNeighbors(residualGraph)) {
            if (!visited.contains(neighbor.id)) {
                dfsTopologicalSort(neighbor, visited, topologicalOrder);
            }
        }

        topologicalOrder.add(node.id);
    }

}
