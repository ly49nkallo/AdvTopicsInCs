package studio.itrack.buckland.common.graph;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;


import studio.itrack.buckland.common.misc.Utils;


public abstract class AbstractSparseGraph<NodeType extends GraphNode,EdgeType extends GraphEdge>  {
	public static final int INVALID_NODE_INDEX = -1;
	
	//the nodes that comprise this graph
	protected ArrayList<NodeType> nodes;

	//a vector of adjacency edge lists. (each node index keys into the 
	//list of edges associated with that node)
	ArrayList<ArrayList<EdgeType>> edges;
	
	//is this a directed graph?
	boolean isDirected;

	//the index of the next node to be added
	protected int nextNodeIndex = 0;
	
	/*
	 * returns true if an edge is not already present in the graph. Used
  	 * when adding edges to make sure no duplicates are created.
	 */
	private boolean isUniqueEdge(int from, int to) {
		Iterator<EdgeType> iter = edges.get(from).iterator();
		
		while(iter.hasNext()) {
			EdgeType e = iter.next();
			if(e.to == to) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * iterates through all the edges in the graph and removes any that point
	 * to an invalidated node
	 */
	private void cullInvalidEdges() {
		Iterator<ArrayList<EdgeType>> edgeListIter = edges.iterator();
		
		while(edgeListIter.hasNext()) {
			ArrayList<EdgeType> currEdgeList = edgeListIter.next();
			Iterator<EdgeType> currListIter = currEdgeList.iterator();
			while(currListIter.hasNext()) {
				EdgeType currEdge = currListIter.next();
				if(currEdge.to() == INVALID_NODE_INDEX || currEdge.from() == INVALID_NODE_INDEX) {
					currEdgeList.remove(currEdge);
				}
			}
		}
	}
	
	/**
	 * Create new Sparse Graph
	 * @param directed true if graph is a DAG
	 */
	public AbstractSparseGraph(boolean directed) {
		isDirected = directed;
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	/**
	 * Get node from an index
	 * @param i index of node
	 * @return NodeType or null if not found
	 */
	public NodeType getNode(int i) {
		assert(Utils.isInRange(i, 0, nodes.size())) :
			String.format("node %d is out of range, nodeSize = %d", i, nodes.size());
		return nodes.get(i);
	}
	
	/**
	 * Get edge connecting specified locations
	 * @param from origin of edge
	 * @param to destination of edge
	 * @return EdgeType or null if not found
	 */
	public EdgeType getEdge(int from, int to) {
		assert(Utils.isInRange(from, 0, nodes.size()) && 
			   Utils.isInRange(to, 0, nodes.size())) : 
			   String.format(" a node in edge %d->%d is out of node range. Nodes size %d",
					   		 from,to, nodes.size());
		
		assert(from != INVALID_NODE_INDEX) : String.format("index from in %d->%d, is an invalid index", from, to);
		assert(to != INVALID_NODE_INDEX) : String.format("index to in %d->%d,, is an invalid index", from, to);
		
		Iterator<EdgeType> it = edges.get(from).iterator();
		
		while(it.hasNext()) {
			EdgeType e = it.next();
			if(e.to == to) {
				return e;
			}
		}
		
		System.err.println(String.format("could not get edge %d->%d. Not found",from,to));
		return null;		
	}
	
	/**
	 * 
	 * @return retrieves the next free node index
	 */
	public int getNextFreeNodeIndex() {
		return nextNodeIndex;
	}
	
	/**
	 * Given a node this method first checks to see if the node has been added
	 * previously but is now innactive. If it is, it is reactivated.
	 * 
	 * If the node has not been added previously, it is checked to make sure its
	 * index matches the next node index before being added to the graph	  
	 * @param node
	 * @return index of node added
	 */
	public int addNode(NodeType node) {
		if (node.index() < nodes.size()){
			//make sure the client is not trying to add a node with the same ID as
		    //a currently active node
		    if(nodes.get(node.index()).index() == INVALID_NODE_INDEX) {		      
		    	System.out.println("<SparseGraph::AddNode>: Attempting to add a node with a duplicate ID");
		    
		      nodes.set(node.index(), node);

		      // the nextNodeIndex is still the same
		      return nextNodeIndex;
		    }
		} else {
		    //make sure the new node has been indexed correctly
		    if(node.index() == nextNodeIndex) {
		    	nodes.add(node);
		    	edges.add(new ArrayList<EdgeType>());
		    	
		    	// return the nodeIndex and advance it
		    	return nextNodeIndex++;
		    } else {
		    	System.out.println("<SparseGraph::AddNode>: invalid node id; id should be equal to nextNodeId");
		    }
		 }
	  	return nextNodeIndex;
	}
	
	/**
	 * Removes a node from the graph and removes any links to neighbouring nodes
	 * Note removal is just setting the id to INVALID_INDEX. A removed node can be
	 * re-added
	 * @param index of node to retrieve
	 */
	public void removeNode(int index) {
		
		if(index >= nodes.size()) {
			System.out.println("<SparseGraph::RemoveNode>: invalid node index, " + index);
			return;
		}
		
		//set this node's index to invalid_node_index
		nodes.get(index).setIndex(AbstractSparseGraph.INVALID_NODE_INDEX);
		
		//if the graph is not directed remove all edges leading to this node and then
		//clear the edges leading from the node
		if (!isDirected) {   
			
		    //visit each neighbor and erase any edges leading to this node
			ArrayList<EdgeType> neighborEdges = edges.get(index);
			Iterator<EdgeType> edgesFrom = neighborEdges.iterator();
			
			while(edgesFrom.hasNext()) {
				// this is an edge From the node we want to delete to a neighbor...
				EdgeType neighborEdge = edgesFrom.next();
				//... for that neighbor get all of its edges...
				Iterator<EdgeType> edgesTo = edges.get(neighborEdge.to()).iterator();
				
				while(edgesTo.hasNext()) {
					// this is the edge from the Neighbor to another node
					// check to see if the To node for this edge is the node
					// that we want to delete...
					EdgeType currEdge = edgesTo.next();
					
					if(currEdge.to() == index) {
						// remove this edge from the list for this node
						edges.get(currEdge.from).remove(currEdge);						
						break;
					}					
				}
			}
			//finally, clear this node's edges
		    edges.get(index).clear();
		  }		
		  //if a digraph remove the edges the slow way
		  else
		  {
		    cullInvalidEdges();
		  }		
		  
	}
	
	/** 
	 * Use this to add an edge to the graph. The method will ensure that the
	 * edge passed as a parameter is valid before adding it to the graph. If the
	 * graph is a digraph then a similar edge connecting the nodes in the opposite
	 * direction will be automatically added.
	 * 
	 * @param edge
	 * 
	 */ 
	public void addEdge(EdgeType edge) {
		// first make sure the from and to nodes exist within the graph 
		
		try {
			assert Utils.isInRange(edge.from(),0, nextNodeIndex) :
					String.format("edge %s from is out of range",edge.from());
			
			assert Utils.isInRange(edge.to(),0, nextNodeIndex) :
				String.format("edge %s to is out of range",edge.from());
		} catch(AssertionError e) {
			return;
		}
	
//		
//		if(! (edge.from() < nextNodeIndex) && (edge.to() < nextNodeIndex)) {
//			String err = String.format("<SparseGraph::AddEdge>: edge %s has an invalid node index. Both indices shoud be less than %d",
//										edge,nextNodeIndex);
//			System.err.println(err);
//			return;
//		}		        

		// make sure both nodes are active before adding the edge		
		if ( (nodes.get(edge.from()).index() != INVALID_NODE_INDEX) && 
		     (nodes.get(edge.to()).index()   != INVALID_NODE_INDEX)) {
		  // add the edge, first making sure it is unique
		  if (isUniqueEdge(edge.from(), edge.to())) {
		    edges.get(edge.from()).add(edge);		    
		  }

		  // if the graph is undirected we must add another 
		  // connection in the opposite direction
		  if (!isDirected) {
		    //check to make sure the edge is unique before adding
		    if (isUniqueEdge(edge.to(),edge.from())) {
	    		EdgeType newEdge = createEdge(edge.to(), edge.from(), edge.cost()); 		    			    		
	    		edges.get(edge.to()).add(newEdge);		    		
	    	
		    }
		  }
		}		
	}
	
	abstract EdgeType createEdge(int from, int to, double cost);

	/**
	 * removes the edge connecting from and to from the graph (if present). If
	 *	a digraph then the edge connecting the nodes in the opposite direction 
	 *	will also be removed. 
	 * @param from
	 * @param to
	 */	
	public void removeEdge(int from, int to) {
		 
	}

	/**
	 * sets the cost of an edge
	 * @param from
	 * @param to
	 * @param cost
	 */
	public void setEdgeCost(int from, int to, double cost) {
		
	}
	
	/**
	 * returns the number of active + inactive nodes present in the graph
	 * @return
	 */
	public int nodeCount(){
		return nodes.size();
	}

	/**
	 * returns the number of active nodes present in the graph (this method's
	 * performance can be improved greatly by caching the value)
	 * @return
	 */
	public int activeNodeCount() {	  
	  return nodes
	  	.stream()
	  	.filter(n -> n.index() != INVALID_NODE_INDEX)
	  	.collect(Collectors.toList())
	  	.size();
	  
	}	
	
	public int numberOfEdges() {
		int total = 0;
		
		Iterator<ArrayList<EdgeType>> iter = edges.iterator();
		
		while(iter.hasNext()) {
			total += iter.next().size();
		}
		
		return total;
	}
	
	/**
	 * returns the number of active + inactive nodes present in the graph
	 * @return
	 */
	public int numberOfNodes(){
		return nodes.size();
	}

	/**
	 * returns the number of active nodes present in the graph (this method's
	 * performance can be improved greatly by caching the value)
	 * @return
	 */
	public int numberActiveNodes() {	  
	  return nodes
	  	.stream()
	  	.filter(n -> n.index() != INVALID_NODE_INDEX)
	  	.collect(Collectors.toList())
	  	.size();
	}
	
	public Iterator<EdgeType> edgeIterator(int nodeIndex) {
		return edges.get(nodeIndex).iterator();
	}
	
	public String toString() {
		String s = "{";
		for(ArrayList<EdgeType> es : edges) {
			
			for(EdgeType e : es) {
				s += String.format("\n   %s", e);
			}
		}
		s += "}";
		return s;
	}
}
