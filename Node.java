import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Soham
 */

class Edge {
    int capacity;
    int to;

    public Edge(int to, int capacity) {
        this.to = to;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
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

    public static Node findNodeById(ArrayList<Node> nodes, int nodeId) {
        for (Node node : nodes) {
            if (node.id == nodeId) {
                return node;
            }
        }
        return null;
    }

    public ArrayList<Node> getNeighbors(ArrayList<Node> nodes){
        ArrayList<Node> neighbours = new ArrayList<>();
        for(Edge edge : edges){
            Node node = findNodeById(nodes, edge.to);
            if(!neighbours.contains(node)){
                neighbours.add(node);
            }
        }
        return neighbours;
    }

    public static int getNoOfEdges(ArrayList<Node> nodes){
        int noOfEdges = 0;
        for(Node node : nodes){
            for(Edge edge : node.edges){
                noOfEdges++;
            }
        }
        return noOfEdges;
    }


    @Override
    public String toString() {
        return "Node: " + id + ": " + x + "," + y + edges;
    }
}

