package chess.piece;

import java.util.ArrayList;

import chess.Cell;
import chess.move.Move;
import chess.player.Player;

public class Bishop extends Piece {

	public Bishop(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "F";
		else return "f";
	}

	@Override
	public boolean canMove(Move move, Cell[][] grid) {
		
		// deplacement diagonale
		return Math.abs(move.xEnd - move.xStart) == Math.abs(move.yEnd - move.yStart);
	}

	@Override
	public Cell[] getPath(Move move, Cell[][] grid) {
		
		// distance a parcourir
		int n = Math.abs(move.xEnd - move.xStart);

		Cell[] path = new Cell[n-1];
		
		// parcours la diagonale correspondant au déplacement parmis les quatre directions possible
		if (move.xEnd > move.xStart && move.yEnd > move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart + i][move.yStart + i];
		}
		if (move.xEnd > move.xStart && move.yEnd < move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart + i][move.yStart - i];
		}
		if (move.xEnd < move.xStart && move.yEnd > move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart - i][move.yStart + i];
		}
		if (move.xEnd < move.xStart && move.yEnd < move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart - i][move.yStart - i];
		}
		
		return path;
	}
}
