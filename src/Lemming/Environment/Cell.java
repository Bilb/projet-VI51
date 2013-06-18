package Lemming.Environment;

import Lemming.CellCoord;

public class Cell {

	public Cell(TerrainType terrainType_, CellCoord cellCoord_) {
		terrainType = terrainType_;
		cellCoord = cellCoord_;
	}
	
	
	private TerrainType terrainType;

	private CellCoord cellCoord;

	public CellCoord getCellCoord() {
		return cellCoord;
	}

	public void setCellCoord(CellCoord cellCoord) {
		this.cellCoord = cellCoord;
	}

	public TerrainType getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(TerrainType terrainType) {
		this.terrainType = terrainType;
	}

}
