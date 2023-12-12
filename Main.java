import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Soham
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // this method is commented out because the graphs have already been generated
        //generateCustomGraphs();
        System.out.println("------------------------------------GRAPH 1------------------------------------");
        runAlgorithmsOnGraph("Graphs/Graph-1.csv");
        System.out.println("------------------------------------GRAPH 2------------------------------------");
        runAlgorithmsOnGraph("Graphs/Graph-2.csv");
        System.out.println("------------------------------------GRAPH 3------------------------------------");
        runAlgorithmsOnGraph("Graphs/Graph-3.csv");
        System.out.println("------------------------------------GRAPH 4------------------------------------");
        runAlgorithmsOnGraph("Graphs/Graph-4.csv");
        System.out.println("------------------------------------GRAPH 5------------------------------------");
        runAlgorithmsOnGraph("Graphs/Graph-5.csv");
        System.out.println("------------------------------------GRAPH 6------------------------------------");
        runAlgorithmsOnGraph("Graphs/Graph-6.csv");
        System.out.println("------------------------------------GRAPH 7------------------------------------");
        runAlgorithmsOnGraph("Graphs/Graph-7.csv");
        System.out.println("------------------------------------GRAPH 8------------------------------------");
        runAlgorithmsOnGraph("Graphs/Graph-8.csv");
        System.out.println("------------------------------------Custom GRAPH 1------------------------------------");
        runAlgorithmsOnGraph("Graphs/CustomGraph-1.csv");
        System.out.println("------------------------------------Custom GRAPH 2------------------------------------");
        runAlgorithmsOnGraph("Graphs/CustomGraph-2.csv");
    }

    /*
    This method runs all the algorithms on the given graph
     */
    static void runAlgorithmsOnGraph(String csvName) throws Exception {

        System.out.println("Running graph: " + csvName);
        FetchResult graph = FetchGraphCSV.fetchGraphFromCSV(csvName);
        ArrayList<Node> residualGraph = runFordFulkersonAlgorithm(csvName);
        System.out.println();

        System.out.println("Shortest Augmenting Path (SAP)");
        ArrayList<Integer> shortestPath = runShortestPathAlgorithm(residualGraph, graph.sourceNode, graph.sinkNode);
        System.out.print("Shortest path: ");
        System.out.println(shortestPath);
        System.out.println();

        System.out.println("DFS-Like");
        ArrayList<Integer> dfsLike = runDFSLikeAlgorithm(residualGraph, graph.sourceNode, graph.sinkNode);
        System.out.print("Path: ");
        System.out.println(dfsLike);
        System.out.println();

        System.out.println("Maximum Capacity Algorithm");
        ArrayList<Integer> maxCapacityPath = runMaximumCapacityAlgorithm(residualGraph, graph.sourceNode, graph.sinkNode);
        System.out.print("Path: ");
        System.out.println(maxCapacityPath);
        System.out.println();

        System.out.println("Randomly Implemented dijkstra");
        ArrayList<Integer> randomDijkstra = runRandomDijkstraAlgorithm(residualGraph, graph.sourceNode, graph.sinkNode);
        System.out.print("Path: ");
        System.out.println(randomDijkstra);
        System.out.println();


    }

    /*
    To run the ford fulkerson algorithm
    returns an arraylist (shortest path) from source to sink
     */
    static ArrayList<Node> runFordFulkersonAlgorithm(String csvName) throws Exception {
        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm();
        ArrayList<Node> residualGraph = fordFulkersonAlgorithm.performFordFulkerson(csvName);
        //RandomSourceSinkGraphs.printGraph(residualGraph);
        return residualGraph;
    }

    /*
    to run the shortest path algorithm
     returns an arraylist (shortest path) from source to sink
     */
    static ArrayList<Integer> runShortestPathAlgorithm(ArrayList<Node> residualGraph, int sourceNode, int sinkNode) {
        ShortestAugmentingPath shortestAugmentingPath = new ShortestAugmentingPath();
        Map<Integer, ArrayList<Integer>> Sap = shortestAugmentingPath.findShortestAugmentingPath(residualGraph, sourceNode, sinkNode);
        if (!Sap.get(sinkNode).isEmpty()) {
            Sap.get(sinkNode).add(0, sourceNode);
        }
        return Sap.get(sinkNode);
    }

    /*
    to run the dfs like algorithm
     returns an arraylist (shortest path) from source to sink
     */
    static ArrayList<Integer> runDFSLikeAlgorithm(ArrayList<Node> residualGraph, int sourceNode, int sinkNode) {
        DFSLike dfsLike = new DFSLike();
        Map<Integer, ArrayList<Integer>> dfs = dfsLike.decreasingDijkstra(residualGraph, sourceNode, sinkNode);
        if (!dfs.get(sinkNode).isEmpty()) {
            dfs.get(sinkNode).add(0, sourceNode);
        }
        return dfs.get(sinkNode);
    }

    /*
    to run the maximum path algorithm
     returns an arraylist (shortest path) from source to sink
     */
    static ArrayList<Integer> runMaximumCapacityAlgorithm(ArrayList<Node> residualGraph, int sourceNode, int sinkNode){
        MaximumCapacity maximumCapacity = new MaximumCapacity();
        ArrayList<Integer> maxCapacity = maximumCapacity.dijkstraMaxCriticalCapacity(residualGraph, sourceNode, sinkNode);
        return maxCapacity;
    }

    /*
    to run the random dijkstra algorithm
     returns an arraylist (shortest path) from source to sink
     */
    static ArrayList<Integer> runRandomDijkstraAlgorithm(ArrayList<Node> residualGraph, int sourceNode,  int sinkNode){
        RandomDijkstra randomDijkstra = new RandomDijkstra();
        Map<Integer, ArrayList<Integer>> randomPath = randomDijkstra.dijkstraWithRandomizedPriority(residualGraph, sourceNode, sinkNode);
        if(!randomPath.get(sinkNode).isEmpty()){
            randomPath.get(sinkNode).add(0, sourceNode);
        }
        return randomPath.get(sinkNode);
    }



    /*
    Method to generate random graphs on custom values
     */
    static void generateCustomGraphs() throws FileNotFoundException{
        ArrayList<Node> nodes = new ArrayList<>();
        int n = 100;
        double r = 0.4;
        int upperCap = 20;
        RandomSourceSinkGraphs graphGenerator1 = new RandomSourceSinkGraphs();
        nodes = graphGenerator1.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/CustomGraph-1.csv");

        nodes = new ArrayList<>();
        n = 150;
        r = 0.2;
        upperCap = 30;
        RandomSourceSinkGraphs graphGenerator2 = new RandomSourceSinkGraphs();
        nodes = graphGenerator2.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/CustomGraph-2.csv");
    }

    /*
    Method to generate graphs based on the project description
     */
    static void generateGraphs() throws FileNotFoundException {
//        Generating graphs based on the data given in the project description:
//        1. n = 100, r = 0.2, upperCap = 2
//        2. n = 200, r = 0.2, upperCap = 2
//        3. n = 100, r = 0.3, upperCap = 2
//        4. n = 200, r = 0.3, upperCap = 2
//        5. n = 100, r = 0.2, upperCap = 50
//        6. n = 200, r = 0.2, upperCap = 50
//        7. n = 100, r = 0.3, upperCap = 50
//        8. n = 200, r = 0.3, upperCap = 50

        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you sure you want to regenerate graphs? It will erase all the previous data (Y/N)");
        String yesOrNo = scanner.nextLine();
        if (!yesOrNo.equals("Y")) {
            return;
        }
        System.out.println("Generating graphs");
        // generating graph no. 1
        ArrayList<Node> nodes = new ArrayList<>();
        int n = 100;
        double r = 0.2;
        int upperCap = 2;
        RandomSourceSinkGraphs graphGenerator1 = new RandomSourceSinkGraphs();
        nodes = graphGenerator1.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-1.csv");
        // generating graph no. 2
        nodes = new ArrayList<>();
        n = 200;
        r = 0.2;
        upperCap = 2;
        RandomSourceSinkGraphs graphGenerator2 = new RandomSourceSinkGraphs();
        nodes = graphGenerator2.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-2.csv");

        // generating graph no. 3
        nodes = new ArrayList<>();
        n = 100;
        r = 0.3;
        upperCap = 2;
        RandomSourceSinkGraphs graphGenerator3 = new RandomSourceSinkGraphs();
        nodes = graphGenerator3.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-3.csv");

        // generating graph no. 4
        nodes = new ArrayList<>();
        n = 200;
        r = 0.3;
        upperCap = 2;
        RandomSourceSinkGraphs graphGenerator4 = new RandomSourceSinkGraphs();
        nodes = graphGenerator4.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-4.csv");

        // generating graph no. 5
        nodes = new ArrayList<>();
        n = 100;
        r = 0.2;
        upperCap = 50;
        RandomSourceSinkGraphs graphGenerator5 = new RandomSourceSinkGraphs();
        nodes = graphGenerator5.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-5.csv");

        // generating graph no. 6
        nodes = new ArrayList<>();
        n = 200;
        r = 0.2;
        upperCap = 50;
        RandomSourceSinkGraphs graphGenerator6 = new RandomSourceSinkGraphs();
        nodes = graphGenerator6.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-6.csv");

        // generating graph no. 7
        nodes = new ArrayList<>();
        n = 100;
        r = 0.3;
        upperCap = 50;
        RandomSourceSinkGraphs graphGenerator7 = new RandomSourceSinkGraphs();
        nodes = graphGenerator7.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-7.csv");

        // generating graph no. 8
        nodes = new ArrayList<>();
        n = 200;
        r = 0.3;
        upperCap = 50;
        RandomSourceSinkGraphs graphGenerator8 = new RandomSourceSinkGraphs();
        nodes = graphGenerator8.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-8.csv");
    }
}
