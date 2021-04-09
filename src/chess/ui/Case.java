package chess.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Case extends JPanel {
	
	private Color color;
	private int width;
	private int x;
	private int y;
	
	public Case(Color color, int x, int y, int width) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);  
	    g.drawRect(x*WIDTH,y*WIDTH,WIDTH,WIDTH);  
	    g.setColor(color);
	    g.fillRect(x*WIDTH,y*WIDTH,WIDTH,WIDTH);  
	  }
}
