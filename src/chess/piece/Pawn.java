package chess.piece;

import chess.Cell;
import chess.move.Move;
import chess.player.Player;

public class Pawn extends Piece {

	public Pawn(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "P";
		else return "p";
	}

	@Override
	public boolean canMove(Move move, Cell[][] grid) {

		if (player == Player.WHITE) {
			
			// mange une piece noire
			if ((move.xEnd == move.xStart + 1 || move.xEnd == move.xStart - 1) && move.yEnd - move.yStart == 1)
				if (grid[move.xEnd][move.yEnd].isOccupied())
					return grid[move.xEnd][move.yEnd].getPiece().getPlayer() == Player.BLACK;
			
			// avance d'une case ou de deux case au premier deplacement
			return (move.xEnd == move.xStart && move.yEnd - move.yStart == 1
					|| move.xEnd == move.xStart && move.yStart == 1 && move.yEnd == 3 && !grid[move.xEnd][2].isOccupied())
					&& !grid[move.xEnd][move.yEnd].isOccupied();
		}
		else {
			
			// mange une piece blanche
			if ((move.xEnd == move.xStart + 1 || move.xEnd == move.xStart - 1) && move.yEnd - move.yStart == -1)
				if (grid[move.xEnd][move.yEnd].isOccupied())
					return grid[move.xEnd][move.yEnd].getPiece().getPlayer() == Player.WHITE;
			
			// avance d'une case ou de deux case au premier deplacement
			return (move.xEnd == move.xStart && move.yEnd - move.yStart == -1
					|| move.xEnd == move.xStart && move.yStart == 6 && move.yEnd == 4 && !grid[move.xEnd][5].isOccupied())
					&& !grid[move.xEnd][move.yEnd].isOccupied();
		}
	}

	@Override
	public Cell[] getPath(Move move, Cell[][] grid) {
		// TODO Auto-generated method stub
		return null;
	}
}
