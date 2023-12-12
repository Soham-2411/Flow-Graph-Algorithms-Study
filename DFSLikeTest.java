import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DFSLikeTest {

    @Test
    void TestDfsLike() throws Exception {
        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm();
        ArrayList<Node> residualGraph = fordFulkersonAlgorithm.performFordFulkerson("Graphs/test.csv");
        assertNotNull(residualGraph);
        int sourceNode = 6;
        int sinkNode = 2;
        DFSLike dfsLike = new DFSLike();
        Map<Integer, ArrayList<Integer>>
                dfsresult = dfsLike.decreasingDijkstra(residualGraph, sourceNode, sinkNode);

        ArrayList<Integer> ans = new ArrayList<>(List.of(3,8,2));
        assertEquals(dfsresult.get(sinkNode), ans);
    }

}