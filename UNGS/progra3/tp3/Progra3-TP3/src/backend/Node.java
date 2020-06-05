package backend;

import java.util.ArrayList;

public class Node {
	
	//this is the number of the node, it's used like an id for identify the neighbors nodes
	private int number;
	//weight of the node
	private double weight;
	// it's an arrayList of neighbors nodes, each element of the arrayList it's the number (id) of the node
	private ArrayList<Integer> neighbors;
	//it's for initialize the numbers
	static private int cont = 0;

	Node(double weight) {
		
		this.weight = weight;
		this.number = ++cont;
		this.neighbors = new ArrayList<>();
	}
	
	//getter weight
	public double getWeight() {
		double ret = this.weight;
		return ret;
	}
	
	//getter number
	public int getNumber() {
		int ret = this.number;
		return ret;
	}
	
	//getter neighbors
	public ArrayList<Integer> getNeighbors() {
		ArrayList<Integer> ret = this.neighbors;
		return ret;
	}
	
	//add neighbor
	public void addNeighbor(Node node) {
		if (!this.neighbors.contains(node.getNumber())) {
			this.neighbors.add(node.getNumber());
			node.addNeighbor(this);
		}
	}

	//this method returns if the actual node is neighbor of all of the nodes in the arrayList of the solution)
	public boolean isNeighborOfAll(Solution solution) {
		
		return solution.getNodes()
							.stream()
						    .allMatch(n -> n.getNeighbors().contains(this.number));
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		
		ret.append("[ Node: "+Integer.toString(this.number)+" ,Weight: "+ Double.toString(this.weight)+" ]");
		return ret.toString();
	}

}
