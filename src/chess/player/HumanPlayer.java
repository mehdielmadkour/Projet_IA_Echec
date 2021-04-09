package chess.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import chess.Board;
import chess.move.Move;
import chess.move.Validator;

public class HumanPlayer extends Player {

	public HumanPlayer(int color, Board board){
		setColor(color);
		this.board = board;
	}

	@Override
	public boolean play() {
		
		ArrayList<Move> possibleMoves = getPossibleMoves();
		if (possibleMoves.size() == 0) return false;
		
		Move move = null;
		
		do{
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
		}
		while(!Validator.validateMove(move, this));
		this.board.movePiece(move);
		
		return true;
	}

}
