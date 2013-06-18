package Lemming.Perception;

import Lemming.CellCoord;

public class ExitPerception extends Perception {

private CellCoord exitPosition;

public ExitPerception(CellCoord position) {
	this.exitPosition=position;
}

public CellCoord getExitPosition() {
	return this.exitPosition;
}
}
