import java.util.*;

/**
 * @author Soham
 */
public class ShortestAugmentingPath {
    public Map<Integer, ArrayList<Integer>> findShortestAugmentingPath(ArrayList<Node> residualGraph, int sourceNode, int sinkNode) {

        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, ArrayList<Integer>> shortestPaths = new HashMap<>();
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(NodeDistance::getDistance));

        // Initialize distances to infinity and add the source node
        for (Node node : residualGraph) {
            distances.put(node.id, Integer.MAX_VALUE);
        }
        distances.put(sourceNode, 0);
        priorityQueue.add(new NodeDistance(sourceNode, 0));

        while (!priorityQueue.isEmpty()) {
            NodeDistance currentNode = priorityQueue.poll();
            int currentId = currentNode.nodeId;
            int currentDistance = currentNode.distance;

            if (currentDistance > distances.get(currentId)) {
                continue; // Skip if a shorter distance is already found
            }

            Node node = Node.findNodeById(residualGraph, currentId);
            for (Edge edge : node.edges) {
                int neighborId = edge.to;
                int edgeCapacity = 1; // Treat all edges as having unit capacities

                int newDistance = currentDistance + edgeCapacity;
                if (newDistance < distances.get(neighborId)) {
                    distances.put(neighborId, newDistance);
                    priorityQueue.add(new NodeDistance(neighborId, newDistance));

                    // Update the shortest path to the neighbor
                    ArrayList<Integer> newPath = new ArrayList<>(shortestPaths.getOrDefault(currentId, new ArrayList<>()));
                    newPath.add(neighborId);
                    shortestPaths.put(neighborId, newPath);

                }
            }
        }
        double meanLength = 0;
        for(Map.Entry<Integer, ArrayList<Integer>> node : shortestPaths.entrySet()){
            meanLength+=node.getValue().size();
        }
        List<Integer> longestPath = FindLongestAcyclicPath.findLongestAcyclicPath(residualGraph, sourceNode, sinkNode);
        if (!longestPath.isEmpty()) {
            longestPath.add(0, sourceNode);
        }
        meanLength /= shortestPaths.size();
        //System.out.println(shortestPaths);
        System.out.println("Mean length: " + meanLength);
        System.out.println("Mean Proportional length (mean length/longest path size): " + meanLength / longestPath.size());
        System.out.println("Total number of edges in the graph: " + Node.getNoOfEdges(residualGraph));
        return shortestPaths;
    }

    class NodeDistance {
        int nodeId;
        int distance;

        public NodeDistance(int nodeId, int distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }
    }
}
