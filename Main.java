import java.util.ArrayList;

/**
 * @author Soham
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Node> nodes = new ArrayList<>();
        int n = 6;
        double r = 0.6;
        int upperCap = 10;
        RandomSourceSinkGraphs graphGenerator = new RandomSourceSinkGraphs();
        graphGenerator.generateGraph(n, r, upperCap, nodes);
    }
}
