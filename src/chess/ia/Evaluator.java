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

	private float[] values = new float[9];
	private int player;
	private int agent;
	
	public Evaluator(int color, Genome genome) {
		
		for (int i = 0; i < 9; i++) values[i] = genome.getGene(i).getValue();
		
		this.agent = color;
		
		if (color == Player.BLACK) this.player = Player.WHITE;
		else this.player = Player.BLACK;
		
		
	}
	
	public float evaluate(Cell[][] grid, int player) {
		
		float score = 0;
		
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
		
		ArrayList<Move> possibleMoves = Validator.getPossibleMoves(grid, player, playerPositions); 
		score += values[6] * possibleMoves.size();
		
		if (possibleMoves.size() == 0) {
			if (Validator.isCheck(this.player, grid)) score -= values[8];
			else score -= values[7];
		}
		return score;
	}
	
	public float evaluate(Cell[][] grid) {
		
		float playerScore = evaluate(grid, player);
		float agentScore = evaluate(grid, agent);

		return agentScore - playerScore;
	}
}
