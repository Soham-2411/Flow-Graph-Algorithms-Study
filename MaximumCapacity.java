import java.util.*;

/**
 * This class calculates and returns a path with maximum critical capacity
 * @author Soham
 */
public class MaximumCapacity {

    static int maxCapacity;
    public ArrayList<Integer> dijkstraMaxCriticalCapacity(ArrayList<Node> residualGraph, int sourceNode, int sinkNode) {
        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Node> previousNodes = new HashMap<>();
        double meanLength = 0;

        for (Node node : residualGraph) {
            distances.put(node.id, Double.POSITIVE_INFINITY);
            previousNodes.put(node.id, null);
        }
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingDouble(node -> distances.get(node.id)));
        distances.put(sourceNode, 1.0);
        minHeap.add(residualGraph.get(sourceNode));

        double totalLength = 0;
        int pathCount = 0;

        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.poll();

            for (Edge edge : currentNode.edges) {
                Node neighbor = Node.findNodeById(residualGraph, edge.to);

                double newDistance = distances.get(currentNode.id) + edge.capacity;

                if (newDistance < distances.get(neighbor.id)) {
                    distances.put(neighbor.id, newDistance);
                    previousNodes.put(neighbor.id, currentNode);
                    minHeap.add(neighbor);
                    totalLength +=edge.capacity;
                    pathCount++;
                }
            }
        }

        // Reconstruct the path
        Node current = residualGraph.get(sinkNode);
        ArrayList<Node> path = new ArrayList<>();

        while (current != null) {
            path.add(current);
            current = previousNodes.get(current.id);
        }

        Collections.reverse(path);

        ArrayList<Integer> maxPath = new ArrayList<>();
        for(Node node: path){
            maxPath.add(node.id);
        }
        meanLength = pathCount > 0 ? totalLength / pathCount : 0.0;

        List<Integer> longestPath = FindLongestAcyclicPath.findLongestAcyclicPath(residualGraph, sourceNode, sinkNode);
        if (!longestPath.isEmpty()) {
            longestPath.add(0, sourceNode);
        }
        System.out.println("Paths: " + pathCount);
        System.out.println("Mean length: " + meanLength);
        System.out.println("Mean Proportional length (mean length/longest path size): " + meanLength / longestPath.size());
        System.out.println("Maximum Flow Value: " + distances.get(sinkNode));
        System.out.println("Total number of edges in the graph: " + Node.getNoOfEdges(residualGraph));
        return maxPath;
    }
}
