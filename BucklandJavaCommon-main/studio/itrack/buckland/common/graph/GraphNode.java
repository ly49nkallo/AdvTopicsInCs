package studio.itrack.buckland.common.graph;

/**
 * class to represent graph nodes in algorithms
 * This is a bare bones node. Subclass this to add state information
 */
public class GraphNode {	
	protected int index;
	 
	public GraphNode(int i) {
		index = i;
	}
	
	public int index() {
		return index;
	}
	
	public void setIndex(int i) {
		index = i;
	}	  

	public String toString() {
	    return String.format("index: %d", index);
	}
}
