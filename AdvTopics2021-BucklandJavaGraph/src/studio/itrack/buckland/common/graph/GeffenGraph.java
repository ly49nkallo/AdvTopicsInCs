package studio.itrack.buckland.common.graph;

public class GeffenGraph extends SparseGraph {

    public GeffenGraph(boolean directed) {
        super(directed);
    }


	static String[] names = {
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

	static int[] paths = {
		1,2,
		1,3,
		1,4,
		2,3,
		2,5,
		3,4,
		3,5,
		3,6,
		3,7,
		4,6,
		5,6,
		5,7,
		6,7,
		7,8,
		8,9,
		8,10,
		9,13,
		9,14,
		10,11,
		11,12,
		12,13,
		13,14,
	};

    public static GeffenGraph getInstance() {
		GeffenGraph instance = new GeffenGraph(false);
        int nodeCount = names.length;
        for(int i=0; i < nodeCount+1; ++i) {
            instance.addNode(new GraphNode(instance.getNextFreeNodeIndex()));
        }
		
		for (int i = 0; i < paths.length; i += 2){
			instance.addEdge(new GraphEdge(paths[i], paths[i+1]));
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
		return instance;
	}
	public static double[][] getHeuristic() {
		double[][] coordinates = new double[getInstance().numberActiveNodes()][getInstance().numberActiveNodes()];
		/*
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
		*/
		int[] pos = {
			//x, y, z,
			50, 0, 0,
			25, 25, 0,
			50, 25, 0,
			90, 0, 0,
			50, 70, 0,
			90, 50, 0,
			80, 40, 0,
			80, 40, 30,
			80, 40, 60,
			70, 50, 30,
			20, 50, 30,
			20, 50, 60,
			70, 50, 60,
			90, 50, 60,
		};
		for(int i = 0; i < pos.length/3; i++){
			for (int j = 0; j < pos.length/3; j++){
				if (i == j) continue;
				int distanceX = pos[j*3] - pos[i*3];
				int distanceY = pos[j*3 + 1] - pos[i*3 + 1];
				int distanceZ = pos[j*3 + 2] - pos[j*3 + 2];

				coordinates[i][j] = Math.sqrt((distanceX*distanceX) + (distanceY*distanceY) + (distanceZ*distanceZ));
				if (coordinates[i][j] < 0) System.out.println("neg distance");
			}
		}

		return coordinates;
	}
}

