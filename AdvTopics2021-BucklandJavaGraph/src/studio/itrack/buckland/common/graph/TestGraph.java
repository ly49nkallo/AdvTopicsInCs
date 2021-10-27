package studio.itrack.buckland.common.graph;
import java.util.ArrayList;

public class TestGraph extends SparseGraph {

    public TestGraph(boolean directed) {
        super(directed);
    }
    static int[] paths = {
        0,1,    4,
        0,7,    8,
        1,2,    8,
        7,8,    7,
        6,7,    1,
        2,8,    2,
        6,8,    6,
        6,5,    2,
        2,3,    7,
        2,5,    4,
        5,4,    10,
        3,4,    9,
    };

	public static TestGraph getInstance() {
        TestGraph instance = new TestGraph(false);
        int len = (paths.length) ;
        for (int i = 0; i < 9; i++) {instance.addNode(new GraphNode(instance.getNextFreeNodeIndex()));}
        for (int i = 0; i < len; i+=3) { 
            instance.addEdge(new GraphEdge(paths[i], paths[i+1], paths[i+2]));
        }
        return instance;
    }
    public static double[][] getHeuristic() {
        return new double[TestGraph.paths.length / 3][TestGraph.paths.length / 3];
    }
}

