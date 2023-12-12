import java.util.*;

/**
 * @author Soham
 */
public class RandomDijkstra {

    public Map<Integer, ArrayList<Integer>> dijkstraWithRandomizedPriority(ArrayList<Node> residualGraph, int sourceNode, int sinkNode) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, ArrayList<Integer>> shortestPaths = new HashMap<>();
        PriorityQueue<CounterNodeDistance> priorityQueue = new PriorityQueue<>();

        // Initialize distances to infinity and add the source node
        ArrayList<Node> randomizedOrder = new ArrayList<>(residualGraph);
        Collections.shuffle(randomizedOrder);
        for (Node node : randomizedOrder) {
            distances.put(node.id, Integer.MAX_VALUE);
        }
        distances.put(sourceNode, 0);
        priorityQueue.add(new CounterNodeDistance(sourceNode, 0));

        while (!priorityQueue.isEmpty()) {
            CounterNodeDistance currentNode = priorityQueue.poll();
            int currentId = currentNode.nodeId;
            int currentDistance = currentNode.distance;

            if (currentDistance > distances.get(currentId)) {
                continue; // Skip if a shorter distance is already found
            }

            Node node = Node.findNodeById(residualGraph, currentId);
            for (Edge edge : node.edges) {
                int neighborId = edge.to;
                int edgeCapacity = edge.capacity;

                int newDistance = currentDistance + edgeCapacity;
                if (newDistance < distances.get(neighborId)) {
                    distances.put(neighborId, newDistance);
                    priorityQueue.add(new CounterNodeDistance(neighborId, newDistance));

                    // Update the shortest path to the neighbor
                    ArrayList<Integer> newPath = new ArrayList<>(shortestPaths.getOrDefault(currentId, new ArrayList<>()));
                    newPath.add(neighborId);
                    shortestPaths.put(neighborId, newPath);
                }
            }
        }
        double meanLength = 0;
        int paths = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> node : shortestPaths.entrySet()) {
            meanLength += node.getValue().size();
            paths++;
        }
        List<Integer> longestPath = FindLongestAcyclicPath.findLongestAcyclicPath(residualGraph, sourceNode, sinkNode);
        if (!longestPath.isEmpty()) {
            longestPath.add(0, sourceNode);
        }
        meanLength /= shortestPaths.size();
        System.out.println("Paths: " + paths);
        System.out.println("Mean length: " + meanLength);
        System.out.println("Mean Proportional length (mean length/longest path size): " + meanLength / longestPath.size());
        System.out.println("Total number of edges in the graph: " + Node.getNoOfEdges(residualGraph));
        return shortestPaths;
    }

    static class CounterNodeDistance implements Comparable<CounterNodeDistance> {
        int nodeId;
        int distance;

        public CounterNodeDistance(int nodeId, int distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }

        @Override
        public int compareTo(CounterNodeDistance other) {
            // Compare based on distance, with higher distances coming first
            return Integer.compare(other.distance, this.distance);
        }
    }
}
