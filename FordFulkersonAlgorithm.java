/**
*@author Soham
*/
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FordFulkersonAlgorithm {

    ArrayList<ArrayList<Integer>> allPaths;
    ArrayList<Node> residualGraph;


    int maxFlow;

    public ArrayList<Node> performFordFulkerson(String csvFileName) throws Exception {
        // Fetch the graph and source/sink nodes from CSV
        FetchResult fetchResult = FetchGraphCSV.fetchGraphFromCSV(csvFileName);
        allPaths = new ArrayList<>();
        ArrayList<Node> nodes = fetchResult.fetchedNodes;
        int sourceNode = fetchResult.sourceNode;
        int sinkNode = fetchResult.sinkNode;
        System.out.println("Source Node: " + sourceNode + ", Sink Node: " + sinkNode);
        System.out.println("Started ford fulkerson");
        // Apply Ford-Fulkerson algorithm
        maxFlow = fordFulkerson(nodes, sourceNode, sinkNode);

        // Print the maximum flow
        System.out.println("Maximum Flow: " + maxFlow);

        return residualGraph;
    }



    private int fordFulkerson(List<Node> nodes, int source, int sink) throws InterruptedException {
        maxFlow = 0;

        // Initialize the residual graph with the original capacities
        residualGraph = new ArrayList<>(nodes);
        System.out.println("Initialized residual graph");
        //printResidualGraph(residualGraph);
        // Continue finding augmenting paths and updating the flow until no more paths
        ArrayList<Integer> augmentingPath = findAugmentingPath(residualGraph, source, sink);
        int noOfAugmentingPaths = 0;
        while (augmentingPath != null) {
            allPaths.add(augmentingPath);
            noOfAugmentingPaths++;
            int minCapacity = findMinCapacity(residualGraph, augmentingPath);
            updateResidualGraph(residualGraph, augmentingPath, minCapacity);
            maxFlow += minCapacity;
            augmentingPath = findAugmentingPath(residualGraph, source, sink);
        }
        System.out.println("All the paths: ");
        for(ArrayList<Integer> path : allPaths){
            System.out.println(path);
        }
        System.out.println("Paths: " + noOfAugmentingPaths);
        return maxFlow;
    }

    private static void printResidualGraph(List<Node> residualGraph) {
        for (Node node : residualGraph) {
            System.out.print("Node " + node.id + ": ");
            for (Edge edge : node.edges) {
                System.out.print("(" + edge.to + ", " + edge.capacity + ") ");
            }
            System.out.println();
        }
    }
    private ArrayList<Integer> findAugmentingPath(List<Node> residualGraph, int source, int sink) {
        // Use BFS to find an augmenting path
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parentMap = new HashMap<>();

        queue.add(source);
        parentMap.put(source, -1);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Edge edge : findNodeById(residualGraph, current).edges) {
                int neighborId = edge.to;

                if (!parentMap.containsKey(neighborId) && edge.capacity > 0) {
                    queue.add(neighborId);
                    parentMap.put(neighborId, current);

                    if (neighborId == sink) {
                        // Found an augmenting path
                        ArrayList<Integer> path = new ArrayList<>();
                        int node = sink;

                        while (node != -1) {
                            path.add(node);
                            node = parentMap.get(node);
                        }

                        Collections.reverse(path);
                        return path;
                    }
                }
            }
        }

        // No more augmenting paths
        return null;
    }

    private int findMinCapacity(List<Node> residualGraph, List<Integer> path) {
        int minCapacity = Integer.MAX_VALUE;

        for (int i = 0; i < path.size() - 1; i++) {
            Node node = findNodeById(residualGraph, path.get(i));
            Node nextNode = findNodeById(residualGraph, path.get(i + 1));

            for (Edge edge : node.edges) {
                if (edge.to == nextNode.id) {
                    minCapacity = Math.min(minCapacity, edge.capacity);
                    break;
                }
            }
        }

        return minCapacity;
    }

    private void updateResidualGraph(List<Node> residualGraph, List<Integer> path, int minCapacity) {
        for (int i = 0; i < path.size() - 1; i++) {
            Node node = findNodeById(residualGraph, path.get(i));
            Node nextNode = findNodeById(residualGraph, path.get(i + 1));

            for (Edge edge : node.edges) {
                if (edge.to == nextNode.id) {
                    edge.capacity -= minCapacity; // Update forward edge
                    break;
                }
            }

            for (Edge edge : nextNode.edges) {
                if (edge.to == node.id) {
                    edge.capacity += minCapacity; // Update backward edge
                    break;
                }
            }
        }
    }

    private Node findNodeById(List<Node> nodes, int nodeId) {
        for (Node node : nodes) {
            if (node.id == nodeId) {
                return node;
            }
        }
        return null;
    }
}

