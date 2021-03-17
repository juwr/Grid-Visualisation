package core;

import java.awt.Color;

public final class Structs {
	
	public static enum NodeType {
		OPEN, WALL
	}
	
	public static Color[] COLORS = { Color.WHITE, Color.BLACK, Color.GREEN, Color.RED, Color.BLUE, Color.CYAN, Color.MAGENTA };
	
	public static final int COST_DIAGONAL = 14;
	public static final int COST_SIDE = 10;
}
