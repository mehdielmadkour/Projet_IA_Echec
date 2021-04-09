package chess.ia;

import java.util.ArrayList;

import chess.Cell;
import chess.move.Move;
import chess.move.Validator;
import chess.piece.Piece;
import chess.player.Player;

public class Minimax {

	private static int maxDepth = 3;
	private static Evaluator evaluator;
	private static int agentColor;
	
	public static Move getNextMove(Player player) {
		
		evaluator = player.evaluator;
		ArrayList<Move> possibleMoves = Validator.getPossibleMoves(player.getBoard().copyGrid(), player.getColor());
		
		Move move = null;
		int value = Integer.MIN_VALUE;
		
		for (Move possibleMove : possibleMoves) {
			
			Cell[][] copy = copyGrid(player.getBoard().getGrid());
			
			int nextPlayer;
			if (agentColor == Player.WHITE) nextPlayer = Player.BLACK;
			else nextPlayer = Player.WHITE;
			
			int possibleValue = minimax(copy, 1, nextPlayer);
			
			System.out.println(possibleMove.toString() + " " + value);
			
			if (possibleValue > value) {
				value = possibleValue;
				move = possibleMove;
			}
		}
		
		return move;
	}
	
	private static int minimax(Cell[][] grid, int depth, int player) {
		
		ArrayList<Move> possibleMoves = Validator.getPossibleMoves(grid, player);
		
		if (depth == maxDepth || possibleMoves.size() == 0)
			return evaluator.evaluate(grid);
		
		int nextPlayer;
		if (player == Player.WHITE) nextPlayer = Player.BLACK;
		else nextPlayer = Player.WHITE;
		
		if (agentColor == player) {
			
			int value = Integer.MIN_VALUE;
			
			for (Move possibleMove : possibleMoves) {
				
				Cell[][] copy = copyGrid(grid);
				copy[possibleMove.xEnd][possibleMove.yEnd].setPiece((Piece) copy[possibleMove.xStart][possibleMove.yStart].getPiece().clone());
				copy[possibleMove.xStart][possibleMove.yStart].release();
				
				int possibleValue = minimax(copy, depth + 1, nextPlayer);
				
				if (possibleValue > value) {
					value = possibleValue;
				}
			}
			
			return value;
		}
		else {
			
			int value = Integer.MAX_VALUE;
			
			for (Move possibleMove : possibleMoves) {
				
				Cell[][] copy = copyGrid(grid);
				copy[possibleMove.xEnd][possibleMove.yEnd].setPiece((Piece) copy[possibleMove.xStart][possibleMove.yStart].getPiece().clone());
				copy[possibleMove.xStart][possibleMove.yStart].release();
				
				int possibleValue = minimax(copy, depth + 1, nextPlayer);
				
				if (possibleValue < value) {
					value = possibleValue;
				}
			}
			
			return value;
		}
	}
	
	private static Cell[][] copyGrid(Cell[][] grid){
		
		Cell[][] copy = new Cell[grid.length][grid.length];
		
		for (int x = 0; x < grid.length; x++)
			for (int y = 0; y < grid.length; y++) {
				copy[x][y] = new Cell(x, y);
				if (grid[x][y].isOccupied()) {
					copy[x][y].setPiece((Piece) grid[x][y].getPiece().clone());
				}
			}
		
		return copy;
	}
}
