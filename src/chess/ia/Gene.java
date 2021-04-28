package chess.ia;

import java.util.Random;

public class Gene {

	private float value;
	private float ALPHA = (float) 0.15;
	
	public Gene(float value) {
		
		Random rand = new Random();
		this.value = value + rand.nextFloat() * value;
	}
	
	// algorithme BLX-alpha
	public Gene(Gene X, Gene Y) {
		
		float min = X.getValue();
		float max = Y.getValue();
		if (max < min) {
			float temp = min;
			min = max;
			max = temp;
		}
		
		float range = max - min;
		max = max + ALPHA*range;
		min = min - ALPHA*range;
		
		Random rand = new Random();
		this.value = rand.nextFloat() * (max - min) + min;
	}
	
	public float getValue() {
		return this.value;
	}
	
	public String toString() {
		return String.valueOf(value);
	}
}
