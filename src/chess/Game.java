package chess;

import java.util.Random;

import chess.ia.Genetic;
import chess.ia.Genome;
import chess.move.Validator;
import chess.player.AgentPlayer;
import chess.player.HumanPlayer;
import chess.player.Player;

public class Game {

	protected Board board;
	private UI ui;

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void start() {
		
		ui = new UI();
		board = new Board(ui);
		
		Player human = new HumanPlayer(Player.WHITE, board, ui);
		Player agent = new AgentPlayer(Player.BLACK, board, new Genome());

		while (true){
			if (!human.play()) break;
			if (!agent.play()) break;				
		}
		board.close();
	}
	
	public void learn() {
		
		new Genetic(10, 100);
		
	}
}
