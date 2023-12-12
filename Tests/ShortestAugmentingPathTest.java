import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShortestAugmentingPathTest {

    @Test
    void TestShortestAugmentingPath() throws Exception {
        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm();
        ArrayList<Node> residualGraph = fordFulkersonAlgorithm.performFordFulkerson("Graphs/test.csv");
        assertNotNull(residualGraph);
        ShortestAugmentingPath shortestAugmentingPath = new ShortestAugmentingPath();
        int sourceNode = 7;
        int sinkNode = 2;
        Map<Integer, ArrayList<Integer>>
                shortestResult = shortestAugmentingPath.findShortestAugmentingPath(residualGraph, sourceNode, sinkNode);
        ArrayList<Integer> ans = new ArrayList<>(List.of(9,2));
        assertEquals(shortestResult.get(sinkNode), ans);
    }

}