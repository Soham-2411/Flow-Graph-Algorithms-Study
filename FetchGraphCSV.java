import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class helps retrieve all the data from the csv file and store it in an instance of the
 * Fetch Result graph
 * @author Soham
 */
public class FetchGraphCSV {
    public static FetchResult fetchGraphFromCSV(String csvFileName) {
        ArrayList<Node> fetchedNodes = new ArrayList<>();
        int sourceNode = 0;
        int sinkNode = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            Map<Integer, Node> nodeMap = new HashMap<>();


            // Skip the header lines
            reader.readLine();
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].equals("Source node:")){
                    sourceNode = Integer.parseInt(parts[1]);
                    line = reader.readLine();
                    parts = line.split(",");
                    sinkNode = Integer.parseInt(parts[1]);
                    break;
                }
                if(parts[0].equals("Sink node:")){
                    sinkNode = Integer.parseInt(parts[1]);
                    break;
                }
                int nodeId = Integer.parseInt(parts[0]);
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                int edgeTo = parts[3].equals("-") ? -1 : Integer.parseInt(parts[3]);
                int capacity = parts[4].equals("-") ? -1 : Integer.parseInt(parts[4]);

                Node node = nodeMap.computeIfAbsent(nodeId, id -> new Node(id, x, y));
                if (edgeTo != -1) {
                    node.addEdge(new Node(edgeTo, 0.0, 0.0), capacity);
                }
                nodeMap.put(nodeId, node);
            }

            fetchedNodes.addAll(nodeMap.values());

        } catch (IOException e) {
            e.printStackTrace();
        }

        FetchResult fetchResult = new FetchResult(fetchedNodes, sourceNode, sinkNode);

        return fetchResult;
    }
}

class FetchResult{
    ArrayList<Node> fetchedNodes;
    int sourceNode;
    int sinkNode;

    public FetchResult(ArrayList<Node> fetchedNodes, int sourceNode, int sinkNode){
        this.fetchedNodes = fetchedNodes;
        this.sourceNode = sourceNode;
        this.sinkNode = sinkNode;
    }
}
