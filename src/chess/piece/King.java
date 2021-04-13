package chess.piece;

import java.util.ArrayList;

import chess.Cell;
import chess.move.Move;
import chess.player.Player;

public class King extends Piece {

	public King(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "R";
		else return "r";
	}

	@Override
	public boolean canMove(Move move, Cell[][] grid) {
		
		// deplacement d'une case
		return Math.abs(move.xEnd - move.xStart) <= 1 && Math.abs(move.yEnd - move.yStart) <= 1;
	}

	@Override
	public Cell[] getPath(Move move, Cell[][] grid) {
		// TODO Auto-generated method stub
		return null;
	}
}
