package studio.itrack.buckland.common.graph;

import java.lang.Comparable;//
//An edge has an associated cost.
public class GraphEdge implements Comparable<GraphEdge>{
	protected int from,  to;
	protected double cost;
	
	public GraphEdge(int from, int to, double cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}

	@Override
	public int compareTo(GraphEdge other){
		//if (Math.abs(this.cost() - other.cost()) < 0.001) return 0;
		//System.out.println((this.cost()+' '+ other.cost()));
		//System.out.println(Double.compare(this.cost(), other.cost()));
		return Double.compare(this.cost(), other.cost());
	}
	
	public GraphEdge(int from, int to) {
		this(from,to,0);
	}
	
	public GraphEdge(double cost) {
		this(0,0,cost);
	}
	
	/**
	 * Copy Constructor
	 * @param other
	 */
	public GraphEdge(GraphEdge other) {
		this(other.from, other.to, other.cost);
	}

	
	public GraphEdge copy() {
		return new GraphEdge(this);
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) {
			return true;
		}
		
		if(!(other instanceof GraphEdge)) {
			return false;
		}
		
		GraphEdge o = (GraphEdge)other;
		return from == o.from && to == o.to && cost == o.cost;
	}
	
	// Getters and Setters
	public int from() { return from; }
	public int to()   { return to; }
	public double cost() { return cost; }
	public void setFrom(int x) { from = x; }
	public void setTo(int x) { to = x; }
	public void setCost(double x) { cost = x; }
	
	// Generic writer to output GraphEdge to different locations
	//TODO Implement write for graph loading and saving
//	public static PrintStream write(OutputStream stream, GraphEdge e) {		
//		PrintStream out = new PrintStream(stream);
//		out.println(e);
//		return out;
//	}
	
	public String toString() {
		return String.format("{%d->%d,(cost:%.2f)}",from,to,cost);
	}
}
