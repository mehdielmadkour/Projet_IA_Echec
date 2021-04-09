package chess.move;

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
		return String.format("%s%s %s%s", String.valueOf((char) (xStart + 'a')), 
				String.valueOf((char) (yStart + '1')), 
				String.valueOf((char) (xEnd + 'a')), 
				String.valueOf((char) (yEnd + '1')));
	}

}
