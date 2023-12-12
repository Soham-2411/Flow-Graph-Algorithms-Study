import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaximumCapacityTest {

    @Test
    void TestMaximumCapacity() throws Exception {
        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm();
        ArrayList<Node> residualGraph = fordFulkersonAlgorithm.performFordFulkerson("Graphs/test.csv");
        assertNotNull(residualGraph);
        MaximumCapacity maximumCapacity = new MaximumCapacity();
        int sourceNode = 4;
        int sinkNode = 2;
        ArrayList<Integer> maxCap = maximumCapacity.dijkstraMaxCriticalCapacity(residualGraph, sourceNode, sinkNode);
        ArrayList<Integer> ans = new ArrayList<>(List.of(4,6,3,8,2));
        assertEquals(maxCap,ans);
    }

}