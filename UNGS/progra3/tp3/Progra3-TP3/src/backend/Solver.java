package backend;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;

public class Solver {
	
	private Graph graph;
	
	//the parameter of the initial instance is a Graph
	public Solver(Graph graph) {
		
		this.graph = graph;
	}

	//this method runs the sorted arraylist and applies the greedy algorithm
	// if the node is neighbor of all in the solution then the algorithm add the node
	public Solution solution() {
		
		Solution ret = new Solution();
		ArrayList<Node> nodes = this.graph.getNodes();
		
				nodes
					.stream()
				    .sorted((one, other) -> (-(int)one.getWeight()+(int)other.getWeight()))
				    .filter(p -> p.isNeighborOfAll(ret))
				    .forEach(ret::addNode);	
		return ret;	
	}
}
