package core;

import java.util.ArrayList;

public class Grid {
	
	private Node[][] nodes;
	
	public Node start;
	public Node goal;
	
	public int width;
	public int height;
	
	
	
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		this.nodes = new Node[height][width];
		init();
	}
	
	
	
	private void init() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.nodes[i][j] = new Node(j, i, Structs.NodeType.OPEN);
			}
		}
	}
	
	public Node getNode(int x, int y) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
			return null;
		} else {
			return this.nodes[y][x];
		}
	}
	
	public ArrayList<Node> getNeighbours(Node node) {
		ArrayList<Node> result = new ArrayList<Node>();
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (!(i == 0 && j == 0)) {
					Node temp = this.getNode(node.x + j, node.y + i);
					if (temp != null) {
						result.add(temp);
					}
				}
			}
		}
		
		return result;
		
	}
}
