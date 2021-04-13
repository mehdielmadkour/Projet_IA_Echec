package chess;

import chess.player.AgentPlayer;
import chess.player.HumanPlayer;
import chess.player.Player;

public class Chess {

	protected Board board;
	private UI ui;
	
	public Chess() {

		ui = new UI();
		board = new Board(ui);

	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	


	private void start() {
		Player human = new HumanPlayer(Player.WHITE, board, ui);
		Player agent = new AgentPlayer(Player.BLACK, board);

		while (true){
			//board.print();
			if (!human.play()) break;
			//board.print();
			if (!agent.play()) break;				
		}
	}
	
	public static void main(String[] args) {
		new Chess().start();
	}
}
