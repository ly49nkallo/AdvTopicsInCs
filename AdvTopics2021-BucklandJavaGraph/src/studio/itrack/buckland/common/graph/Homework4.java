package studio.itrack.buckland.common.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

public class Homework4 {

	/**
	 * Class for GraphNode that has a string associated with it
	 * @author rspell
	 *
	 */
	static class StringGraphNode extends GraphNode {
		String label;
		
		StringGraphNode(int id, String s) {
			super(id);
			label = s;
		}
		
		@Override
		public String toString() {
			return String.format("index:%d(%s)",index,label);
		}
	}
	
	/**
	 * Create a Graph that can hold StringGraphNodes
	 * @author rspell
	 *
	 */
	static class StringNodeSparseGraph extends AbstractSparseGraph<StringGraphNode,GraphEdge> {

		public StringNodeSparseGraph(boolean directed) {
			super(directed);
		}

		@Override
		GraphEdge createEdge(int from, int to, double cost) {			
			return new GraphEdge(from,to,cost);
		}
		
	}

	// =-=-=-=-=-=-=-=-
	// main method
	// =-=-=-=-=-=-=-=-	
	public static void main(String[] args) {
	
		
		System.out.println("the romania graph");
		System.out.println(romaniaGraph());		
		
		System.out.println("the Buckland Demo graph from the Djikstra reading");
		System.out.println(bucklandDjikstraGraph());				
	}
	
	static GraphEdge connectCity(String from, String to, double distance) {
		return new GraphEdge(cityNodeIndex.get(from.toLowerCase()), cityNodeIndex.get(to.toLowerCase()), distance);
	}
	
	static String[] city = new String[] {"Arad", "Sibiu", "Bucharest", "Giurgiu", "Craiova",
												 "Drobeta", "Eforie", "Fagaras", "Hirsova", "Iasi",
												 "Neamt", "Lugoj", "Timisoara", "Mehadia", "Oradea",
												 "Zerind", "Pitesti", "Rimnicu", "Urziceni", "Vaslui"};
	
	static HashMap<String, Integer>cityNodeIndex = new HashMap<>();											 
	
	/**
	 * 
	 * @return SparseGraph where nodes have the city name attached
	 */
	public static StringNodeSparseGraph romaniaGraph() {
		//not directed
		StringNodeSparseGraph romania = new StringNodeSparseGraph(false);
		
		//create city graph nodes
		for(String s: city) {
			int i = romania.getNextFreeNodeIndex();
			romania.addNode( new StringGraphNode(i,s));
			// input an entry in the map so we can get the index for this city
			cityNodeIndex.put(s.toLowerCase(),i);
		}
		//create edges with distances for cost
		romania.addEdge(connectCity("Arad","Zerind",75));
		romania.addEdge(connectCity("Arad","Sibiu",140));
		romania.addEdge(connectCity("Arad","Timisoara",118));
		romania.addEdge(connectCity("Bucharest","Urziceni",85));
		romania.addEdge(connectCity("Bucharest","Pitesti",101));
		romania.addEdge(connectCity("Bucharest","Giurgiu",90));
		romania.addEdge(connectCity("Bucharest","Fagaras",211));
		romania.addEdge(connectCity("Craiova","Drobeta",120));
		romania.addEdge(connectCity("Craiova","Rimnicu",146));
		romania.addEdge(connectCity("Craiova","Pitesti",138));
		romania.addEdge(connectCity("Drobeta","Mehadia",75));
		romania.addEdge(connectCity("Eforie","Hirsova",86));
		romania.addEdge(connectCity("Fagaras","Sibiu",99));
		romania.addEdge(connectCity("Hirsova","Urziceni",98));
		romania.addEdge(connectCity("Iasi","Vaslui",92));
		romania.addEdge(connectCity("Iasi","Neamt",87));
		romania.addEdge(connectCity("Lugoj","Timisoara",111));
		romania.addEdge(connectCity("Lugoj","Mehadia",70));
		romania.addEdge(connectCity("Oradea","Zerind",71));
		romania.addEdge(connectCity("Oradea","Sibiu",151));
		romania.addEdge(connectCity("Pitesti","Rimnicu",97));
		romania.addEdge(connectCity("Rimnicu","Sibiu",80));
		romania.addEdge(connectCity("Urziceni","Vaslui",142));
		
		return romania;
	}
	
	/**
	 * Returns the graph that is used in the Buckland Example from class
	 * @return
	 */
	public static SparseGraph bucklandDjikstraGraph() {
		SparseGraph graph = new SparseGraph(true);
		//create nodes
		for(int i=0; i < 7; ++i) {
			graph.addNode(new GraphNode(graph.getNextFreeNodeIndex()));
		}
		
		//create edges
		graph.addEdge(new GraphEdge(1, 5, 2.9 ));
		graph.addEdge(new GraphEdge(1, 6, 1.0 ));
		graph.addEdge(new GraphEdge(5, 6, 3.0 ));
		graph.addEdge(new GraphEdge(5, 2, 1.9 ));			
		graph.addEdge(new GraphEdge(3, 5, 0.8 ));
		graph.addEdge(new GraphEdge(4, 3, 3.7 ));
		graph.addEdge(new GraphEdge(6, 4, 1.1 ));
		graph.addEdge(new GraphEdge(2, 3, 3.1 ));
		
		return graph;
	}
	/*
	Arad=(91, 492), 
	Bucharest=(400, 327),
	Craiova=(253, 288),

	Drobeta=(165, 299),
	Eforie=(562, 293),
	Fagaras=(305, 449),

	Giurgiu=(375, 270),
	Hirsova=(534, 350), 
	Iasi=(473, 506),

	Lugoj=(165, 379), 
	Mehadia=(168, 339), 
	Neamt=(406, 537),

	Oradea=(131, 571),
	Pitesti=(320, 368), 
	Rimnicu=(233, 410),

	Sibiu=(207, 457), 
	Timisoara=(94, 410), 
	Urziceni=(456, 350),

	Vaslui=(509, 444), 
	Zerind=(108, 531))
	*/
	public static double[][] romaniaHeuristic() {
		double[][] coordinates = new double[romaniaGraph().numberActiveNodes()][romaniaGraph().numberActiveNodes()];
		int[] pos = {
			91, 492,
			400, 327,
			253, 288,
			165, 299,
			562, 293,
			305, 449,
			375, 270,
			534, 350,
			473, 506,
			165, 379,
			168, 339,
			406, 537,
			131, 571,
			320, 368,
			233, 410,
			207, 457,
			94, 410,
			456, 350,
			509, 444,
			108, 531,
		};
		if (romaniaGraph().numberActiveNodes() != pos.length/2) System.out.println("err");

		//calculate the distances between any two nodes
		for(int i = 0; i < pos.length/2; i++){
			for (int j = 0; j < pos.length/2; j++){
				if (i == j) continue;
				int distanceX = pos[j*2] - pos[i*2];
				int distanceY = pos[j*2 + 1] - pos[i*2 + 1];

				coordinates[i][j] = Math.sqrt((distanceX*distanceX) + (distanceY*distanceY));
				if (coordinates[i][j] <= 0) System.out.println("0 distance");
			}
		}
		return coordinates;
	}
}