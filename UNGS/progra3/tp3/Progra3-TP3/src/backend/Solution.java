package backend;

import java.util.ArrayList;

public class Solution {
	
	private ArrayList<Node> nodes;
	
	//it's the return of the solver
	public Solution() {
		
		this.nodes = new ArrayList<>();
	}
	
	//add node to the solution
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
	//getter of the arrayList of solution
	@SuppressWarnings("unchecked")
	public ArrayList<Node> getNodes(){
		
		return (ArrayList<Node>) this.nodes.clone();
	}
	
	//getter of the total weight in the arrayList result
	public double getWeight() {
		
		return this.nodes.stream()
				   .mapToDouble(Node::getWeight)
				   .sum();
	}
	
	//this method says if the solution contains a specified number 
	// I use this method for testing the object solver
	public boolean containsNodeNumber(int number) {
	
		return this.nodes
					.stream()
					.anyMatch(n -> n.getNumber()==number);
	}

}
