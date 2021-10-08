package studio.itrack.buckland.common.graph;


import com.eckel.UnitTest;


import static org.hamcrest.MatcherAssert.*;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.*;

public class SparseGraph extends AbstractSparseGraph<GraphNode,GraphEdge>  {
	
	public SparseGraph(boolean directed) {
		super(directed);
	}
	
	@Override
	public GraphEdge createEdge(int from, int to, double cost) {
		return new GraphEdge(from,to, cost);
	}

	
	/*
	 * Class for quick unit testing of the class functionality
	 * Must Use com.eckel.RunUnitTest to run the tests
	 * 
	 * TODO but this in the build file for easier running 
	 */
	public static class TestSparseGraph extends UnitTest {		
		
		/**
		 * 
		 */
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
