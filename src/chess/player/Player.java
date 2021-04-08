package chess.player;

import chess.Board;

public abstract class Player {
	public static final int WHITE = 1;
	public static final int BLACK = 0;
	
	protected int color;
	protected Board board;

	public abstract void play();
	
	public int getColor(){
		return this.color;
	}
	public void setColor(int color){
		this.color = color;
	}
	public Board getBoard() {
		return board;
	}
}
