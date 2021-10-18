package studio.itrack.buckland.common.graph;

public class TestGraph extends SparseGraph {

    public TestGraph(boolean directed) {
        super(directed);
    }

	public static TestGraph instance = null;
	public static TestGraph getInstance() {
		if (instance == null) {
			instance = new TestGraph(false);
			instance.setup();
		}
		return instance;
	}


	String[] names = {
        "entrance",					// 1
        "dining commons",			// 2
        "living room",				// 3
        "basketball court",			// 4
        "makerspace",				// 5
        "gym",						// 6
        "elevator - level 1",		// 7
        "elevator - level 2",		// 8
        "elevator - level 3",		// 9
        "meeting space level 2",	//10
        "library",					//11
        "science lounge",			//12
        "common space 3rd floor",	//13
        "music room lounge space",	//14
    };

	int[] paths = {
		1,2,
        2,3,
        3,4,
        4,1,
        5,6,
        7,8,
	};

    public void setup() {
        int nodeCount = names.length;
        for(int i=0; i < nodeCount; ++i) {
            instance.addNode(new GraphNode(instance.getNextFreeNodeIndex()));
        }
		
		for (int i = 0; i < paths.length; i += 2){
			instance.addEdge(new GraphEdge(paths[i]-1, paths[i+1]-1));
		}
		/*
		for (int i = 0; i < names.length; ++i){
			// this is how you can iterate the edges coming from a node
			Iterator<GraphEdge> edgeIt = instance.edgeIterator(i);
				
			// verify that there are 2 connections coming off of 2
			while(edgeIt.hasNext()) {
				GraphEdge e = edgeIt.next();
				System.out.println(e);
			}
		}
		*/
	}
}

