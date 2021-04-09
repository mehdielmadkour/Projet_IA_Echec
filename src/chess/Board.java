package chess;

import chess.player.Player;

import java.util.ArrayList;

import chess.move.Move;
import chess.move.Validator;
import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Piece;
import chess.piece.Queen;
import chess.piece.Rook;

public class Board {
	private Cell[][] grid;
	public static final int SIZE = 8;
	private UI ui;

	public Board(UI ui) {
		this.ui = ui;
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
			ui.addPiece(new Pawn(Player.WHITE), x, 1);
			
			grid[x][6].setPiece(new Pawn(Player.BLACK));
			ui.addPiece(new Pawn(Player.BLACK), x, 6);
		}
		for (int x = 2; x < 8; x += 3) {
			grid[x][0].setPiece(new Bishop(Player.WHITE));
			ui.addPiece(new Bishop(Player.WHITE), x, 0);
			
			grid[x][7].setPiece(new Bishop(Player.BLACK));
			ui.addPiece(new Bishop(Player.BLACK), x, 7);
		}

		for (int x = 1; x < 8; x += 5) {
			grid[x][0].setPiece(new Knight(Player.WHITE));
			ui.addPiece(new Knight(Player.WHITE), x, 0);
			
			grid[x][7].setPiece(new Knight(Player.BLACK));
			ui.addPiece(new Knight(Player.BLACK), x, 7);
		}

		for (int x = 0; x < 8; x += 7) {
			grid[x][0].setPiece(new Rook(Player.WHITE));
			ui.addPiece(new Rook(Player.WHITE), x, 0);
			
			grid[x][7].setPiece(new Rook(Player.BLACK));
			ui.addPiece(new Rook(Player.BLACK), x, 7);
		}

		grid[3][0].setPiece(new Queen(Player.WHITE));
		ui.addPiece(new Queen(Player.WHITE), 3, 0);
		
		grid[3][7].setPiece(new Queen(Player.BLACK));
		ui.addPiece(new Queen(Player.BLACK), 3, 7);

		grid[4][0].setPiece(new King(Player.WHITE));
		ui.addPiece(new King(Player.WHITE), 4, 0);
		
		grid[4][7].setPiece(new King(Player.BLACK));
		ui.addPiece(new King(Player.BLACK), 4, 7);
	}

	public void movePiece(Move move) {
		grid[move.xEnd][move.yEnd].setPiece(grid[move.xStart][move.yStart].getPiece());
		grid[move.xStart][move.yStart].release();
		ui.movePiece(move);
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
	
	public Cell[][] copyGrid(){
		
		Cell[][] copy = new Cell[SIZE][SIZE];
		
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
