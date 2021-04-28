package chess.ia;

public class Genome {

	private Gene[] genes = new Gene[9];
	
	public Genome() {
		
		/*genes[0] = new Gene((float) -257621.1); // pawn
		genes[1] = new Gene((float) 427772.06); // knight
		genes[2] = new Gene((float) 469250.03); // bishop
		genes[3] = new Gene((float) 133332.42); // rook
		genes[4] = new Gene((float) -371793.5); // queen
		genes[5] = new Gene((float) -485755.03); // king
		genes[6] = new Gene((float) 214145.2); // coups possible
		genes[7] = new Gene((float) 99952.27); // partie nulle
		genes[8] = new Gene((float) -352145.3); // echec et mat*/
		
		for (int i = 0; i < genes.length; i++) {
			genes[i] = new Gene();
		}
	}
	
	public Genome(Genome X, Genome Y) {
		
		for (int i = 0; i < genes.length; i++) {
			genes[i] = new Gene(X.getGene(i), Y.getGene(i));
		}
	}
	
	public Gene getGene(int i) {
		return genes[i];
	}
	
	public String toString() {
		String str = "|";
		for (int i = 0; i < genes.length; i++) {
			str += String.format(" %s |", genes[i].toString());
		}
		return str;
	}
}
