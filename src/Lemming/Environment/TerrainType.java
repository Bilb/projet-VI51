package Lemming.Environment;

public enum TerrainType {
	
	/**
	 * De l'eau : c'est un danger pour le lemming!
	 */
	WATER(true, false, true, false) {
	},

	EMPTY(false, false, true, false) {
	},
	
	EXIT(false, false, true, false) {
	},
	
	GROUND(false, true, false, true) {
	},
	
	ROCK(false, false, false, true) {
	},

	ENTRANCE(false,false,true, false) {
	},
	
	LEMMING_BLOCKED(false,false,false, false){
	};
	
	


	TerrainType(boolean isDanger, boolean isDiggable, boolean isTraversable, boolean isSolid) {
		this.isDanger = isDanger;
		this.isDiggable = isDiggable;
		this.isTraversable = isTraversable;
		this.isSolid = isSolid;
	}
	
	

	public final boolean isDanger;
	public final boolean isDiggable;
	public final boolean isTraversable;
	public final boolean isSolid;

}
