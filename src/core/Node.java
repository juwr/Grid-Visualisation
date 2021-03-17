package core;

import java.util.ArrayList;

public class Node {

	public int x;
	public int y;
	
	public Structs.NodeType nodeType;
	
	public Node parent;
	public int f_cost;
	
	public ArrayList<Node> neighbours;
	
	Node(int x, int y, Structs.NodeType nodeType) {
		this.x = x;
		this.y = y;
		this.nodeType = nodeType;
		this.parent = null;
		this.f_cost = 0;
		this.neighbours = new ArrayList<Node>();
	}
	
	public boolean isNode(Node node) {
		if (node.x == this.x && node.y == this.y) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isNeighbour(Node node) {
		if ((node.x == this.x) && (node.y == this.y + 1 || node.y == this.y - 1)) {
			return true;
		} else if ((node.y == this.y) && (node.x == this.x + 1 || node.x == this.x - 1)) {
			return true;
		} else if ((node.x == this.x + 1) && (node.y == this.y + 1 || node.y == this.y - 1)) {
			return true;
		} else if ((node.x == this.x - 1) && (node.y == this.y + 1 || node.y == this.y - 1)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equals(Node node) {
		if (this.x == node.x && this.y == node.y) {
			return true;
		} else {
			return false;
		}
	}
	
}
