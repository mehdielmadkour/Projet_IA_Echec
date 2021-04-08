package chess.player;

public class Move {
	public int xStart;
	public int xEnd;
	public int yStart; 
	public int yEnd;

	public Move(int xStart, int xEnd, int yStart, int yEnd) {
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
	}

	public String toString() {
		return (char)('a' + xStart) + "" + yStart + (char)('a' + xEnd) + "" + yEnd;
	}

}
