import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Soham
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        ArrayList<Node> nodes = new ArrayList<>();
//        int n = 100;
//        double r = 0.2;
//        int upperCap = 2;
//        RandomSourceSinkGraphs graphGenerator1 = new RandomSourceSinkGraphs();
//        nodes = graphGenerator1.generateGraph(n, r, upperCap, nodes);
//        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-1.csv");
        FetchResult graph1 = FetchGraphCSV.fetchGraphFromCSV("Graphs/Graph-5.csv");
        ArrayList<ArrayList<Integer>> adj = RandomSourceSinkGraphs.generateAdjacencyList(graph1.fetchedNodes);
        RandomSourceSinkGraphs.printGraph(adj);
        FordFulkersonAlgorithm.performFordFulkerson("Graphs/Graph-5.csv");
//        FetchResult graph2 = FetchGraphCSV.fetchGraphFromCSV("Graphs/Graph-2.csv");
//        FetchResult graph3 = FetchGraphCSV.fetchGraphFromCSV("Graphs/Graph-3.csv");
//        FetchResult graph4 = FetchGraphCSV.fetchGraphFromCSV("Graphs/Graph-4.csv");
//        FetchResult graph5 = FetchGraphCSV.fetchGraphFromCSV("Graphs/Graph-5.csv");
//        FetchResult graph6 = FetchGraphCSV.fetchGraphFromCSV("Graphs/Graph-6.csv");
//        FetchResult graph7 = FetchGraphCSV.fetchGraphFromCSV("Graphs/Graph-7.csv");
//        FetchResult graph8 = FetchGraphCSV.fetchGraphFromCSV("Graphs/Graph-8.csv");


    }

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

        ArrayList<Node> nodes = new ArrayList<>();
        int n = 100;
        double r = 0.2;
        int upperCap = 2;
        RandomSourceSinkGraphs graphGenerator1 = new RandomSourceSinkGraphs();
        nodes = graphGenerator1.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graphs/Graph-1.csv");

//         generating graph no. 2
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
