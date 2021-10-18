package studio.itrack.buckland.common.graph;
import com.eckel.UnitTest;

import static org.hamcrest.MatcherAssert.*;

import java.util.Queue;
import java.util.Stack;

import javax.swing.plaf.TreeUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import static org.hamcrest.CoreMatchers.*;


public class MyGraph extends AbstractSparseGraph<GraphNode,GraphEdge> {

    public MyGraph(boolean directed) {
        super(directed);
    }

	public static MyGraph instance = null;
	public static MyGraph getInstance() {
		if (instance == null) {
			instance = new MyGraph(false);
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

    @Override
	public GraphEdge createEdge(int from, int to, double cost) {
		return new GraphEdge(from,to, cost);
	}

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

  	public boolean BFS(int from, int to){
		//convert to indicies
		from -= 1; to -= 1;
		boolean[] visited = new boolean[instance.numberOfNodes()];
		//queue inheriter that preserves order
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		//track edges investigated
		ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();
		//initialize current starting node
		visited[from] = true;
		queue.add(instance.getNode(from));
		
		while(queue.size() != 0) {
			//pop item from queue
			GraphNode focusedNode = queue.poll();
			Iterator<GraphEdge> edgeIt = instance.edgeIterator(focusedNode.index());
			//iterate over all edges originating at the polled node
			while(edgeIt.hasNext()) {
				GraphEdge e = edgeIt.next();
				//filter out only nodes that have not already been explored
				if (!visited[e.to()]) {
					visited[e.to()] = true;
					queue.add(instance.getNode(e.to()));
					edges.add(e);
					//Show investigation
					System.out.print(focusedNode.index() + 1);
					System.out.print(" --> ");
					System.out.println(e.to() + 1);
					//System.out.println(queue);
					//System.out.println(edges);
					//check to see if revealed node is correct
					if (e.to() == to) break;
				}
			}
			// exit if target node never visited
			if (visited[to]) {break;}
			// check to see if target node is found
		}
		if (!visited[to]) {System.out.println("NO RESULT"); return false;}
		LinkedList<GraphEdge> path = new LinkedList<GraphEdge>();
		//Traceback
		GraphEdge e = edges.get(edges.size() - 1);
		path.add(e);
		while (e.from() != from) {
			for (int i = 0; i < edges.size(); i++){
				if (edges.get(i).to() == e.from()){
					e = edges.get(i);
					path.add(e);
					break;
				}
			}
		}

		//iterate over the backtracked path in reverse order
		System.out.println("PATH FOUND: ");
		System.out.print((path.get(path.size() - 1).from() + 1) + "-->");
		for (int i = path.size() - 1; i > -1; i--) {
			GraphEdge ge = path.get(i);
			System.out.print((ge.to() + 1) + "-->");
		}
		System.out.println();

		return true;
	} 

	public void DFS(int from, int to) {
		from -= 1; to -= 1;
		Stack<GraphNode> stack = new Stack<GraphNode>();
		//initializes to all false
		boolean[] visited = new boolean[instance.numberOfNodes()];
		//initialize from node
		stack.add(instance.getNode(from));
		while (stack.size() != 0) {
			//pop off the first item of the stack and expand it
			GraphNode fn  = stack.pop();
			System.out.println(fn);
			visited[fn.index()] = true;
			Iterator<GraphEdge> iterator = instance.edgeIterator(fn.index());
			while (iterator.hasNext()) {
				GraphEdge e = iterator.next();
				if (!visited[e.to()]) {
					stack.add(instance.getNode(e.to()));
				}
			}
			System.out.println(stack);
			if (stack.contains(instance.getNode(to))) break;	
		}
	}

    public static class Test extends UnitTest{
		public void testGraph() {
			MyGraph mygraph = new MyGraph(false);
			mygraph.setup();

		}

		public void testNodeCreation() {
			// to create GraphNode just pass in an index BUT!!
			// beware you need to do this differently when you are 
			// creating a node to be used in a graph
			GraphNode dummyNode = new GraphNode(0);
			assertThat("dummyNode has the wrong index", dummyNode.index(),equalTo(0));;
			
			// It is better to get the index from the graph that you are going 
			// to use the node in
			
			//create a directed graph
			//
			SparseGraph graph = new SparseGraph(true);
			
			// create a node by calling SparseGraph::getNextFreeNodeIndex
			// to get the Node Index to create the node with...
			GraphNode n0 = new GraphNode(graph.getNextFreeNodeIndex());
			
			// ... adding the node to the graph will update the Node index
			graph.addNode(n0); 
			
			//... repeat to add more nodes...of course you could just do this in loop
			GraphNode n1 = new GraphNode(graph.getNextFreeNodeIndex()); 
			// ... adding the node to the graph will update the Node index
			graph.addNode(n1); 
			
			assertThat("node 0 has the wrong index", n0.index(),equalTo(0));
			assertThat("node 1 has the wrong index", n1.index(),equalTo(1));

//			GraphNode[] nodes = new GraphNode[8];
//			int i = 0;
//			
//			for(GraphNode n : nodes) {
//				nodes[i] = new GraphNode(testGraph.getNextFreeNodeIndex());
//				testGraph.addNode(nodes[i]);
//				i++;
//			}
//			
//			// test to make sure that the id of a node is correct 
//			i = 0;
//			for(GraphNode n : nodes) {
//				assertThat(String.format("index error: %s", n),n.index(), equalTo(i++));
//			}
//			
//			// add 5 edges; should result in 10 edges in undirected graph
//			int added = 0;
//			testGraph.addEdge(new GraphEdge(1,6)); added++;
//			testGraph.addEdge(new GraphEdge(3,4)); added++;
//			testGraph.addEdge(new GraphEdge(2,4)); added++;
//			testGraph.addEdge(new GraphEdge(2,5)); added++;
//			testGraph.addEdge(new GraphEdge(5,7)); added++;
//			
//			assertThat(String.format("Undirected graph; expected %d edges actual %d edges",12,6),
//					testGraph.numberOfEdges(),equalTo(2*added));
//			
//			// check that 2 directional edge was added
//			GraphEdge test1 = testGraph.getEdge(1, 6);
//			GraphEdge test2 = testGraph.getEdge(6, 1);
//			assertThat( test1.from == test2.to && test1.to == test2.from, equalTo(true));			
		}
		
		/**
		 * 
		 */
		public void testDirectedGraphEdgeCreation() {
			//create a directed graph
			SparseGraph graph = new SparseGraph(true);
			
			// create 3 nodes
			int nodeCount = 3;			
			for(int i=0; i < nodeCount; ++i) {
				graph.addNode(new GraphNode(graph.getNextFreeNodeIndex()));
			}
			
			// we should be able to create edges between nodes with id's of
			// 0,1,2
			int added = 3;
			graph.addEdge(new GraphEdge(0,1)); 
			graph.addEdge(new GraphEdge(2,0)); 
			graph.addEdge(new GraphEdge(2,1)); 
			
			assertThat(graph.getEdge(0, 1).from(),equalTo(0));
			assertThat(graph.getEdge(0, 1).to(),equalTo(1));
			assertThat(graph.getEdge(2,0).from(),equalTo(2));
			assertThat(graph.getEdge(2,0).to(),equalTo(0));
			assertThat(graph.getEdge(2,1).from(),equalTo(2));
			assertThat(graph.getEdge(2,1).to(),equalTo(1));

			// this is a directed graph so the edges are one directional only 
			assertThat(graph.getEdge(1,0), is(nullValue()));
			assertThat(graph.getEdge(0,2), is(nullValue()));
			assertThat(graph.getEdge(1,2), is(nullValue()));

			// this is how you can iterate the edges coming from a node
			Iterator<GraphEdge> edgeIt = graph.edgeIterator(2);
			
			// verify that there are 2 connections coming off of 2
			while(edgeIt.hasNext()) {
				GraphEdge e = edgeIt.next();
				assertThat( e.from(), equalTo(2));
				assertThat( e.to(), anyOf(equalTo(1), equalTo(0)));
			}
		}
        public void testUndirectedGraphEdgeCreation() {
			//create a directed graph
			SparseGraph graph = new SparseGraph(false);
			
			// create 3 nodes
			int nodeCount = 3;			
			for(int i=0; i < nodeCount; ++i) {
				graph.addNode(new GraphNode(graph.getNextFreeNodeIndex()));
			}
			
			// we should be able to create edges between nodes with id's of
			// 0,1,2
			int added = 3;
			graph.addEdge(new GraphEdge(0,1)); 
			graph.addEdge(new GraphEdge(2,0)); 
			graph.addEdge(new GraphEdge(2,1)); 
			
			// these are the same as the directed test...
			assertThat(graph.getEdge(0, 1).from(),equalTo(0));
			assertThat(graph.getEdge(0, 1).to(),equalTo(1));
			assertThat(graph.getEdge(2,0).from(),equalTo(2));
			assertThat(graph.getEdge(2,0).to(),equalTo(0));
			assertThat(graph.getEdge(2,1).from(),equalTo(2));
			assertThat(graph.getEdge(2,1).to(),equalTo(1));
			
			// ... these are the one automagicall created because this 
			// is an undirected graph
			assertThat(graph.getEdge(1,0).from(),equalTo(1));
			assertThat(graph.getEdge(1,0).to(),equalTo(0));
			assertThat(graph.getEdge(0,2).from(),equalTo(0));
			assertThat(graph.getEdge(0,2).to(),equalTo(2));
			assertThat(graph.getEdge(1,2).from(),equalTo(1));
			assertThat(graph.getEdge(1,2).to(),equalTo(2));


			// this is how you can iterate the edges coming from a node
			Iterator<GraphEdge> edgeIt = graph.edgeIterator(2);
			
			// verify that there are 2 connections coming off of 2
			while(edgeIt.hasNext()) {
				GraphEdge e = edgeIt.next();
				assertThat( e.from(), equalTo(2));
				assertThat( e.to(), anyOf(equalTo(1), equalTo(0)));
			}			
		}
    }
   

}

