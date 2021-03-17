package core;

import gui.GUI;

public class Astar extends Algorithm {
	
	public Astar(Grid grid, GUI gui) {
		super(grid, gui);
	}
	
	public void execute() {
		saveImage();
		this.open.add(grid.start);
		
		while(!path_found) {
			Node current = this.open.get(0);
			for (Node node : open) {
				if (node.f_cost < current.f_cost) {
					current = node;
				}
			}
			
			this.open.remove(current);
			this.closed.add(current);
			
			if (current.equals(this.grid.goal)) {
				this.path_found = true;
				
			} else {
				for (Node node : grid.getNeighbours(current)) {
					if (!(node.nodeType == Structs.NodeType.WALL || this.closed.contains(node))) {
						
						if(this.open.contains(node)) {
							if(calculateG(node, node.parent) > calculateG(node, current)) {
								node.parent = current;
								node.f_cost = calculateF(node);
							}
						} else {
							node.parent = current;
							node.f_cost = calculateF(node);
							this.open.add(node);
						}
						
					}
				}
			}
			
			saveImage();
			
		}
		
		//	Trace path back to starting node.
		Node temp = this.grid.goal;
		while(temp != null) {
			this.path.add(temp);
			temp = temp.parent;
			//saveImage();
		}
		saveImage();
		this.finished = true;
	}
	
	public void stepByStep() {
		saveImage();
		
		this.open.add(grid.start);
		
		if(this.gui.paused || this.gui.historic || this.gui.backwards) {
			while(this.gui.paused || this.gui.historic || this.gui.backwards) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
			}
		}
		
		while(!finished) {
			
			step();
			saveImage();
			
			if(this.gui.increment > 0) {
				try {
					Thread.sleep(this.gui.increment);
				} catch (InterruptedException e) {
					
				}
			}
			
			if(this.gui.paused || this.gui.historic || this.gui.backwards) {
				while(this.gui.paused || this.gui.historic || this.gui.backwards) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
					}
				}
			}
			
		}
		
		this.gui.setPaused(true);
		
		
		
	}
	
	private void step() {
		if(!path_found) {
			Node current = this.open.get(0);
			for (Node node : open) {
				if (node.f_cost < current.f_cost) {
					current = node;
				}
			}
			
			this.open.remove(current);
			this.closed.add(current);
			
			if (current.equals(this.grid.goal)) {
				this.path_found = true;
				
			} else {
				for (Node node : grid.getNeighbours(current)) {
					if (!(node.nodeType == Structs.NodeType.WALL || this.closed.contains(node))) {
						
						if(this.open.contains(node)) {
							if(calculateG(node, node.parent) > calculateG(node, current)) {
								node.parent = current;
								node.f_cost = calculateF(node);
							}
						} else {
							node.parent = current;
							node.f_cost = calculateF(node);
							this.open.add(node);
						}
						
					}
				}
			}
			
		} else {
			//	Trace path back to starting node.
			Node temp = this.grid.goal;
			while(temp != null) {
				this.path.add(temp);
				temp = temp.parent;
				//saveImage();
			}
			
			this.finished = true;
		}
	}
	
	public int calculateH(Node start, Node goal) {
		int cost = 0;
		
		int deltaX = Math.abs(start.x - goal.x);
		int deltaY = Math.abs(start.y - goal.y);
		
		if(deltaX > deltaY) {
			cost = deltaY * Structs.COST_DIAGONAL;
			deltaX = deltaX - deltaY;
			cost = cost + deltaX * Structs.COST_SIDE;
		} else {
			cost = deltaX * Structs.COST_DIAGONAL;
			deltaY = deltaY - deltaX;
			cost = cost + deltaY * Structs.COST_SIDE;
		}
		
		return cost;
	}
	
	public int calculateG(Node node, Node parent) {
		if (this.grid.start.equals(parent)) {
			return calculateH(node, parent);
		} else {
			return calculateH(node, parent) + calculateG(parent, parent.parent);
		}
	}
	
	public int calculateF(Node node) {
		return calculateH(node, this.grid.goal) + calculateG(node, node.parent);
	}
}
