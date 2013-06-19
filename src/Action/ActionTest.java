package Action;

import Lemming.CellCoord;

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

