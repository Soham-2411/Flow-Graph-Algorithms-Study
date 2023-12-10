import java.io.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Random;

class Edge {
    int capacity;
    int to;

    public Edge(int to, int capacity) {
        this.to = to;
        this.capacity = capacity;
    }

    @Override
    public String toString(){
        return "Connected to: " + to + " with capacity: " + capacity;
    }
}

class Node {
    int id;
    double x, y;
    LinkedList<Edge> edges;

    public Node(int id, double x, double y) {
        this.id = id;
        DecimalFormat df = new DecimalFormat("0.00");
        this.x = Double.parseDouble(df.format(x));
        this.y = Double.parseDouble(df.format(y));
        this.edges = new LinkedList<>();
    }

    public void addEdge(Node node, int capacity) {
        edges.add(new Edge(node.id, capacity));
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
        return "Node: " + id + ": " + x + "," + y + edges;
    }
}


public class RandomSourceSinkGraphs {

    ArrayList<ArrayList<Integer>> graphAdjacencyList;
    Random randNum;

    public RandomSourceSinkGraphs(){
        randNum = new Random();
        randNum.setSeed(123456789);
    }
    static int sourceNode;
    static int sinkNode;
    ArrayList<Integer> distanceToEachNodeForSource = new ArrayList<>();

    private double calculateDistance(Node a, Node b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public ArrayList<Node> generateGraph(int n, double r, int upperCap, ArrayList<Node> nodes) {

        for (int i = 0; i < n; i++) {
            Node node = new Node(i, randNum.nextDouble(1), randNum.nextDouble(1));
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
                        int capacity = randNum.nextInt(upperCap);
                        nodes.get(i).addEdge(nodes.get(j), capacity);
                    }
                }
            }
        }
        graphAdjacencyList = generateAdjacencyList(nodes);
        printGraph(graphAdjacencyList);
        do {
            sourceNode = randNum.nextInt(n-1);
            System.out.println("Generated : " + sourceNode);
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
        return nodes;
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

    public static ArrayList<ArrayList<Integer>> generateAdjacencyList(ArrayList<Node> nodes) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (Node node : nodes) {
            graph.add(new ArrayList<>(List.of(node.id)));
            for (Edge edge : node.edges) {
                graph.get(node.id).add(edge.to);
            }
        }
        return graph;
    }


    public static void printGraph(ArrayList<ArrayList<Integer>> graph) {
        for (ArrayList<Integer> nodes : graph) {
            System.out.println(nodes);
        }
    }

    public static void writeIntoCSV(double r, int upperCap, ArrayList<Node> nodes, String fileName) throws FileNotFoundException {
        File csvFile = new File(fileName);
        PrintWriter out = new PrintWriter(csvFile);
        out.println("r = " + r + ",upperCap = " + upperCap);
        out.println("NodeID,X,Y,EdgeTo,Capacity");
        for (Node node : nodes) {
            if(node.edges.isEmpty()){
                out.printf("%d,%.2f,%.2f,-,-%n", node.id, node.x, node.y);
            }


            // Write the edges if they exist
            for (Edge edge : node.edges) {
                out.printf("%d,%.2f,%.2f,%d,%d%n", node.id, node.x, node.y, edge.to, edge.capacity);
            }
        }
        out.println("Source node:," + sourceNode);
        out.println("Sink node:," + sinkNode);
        out.close();
    }


}
