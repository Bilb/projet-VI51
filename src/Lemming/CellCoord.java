package Lemming;

import Lemming.Ui.Game;

public class CellCoord extends Coord {
	
	public CellCoord() {
		super();
	}
	
	public CellCoord(int x, int y) {
		super(x,y);
	}

	public PixelCoord toPixelCoord() {
		PixelCoord pixelCoord = new PixelCoord(getX()*Game.CellDim, getY()*Game.CellDim);
		
		return pixelCoord;
	}

	@Override
	public String toString() {
		return "CellCoord [X=" + getX() + ", Y=" + getY() + "]";
	}
	
	
	
	
}
