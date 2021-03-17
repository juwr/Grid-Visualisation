package program;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import core.Grid;
import core.Structs;

public class Scanner {
	
	public Scanner() {
		
	}
	
	public Grid readImage(String filepath) {
		
		BufferedImage image;
		try {
			image = ImageIO.read(Scanner.class.getClassLoader().getResource(filepath)); // Read Image
			
			int width = image.getWidth();
		    int height = image.getHeight();
		    
		    if(width * height > 22500) {
		    	System.out.println("Image is too big. Maximum size is 150x150 pixel!");
		    	return null;
		    }
		    
		    Color[][] colors = new Color[height][width];

		    for (int row = 0; row < height; row++) {
		    	for (int col = 0; col < width; col++) {
		    		Color pixelColor = new Color(image.getRGB(col, row));
		    		
		    		boolean detected = false;
		    		for (Color predefined : Structs.COLORS) {
		    			if(compareColors(pixelColor, predefined)) {
		    				pixelColor = predefined;
		    				detected = true;
		    			}
		    		}
		    		
		    		if(!detected) {
		    			pixelColor = Color.WHITE;	//	If the intended color of the pixel can't be determined, it defaults to white.
		    		}
		    		
		    		colors[row][col] = pixelColor;
		    	}
		   	}
		    
		    
		    Grid grid = new Grid(width, height);
		    
		    for (int row = 0; row < height; row++) {
		    	for (int col = 0; col < width; col++) {
		    		
		    		Color pixelColor = colors[row][col];
		    		if (pixelColor.equals(Color.WHITE)) {
		    			grid.getNode(col, row).nodeType = Structs.NodeType.OPEN;
		    		} else if (pixelColor.equals(Color.BLACK)) {
		    			grid.getNode(col, row).nodeType = Structs.NodeType.WALL;
		    		} else if (pixelColor.equals(Color.BLUE)) {
		    			grid.getNode(col, row).nodeType = Structs.NodeType.OPEN;
		    			grid.start = grid.getNode(col, row);
		    		} else if (pixelColor.equals(Color.CYAN)) {
		    			grid.getNode(col, row).nodeType = Structs.NodeType.OPEN;
		    			grid.goal = grid.getNode(col, row);
		    		} else {
		    			grid.getNode(col, row).nodeType = Structs.NodeType.OPEN;
		    		}
		    		
		    	}
		    }
		    
		    
		    return grid;
		      
		      
		      
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private boolean compareColors(Color a, Color b) {
		double distance = Math.sqrt((a.getRed() - b.getRed())*(a.getRed() - b.getRed()) + (a.getGreen() - b.getGreen())*(a.getGreen() - b.getGreen()) + (a.getBlue() - b.getBlue())*(a.getBlue() - b.getBlue()));
		
		if (distance < 127.5) {
			return true;
		} else {
			return false;
		}
		
	}
}
