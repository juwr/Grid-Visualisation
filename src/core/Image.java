package core;

import java.awt.Color;
import java.util.ArrayList;

public class Image {
	
	private Color grid[][];
	
	public Image(Grid grid, ArrayList<Node> open, ArrayList<Node> closed, ArrayList<Node> path) {
		this.grid = new Color[grid.height][grid.width];
		
		init(grid, open, closed, path);
	}
	
	private void init(Grid grid, ArrayList<Node> open, ArrayList<Node> closed, ArrayList<Node> path) {
		
		for (int i = 0; i < grid.height; i++) {
			for (int j = 0; j < grid.width; j++) {
				Node current = grid.getNode(j, i);
				Color color;
				
				switch(current.nodeType) {
				
				case WALL:
					color = Color.BLACK;
					break;
				case OPEN:
					
					if (open.contains(current)) {
						color = Color.GREEN;
					} else if (closed.contains(current)) {
						color = Color.RED;
					} else {
						color = Color.WHITE;
					}
					
					if (path.contains(current)) {
						color = Color.MAGENTA;
					}
					
					if (current.equals(grid.start)) {
						color = Color.BLUE;
					} else if (current.equals(grid.goal)) {
						color = Color.CYAN;
					}
					break;
				default:
					color = Color.PINK;
				}
				
				
				this.grid[i][j] = color;
				
				//x = j;
				//y = i;
			}
		}
		
	}
	
	public void setColor(int x, int y, Color color) {
		this.grid[y][x] = color;
	}
	
	public Color getColor(int x, int y) {
		return this.grid[y][x];
	}
	
}
