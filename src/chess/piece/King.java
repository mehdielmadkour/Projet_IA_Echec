package chess.piece;

import chess.player.Player;

public class King extends Piece {

	public King(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "R";
		else return "r";
	}
}
