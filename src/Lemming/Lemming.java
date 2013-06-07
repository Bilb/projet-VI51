package Lemming;

import java.util.List;

public class Lemming {

	private CellCoord cellCoord;

	private LemmingBody lemmingBody;

	private Action action;
	
	Lemming(CellCoord cellCoord_, LemmingBody lemmingBody_) {
		cellCoord = cellCoord_;
		lemmingBody = lemmingBody_;
		
	}

	public void live() {
		List<Perception> perc = lemmingBody.getPerceptions();
		if((TerrainType)(perc.get(5)).isDanger || lemmingBody.getCurrentFall() > lemmingBody.getSupportedFall()) { // 5 is the perception of the terrain where is the lemming
			suicide();
		}
		else {
			executeAction(choseAction(perc));
		}

	}
	
	public Action choseAction(List<Perception> perceptions) {
		
		// QLEARNING?????
		return action;
	}

	public void executeAction(Action action) {
		lemmingBody.doAction(action);
	}

	public void suicide() {
		lemmingBody.suicide();
		lemmingBody = null;
	}

	public List<Perception> getPerceptions() {
		return null;
	}

	public CellCoord getCellCoord() {
		return cellCoord;
	}

	public void setCellCoord(CellCoord cellCoord) {
		this.cellCoord = cellCoord;
	}

	public LemmingBody getLemmingBody() {
		return lemmingBody;
	}

	public void setLemmingBody(LemmingBody lemmingBody) {
		this.lemmingBody = lemmingBody;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	
}
