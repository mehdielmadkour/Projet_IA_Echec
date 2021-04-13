package chess.player;

import java.util.ArrayList;
import java.util.Random;

import chess.Board;
import chess.ia.Evaluator;
import chess.ia.Minimax;
import chess.move.Move;
import chess.move.Validator;
import chess.piece.Piece;

public class AgentPlayer extends Player {

	private Minimax minimax;
	
	public AgentPlayer(int color, Board board){
		setColor(color);
		this.board = board;
		evaluator = new Evaluator(this.color);
		minimax = new Minimax(this);
	}
	
	@Override
	public boolean play() {
		
		/*ArrayList<Move> possibleMoves = Validator.getPossibleMoves(board.copyGrid(), color);
		if (possibleMoves.size() == 0) return false;
		
		Random rand = new Random();
		int n = rand.nextInt(possibleMoves.size());
		
		this.board.movePiece(possibleMoves.get(n));
		*/
		
		Move nextMove = minimax.getNextMove();
		
		if (nextMove == null) return false;
		
		this.board.movePiece(nextMove);
		return true;
	}

	@Override
	public void sendMove(Move move) {
		// TODO Auto-generated method stub
		
	}

}
