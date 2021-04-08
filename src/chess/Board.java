package chess;

import chess.player.Move;
import chess.player.Player;
import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Queen;
import chess.piece.Rook;

public class Board {
	private Cell[][] grid;
	public static final int SIZE = 8;

	public Board() {
		grid = new Cell[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				grid[j][i] = new Cell(j, i);
			}
		}
		setupChessBoard();
	}

	private void setupChessBoard() {

		for (int x = 0; x < SIZE; x++) {
			grid[x][1].setPiece(new Pawn(Player.WHITE));
			grid[x][6].setPiece(new Pawn(Player.BLACK));
		}
		for (int x = 2; x < 8; x += 3) {
			grid[x][0].setPiece(new Bishop(Player.WHITE));
			grid[x][7].setPiece(new Bishop(Player.BLACK));
		}

		for (int x = 1; x < 8; x += 5) {
			grid[x][0].setPiece(new Knight(Player.WHITE));
			grid[x][7].setPiece(new Knight(Player.BLACK));
		}

		for (int x = 0; x < 8; x += 7) {
			grid[x][0].setPiece(new Rook(Player.WHITE));
			grid[x][7].setPiece(new Rook(Player.BLACK));
		}

		grid[3][0].setPiece(new Queen(Player.WHITE));
		grid[3][7].setPiece(new Queen(Player.BLACK));

		grid[4][0].setPiece(new King(Player.WHITE));
		grid[4][7].setPiece(new King(Player.BLACK));
	}

	public void movePiece(Move move) {
		System.out.println(move.xStart);
		System.out.println(move.yStart);
		System.out.println(move.xEnd);
		System.out.println(move.yEnd);
		grid[move.xEnd][move.yEnd].setPiece(grid[move.xStart][move.yStart].getPiece());
		grid[move.xStart][move.yStart].release();
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public String toString() {
		String s = "";
		for (int y = SIZE - 1; y >= 0; y--) {
			s += ((char) ('1' + y) + "| ");
			for (int x = 0; x < SIZE; x++) {
				if (grid[x][y].isOccupied()) {
					s += grid[x][y].getPiece() + " ";
				} else
					s += "  ";
			}
			s += "\n";
		}

		s += "  ";
		for (int x = 0; x < SIZE; x++)
			s += ("--");

		s += "\n";
		s += "   ";
		for (int x = 0; x < SIZE; x++)
			s += ((char) ('a' + x) + " ");
		s += "\n";
		return s;
	}

	public void print() {
		System.out.println(this.toString());
	}
}
