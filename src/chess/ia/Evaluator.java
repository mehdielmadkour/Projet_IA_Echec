package chess.ia;

import java.util.ArrayList;

import chess.Board;
import chess.Cell;
import chess.move.Move;
import chess.move.Validator;
import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Piece;
import chess.piece.Queen;
import chess.piece.Rook;
import chess.player.Player;

public class Evaluator {

	private int[] values = new int[7];
	private int player;
	private int agent;
	
	public Evaluator(int color) {
		values[0] = 10; // pawn
		values[1] = 30; // knight
		values[2] = 30; // bishop
		values[3] = 50; // rook
		values[4] = 100; // queen
		values[5] = Integer.MAX_VALUE; // king
		values[6] = 1; // coups possible
		
		this.agent = color;
		
		if (color == Player.BLACK) this.player = Player.WHITE;
		else this.player = Player.BLACK;
		
		
	}
	
	public int evaluate(Cell[][] grid, int player) {
		
		int score = 0;
		
		ArrayList<int[]> playerPositions = Validator.getPlayerPositions(player, grid);
		
		for (int[] position : playerPositions) {
			
			Piece piece = grid[position[0]][position[1]].getPiece();
			
			if (piece instanceof Pawn) score += values[0];
			if (piece instanceof Knight) score += values[1];
			if (piece instanceof Bishop) score += values[2];
			if (piece instanceof Rook) score += values[3];
			if (piece instanceof Queen) score += values[4];
			if (piece instanceof King) score += values[5];
			
		}
		
		//ArrayList<Move> possibleMoves = Validator.getPossibleMoves(grid, player); 
		//score += values[6] * possibleMoves.size();
		
		return score;
	}
	
	public int evaluate(Cell[][] grid) {
		
		int playerScore = evaluate(grid, player);
		int agentScore = evaluate(grid, agent);
		
		return agentScore - playerScore;
	}
}
