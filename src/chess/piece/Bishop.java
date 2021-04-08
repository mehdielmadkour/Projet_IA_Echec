package chess.piece;

import chess.player.Player;

public class Bishop extends Piece {

	public Bishop(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "F";
		else return "f";
	}
}
