package Lemming.Perception;

import Lemming.Environment.TerrainType;


/**
 * Cette classe est utilisee pour que les lemmings puissent percevoir les terrains qui les entourent  
 * 
 */
public class TerrainPerception extends Perception{

	private TerrainType terrainElement;

	public TerrainPerception(TerrainType terrain) {
		this.terrainElement=terrain;
	}

	public TerrainType getTerrainElement() {
		return this.terrainElement;
	}

}
