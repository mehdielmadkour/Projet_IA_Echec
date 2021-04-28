package chess.ia;

public class Genome {

	private Gene[] genes = new Gene[9];
	
	public Genome() {
		
		for (int i = 0; i < genes.length; i++) {
			genes[0] = new Gene(10); // pawn
			genes[1] = new Gene(30); // knight
			genes[2] = new Gene(30); // bishop
			genes[3] = new Gene(50); // rook
			genes[4] = new Gene(100); // queen
			genes[5] = new Gene(1000000); // king
			genes[6] = new Gene(1); // coups possible
			genes[7] = new Gene(-100); // partie nulle
			genes[8] = new Gene(1000000); // echec et mat
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
