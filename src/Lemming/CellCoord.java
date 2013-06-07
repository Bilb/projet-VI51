package Lemming;

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
		
}
