package chess.piece;

import chess.Cell;
import chess.move.Move;
import chess.player.Player;

public class Knight extends Piece {

	public Knight(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "C";
		else return "c";
	}

	@Override
	public boolean canMove(Move move, Cell[][] grid) {
		
		// deplacement du cavalier
		return (Math.abs(move.xEnd - move.xStart) == 1 && Math.abs(move.yEnd - move.yStart) == 2)
				|| (Math.abs(move.xEnd - move.xStart) == 2 && Math.abs(move.yEnd - move.yStart) == 1);
	}

	@Override
	public Cell[] getPath(Move move, Cell[][] grid) {
		// TODO Auto-generated method stub
		return null;
	}
}
