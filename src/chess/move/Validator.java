package chess.move;

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

	public static boolean validateMove(Move move, Player player) {
		
		Board board = player.getBoard();
		
		// verification des conditions
		if (isPlayerPiece(board, move, player.getColor()) 
				&& isOnBoard(move)
				&& cellIsAvailable(board, move) 
				&& canMove(board, move)
				&& !passOverAPiece(board, move)) {
			
			// valide le coup
			return true;
		}
		else {
			return false;
		}
	}
	
	// la position initiale contient une piece appartenant au joueur
	private static boolean isPlayerPiece(Board board, Move move, int color) {
		
		Cell[][] grid = board.getGrid();
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
	private static boolean cellIsAvailable(Board board, Move move) {
		
		Cell[][] grid = board.getGrid();

		Cell initialCell = grid[move.xStart][move.yStart];
		Cell finalCell = grid[move.xEnd][move.yEnd];
		
		if (finalCell.isOccupied()) return initialCell.getPiece().getPlayer() != finalCell.getPiece().getPlayer();
		else return true;
	}
	
	// le mouvement est possible pour la piece déplacee
	private static boolean canMove(Board board, Move move) {
		
		Piece piece = board.getGrid()[move.xStart][move.yStart].getPiece();
		
		if (piece instanceof Bishop) return bishopCanMove(move);
		if (piece instanceof King) return kingCanMove(move);
		if (piece instanceof Knight) return knightCanMove(move);
		if (piece instanceof Queen) return queenCanMove(move);
		if (piece instanceof Rook) return rookCanMove(move);
		if (piece instanceof Pawn) {
			if (piece.getPlayer() == Player.WHITE) return whitePawnCanMove(board, move);
			if (piece.getPlayer() == Player.BLACK) return blackPawnCanMove(board, move);
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
	
	private static boolean whitePawnCanMove(Board board, Move move) {
		
		// mange une piece noire
		if ((move.xEnd == move.xStart + 1 || move.xEnd == move.xStart - 1) && move.yEnd - move.yStart == 1)
			if (board.getGrid()[move.xEnd][move.yEnd].isOccupied())
				return board.getGrid()[move.xEnd][move.yEnd].getPiece().getPlayer() == Player.BLACK;
		
		// avance d'une case ou de deux case au premier deplacement
		return move.xEnd == move.xStart && move.yEnd - move.yStart == 1
				|| move.xEnd == move.xStart && move.yStart == 1 && move.yEnd == 3;
	}
	
	private static boolean blackPawnCanMove(Board board, Move move) {
		
		// mange une piece blanche
		if ((move.xEnd == move.xStart + 1 || move.xEnd == move.xStart - 1) && move.yEnd - move.yStart == -1)
			if (board.getGrid()[move.xEnd][move.yEnd].isOccupied())
				return board.getGrid()[move.xEnd][move.yEnd].getPiece().getPlayer() == Player.WHITE;
		
		// avance d'une case ou de deux case au premier deplacement
		return move.xEnd == move.xStart && move.yEnd - move.yStart == -1
				|| move.xEnd == move.xStart && move.yStart == 6 && move.yEnd == 4;
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
	private static boolean passOverAPiece(Board board, Move move) {
		
		Piece piece = board.getGrid()[move.xStart][move.yStart].getPiece();
		
		// ignore les piece ne se deplacant que d'une case et le cavalier
		if (piece instanceof King || piece instanceof Knight || piece instanceof Pawn) return false;
		else {
			Cell[] path;
			
			// recupere le parcours de la piece
			if (piece instanceof Bishop) path = getBishopPath(board, move);
			else if (piece instanceof Rook) path = getRookPath(board, move);
			else path = getQueenPath(board, move);
			
			// teste la presence d'une piece sur le parcours
			for (Cell cell : path) {
				if (cell.isOccupied()) return true;
			}
		}
		
		return false;
	}
	
	private static Cell[] getBishopPath(Board board, Move move) {
		
		// distance a parcourir
		int n = Math.abs(move.xEnd - move.xStart);

		Cell[] path = new Cell[n-1];
		
		// parcours la diagonale correspondant au déplacement parmis les quatre directions possible
		if (move.xEnd > move.xStart && move.yEnd > move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart + i][move.yStart + i];
		}
		if (move.xEnd > move.xStart && move.yEnd < move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart + i][move.yStart - i];
		}
		if (move.xEnd < move.xStart && move.yEnd > move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart - i][move.yStart + i];
		}
		if (move.xEnd < move.xStart && move.yEnd < move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart - i][move.yStart - i];
		}
		
		return path;
	}
	
	private static Cell[] getRookPath(Board board, Move move) {
		
		// distance a parcourir
		int n = Math.max(Math.abs(move.xEnd - move.xStart), Math.abs(move.yEnd - move.yStart));
		
		Cell[] path = new Cell[n-1];
		
		// parcours la ligne ou la colonne correspondant au déplacement parmis les quatre directions possible
		if (move.xEnd > move.xStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart + i][move.yStart];
		}
		if (move.xEnd < move.xStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart - i][move.yStart];
		}
		if (move.yEnd > move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart][move.yStart + i];
		}
		if (move.yEnd < move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart][move.yStart - i];
		}
		
		return path;
	}
	
	private static Cell[] getQueenPath(Board board, Move move) {
		
		// distance a parcourir
		int n = Math.max(Math.abs(move.xEnd - move.xStart), Math.abs(move.yEnd - move.yStart));
		
		Cell[] path = new Cell[n-1];
		
		// parcours la ligne ou la colonne correspondant au déplacement parmis les quatre directions possible
		if (move.xEnd > move.xStart && move.yEnd == move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart + i][move.yStart];
		}
		if (move.xEnd < move.xStart && move.yEnd == move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart - i][move.yStart];
		}
		if (move.yEnd > move.yStart && move.xEnd == move.xStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart][move.yStart + i];
		}
		if (move.yEnd < move.yStart && move.xEnd == move.xStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart][move.yStart - i];
		}
		
		// parcours la diagonale correspondant au déplacement parmis les quatre directions possible
		if (move.xEnd > move.xStart && move.yEnd > move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart + i][move.yStart + i];
		}
		if (move.xEnd > move.xStart && move.yEnd < move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart + i][move.yStart - i];
		}
		if (move.xEnd < move.xStart && move.yEnd > move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart - i][move.yStart + i];
		}
		if (move.xEnd < move.xStart && move.yEnd < move.yStart) {
			for (int i = 1; i < n; i++) path[i-1] = board.getGrid()[move.xStart - i][move.yStart - i];
		}
		
		return path;
	}
}
