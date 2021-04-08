package chess.piece;

import chess.player.Player;

public class Pawn extends Piece {

	public Pawn(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "P";
		else return "p";
	}
}
