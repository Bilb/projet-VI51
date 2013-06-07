package Lemming;

public class TerrainPerception extends Perception{

private TerrainType terrainElement;

public TerrainPerception(TerrainType terrain) {
	this.terrainElement=terrain;
}
	
	public TerrainType getTerrainElement() {
		return this.terrainElement;
	}

}
