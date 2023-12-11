import java.util.*;

/**
 * @author Soham
 */
public class MaximumCapacity {

    static int maxCapacity;
    public ArrayList<Integer> dijkstraMaxCriticalCapacity(ArrayList<Node> residualGraph, int sourceNode, int sinkNode) {
        Map<Integer, Integer> criticalCapacities = new HashMap<>();
        Map<Integer, ArrayList<Integer>> paths = new HashMap<>();
        PriorityQueue<NodeCriticalCapacity> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());

        // Initialize critical capacities to infinity and add the source node
        for (Node node : residualGraph) {
            criticalCapacities.put(node.id, Integer.MAX_VALUE);
        }
        criticalCapacities.put(sourceNode, Integer.MAX_VALUE);
        priorityQueue.add(new NodeCriticalCapacity(sourceNode, Integer.MAX_VALUE));

        while (!priorityQueue.isEmpty()) {
            NodeCriticalCapacity currentNode = priorityQueue.poll();
            int currentId = currentNode.nodeId;
            int currentCriticalCapacity = currentNode.criticalCapacity;

            if (currentCriticalCapacity == 0 || currentId == sinkNode) {
                // If critical capacity is zero or sink node is reached, break out of the loop
                break;
            }

            Node node = Node.findNodeById(residualGraph, currentId);

            for (Edge edge : node.edges) {
                System.out.println("Entering node" + node.id);
                int neighborId = edge.to;
                int edgeCapacity = edge.capacity;
                System.out.println("Current capacity: " + currentCriticalCapacity + " Edge capacity: " + edgeCapacity + " Neighbor: " + neighborId);
                int newCriticalCapacity = Math.min(currentCriticalCapacity, edgeCapacity);

                //if (newCriticalCapacity > criticalCapacities.get(neighborId) || criticalCapacities.get(neighborId) == Integer.MAX_VALUE) {
                    criticalCapacities.put(neighborId, newCriticalCapacity);

                    // Update the augmenting path
                    ArrayList<Integer> currentPath = paths.getOrDefault(currentId, new ArrayList<>());
                    ArrayList<Integer> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighborId);
                    paths.put(neighborId, newPath);

                    priorityQueue.add(new NodeCriticalCapacity(neighborId, newCriticalCapacity));
                //}
            }
        }

        double meanLength = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> node : paths.entrySet()) {
            meanLength += node.getValue().size();
        }
        List<Integer> longestPath = FindLongestAcyclicPath.findLongestAcyclicPath(residualGraph, sourceNode, sinkNode);
        if (!longestPath.isEmpty()) {
            longestPath.add(0, sourceNode);
        }
        meanLength /= paths.size();
        System.out.println(paths);
        System.out.println("Mean length: " + meanLength);
        System.out.println("Mean Proportional length (mean length/longest path size): " + meanLength / longestPath.size());
        System.out.println("Total number of edges in the graph: " + Node.getNoOfEdges(residualGraph));
        System.out.println("Maximum capacity: " + maxCapacity);
        return paths.get(sinkNode);
    }


    static class NodeCriticalCapacity implements Comparable<NodeCriticalCapacity> {
        int nodeId;
        int criticalCapacity;

        public NodeCriticalCapacity(int nodeId, int criticalCapacity) {
            this.nodeId = nodeId;
            this.criticalCapacity = criticalCapacity;
        }

        @Override
        public int compareTo(NodeCriticalCapacity other) {
            // Compare based on critical capacity, with higher capacities coming first
            return Integer.compare(other.criticalCapacity, this.criticalCapacity);
        }
    }
}
