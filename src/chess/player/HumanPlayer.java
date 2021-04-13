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
		evaluator = new Evaluator(this.color);
		this.ui = ui;
		ui.setPlayer(this);
	}

	@Override
	public boolean play() {
		
		
		/*do{
			System.out.println("Votre coup? <e2e4> ");
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String moveStr = new String(reader.readLine());
				char[] moveChar = moveStr.toCharArray();
				move = new Move(moveChar[0]-'a', moveChar[2]-'a', moveChar[1] - '1', 	moveChar[3]-'1');
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}*/
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
