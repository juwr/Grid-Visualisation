package core;

import java.util.ArrayList;

import gui.GUI;

public class Algorithm {
	
	public History history;
	public Grid grid;
	
	public ArrayList<Node> open;
	public ArrayList<Node> closed;
	public ArrayList<Node> path;
	
	public boolean path_found;
	public boolean finished;
	
	protected GUI gui;
	
	public Algorithm(Grid grid, GUI gui) {
		this.grid = grid;
		this.gui = gui;
		this.history = new History(gui, this);
		
		this.path_found = false;
		this.finished = false;
		
		this.open = new ArrayList<Node>();
		this.closed = new ArrayList<Node>();
		this.path = new ArrayList<Node>();
		this.gui.history = this.history;
	}
	
	protected void saveImage() {
		Image image = new Image(this.grid, this.open, this.closed, this.path);
		
		this.history.addImage(image);
		
		pushImage(image);
	}
	
	protected void pushImage(Image image) {
		this.gui.displayImage(image);
	}
	
}
