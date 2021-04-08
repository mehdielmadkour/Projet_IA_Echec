package chess.piece;

import chess.player.Player;

public class Rook extends Piece {

	public Rook(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "T";
		else return "t";
	}
}
