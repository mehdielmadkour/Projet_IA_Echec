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
	public boolean play() {
		
		ArrayList<Move> possibleMoves = getPossibleMoves();
		if (possibleMoves.size() == 0) return false;
		
		Random rand = new Random();
		int n = rand.nextInt(possibleMoves.size());
		
		this.board.movePiece(possibleMoves.get(n));
		
		return true;
	}

}
