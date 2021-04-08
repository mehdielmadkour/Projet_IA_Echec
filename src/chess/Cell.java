package chess;

import chess.piece.Piece;

public class Cell {
	private boolean isOccupied;
	private Piece piece;
	private int x, y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.isOccupied = false;
	}


	public Cell(int x, int y, Piece p) {
		this.x = x;
		this.y = y;
		setPiece(p);
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
		this.isOccupied = true;
	}
	public Piece getPiece() {
		return piece;
	}
	public void release(){
		setPiece(null);
		this.isOccupied = false;
	}
}
