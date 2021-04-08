package chess.piece;

import chess.player.Player;

public class Knight extends Piece {

	public Knight(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "C";
		else return "c";
	}
}
