package Lemming;

public enum TerrainType {
	
	/**
	 * De l'eau : c'est un danger pour le lemming!
	 */
	WATER(true, false, true) {
	},

	EMPTY(false, false, true) {
	},
	
	EXIT(false, false, true) {
	},
	
	GROUND(false, true, false) {
	},
	
	ROCK(false, false, false) {
	},

	ENTRANCE(false,false,true) {
	};
	
	


	TerrainType(boolean isDanger, boolean isDiggable, boolean isTraversable) {
		this.isDanger = isDanger;
		this.isDiggable = isDiggable;
		this.isTraversable = isTraversable;
	}
	
	
	public final boolean isDanger;
	public final boolean isDiggable;
	public final boolean isTraversable;
	 
}
