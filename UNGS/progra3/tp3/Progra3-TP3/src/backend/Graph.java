package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Graph {
	
	//instance of Graph is an arrayList of Nodes
	private ArrayList<Node> nodes;
	
	public Graph(){
		
		nodes = new ArrayList<Node>();
	}
	
	//return the nodes of the graph
	@SuppressWarnings("unchecked")
	public ArrayList<Node> getNodes(){
		return (ArrayList<Node>) this.nodes.clone();
	}
	
	//add specified node to the graph
	public void addNode(Node node) {
		if (!this.nodes.contains(node))
			this.nodes.add(node);
	}
	
	//generate and save JSON from the graph
	public void generateJSON(String archivo) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);
		
		try {
			FileWriter writer = new FileWriter (archivo);
			writer.write(json);
			writer.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//read the a JSON located in project
	public static Graph readJSON (String archivo) {
		
		Gson gson = new Gson();
		Graph ret = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			ret = gson.fromJson(br, Graph.class);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return ret;
	}
}
