package studio.itrack.buckland.common.graph;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Iterator;
import studio.itrack.buckland.common.graph.SparseGraph;

public class SearchAlogorithms {
    public SearchAlogorithms(){
    }
    
    public static boolean BFS(int from, int to, SparseGraph instance){
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
		boolean x = false; // boolean to store whether a path was found default false
		for (GraphEdge a : edges){
			if (a.to() == to) x = true;
		}
		//iterate over the backtracked path in reverse order
		if (x) {
			System.out.println("PATH FOUND: ");
			System.out.print((path.get(path.size() - 1).from() + 1) + "-->");
			for (int i = path.size() - 1; i > -1; i--) {
				GraphEdge ge = path.get(i);
				System.out.print((ge.to() + 1) + "-->");
			}
			System.out.println();
		} else {
			System.out.println("NO PATH FOUND");
		}
		return x;
	} 

	public static boolean DFS(int from, int to, SparseGraph instance) {
		from -= 1; to -= 1;
		Stack<GraphNode> stack = new Stack<GraphNode>();
		LinkedList<GraphEdge> edges = new LinkedList<GraphEdge>();
		//initializes to all false
		boolean[] visited = new boolean[instance.numberOfNodes()];
		//initialize from node
		stack.add(instance.getNode(from));
		while (stack.size() != 0) {
			//pop off the first item of the stack and expand it
			GraphNode fn  = stack.pop();
			System.out.println(fn);
			visited[fn.index()] = true;
			//iterate over all children and add then to the stack
			Iterator<GraphEdge> iterator = instance.edgeIterator(fn.index());
			while (iterator.hasNext()) {
				GraphEdge e = iterator.next();
				if (!visited[e.to()]) {
					stack.add(instance.getNode(e.to()));
					edges.add(e);
				}
			}
			//System.out.println(stack);
			//System.out.println(visitedEdges);
			if (stack.contains(instance.getNode(to))) break;	
		}
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
			System.out.print((path.get(path.size() - 1).from() + 1) + "-->");
			for (int i = path.size() - 1; i > -1; i--) {
				GraphEdge ge = path.get(i);
				System.out.print((ge.to() + 1) + "-->");
			}
			System.out.print(to + 1);
			System.out.println();
		} else {
			System.out.println("NO PATH FOUND");
		}
		return x;
		
	}
}
