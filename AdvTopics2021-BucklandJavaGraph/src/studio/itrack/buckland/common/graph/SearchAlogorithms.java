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
		System.out.println("BFS from " + from + " to " + to);

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
		System.out.println("\n--------------------------");
		return x;
	} 
	public static boolean DFS(int from, int to, SparseGraph instance) {
		System.out.println("DFS from " + from + " to " + to);
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
		System.out.println("\n--------------------------");
		return x;
		
	}
	public static boolean Dijkstra(int from, int to, SparseGraph instance) {
		//PriorityQueue<GraphEdge> q = new PriorityQueue<GraphEdge>();
		System.out.println("Dijkstra from " + from + " to " + to);
		ArrayList<GraphNode> unvisitedNodes = new ArrayList<GraphNode>();
		for (GraphNode n : instance.nodes)  {if (n == instance.getNode(from)) continue; if (n.index == 0) continue; unvisitedNodes.add(n);}

		boolean[] visited = new boolean[instance.numberOfNodes()];
		double[] distance = new double[instance.numberOfNodes()];

		int[] parent = new int[instance.numberOfNodes()];
		//initialize values
		for (int i = 0; i < distance.length; ++i) {distance[i] = Double.MAX_VALUE; parent[i] = -1; parent[from] = from;}
		distance[from] = 0d;
		GraphNode currentNode = null;
		//find the shortest path for each vertice
		while(unvisitedNodes.size() > 0) {
			//System.out.println("UnvisitedNodes: " + unvisitedNodes);
			if (mini(distance, visited) == -1) {System.out.println("NO PATH FOUND"); return false;}
			//if (currentNode != null) parent[mini(distance, visited)] = currentNode.index();
			currentNode = instance.getNode(mini(distance, visited));
			//System.out.println("CurrentNode: " + currentNode);
			visited[currentNode.index()] = true;
			unvisitedNodes.remove(currentNode);
			//find add the edges of the node to the priority queue
			Iterator<GraphEdge> Iterator = instance.edgeIterator(currentNode.index());
			while (Iterator.hasNext()) {
				GraphEdge edge = Iterator.next();	
				//System.out.println(edge);
				if (!visited[edge.to()]) {
					//q.add(edge);
					double newCost = distance[currentNode.index()] + edge.cost();
					// if the new cost is better, reassign the parent as well as the tracked distance
					if (newCost < distance[edge.to()]) {
						distance[edge.to()] = newCost;
						parent[edge.to()] = currentNode.index();
					}
				}
			}
			//for (int i = 0; i < distance.length; i++) System.out.println(i + ":" + distance[i] + ",");
			//if we find the node we want, break out of loop
			if (visited[to]) break;
		}
		// print out information we found
		for (int i = 0; i < distance.length; i++) if (distance[i] != Double.MAX_VALUE) System.out.println("Distance from " + i + ":" + distance[i] + ",");
		System.out.println("Parents");
		for (int i = 0; i < parent.length; i++) if (parent[i] != -1) System.out.println(i + " is a child of " + parent[i] + ",");

		//traceback using parent list
		ArrayList<Integer> invertedPath = new ArrayList<Integer>();
		int ni = to;
		while (ni != from) {
			invertedPath.add(ni);
			ni = parent[ni];
			if (ni == -1) {System.out.println("hmmm..."); return false;}
		}
		invertedPath.add(from);

		System.out.println("------PATH------");
		//print the last (first) element of the path
		System.out.print(invertedPath.get(invertedPath.size() - 1));
		//iterate in reverse order to display the found path
		for (int i = invertedPath.size() - 2; i > -1; i--){
			System.out.print("-->" + invertedPath.get(i));
		}
		
		System.out.println("\n--------------------------");
		return true;
	}
	// find least value in a array of doubles (there's probably a java standard implementation but i'm too lazy to find out)
	private static int mini(double[] a, boolean[] visited){
		double min = Double.MAX_VALUE;
		int z = -1;
		for (int i = 0; i < a.length; i++) {
			if (a[i] <= min && !visited[i]) {
				z = i;
				min = a[i];
			}
		}
		// if the smallest value is not found
		if (min == Double.MAX_VALUE) return -1;
		//System.out.println(z);
		return z;
	}
	//private GraphNode getMinNode (boolean[] visited, double[] distance, int from, SparseGraph instance, P) {
		
	//}
	
	public static boolean Dijkstra (int from, int to, StringNodeSparseGraph instance) {return false;}
	public static boolean AStar() {return false;}
}
