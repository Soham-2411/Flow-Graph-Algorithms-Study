import java.util.*;

/**
 * @author Soham
 */
public class MaximumCapacity {

    static int maxCapacity;
    public ArrayList<Integer> dijkstraMaxCriticalCapacity(ArrayList<Node> residualGraph, int sourceNode, int sinkNode) {
        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Node> previousNodes = new HashMap<>();

        for (Node node : residualGraph) {
            distances.put(node.id, Double.POSITIVE_INFINITY);
            previousNodes.put(node.id, null);
        }
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingDouble(node -> distances.get(node.id)));
        distances.put(sourceNode, 0.0);
        minHeap.add(residualGraph.get(sourceNode));

        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.poll();

            for (Edge edge : currentNode.edges) {
                Node neighbor = Node.findNodeById(residualGraph, edge.to);

                double newDistance = distances.get(currentNode.id) + edge.capacity;

                if (newDistance < distances.get(neighbor.id)) {
                    distances.put(neighbor.id, newDistance);
                    previousNodes.put(neighbor.id, currentNode);
                    minHeap.add(neighbor);
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

        System.out.println("Maximum Flow Value: " + distances.get(sinkNode));
        return maxPath;
    }
}
