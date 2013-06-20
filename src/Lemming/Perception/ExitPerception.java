package Lemming.Perception;

import Lemming.CellCoord;

/**
 * Cette classe permet d'identifier la perception symbolisant la sortie et sa position 
 *
 */
public class ExitPerception extends Perception {

	private CellCoord exitPosition;

	public ExitPerception(CellCoord position) {
		this.exitPosition=position;
	}

	public CellCoord getExitPosition() {
		return this.exitPosition;
	}
}
