package chess.player;

import java.util.ArrayList;

import chess.Board;
import chess.move.Move;
import chess.move.Validator;
import chess.piece.Piece;

public abstract class Player {
	public static final int WHITE = 1;
	public static final int BLACK = 0;
	
	protected int color;
	protected Board board;

	public abstract boolean play();
	
	public int getColor(){
		return this.color;
	}
	public void setColor(int color){
		this.color = color;
	}
	public Board getBoard() {
		return board;
	}
	
	protected ArrayList<Move> getPossibleMoves(){

		ArrayList<Move> possibleMoves = new ArrayList<>();
		
		ArrayList<int[]> playerPositions = board.getPlayerPositions(this.color);
		
		for (int[] position : playerPositions) {
			
			Piece piece = board.getGrid()[position[0]][position[1]].getPiece();
			
			for (int x = 0; x < board.SIZE; x++)
				for (int y = 0; y < board.SIZE; y++) {
					Move move = new Move(position[0], x, position[1], y);
					if (Validator.validateMove(move, this)) {
						possibleMoves.add(move);
						System.out.println(move.toString());
					}
				}
		}
		
		return possibleMoves;
	}
}
