package chess.player;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;

import chess.Board;
import chess.player.Move;

public class HumanPlayer extends Player {

	public HumanPlayer(int color, Board board){
		setColor(color);
		this.board = board;
	}

	@Override
	public Move play() {
		Move move;
		
		boolean legalMove = true;
		do{
			System.out.println("Votre coup? <e2e4> ");
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String moveStr = new String(reader.readLine());
				char[] moveChar = moveStr.toCharArray(); 
				
				move = new Move(moveChar[0]-'a', moveChar[1]-'1', moveChar[2] - 'a', 	moveChar[3]-'1');
			}
			catch (Exception e) {
				return null;
			}
		}
		while(!legalMove);
		return move;
	}

}
