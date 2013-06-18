package Lemming;

public class FallInfluence extends Influence {
	
	private int nbCell;
	
	public int getNbCell() {
		return nbCell;
	}
	
	public FallInfluence(int nbCell) {
		this.nbCell = nbCell;
	}

	@Override
	public String toString() {
		return "FallInfluence [nbCell=" + nbCell + "]";
	}
	
	
}
