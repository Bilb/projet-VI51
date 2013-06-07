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


/*

public enum Direction {
	
	NORTH(0,-1) {
		@Override
		public Direction opposite() {
			return SOUTH;
		}
	},
	EAST(+1,0) {
		@Override
		public Direction opposite() {
			return WEST;
		}
	},
	SOUTH(0,+1) {
		@Override
		public Direction opposite() {
			return NORTH;
		}
	},
	WEST(-1,0) {
		@Override
		public Direction opposite() {
			return EAST;
		}
//	};
//	
//	Direction(int dx, int dy) {
//		this.dx = dx;
//		this.dy = dy;
//	}
//	
//	/**
//	 * delta x pour cette direction
//	 */
//	public final int dx;
//	
//	/**
//	 * delta y pour cette direction
//	 */
//	public final int dy;
//	
//	
//	/**
//	 * Permet d'obtenir la direction opposée à une direction
//	 */
//	public abstract Direction opposite();
//	
//}
//
//*/