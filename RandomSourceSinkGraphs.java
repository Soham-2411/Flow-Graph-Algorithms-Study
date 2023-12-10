import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Random;

class Edge {
    int capacity;
    int to;

    public Edge(int to, int capacity) {
        this.to = to;
        this.capacity = capacity;
    }
}

class Node {
    int id;
    double x, y;
    LinkedList<Edge> edges;

    public Node(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.edges = new LinkedList<>();
    }

    public void addEdge(Node node, int capacity) {
        edges.add(new Edge(node.id, 0));
    }

    public boolean containsEdgeWithNode(Node node) {
        for (Edge edge : this.edges) {
            if (edge.to == node.id) {
                return true;
            }
        }
        for (Edge edge : node.edges) {
            if (edge.to == this.id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Node: " + id + ": " + x + "," + y;
    }
}


public class RandomSourceSinkGraphs {

    ArrayList<ArrayList<Integer>> graphAdjacencyList;

    ArrayList<ArrayList<HashMap<Integer, Integer>>> graphAdjacencyListWithCapacities;

    int sourceNode;
    int sinkNode;
    ArrayList<Integer> distanceToEachNodeForSource = new ArrayList<>();

    private double calculateDistance(Node a, Node b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public void generateGraph(int n, double r, int upperCap, ArrayList<Node> nodes) {
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            Node node = new Node(i, rand.nextDouble(1), rand.nextDouble(1));
            //System.out.println(node.toString());
            nodes.add(node);
        }
        ArrayList<Integer> nodesList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodesList.add(i);
        }
        Collections.shuffle(nodesList);
        for (int i : nodesList) {
            for (int j = 0; j < n; j++) {
                if (i != j && calculateDistance(nodes.get(i), nodes.get(j)) <= r) {
                    if (!nodes.get(i).containsEdgeWithNode(nodes.get(j))) {
                        nodes.get(i).addEdge(nodes.get(j), rand.nextInt(upperCap));
                    }
                }
            }
        }
        generateAdjacencyList(nodes);
        printGraph();
        do {
            sourceNode = rand.nextInt(n);
        } while (graphAdjacencyList.get(sourceNode).size() <= 1);
        for (int i = 0; i < nodes.size(); i++) {
            distanceToEachNodeForSource.add(0);
        }
        System.out.println("RUNNING BFS");
        bfs(sourceNode);
        int maxValue = Integer.MIN_VALUE;
        for(int dist : distanceToEachNodeForSource){
            if(maxValue< dist && dist!=Integer.MAX_VALUE){
                maxValue = dist;
            }
        }
        sinkNode = distanceToEachNodeForSource.indexOf(maxValue);
        System.out.println("Source: " + sourceNode + " Sink: " + sinkNode);
        System.out.println(distanceToEachNodeForSource);
    }

    private void bfs(int source) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visitedNodes = new boolean[graphAdjacencyList.size()];
        for (int i = 0; i < graphAdjacencyList.size(); i++) {
            visitedNodes[i] = false;
            distanceToEachNodeForSource.set(i, Integer.MAX_VALUE);
        }
        visitedNodes[source] = true;
        queue.add(source);
        distanceToEachNodeForSource.set(source, 0);
        while (!queue.isEmpty()) {
            int cur = queue.remove();
            for (int i = 0; i < graphAdjacencyList.get(cur).size(); i++) {
                int neighbour = graphAdjacencyList.get(cur).get(i);
                if (!visitedNodes[neighbour]) {
                    visitedNodes[neighbour] = true;
                    distanceToEachNodeForSource.set(neighbour, distanceToEachNodeForSource.get(cur) + 1);
                    queue.add(neighbour);
                }
            }
        }
    }

    private void generateAdjacencyList(ArrayList<Node> nodes) {
        graphAdjacencyList = new ArrayList<>();
        graphAdjacencyListWithCapacities = new ArrayList<>();
        for (Node node : nodes) {
            graphAdjacencyList.add(new ArrayList<>(List.of(node.id)));
            graphAdjacencyListWithCapacities.add(new ArrayList<>());
            for (Edge edge : node.edges) {
                graphAdjacencyListWithCapacities.get(node.id).add(new HashMap<>(1, 2));
                graphAdjacencyList.get(node.id).add(edge.to);
            }
        }

        graphAdjacencyListWithCapacities = new ArrayList<>();
//        graphAdjacencyList = new ArrayList<>(List.of(new ArrayList<>(List.of(0, 1, 2, 3)),
//                new ArrayList<>(List.of(1, 4)),
//                new ArrayList<>(List.of(2, 3)),
//                new ArrayList<>(List.of(3, 4)),
//                new ArrayList<>(List.of(4, 5)),
//                new ArrayList<>(List.of(5, 2))));
    }

    private void printGraph() {
        for (ArrayList<Integer> nodes : graphAdjacencyList) {
            System.out.println(nodes);
        }
    }

    public void writeIntoCSV() throws FileNotFoundException {
        File csvFile = new File("Generated-Graphs.csv");
        PrintWriter out = new PrintWriter(csvFile);
        for(ArrayList<Integer> nodes: graphAdjacencyList){
            out.println(nodes);
        }
        out.close();
    }
}
