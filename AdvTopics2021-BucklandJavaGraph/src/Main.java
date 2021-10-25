import studio.itrack.buckland.common.graph.*;
//import java.util.Iterator;

// Author: Ty Brennan
// Advanced Computer Science
// 10-18-2021
public class Main {
    public static void main(String[] args) {
        AbstractSparseGraph<GraphNode,GraphEdge> instance = GeffenGraph.getInstance();
        //System.out.println(instance.toString());
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
		
		int[] tests = {
			1,5,
			11,2,
			14,1,
		};
		for (int i = 0; i < tests.length; i+=2) {
			//use the geffen graph to test the BFS and DFS algorithms
			SearchAlogorithms.BFS(tests[i], tests[i+1], instance);
			SearchAlogorithms.DFS(tests[i], tests[i+1], instance);
			SearchAlogorithms.Dijkstra(tests[i], tests[i+1], instance);
		}
		*/
		SearchAlogorithms.Dijkstra(0,4,TestGraph.getInstance());
		System.out.println("test");
		SearchAlogorithms.Dijkstra(1,8,Homework4.romaniaGraph());
        /*
        TestGraph testInstance = TestGraph.getInstance();
        tests = new int[]{
			1,2,
            1,5,
            1,10,
		};
		for (int i = 0; i < tests.length; i+=2) {
			System.out.println("from " + tests[i] + " to " + tests[i+1]);
			System.out.println("BFS");
			SearchAlogorithms.BFS(tests[i], tests[i+1], testInstance);
			System.out.println("DFS");
			SearchAlogorithms.DFS(tests[i], tests[i+1], testInstance);
			System.out.println("--------------------------");
		}
		*/

    }
}
