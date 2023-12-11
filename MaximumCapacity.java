import java.util.*;

/**
 * @author Soham
 */
public class MaximumCapacity {

    static int maxCapacity;
    public ArrayList<Integer> dijkstraMaxCriticalCapacity(ArrayList<Node> residualGraph, int sourceNode, int sinkNode) {
        // Initialize distances and predecessors
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> predecessors = new HashMap<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Node node : residualGraph) {
            int nodeId = node.id;
            distances.put(nodeId, Integer.MAX_VALUE);
            predecessors.put(nodeId, null);
        }

        distances.put(sourceNode, 0);
        pq.add(sourceNode);

        while (!pq.isEmpty()) {
            int currentId = pq.poll();

            for (Node neighbor : Node.findNodeById(residualGraph, currentId).getNeighbors(residualGraph)) {
                try {
                    int capacity = getCapacity(currentId, neighbor.id, residualGraph);
                    int alt = Math.min(distances.get(currentId), capacity);

                    if (alt > distances.get(neighbor.id)) {
                        distances.put(neighbor.id, alt);
                        predecessors.put(neighbor.id, currentId);
                        pq.add(neighbor.id);
                    }
                }catch (Exception e){
                    System.out.println("Empty neighbors");
                }

            }
        }

        // Reconstruct the path
        ArrayList<Integer> path = new ArrayList<>();
        int currentId = sinkNode;
        System.out.println(predecessors);
        while (currentId != 0) { // Stop when reaching the source with ID 0
            path.add(currentId);
            System.out.println(path);
            currentId = predecessors.get(currentId);
        }

        Collections.reverse(path);
        return path;
    }

    private static int getCapacity(int fromId, int toId, ArrayList<Node> nodes) {
        Node from = Node.findNodeById(nodes, fromId);
        for (Edge edge : from.edges) {
            if (edge.to == toId) {
                return edge.capacity;
            }
        }
        return 0; // No direct edge from 'from' to 'to'
    }

//    static class NodeCriticalCapacity implements Comparable<NodeCriticalCapacity> {
//        int nodeId;
//        int criticalCapacity;
//
//        public NodeCriticalCapacity(int nodeId, int criticalCapacity) {
//            this.nodeId = nodeId;
//            this.criticalCapacity = criticalCapacity;
//        }
//
//        @Override
//        public int compareTo(NodeCriticalCapacity other) {
//            // Compare based on critical capacity, with higher capacities coming first
//            return Integer.compare(other.criticalCapacity, this.criticalCapacity);
//        }
//    }
}
