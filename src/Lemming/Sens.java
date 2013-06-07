package Lemming;

public enum Sens {
	LEFT(-1) {
		
	},
	
	STAY(0) {
		
	},
	
	RIGHT(1) {
		
	}
	;
	
	Sens(int dx) {
		this.dx = dx;
	}
	
	public int dx;
}
