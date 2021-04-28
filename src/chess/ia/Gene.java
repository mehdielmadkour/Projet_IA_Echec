package chess.ia;

import java.util.Random;

public class Gene {

	private float value;
	private float ALPHA = (float) 0.15;
	
	public Gene(float value) {
		
		this.value = value;
	}
	
	public Gene() {
		
		Random rand = new Random();
		this.value = value + rand.nextFloat() * 2000000 - 1000000;
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
