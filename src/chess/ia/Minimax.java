package chess.ia;

import java.util.ArrayList;
import java.util.Random;

import chess.Cell;
import chess.move.Move;
import chess.move.Validator;
import chess.piece.Piece;
import chess.player.Player;

public class Minimax {

	private int maxDepth = 3;
	private Evaluator evaluator;
	private int agentColor;
	private Player player;
	
	public Minimax(Player player) {
		evaluator = player.evaluator;
		this.player = player;
		agentColor = player.getColor();
	}
	
	public Move getNextMove() {
		
		
		ArrayList<Move> possibleMoves = Validator.getPossibleMoves(player.getBoard().copyGrid(), player.getColor());
		
		Move move = null;
		int value = Integer.MIN_VALUE;
		
		int nextPlayer;
		if (agentColor == Player.WHITE) nextPlayer = Player.BLACK;
		else nextPlayer = Player.WHITE;
		
		ArrayList<Move> bestMoves = new ArrayList<>();
		
		for (Move nextMove : possibleMoves) {
			
			Cell[][] copy = play(player.getBoard().copyGrid(), nextMove);
			
			int nextValue = minimax_alphaBeta(copy, 1, nextPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE);
			
			if (nextValue == value) {
				bestMoves.add(nextMove);
			}
			if (nextValue > value) {
				bestMoves.clear();
				bestMoves.add(nextMove);
				value = nextValue;
			}
		}
		
		Random rand = new Random();
		int n = rand.nextInt(bestMoves.size());
		
		move = bestMoves.get(n);
		
		System.out.println("best " + agentColor + " move : " + move.toString() + " : " + value);
		
		return move;
	}
	
	private int minimax(Cell[][] grid, int depth, int player) {
		
		ArrayList<Move> possibleMoves = Validator.getPossibleMoves(grid, player);
		
		if (depth == maxDepth || possibleMoves.size() == 0)
			return evaluator.evaluate(grid);
		
		int nextPlayer;
		if (player == Player.WHITE) nextPlayer = Player.BLACK;
		else nextPlayer = Player.WHITE;
		
		if (agentColor == player) {
			
			int value = Integer.MIN_VALUE;
			
			for (Move nextMove : possibleMoves) {
				
				Cell[][] nextGrid = play(grid, nextMove);
				
				int nextValue = minimax(nextGrid, depth + 1, nextPlayer);
				
				if (nextValue > value) {
					value = nextValue;
				}
			}
			
			return value;
		}
		else {
			
			int value = Integer.MAX_VALUE;
			
			for (Move nextMove : possibleMoves) {
				
				Cell[][] nextGrid = play(grid, nextMove);
				
				int nextValue = minimax(nextGrid, depth + 1, nextPlayer);
				
				if (nextValue < value) {
					value = nextValue;
				}
			}
			
			return value;
		}
	}
	
	private int minimax_alphaBeta(Cell[][] grid, int depth, int player, int alpha, int beta) {
		
		ArrayList<Move> possibleMoves = Validator.getPossibleMoves(grid, player);
		
		if (depth == maxDepth || possibleMoves.size() == 0)
			return evaluator.evaluate(grid);
		
		int nextPlayer;
		if (player == Player.WHITE) nextPlayer = Player.BLACK;
		else nextPlayer = Player.WHITE;
		
		if (agentColor == player) {
			
			int value = Integer.MIN_VALUE;
			
			for (Move nextMove : possibleMoves) {
				
				Cell[][] nextGrid = play(grid, nextMove);
				
				int nextValue = minimax_alphaBeta(nextGrid, depth + 1, nextPlayer, alpha, beta);
				
				if (nextValue > value) {
					value = nextValue;
				}
				
				if (value >= beta) return value;
				
				if (value > alpha) alpha = value;
			}
			
			return value;
		}
		else {
			
			int value = Integer.MAX_VALUE;
			
			for (Move nextMove : possibleMoves) {
				
				Cell[][] nextGrid = play(grid, nextMove);
				
				int nextValue = minimax_alphaBeta(nextGrid, depth + 1, nextPlayer, alpha, beta);
				
				if (nextValue < value) {
					value = nextValue;
				}
				
				if (alpha >= value) return value;
				
				if (value < beta) beta = value;
			}
			
			return value;
		}
	}
	
	private Cell[][] play(Cell[][] grid, Move move){
		
		Cell[][] copy = copyGrid(grid);
		copy[move.xEnd][move.yEnd].setPiece((Piece) copy[move.xStart][move.yStart].getPiece().clone());
		copy[move.xStart][move.yStart].release();
		
		return copy;
	}
	
	private Cell[][] copyGrid(Cell[][] grid){
		
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
