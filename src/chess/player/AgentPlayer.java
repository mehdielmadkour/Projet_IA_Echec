package chess.player;

import java.util.ArrayList;
import java.util.Random;

import chess.Board;
import chess.move.Move;
import chess.move.Validator;
import chess.piece.Piece;

public class AgentPlayer extends Player {

	public AgentPlayer(int color, Board board){
		setColor(color);
		this.board = board;
	}
	
	@Override
	public void play() {
		
		ArrayList<Move> possibleMoves = getPossibleMoves();
		
		Random rand = new Random();
		int n = rand.nextInt(possibleMoves.size());
		
		this.board.movePiece(possibleMoves.get(n));
		
	}
	
	private ArrayList<Move> getPossibleMoves(){

		ArrayList<Move> possibleMoves = new ArrayList<>();
		
		ArrayList<int[]> playerPositions = getPositions();
		
		for (int[] position : playerPositions) {
			
			Piece piece = board.getGrid()[position[0]][position[1]].getPiece();
			
			for (int x = 0; x < board.SIZE; x++)
				for (int y = 0; y < board.SIZE; y++) {
					Move move = new Move(position[0], x, position[1], y);
					if (Validator.validateMove(move, this))
						possibleMoves.add(move);
				}
		}
		
		return possibleMoves;
	}
	
	private ArrayList<int[]> getPositions(){

		ArrayList<int[]> playerPositions = new ArrayList<>();
		
		for (int x = 0; x < board.SIZE; x++)
			for (int y = 0; y < board.SIZE; y++)
				if (board.getGrid()[x][y].isOccupied())
					if (board.getGrid()[x][y].getPiece().getPlayer() == this.color) {
						int[] position = new int[2];
						position[0] = x;
						position[1] = y;
						playerPositions.add(position);
					}
		
		return playerPositions;
	}

}
