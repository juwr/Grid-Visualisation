package program;

import core.*;
import gui.*;

public class Main {

	public static void main(String[] args) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

		Scanner scanner = new Scanner();
		
		Grid grid = scanner.readImage("Image.png");
		if (grid == null) {
			return;
		}
		
		GUI gui = new GUI(grid.width, grid.height);
		
		Astar script = new Astar(grid, gui);
		
		script.stepByStep();
	}

}
