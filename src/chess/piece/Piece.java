package chess.piece;

import chess.player.Player;

public abstract class Piece {
	protected int player;
    
    public Piece(int player) {
    	setPlayer(player);
	}
    
	public int getPlayer() {
            return player;
    }
	
    public void setPlayer(int player) {
            this.player = player;
    }

    public abstract String toString();
    
    public void print(){
            System.out.println(this.toString()); 
    }
    
    @Override
	public Object clone() { 
    	if (this instanceof Pawn) return new Pawn(this.player);
		if (this instanceof Knight) return new Knight(this.player);
		if (this instanceof Bishop) return new Bishop(this.player);
		if (this instanceof Rook) return new Rook(this.player);
		if (this instanceof Queen) return new Queen(this.player);
		if (this instanceof King) return new King(this.player);
		return null;
	}
}
