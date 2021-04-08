package chess.piece;

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
		try{
			return super.clone();
		}
		catch (CloneNotSupportedException e){
			System.out.println(e);
		}
		return null;
	}
}
