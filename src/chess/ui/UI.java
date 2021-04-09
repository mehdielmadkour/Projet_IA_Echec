package chess.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chess.piece.Piece;
import chess.player.Player;
import chess.move.Move;
import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Queen;
import chess.piece.Rook;

public class UI {

	private final int SIZE = 8;
	private final int WIDTH = 50;
	private JFrame frame; 
	
	private JPanel[][] squares= new JPanel[SIZE][SIZE];
	private JLabel[][] pieces = new JLabel[SIZE][SIZE];
	
	public UI() {
		
		// creation de la fenetre
		frame = new JFrame("Projet IA - Agent Echec");
		frame.setSize(500, 500);
		setBoard();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}
	
	private void setBoard() {

		frame.setLayout(new GridLayout(SIZE, SIZE));
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				squares[x][y] = new JPanel();
				if ((x + y) % 2 == 0) {
	                squares[x][y].setBackground(Color.GRAY);
	            } else {
	                squares[x][y].setBackground(Color.WHITE);
	            }   
	            frame.add(squares[x][y]);
			}
	    }
	}
	
	public void addPiece(Piece piece, int x, int y) {
		
		String imageFile = "";
		
		if (piece instanceof Bishop) {
			if (piece.getPlayer() == Player.WHITE) imageFile = "./img/whiteBishop.png";
			if (piece.getPlayer() == Player.BLACK) imageFile = "./img/blackBishop.png";
		}
		if (piece instanceof King) {
			if (piece.getPlayer() == Player.WHITE) imageFile = "./img/whiteKing.png";
			if (piece.getPlayer() == Player.BLACK) imageFile = "./img/blackKing.png";
		}
		if (piece instanceof Knight) {
			if (piece.getPlayer() == Player.WHITE) imageFile = "./img/whiteKnight.png";
			if (piece.getPlayer() == Player.BLACK) imageFile = "./img/blackKnight.png";
		}
		if (piece instanceof Queen) {
			if (piece.getPlayer() == Player.WHITE) imageFile = "./img/whiteQueen.png";
			if (piece.getPlayer() == Player.BLACK) imageFile = "./img/blackQueen.png";
		}
		if (piece instanceof Rook) {
			if (piece.getPlayer() == Player.WHITE) imageFile = "./img/whiteRook.png";
			if (piece.getPlayer() == Player.BLACK) imageFile = "./img/blackRook.png";
		}
		if (piece instanceof Pawn) {
			if (piece.getPlayer() == Player.WHITE) imageFile = "./img/whitePawn.png";
			if (piece.getPlayer() == Player.BLACK) imageFile = "./img/blackPawn.png";
		}
		
		// creation du composant a afficher
		JLabel pieceLabel = new JLabel(new ImageIcon(imageFile));
		pieceLabel.setBounds((int) WIDTH*x, 350 - ((int) WIDTH*y), WIDTH, WIDTH);
		
		pieces[x][y] = pieceLabel;
		
		// affichage du composant
		JPanel panel = squares[SIZE - y - 1][x];
		panel.add(pieceLabel);
		panel.updateUI();
	}
	
	public void movePiece(Move move) {
		
		JPanel startPanel = squares[SIZE - move.yStart - 1][move.xStart];
		JPanel endPanel = squares[SIZE - move.yEnd - 1][move.xEnd];
		
		
		JLabel startPiece = pieces[move.xStart][move.yStart];
		JLabel endPiece = pieces[move.xEnd][move.yEnd];
		
		
		startPanel.remove(startPiece);
		if (endPiece != null ) endPanel.remove(endPiece);
		endPanel.add(startPiece);
		
		startPanel.updateUI();
		endPanel.updateUI();

		pieces[move.xEnd][move.yEnd] = startPiece;
		pieces[move.xStart][move.yStart] = null;
	}
}
