package Lemming.Action;

import Lemming.CellCoord;


/**
 * 
 * associe une case de l'environnement (CellCoord) à un test à faire dessus
 * le test à réaliser est symbolisé par un ActionTestTag
 *
 */
public class ActionTest {
	public ActionTestTag getTag() {
		return tag;
	}

	public void setTag(ActionTestTag tag) {
		this.tag = tag;
	}

	public CellCoord getCell() {
		return cell;
	}

	public void setCell(CellCoord cell) {
		this.cell = cell;
	}

	private ActionTestTag tag;
	private CellCoord cell;
	
	ActionTest(CellCoord cell,ActionTestTag tag){
		this.cell = cell;
		this.tag = tag;
	}
}

