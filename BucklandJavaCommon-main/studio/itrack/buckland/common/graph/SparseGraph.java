package studio.itrack.buckland.common.graph;


import com.eckel.UnitTest;


import static org.hamcrest.MatcherAssert.*;
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
		
		
		public void testAutoIndexing() {
			SparseGraph testGraph = new SparseGraph(true);
			
			int id = 0;
			int count = 10;			
			
			for(int i=0; i < count; ++i) {
				testGraph.addNode(new GraphNode(testGraph.getNextFreeNodeIndex()));			
			}
						
			try {
				int actual = testGraph.numberActiveNodes();
				int expected = count;
				assertThat(String.format("node count expected: %d actual: %d",expected,actual),expected,equalTo(actual));

				// total nodes should still be the same but activeNodeCount should be 9
				testGraph.removeNode(3);
				expected = 9;
				actual = testGraph.numberActiveNodes();
				assertThat(String.format("node count expected: %d actual: %d",expected,actual),expected,equalTo(actual));				
				
			} catch(AssertionError e) {
				System.err.println(e);
			}
		}
		
		public void testUndirectedCreation() {
			//undirected
			SparseGraph testGraph = new SparseGraph(false);
			GraphNode[] nodes = new GraphNode[8];
			int i = 0;
			
			for(GraphNode n : nodes) {
				nodes[i] = new GraphNode(testGraph.getNextFreeNodeIndex());
				testGraph.addNode(nodes[i]);
				i++;
			}
			
			// test to make sure that the id of a node is correct 
			i = 0;
			for(GraphNode n : nodes) {
				assertThat(String.format("index error: %s", n),n.index(), equalTo(i++));
			}
			
			// add 5 edges; should result in 10 edges in undirected graph
			int added = 0;
			testGraph.addEdge(new GraphEdge(1,6)); added++;
			testGraph.addEdge(new GraphEdge(3,4)); added++;
			testGraph.addEdge(new GraphEdge(2,4)); added++;
			testGraph.addEdge(new GraphEdge(2,5)); added++;
			testGraph.addEdge(new GraphEdge(5,7)); added++;
			
			assertThat(String.format("Undirected graph; expected %d edges actual %d edges",12,6),
					testGraph.numberOfEdges(),equalTo(2*added));
			
			// check that 2 directional edge was added
			GraphEdge test1 = testGraph.getEdge(1, 6);
			GraphEdge test2 = testGraph.getEdge(6, 1);
			assertThat( test1.from == test2.to && test1.to == test2.from, equalTo(true));
		}
	
	}
}
