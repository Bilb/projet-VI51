package Lemming.Action;

import Lemming.CellCoord;

/**
 * associe une case de l'environnement (CellCoord) a une action realiser dessus
 * le test a realiser est symbolise par un ActionProcessTag
 *
 */
public class ActionProcess {
	private ActionProcessTag tag;
	private CellCoord cell;
	
	ActionProcess(CellCoord cell,ActionProcessTag tag){
		this.setCell(cell);
		this.setTag(tag);
	}

	public ActionProcessTag getTag() {
		return tag;
	}

	public void setTag(ActionProcessTag tag) {
		this.tag = tag;
	}

	public CellCoord getCell() {
		return cell;
	}

	public void setCell(CellCoord cell) {
		this.cell = cell;
	}
}

