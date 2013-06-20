package Lemming.Environment;


/**
 * Cet enum rescence tous les terrains possible de notre environnement 
 *
 */
public enum TerrainType {
	
	/**
	 * De l'eau : c'est un danger pour le lemming!
	 */
	WATER(true, false, true, false) {
	},

	/**
	 * Vide
	 */
	EMPTY(false, false, true, false) {
	},
	
	/**
	 * Symbolisant la sortie
	 */
	EXIT(false, false, true, false) {
	},
	
	/**
	 * Symbolisant un terrain creusable
	 */
	GROUND(false, true, false, true) {
	},
	
	/**
	 * Symbolisant un terrain non creusable
	 */
	ROCK(false, false, false, true) {
	},

	/**
	 * Position de l'entree
	 */
	ENTRANCE(false,false,true, false) {
	},
	
	/**
	 * un lemming bloque
	 */
	LEMMING_BLOCKED(false,false,false, false){
	};
	
	

	/**
	 * Construit un terrain
	 * @param isDanger a vrai si c'est un danger pour le lemming
	 * @param isDiggable a vrai si c'est creusable par le lemming
	 * @param isTraversable a vrai si c'est traversable
	 * @param isSolid a vrai si c'est un solid pour le lemming.
	 * Quelque chose de solide est un terrain sur lequel un lemming peut s'appuyer pour grimper (donc pas un Lemming bloque)
	 */
	TerrainType(boolean isDanger, boolean isDiggable, boolean isTraversable, boolean isSolid) {
		this.isDanger = isDanger;
		this.isDiggable = isDiggable;
		this.isTraversable = isTraversable;
		this.isSolid = isSolid;
	}
	
	
	/**
	 * vrai si c'est un danger pour le lemming
	 */
	public final boolean isDanger;
	
	/**
	 * vrai si c'est creusable par le lemming
	 */
	public final boolean isDiggable;
	
	/**
	 * vrai si c'est traversable
	 */
	public final boolean isTraversable;
	
	/**
	 * vrai si c'est un solid pour le lemming
	 */
	public final boolean isSolid;

}
