import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Soham
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Getting graph from csv: ");

        FetchResult fetchedGraphFromCSV = FetchGraphCSV.fetchGraphFromCSV("Graph-1.csv");
        for(Node node: fetchedGraphFromCSV.fetchedNodes){
            System.out.println(node);
        }
        System.out.println("Source Node: " + fetchedGraphFromCSV.sourceNode);
        System.out.println("Sink Node: " + fetchedGraphFromCSV.sinkNode);
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
        RandomSourceSinkGraphs graphGenerator = new RandomSourceSinkGraphs();
        nodes = graphGenerator.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graph-1.csv");
        System.out.println("Stored graph into csv");

//         generating graph no. 2
        nodes = new ArrayList<>();
        n = 200;
        r = 0.2;
        upperCap = 2;
        nodes = graphGenerator.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graph-2.csv");

        // generating graph no. 3
        nodes = new ArrayList<>();
        n = 100;
        r = 0.3;
        upperCap = 2;
        nodes = graphGenerator.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graph-3.csv");

        // generating graph no. 4
        nodes = new ArrayList<>();
        n = 200;
        r = 0.3;
        upperCap = 2;
        nodes = graphGenerator.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graph-4.csv");

        // generating graph no. 5
        nodes = new ArrayList<>();
        n = 100;
        r = 0.2;
        upperCap = 50;
        nodes = graphGenerator.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graph-5.csv");

        // generating graph no. 6
        nodes = new ArrayList<>();
        n = 200;
        r = 0.2;
        upperCap = 50;
        nodes = graphGenerator.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graph-6.csv");

        // generating graph no. 7
        nodes = new ArrayList<>();
        n = 100;
        r = 0.3;
        upperCap = 50;
        nodes = graphGenerator.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graph-7.csv");

        // generating graph no. 8
        nodes = new ArrayList<>();
        n = 200;
        r = 0.3;
        upperCap = 50;
        nodes = graphGenerator.generateGraph(n, r, upperCap, nodes);
        RandomSourceSinkGraphs.writeIntoCSV(r, upperCap, nodes, "Graph-8.csv");
    }
}
