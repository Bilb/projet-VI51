package Lemming;

public abstract class PixelCosmetic {
	protected Boolean updatePixel = false;
	protected PixelCoord pixelCoord;
	protected CellCoord previousPosition;
	protected float speedX = 3;
	protected float speedY = 3;
	
	// méthode pour le déplacement graphique des éléments pixel par pixel
	// appelé uniquement si updatePixel est à vrai
	public void updatePixelPosition(CellCoord nextPosition) {

			if(nextPosition != previousPosition) {
				Boolean deplacementTermineY = false;
				int offX = nextPosition.getX() - previousPosition.getX();
				int offY = nextPosition.getY() - previousPosition.getY();
				int speedSenseX = (int) (Math.signum(offX)*speedX);
				int speedSenseY = (int) (Math.signum(offY)*speedY);
//				System.out.println("NEXT: " + nextPosition.getX() + "Y: " + nextPosition.getY());
//				System.out.println("previousPositionX: " + previousPosition.getX() + "Y: " + previousPosition.getY());
//				System.out.println("speedSX: " + speedSenseX + "speedSY: " + speedSenseY);
//				System.out.println("offX: " + offX + "speedSY: " + offY);
				
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
