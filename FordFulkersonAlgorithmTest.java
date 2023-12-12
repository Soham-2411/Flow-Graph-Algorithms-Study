import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FordFulkersonAlgorithmTest {


    @Test
    void testfordfulkerson() throws Exception {
        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm();
        ArrayList<Node> residualGraph = fordFulkersonAlgorithm.performFordFulkerson("Graphs/test.csv");
        assertNotNull(residualGraph);
    }
}