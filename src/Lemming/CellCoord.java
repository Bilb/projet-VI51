package Lemming;

import Lemming.Ui.Game;

/**
 * Cette classe decrit le system de coordonnee fonctionnant par cellule.
 * Elle herite de la classe Coord
 */
public class CellCoord extends Coord {
	
	public CellCoord() {
		super();
	}
	
	public CellCoord(int x, int y) {
		super(x,y);
	}

	/**
	 * Transforme les coordonnees du format cellule en pixel
	 * @return Les coordonnes en pixel
	 */
	public PixelCoord toPixelCoord() {
		PixelCoord pixelCoord = new PixelCoord(getX()*Game.CellDim, getY()*Game.CellDim);
		
		return pixelCoord;
	}

	@Override
	public String toString() {
		return "CellCoord [X=" + getX() + ", Y=" + getY() + "]";
	}
}
