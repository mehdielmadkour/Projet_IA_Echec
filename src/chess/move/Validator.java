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
		
		if (isPossible(move, player, grid)) 
			if (!wouldBeCheck(player, move, grid))
				return true;
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
	private static boolean canMove(Cell[][] grid, Move move) {
		
		Piece piece = grid[move.xStart][move.yStart].getPiece();
		
		if (piece instanceof Bishop) return bishopCanMove(move);
		if (piece instanceof King) return kingCanMove(move);
		if (piece instanceof Knight) return knightCanMove(move);
		if (piece instanceof Queen) return queenCanMove(move);
		if (piece instanceof Rook) return rookCanMove(move);
		if (piece instanceof Pawn) {
			if (piece.getPlayer() == Player.WHITE) return whitePawnCanMove(grid, move);
			if (piece.getPlayer() == Player.BLACK) return blackPawnCanMove(grid, move);
		}
		return false;
	}
	
	private static boolean bishopCanMove(Move move) {
		
		// deplacement diagonale
		return Math.abs(move.xEnd - move.xStart) == Math.abs(move.yEnd - move.yStart);
	}
	
	private static boolean kingCanMove(Move move) {
		
		// deplacement d'une case
		return Math.abs(move.xEnd - move.xStart) <= 1 && Math.abs(move.yEnd - move.yStart) <= 1;
	}
	
	private static boolean knightCanMove(Move move) {
		
		// deplacement du cavalier
		return (Math.abs(move.xEnd - move.xStart) == 1 && Math.abs(move.yEnd - move.yStart) == 2)
				|| (Math.abs(move.xEnd - move.xStart) == 2 && Math.abs(move.yEnd - move.yStart) == 1);
	}
	
	private static boolean whitePawnCanMove(Cell[][] grid, Move move) {
		
		// mange une piece noire
		if ((move.xEnd == move.xStart + 1 || move.xEnd == move.xStart - 1) && move.yEnd - move.yStart == 1)
			if (grid[move.xEnd][move.yEnd].isOccupied())
				return grid[move.xEnd][move.yEnd].getPiece().getPlayer() == Player.BLACK;
		
		// avance d'une case ou de deux case au premier deplacement
		return (move.xEnd == move.xStart && move.yEnd - move.yStart == 1
				|| move.xEnd == move.xStart && move.yStart == 1 && move.yEnd == 3)
				&& !grid[move.xEnd][move.yEnd].isOccupied();
	}
	
	private static boolean blackPawnCanMove(Cell[][] grid, Move move) {
		
		// mange une piece blanche
		if ((move.xEnd == move.xStart + 1 || move.xEnd == move.xStart - 1) && move.yEnd - move.yStart == -1)
			if (grid[move.xEnd][move.yEnd].isOccupied())
				return grid[move.xEnd][move.yEnd].getPiece().getPlayer() == Player.WHITE;
		
		// avance d'une case ou de deux case au premier deplacement
		return (move.xEnd == move.xStart && move.yEnd - move.yStart == -1
				|| move.xEnd == move.xStart && move.yStart == 6 && move.yEnd == 4)
				&& !grid[move.xEnd][move.yEnd].isOccupied();
	}
	
	private static boolean queenCanMove(Move move) {
		
		// deplacement diagonale, horizontale ou verticale
		return rookCanMove(move) || bishopCanMove(move);
	}
	
	private static boolean rookCanMove(Move move) {
		
		// deplacement horizontale ou verticale
		return move.xEnd == move.xStart || move.yEnd == move.yStart;
	}
	
	// la piece deplace est un cavalier ou ne passe pas au dessus d'une autre piece
	private static boolean passOverAPiece(Cell[][] grid, Move move) {
		
		Piece piece = grid[move.xStart][move.yStart].getPiece();
		
		// ignore les piece ne se deplacant que d'une case et le cavalier
		if (piece instanceof King || piece instanceof Knight || piece instanceof Pawn) return false;
		else {
			Cell[] path;
			
			// recupere le parcours de la piece
			if (piece instanceof Bishop) path = getBishopPath(grid, move);
			else if (piece instanceof Rook) path = getRookPath(grid, move);
			else path = getQueenPath(grid, move);
			
			// teste la presence d'une piece sur le parcours
			for (Cell cell : path) {
				if (cell.isOccupied()) return true;
			}
		}
		
		return false;
	}
	
	private static Cell[] getBishopPath(Cell[][] grid, Move move) {
		
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
	
	private static Cell[] getRookPath(Cell[][] grid, Move move) {
		
		// distance a parcourir
		int n = Math.max(Math.abs(move.xEnd - move.xStart), Math.abs(move.yEnd - move.yStart));
		
		Cell[] path = new Cell[n-1];
		
		// parcours la ligne ou la colonne correspondant au déplacement parmis les quatre directions possible
		if (move.xEnd > move.xStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart + i][move.yStart];
		}
		if (move.xEnd < move.xStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart - i][move.yStart];
		}
		if (move.yEnd > move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart][move.yStart + i];
		}
		if (move.yEnd < move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = grid[move.xStart][move.yStart - i];
		}
		
		return path;
	}
	
	private static Cell[] getQueenPath(Cell[][] grid, Move move) {
		
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
	
	private static boolean wouldBeCheck(int player, Move move, Cell[][] grid) {
		
		grid[move.xEnd][move.yEnd].setPiece(grid[move.xStart][move.yStart].getPiece());
		grid[move.xStart][move.yStart].release();

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
	
	public static ArrayList<Move> getPossibleMoves(Cell[][] grid, int player){

		ArrayList<Move> possibleMoves = new ArrayList<>();
		
		ArrayList<int[]> playerPositions = getPlayerPositions(player, grid);
		
		for (int[] position : playerPositions) {
			
			Piece piece = grid[position[0]][position[1]].getPiece();
			
			for (int x = 0; x < 8; x++)
				for (int y = 0; y < 8; y++) {
					Move move = new Move(position[0], x, position[1], y);
					if (validateMove(move, player, copyGrid(grid))) {
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
