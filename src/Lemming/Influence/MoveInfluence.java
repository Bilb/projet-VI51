package Lemming.Influence;

public class MoveInfluence extends Influence {
	private boolean movementSuccess;
	
	MoveInfluence(boolean movementSuccess) {
		this.movementSuccess = movementSuccess;
	}
	
	
	public boolean getMovementSucess() {
		return movementSuccess;
	}


	@Override
	public String toString() {
		return "MoveInfluence [movementSuccess=" + movementSuccess + "]";
	}
	
	
	
}
