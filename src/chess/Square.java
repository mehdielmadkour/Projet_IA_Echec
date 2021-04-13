package chess;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Square extends JPanel implements MouseListener {

	private int x;
	private int y; 
	private UI ui;
	
	public Square(int x, int y, UI ui) {
		this.x = x;
		this.y = y;
		this.ui = ui;
		
		if ((x + y) % 2 != 0) {
            setBackground(Color.GRAY);
        } else {
            setBackground(Color.WHITE);
        }
		
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//ui.receiveClick(y, 7-x);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ui.selectPiece(y, 7-x);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ui.releasePiece();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		ui.moveMouse(y, 7-x);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
