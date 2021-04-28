package chess.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import chess.Board;
import chess.UI;
import chess.ia.Evaluator;
import chess.move.Move;
import chess.move.Validator;
import chess.piece.King;
import chess.piece.Piece;

public class HumanPlayer extends Player {

	private UI ui;
	private Move move= null;
	
	public HumanPlayer(int color, Board board, UI ui){
		setColor(color);
		this.board = board;
		this.ui = ui;
		ui.setPlayer(this);
	}

	@Override
	public boolean play() {
		
		while(!Validator.validateMove(move, this.color, board.copyGrid()));
		this.board.movePiece(move);
		move = null;
		
		return true;
	}

	@Override
	public void sendMove(Move move) {
		this.move = move;
		
	}

}
