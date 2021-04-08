package chess.piece;

import chess.player.Player;

public class Queen extends Piece {

	public Queen(int player) {
		super(player);
	}

	@Override
	public String toString() {
		if (this.player == Player.WHITE) return "D";
		else return "d";
	}
}
