package Lemming;

import Lemming.Ui.Game;

/**
 * Cette classe gere les coordonnees en pixels.
 * Elle herite de la classe abstraite Coord.
 */
public class PixelCoord extends Coord {

	public PixelCoord() {
		super();
	}
	
	public PixelCoord(int x, int y) {
		super(x,y);
	}
	
	public CellCoord toCellCoord() {
		return new CellCoord(getX()/Game.CellDim, getY()/Game.CellDim);
	}
	
	@Override
	public String toString() {
		return "PixelCoord [X=" + getX() + ", Y=" + getY() + "]";
	}
}
