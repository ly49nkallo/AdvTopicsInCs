package studio.itrack.buckland.common.graph;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import studio.itrack.buckland.common.graph.Homework4.StringNodeSparseGraph;

import java.util.Iterator;
import java.lang.Comparable;

public class SearchAlogorithms {
    public SearchAlogorithms(){}
    
    public static boolean BFS(int from, int to, SparseGraph instance){
		
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
					System.out.print(focusedNode.index());
					System.out.print(" --> ");
					System.out.println(e.to());
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
		boolean x = false; // boolean to store whether a path was found default false
		for (GraphEdge a : edges){
			if (a.to() == to) x = true;
		}
		//iterate over the backtracked path in reverse order
		if (x) {
			System.out.println("PATH FOUND: ");
			System.out.print((path.get(path.size() - 1).from() ) + "-->");
			for (int i = path.size() - 1; i > -1; i--) {
				GraphEdge ge = path.get(i);
				System.out.print((ge.to()) + "-->");
			}
			System.out.println();
		} else {
			System.out.println("NO PATH FOUND");
		}
		return x;
	} 
	public static boolean DFS(int from, int to, SparseGraph instance) {
		
		Stack<GraphNode> stack = new Stack<GraphNode>();
		LinkedList<GraphEdge> edges = new LinkedList<GraphEdge>();
		//initializes to all false
		boolean[] visited = new boolean[instance.numberOfNodes()];
		//initialize from node
		stack.add(instance.getNode(from));
		while (stack.size() != 0) {
			//pop off the first item of the stack and expand it
			GraphNode fn  = stack.pop();
			System.out.println("expanding node: " + (int)(fn.index() + 1));
			visited[fn.index()] = true;
			//iterate over all children and add then to the stack
			Iterator<GraphEdge> iterator = instance.edgeIterator(fn.index());
            GraphEdge previousEdge = null;
			while (iterator.hasNext()) {
				GraphEdge e = iterator.next();
                //check for case where we immediately loop back
                if (previousEdge != null) if (e.to() == previousEdge.from()) continue;
				if (!visited[e.to()]) {
					stack.add(instance.getNode(e.to()));
					edges.add(e);
				}
                previousEdge = e;
			}
			//System.out.println(stack);
			//System.out.println(visitedEdges);
			if (stack.contains(instance.getNode(to))) break;	
		}
		//TODO: fix
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
		boolean x = false; // boolean to store whether a path was found default false
		for (GraphEdge a : edges){
			if (a.to() == to) x = true;
		}
		//iterate over the backtracked path in reverse order
		if (x) {
			System.out.println("PATH FOUND: ");
			System.out.print((path.get(path.size() - 1).from()) + "-->");
			for (int i = path.size() - 1; i > -1; i--) {
				GraphEdge ge = path.get(i);
				System.out.print((ge.to()) + "-->");
			}
			System.out.print(to);
			System.out.println();
		} else {
			System.out.println("NO PATH FOUND");
		}
		return x;
		
	}
	public static boolean Dijkstra(int from, int to, SparseGraph instance) {
		PriorityQueue<GraphEdge> q = new PriorityQueue<GraphEdge>();
		double[] distance = new double[instance.numberOfNodes()];
		int[] parent = new int[instance.numberOfNodes()];
		for (int i = 0; i < distance.length; ++i) {distance[i] = Double.MAX_VALUE; parent[i] = -1;}
		distance[from - 1] = 0d;
		//initialize the queue
		//find the shortest path for each vertice
		for (int i = 0; i < instance.numberOfNodes(); ++i) {
			
		}
		return true;
	}
	private GraphEdge minDistance(int[] distance, Queue<GraphEdge> queue, int source) {
		double min = Double.MIN_VALUE;
		for (GraphEdge e : queue) {
			
		}
	}
	public static boolean Dijkstra (int from, int to, StringNodeSparseGraph instance) {return false;}
	public static boolean AStar() {return false;}
}
