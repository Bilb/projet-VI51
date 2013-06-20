package Lemming;

import Lemming.Ui.Game;

public abstract class PixelCosmetic {
	
	protected Boolean updatePixel = false;
	protected PixelCoord pixelCoord;
	protected CellCoord previousPosition;
	protected float speedX = 30;
	protected float speedY = 30;

	
	// m�thode pour le d�placement graphique des �l�ments pixel par pixel
	// appel� uniquement si updatePixel est � vrai
	public void updatePixelPosition(CellCoord nextPosition) {
			if(nextPosition != previousPosition) {
				Boolean deplacementTermineY = false;
				int offX = nextPosition.getX() - previousPosition.getX();
				int offY = nextPosition.getY() - previousPosition.getY();
				int speedSenseX = (int) (Math.signum(offX)*speedX);
				int speedSenseY = (int) (Math.signum(offY)*speedY);
				
				// on bouge d'abord en Y
				if(Math.abs(offY) > 0) {	
					pixelCoord.add(0, speedSenseY);		
					if(pixelCoord.getY() % Game.CellDim == 0 ) {
						previousPosition.add(0,(int) Math.signum(offY) );
					}
				}
				else {
					deplacementTermineY = true;
					pixelCoord.add(speedSenseX,0);
				}
				if(pixelCoord.getX() % Game.CellDim == 0 && deplacementTermineY) {
					updatePixel = false;
				}

			}
		}
	
	public boolean getUpdatePixel() {
		return updatePixel;
	}
	
	public void setUpdatePixel(CellCoord previousPosition) {
		this.previousPosition = new CellCoord(previousPosition.getX(),previousPosition.getY());
		updatePixel = true;
	}
	
	public PixelCoord getPixelCoord() {
		return pixelCoord;
	}
	
	public void setPixelCoord(int x, int y) {
		this.pixelCoord.set(x, y);
	}
	
	public void setPixelCoord(PixelCoord pc) {
		pixelCoord = pc;
	}
	

}
