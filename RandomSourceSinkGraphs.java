import java.util.*;
import java.util.Random;

class Node {
    int id;
    double x, y;
    int capacity;
    List<Node> linkedNodes;

    public Node(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.capacity = 0;
        this.linkedNodes = new ArrayList<>();
    }

    public void editCapacity(int capacity){
        this.capacity = capacity;
    }

    public void addEdge(Node node){
        linkedNodes.add(node);
    }

    public boolean containsEdgeWithNode(Node node){
        return this.linkedNodes.contains(node) || node.linkedNodes.contains(this.linkedNodes);
    }
}


public class RandomSourceSinkGraphs {
    private double calculateDistance(Node a, Node b) {
        return Math.sqrt(Math.pow(a.x + b.x, 2) + Math.pow(a.y + b.y, 2));
    }

    private void generateGraph(int n, double r, int upperCap, List<Node> nodes) {
        Random rand = new Random();

        for(int i= 0;i<n;i++){
            nodes.add(new Node(i, rand.nextDouble(1), rand.nextDouble(1)));
        }

        for(int i = 0;i<n; i++){
            for(int j = 0; j<n; j++){
                if(i!=j && calculateDistance(nodes.get(i), nodes.get(j))<=r){
                    if(!nodes.get(i).containsEdgeWithNode(nodes.get(j))){
                        nodes.get(i).addEdge(nodes.get(j));
                    }
                }
            }
        }
        int sourceNode = rand.nextInt(n);
    }
}
