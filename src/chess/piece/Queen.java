package chess.piece;

import chess.Cell;
import chess.move.Move;
import chess.player.Player;

public class Queen extends Piece {

	public Queen(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "D";
		else return "d";
	}

	@Override
	public boolean canMove(Move move, Cell[][] grid) {
		
		// deplacement diagonale, horizontale ou verticale
		return new Rook(player).canMove(move, grid) || new Bishop(player).canMove(move, grid);
	}

	@Override
	public Cell[] getPath(Move move, Cell[][] grid) {
		
		// distance a parcourir
		int n = Math.max(Math.abs(move.xEnd - move.xStart), Math.abs(move.yEnd - move.yStart));
		
		Cell[] path = new Cell[n-1];
		
		// parcours la ligne ou la colonne correspondant au déplacement parmis les quatre directions possible
		if (move.xEnd > move.xStart && move.yEnd == move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart + i][move.yStart];
		}
		if (move.xEnd < move.xStart && move.yEnd == move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart - i][move.yStart];
		}
		if (move.yEnd > move.yStart && move.xEnd == move.xStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart][move.yStart + i];
		}
		if (move.yEnd < move.yStart && move.xEnd == move.xStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart][move.yStart - i];
		}
		
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
