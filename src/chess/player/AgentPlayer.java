package chess.player;

import java.util.ArrayList;
import java.util.Random;

import chess.Board;
import chess.ia.Evaluator;
import chess.move.Move;
import chess.move.Validator;
import chess.piece.Piece;

public class AgentPlayer extends Player {
	
	private Evaluator evaluator;

	public AgentPlayer(int color, Board board){
		setColor(color);
		this.board = board;
		evaluator = new Evaluator(this.color);
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
		
		ArrayList<int[]> playerPositions = board.getPlayerPositions(this.color);
		
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

}
