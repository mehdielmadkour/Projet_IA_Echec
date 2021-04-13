package chess.piece;

import java.util.ArrayList;

import chess.Cell;
import chess.move.Move;
import chess.move.Validator;
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
		return (Math.abs(move.xEnd - move.xStart) <= 1 && Math.abs(move.yEnd - move.yStart) <= 1) 
				|| rockPossible(move, grid);
	}
	
	public boolean rockPossible(Move move, Cell[][] grid) {

		if ((move.yStart != 0 || move.yEnd != 0) && (move.yStart != 7 || move.yEnd != 7)) return false;
		if (move.xEnd != 6 && move.xEnd != 2) return false;
		
		// petit roque
		if (move.xEnd == 6) {
			
			if (grid[5][move.yEnd].isOccupied() || grid[6][move.yEnd].isOccupied()) 
				return false;
			
			Move move1 = new Move(move.xStart, 5, move.yStart, move.yEnd);
			
			//if (Validator.wouldBeCheck(player, move, grid)) return false;
			//if (Validator.wouldBeCheck(player, move1, grid)) return false;
			
			if (grid[7][move.yEnd].isOccupied()) {
				if (!(grid[7][move.yEnd].getPiece() instanceof Rook)) return false;
			} else return false;
		}
		
		// grand roque
		if (move.xEnd == 2) {
			
			if (grid[1][move.yEnd].isOccupied() || grid[2][move.yEnd].isOccupied() || grid[3][move.yEnd].isOccupied()) 
				return false;
			
			Move move1 = new Move(move.xStart, 3, move.yStart, move.yEnd);
			
			//if (Validator.wouldBeCheck(player, move, grid)) return false;
			//if (Validator.wouldBeCheck(player, move1, grid)) return false;
			
			if (grid[0][move.yEnd].isOccupied()) {
				if (!(grid[0][move.yEnd].getPiece() instanceof Rook)) return false;
			} else return false;
		}
		
		return true;
	}
	
	public Move getRockMove(Move move) {
		
		// petit roque
		if (move.xEnd == 6) return new Move(7, 5, move.yStart, move.yEnd);
		
		// grand roque
		if (move.xEnd == 2) return new Move(0, 3, move.yStart, move.yEnd);
		
		return null;
	}

	@Override
	public Cell[] getPath(Move move, Cell[][] grid) {
		// TODO Auto-generated method stub
		return null;
	}
}
