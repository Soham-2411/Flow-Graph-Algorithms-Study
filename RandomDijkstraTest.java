import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RandomDijkstraTest {

    @Test
    void TestRandomDijkstra() throws Exception {
        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm();
        ArrayList<Node> residualGraph = fordFulkersonAlgorithm.performFordFulkerson("Graphs/test.csv");
        assertNotNull(residualGraph);
        RandomDijkstra randomDijkstra = new RandomDijkstra();
        int sourceNode = 5;
        int sinkNode = 2;
        Map<Integer, ArrayList<Integer>>
                randomResult = randomDijkstra.dijkstraWithRandomizedPriority(residualGraph, sourceNode, sinkNode);

        ArrayList<Integer> ans = new ArrayList<>(List.of(3, 8, 2));
        assertEquals(randomResult.get(sinkNode), ans);
    }

}