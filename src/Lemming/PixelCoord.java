package Lemming;

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

}
