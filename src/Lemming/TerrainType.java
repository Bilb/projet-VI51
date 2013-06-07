package Lemming;

public enum TerrainType {
	
	
	WATER(true, false, true) {
	},

	EMPTY(false, false, true) {
	},

	ENTRANCE(false,false,true) {
	};


	TerrainType(boolean isDanger, boolean isDiggable, boolean isTraversable) {
		this.isDanger = isDanger;
		this.isDiggable = isDiggable;
		this.isTraversable = isTraversable;
	}
	
	
	public final Boolean isDanger;
	public final Boolean isDiggable;
	public final Boolean isTraversable;
	
	



}