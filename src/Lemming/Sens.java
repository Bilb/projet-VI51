package Lemming;

/**
 * cet enum indique les different sens dans lesquels le Lemming peut se deplacer
 */
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
