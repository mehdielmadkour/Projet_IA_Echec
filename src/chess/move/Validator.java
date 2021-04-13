package chess.move;

import java.util.ArrayList;
import chess.Board;
import chess.Cell;
import chess.player.Player;
import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Piece;
import chess.piece.Queen;
import chess.piece.Rook;

public class Validator {

	public static boolean validateMove(Move move, int player, Cell[][] grid) {
		
		if (move == null) return false;
		
		if (isPossible(move, player, grid)) {
			if (!wouldBeCheck(player, move, grid)) {
				return true;
			}
			
		}
		return false;
	}

	public static boolean isPossible(Move move, int player, Cell[][] grid) {
		
		// verification des conditions
		if (isPlayerPiece(grid, move, player))
			if (isOnBoard(move))
				if (cellIsAvailable(grid, move))
					if (canMove(grid, move))
						if(!passOverAPiece(grid, move)) {
							// valide le coup
							return true;
						}

		return false;
	}
	
	// la position initiale contient une piece appartenant au joueur
	private static boolean isPlayerPiece(Cell[][] grid, Move move, int color) {
		
		Cell initialCell = grid[move.xStart][move.yStart];

		if (initialCell.isOccupied()) {
			return initialCell.getPiece().getPlayer() == color;
		}
		else return false;
	}
	
	// la position finale se trouve sur le l'echiqier
	private static boolean isOnBoard(Move move) {
		return move.xEnd < 8 && move.yEnd < 8 && move.xEnd >= 0 && move.yEnd >= 0;
	}
	
	// la piece ne mange pas une piece de la meme couleur
	private static boolean cellIsAvailable(Cell[][] grid, Move move) {

		Cell initialCell = grid[move.xStart][move.yStart];
		Cell finalCell = grid[move.xEnd][move.yEnd];
		
		if (finalCell.isOccupied()) return initialCell.getPiece().getPlayer() != finalCell.getPiece().getPlayer();
		else return true;
	}
	
	// le mouvement est possible pour la piece déplacee
	public static boolean canMove(Cell[][] grid, Move move) {

		Piece piece = grid[move.xStart][move.yStart].getPiece();
		
		return piece.canMove(move, grid);
	}
	
	// la piece deplace est un cavalier ou ne passe pas au dessus d'une autre piece
	private static boolean passOverAPiece(Cell[][] grid, Move move) {
		
		Piece piece = grid[move.xStart][move.yStart].getPiece();
		
		// ignore les piece ne se deplacant que d'une case et le cavalier
		if (piece instanceof King || piece instanceof Knight || piece instanceof Pawn) return false;
		else {
			Cell[] path;
			
			// recupere le parcours de la piece
			path = piece.getPath(move, grid);
			
			// teste la presence d'une piece sur le parcours
			for (Cell cell : path) {
				if (cell.isOccupied()) return true;
			}
		}
		
		return false;
	}
	
	public static boolean wouldBeCheck(int player, Move move, Cell[][] grid) {
		
		Cell[][] copy = copyGrid(grid);
		
		Piece piece = copy[move.xStart][move.yStart].getPiece();
		if (piece instanceof King) {
			if (((King) piece).rockPossible(move, copy)) {
				Move move1 = ((King) piece).getRockMove(move);
				copy[move1.xEnd][move1.yEnd].setPiece(copy[move1.xStart][move1.yStart].getPiece());
				copy[move1.xStart][move1.yStart].release();
			}
		}
		
		copy[move.xEnd][move.yEnd].setPiece(grid[move.xStart][move.yStart].getPiece());
		copy[move.xStart][move.yStart].release();

		return isCheck(player, copy);
	}
	
	public static boolean isCheck(int player, Cell[][] grid) {

		int[] kingPosition = new int[2];
		for (int x = 0; x < grid.length; x++)
			for (int y = 0; y < grid.length; y++)
				if (grid[x][y].isOccupied()) 
					if (grid[x][y].getPiece().getPlayer() == player && grid[x][y].getPiece() instanceof King) {
						kingPosition[0] = x;
						kingPosition[1] = y;
					}
		
		int color;
		
		if (player == Player.WHITE) color = Player.BLACK;
		else color = Player.WHITE;
		
		ArrayList<int[]> positions = getPlayerPositions(color, grid);
		
		for (int[] position : positions) {
			
			Move nextMove = new Move(position[0], kingPosition[0], position[1], kingPosition[1]);
			
			if (isPossible(nextMove, color, grid)) return true;
		}
		
		return false;
	}
	
	public static ArrayList<int[]> getPlayerPositions(int player, Cell[][] grid){

		ArrayList<int[]> playerPositions = new ArrayList<>();
		
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				if (grid[x][y].isOccupied())
					if (grid[x][y].getPiece().getPlayer() == player) {
						int[] position = new int[2];
						position[0] = x;
						position[1] = y;
						playerPositions.add(position);
					}
		
		return playerPositions;
	}
	
	public static ArrayList<Move> getPossibleMoves(Cell[][] grid, int player, ArrayList<int[]> playerPositions){

		ArrayList<Move> possibleMoves = new ArrayList<>();
		
		for (int[] position : playerPositions) {
			
			for (int x = 0; x < 8; x++)
				for (int y = 0; y < 8; y++) {
					Move move = new Move(position[0], x, position[1], y);
					if (validateMove(move, player, grid)) {
						possibleMoves.add(move);
					}
				}
		}
		
		return possibleMoves;
	}
	
	public static ArrayList<Move> getPossibleMoves(Cell[][] grid, int player){

		ArrayList<Move> possibleMoves = new ArrayList<>();
		
		ArrayList<int[]> playerPositions = getPlayerPositions(player, grid);
		
		for (int[] position : playerPositions) {
			
			for (int x = 0; x < 8; x++)
				for (int y = 0; y < 8; y++) {
					Move move = new Move(position[0], x, position[1], y);
					if (validateMove(move, player, grid)) {
						possibleMoves.add(move);
					}
				}
		}
		
		return possibleMoves;
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
