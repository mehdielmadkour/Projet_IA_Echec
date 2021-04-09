package chess.player;

import chess.Board;

public class AgentPlayer extends Player {

	public AgentPlayer(int color, Board board){
		setColor(color);
		this.board = board;
	}
	
	@Override
	public void play() {
		
	}

}
