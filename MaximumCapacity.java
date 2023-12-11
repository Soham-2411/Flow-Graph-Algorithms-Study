import java.util.*;

/**
 * @author Soham
 */
public class MaximumCapacity {

    static int maxCapacity;
    public static Map<Integer, ArrayList<Integer>> dijkstraMaxCriticalCapacity(ArrayList<Node> residualGraph, int source, int sink) {
        Map<Integer, ArrayList<Integer>> criticalFlows = new HashMap<>();
        ArrayList<Integer> currentPath = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        modifiedDijkstra(residualGraph, source, sink, currentPath, visited, criticalFlows);

        return criticalFlows;
    }

    private static void modifiedDijkstra(ArrayList<Node> residualGraph, int currentNode, int sink, List<Integer> currentPath, Set<Integer> visited, Map<Integer, ArrayList<Integer>> criticalFlows) {
        visited.add(currentNode);
        currentPath.add(currentNode);

        if (currentNode == sink) {
            // Reached the sink, calculate the critical flow for this path
            int criticalFlow = Integer.MAX_VALUE;
            for (int i = 0; i < currentPath.size() - 1; i++) {
                Node node = Node.findNodeById(residualGraph, currentPath.get(i));
                for (Edge edge : node.edges) {
                    if (edge.to == currentPath.get(i + 1)) {
                        criticalFlow = Math.min(criticalFlow, edge.capacity);
                        break;
                    }
                }
            }

            // Update the map with the critical flow and path
            if (!criticalFlows.containsKey(criticalFlow)) {
                criticalFlows.put(criticalFlow, new ArrayList<>());
            }
            criticalFlows.get(criticalFlow).addAll(currentPath);

            // Backtrack to find more paths
            visited.remove(currentNode);
            currentPath.remove(currentPath.size() - 1);
            return;
        }

        Node node = Node.findNodeById(residualGraph, currentNode);
        for (Edge edge : node.edges) {
            if (!visited.contains(edge.to) && edge.capacity > 0) {
                modifiedDijkstra(residualGraph, edge.to, sink, currentPath, visited, criticalFlows);
            }
        }

        visited.remove(currentNode);
        currentPath.remove(currentPath.size() - 1);
    }

    static class CounterNodeCapacity implements Comparable<CounterNodeCapacity> {
        int nodeId;
        int capacity;

        public CounterNodeCapacity(int nodeId, int capacity) {
            this.nodeId = nodeId;
            this.capacity = capacity;
        }

        @Override
        public int compareTo(CounterNodeCapacity other) {
            // Compare based on capacity, with higher capacities coming first
            return Integer.compare(other.capacity, this.capacity);
        }
    }
}
