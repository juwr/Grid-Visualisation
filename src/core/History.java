package core;

import gui.GUI;

import java.util.ArrayList;

public class History {
	
	public ArrayList<Image> history;
	public GUI gui;
	public Algorithm script;
	
	public History(GUI gui, Algorithm script) {
		this.history = new ArrayList<Image>();
		this.gui = gui;
		this.script = script;
	}
	
	public void addImage(Image image) {
		this.history.add(image);
	}
	
	public void cycleForward() {
		
		while(this.gui.historyIndex < this.history.size()-1) {


			if(this.gui.increment > 0) {
				try {
					Thread.sleep(this.gui.increment);
				} catch (InterruptedException e) {
					
				}
			}
			
			if(this.gui.paused || this.gui.backwards) {
				while(this.gui.paused || this.gui.backwards) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
					}
				}
				if(this.gui.historyIndex == this.history.size()-1) {
					this.gui.historic = false;
					return;
				}
			}
			
			this.gui.displayImage(this.history.get(this.gui.historyIndex + 1));
			
			
			
		}
		this.gui.historic = false;
		
		if(this.script.finished) {
			this.gui.setPaused(true);
		}
		
	}
	
	public void cycleBackwards() {
		while(this.gui.historyIndex > 0) {


			if(this.gui.increment > 0) {
				try {
					Thread.sleep(this.gui.increment);
				} catch (InterruptedException e) {
					
				}
			}
			
			if(this.gui.paused || !this.gui.backwards) {
				while(this.gui.paused) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
					}
				}
				if(this.gui.historyIndex == 0) {
					return;
				} else if(!this.gui.backwards) {
					return;
				}
			}
			
			this.gui.displayImage(this.history.get(this.gui.historyIndex - 1));
			
			
			
		}
		
		this.gui.setPaused(true);
	}
}
