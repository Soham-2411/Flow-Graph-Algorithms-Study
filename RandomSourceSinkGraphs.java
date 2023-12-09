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
        System.out.println("Node: " + id + ": " + x + "," + y);
        return null;
    }
}


public class RandomSourceSinkGraphs {

    ArrayList<ArrayList<Integer>> graphAdjacencyList;

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
                    System.out.println(calculateDistance(nodes.get(i), nodes.get(j)) + ", " + r);
                    if (!nodes.get(i).containsEdgeWithNode(nodes.get(j))) {
                        nodes.get(i).addEdge(nodes.get(j), rand.nextInt(upperCap));
                    }
                }
            }
        }
        generateAdjacencyList(nodes);
        printGraph();
        //do {
        sourceNode = 0;
        //} while (graphAdjacencyList.get(sourceNode).size() > 2);

        boolean[] visitedNodes = new boolean[graphAdjacencyList.size()];
        for (int i = 0; i < nodes.size(); i++) {
            distanceToEachNodeForSource.add(0);
        }

        System.out.println("RUNNING DFS");
        dfs(sourceNode, visitedNodes, 0);
        System.out.println(distanceToEachNodeForSource);
        int count = 0, index = 0;
        for (int i = 0; i < distanceToEachNodeForSource.size(); i++) {
            if (count < distanceToEachNodeForSource.get(i)) {
                count = distanceToEachNodeForSource.get(i);
                index = i;
                System.out.print(index + ", ");
            }
        }
        sinkNode = nodes.get(index).id;
        System.out.println("Source node: " + sourceNode + ", " + "Sink Node: " + sinkNode);
    }

    private void dfs(int V, boolean[] visitedNodes, int counter) {
        visitedNodes[V] = true;
        counter++;
        System.out.println(V + ":" + counter);
        for (Integer neighbour : graphAdjacencyList.get(V)) {
            if (!visitedNodes[neighbour]) {
                dfs(neighbour, visitedNodes, counter);
            }
            if (distanceToEachNodeForSource.get(V) < counter) {
                distanceToEachNodeForSource.set(V, counter);
            }
            counter--;
        }
    }

    private void generateAdjacencyList(ArrayList<Node> nodes) {
        graphAdjacencyList = new ArrayList<>();
//        for (Node node : nodes) {
//            graphAdjacencyList.add(new ArrayList<>(List.of(node.id)));
//            for (Edge edge : node.edges) {
//                graphAdjacencyList.get(node.id).add(edge.to);
//            }
//        }
        graphAdjacencyList = new ArrayList<>(List.of(new ArrayList<>(List.of(0, 1, 2, 3)),
                new ArrayList<>(List.of(1, 4, 5)),
                new ArrayList<>(List.of(2, 3)),
                new ArrayList<>(List.of(3, 4)),
                new ArrayList<>(List.of(4)),
                new ArrayList<>(List.of(5, 2))));
    }

    private void printGraph() {
        for (ArrayList<Integer> nodes : graphAdjacencyList) {
            System.out.println(nodes);
        }
    }
}
