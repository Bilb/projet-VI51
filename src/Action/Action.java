package Action;

import java.util.LinkedList;

import Lemming.CellCoord;
import Lemming.Agent.LemmingBody;

import fr.utbm.gi.vi51.learning.qlearning.QAction;
import fr.utbm.gi.vi51.learning.qlearning.QComparable;

public class Action implements QAction{


	private static final long serialVersionUID = 8567701969352353019L;
	private LinkedList<ActionProcess> actionProcessList; // action needed to perform the action
	private LinkedList<ActionTest> actionTestList; // test needed to perform the action
	private Boolean builded = false;
	public enum LemmingActionType {
		SeHisser,
		Climb,
		Dig,
		Drill,
		Parachute,
		Walk,
		Block,
		Turnback,
		Die
	}
	private LemmingActionType lemmingActionType;




	public Action(LinkedList<ActionProcess> actionProcessList , LinkedList<ActionTest> actionTestList, LemmingActionType lemmingActionType) {
		this.actionProcessList = actionProcessList;
		this.actionTestList = actionTestList;
		this.lemmingActionType = lemmingActionType;
	}
	
	public Action(LemmingActionType actionType) {
		lemmingActionType = actionType;
		actionProcessList = null;
		actionTestList = null;
	}
	
	public Action(LemmingActionType actionType, LemmingBody body) {
		lemmingActionType = actionType;
		actionProcessList = null;
		actionTestList = null;
		buildAction(body);
		
	}
	
	public void buildAction(LemmingBody body) {
		actionProcessList = new LinkedList<ActionProcess>();
		actionTestList = new LinkedList<ActionTest>();
		int bodyX = body.getCellCoord().getX();
		int bodyY = body.getCellCoord().getY();
		int dx = body.getSens().dx;
		switch(lemmingActionType) {
		case Climb:
		{
			//building test
			CellCoord cellTestFront = new CellCoord(bodyX+dx,bodyY); // case en face
			CellCoord cellTestUp = new CellCoord(bodyX,bodyY-1); // case en haut
			CellCoord cellTestUpFront =  new CellCoord(bodyX+dx,bodyY-1); // case en haut en face
			CellCoord cellForceUp = new CellCoord(0,-2); // force

			actionTestList.add(new ActionTest(cellTestFront,ActionTestTag.NOT_TRAVERSABLE));
			actionTestList.add(new ActionTest(cellTestFront,ActionTestTag.NOT_BLOCKED_LEMMING));
			actionTestList.add(new ActionTest(cellTestUpFront,ActionTestTag.NOT_TRAVERSABLE));
			actionTestList.add(new ActionTest(cellTestUpFront,ActionTestTag.NOT_BLOCKED_LEMMING));
			actionTestList.add(new ActionTest(cellTestUp,ActionTestTag.TRAVERSABLE));

			//building action to be performed if test are succesfull ordered by first = first action to be done
			
			actionProcessList.add(new ActionProcess(cellForceUp,ActionProcessTag.MOVE));
		}
		break;
		case SeHisser:
		{
			//building test
			CellCoord cellTestFront = new CellCoord(bodyX+dx,bodyY); // case en face
			CellCoord cellTestUp = new CellCoord(bodyX,bodyY-1); // case en haut
			CellCoord cellTestUpFront =  new CellCoord(bodyX+dx,bodyY-1); // case en haut en face
			CellCoord cellForce = new CellCoord(dx,-2); // case en haut

			actionTestList.add(new ActionTest(cellTestFront,ActionTestTag.NOT_TRAVERSABLE));
			actionTestList.add(new ActionTest(cellTestFront,ActionTestTag.NOT_BLOCKED_LEMMING));
			actionTestList.add(new ActionTest(cellTestUp,ActionTestTag.TRAVERSABLE));
			actionTestList.add(new ActionTest(cellTestUpFront,ActionTestTag.TRAVERSABLE));

			//building action to be performed if test are succesfull ordered by first = first action to be done
			actionProcessList.add(new ActionProcess(cellForce,ActionProcessTag.MOVE));
		}
		break;
		case Drill:
		{
			//building test
			CellCoord cellTestDown = new CellCoord(bodyX,bodyY+1); // case en dessous

			actionTestList.add(new ActionTest(cellTestDown,ActionTestTag.DIGGABLE));

			//building action to be performed if test are succesfull
			actionProcessList.add(new ActionProcess(cellTestDown,ActionProcessTag.DESTROY));
			//actionProcessList.add(new ActionProcess(cellTestDown,ActionProcessTag.MOVE));
		}
		break;
		case Walk:
		{
			//building test
			CellCoord cellTestFront = new CellCoord(bodyX+dx,bodyY); // case en face
			CellCoord cellTestDown = new CellCoord(bodyX,bodyY+1); // case en dessous
			
			actionTestList.add(new ActionTest(cellTestFront,ActionTestTag.TRAVERSABLE));
			actionTestList.add(new ActionTest(cellTestDown,ActionTestTag.NOT_TRAVERSABLE));

			//building action to be performed if test are succesfull
			CellCoord cellForce = new CellCoord(dx,-1); // case en haut
			actionProcessList.add(new ActionProcess(cellForce,ActionProcessTag.MOVE));
		}
		break;
		case Dig:
		{
			//building test
			CellCoord cellTestFront = new CellCoord(bodyX+dx,bodyY); // case en face
			CellCoord cellTestDown = new CellCoord(bodyX,bodyY+1); // case en dessous
			actionTestList.add(new ActionTest(cellTestFront,ActionTestTag.DIGGABLE));
			actionTestList.add(new ActionTest(cellTestDown,ActionTestTag.NOT_TRAVERSABLE));

			//building action to be performed if test are succesfull
			actionProcessList.add(new ActionProcess(cellTestFront,ActionProcessTag.DESTROY));
			//actionProcessList.add(new ActionProcess(cellTestFront,ActionProcessTag.MOVE));

		}
		break;
		case Parachute:
		{
			actionProcessList.add(new ActionProcess(null,ActionProcessTag.PARACHUTE));
		}
		break;
		case Block:
		{
			//building test
			CellCoord cellTestFront = new CellCoord(bodyX+dx,bodyY); // case en face
			CellCoord cellTestDownFront = new CellCoord(bodyX+dx,bodyY+1); // case en dessous en face
			CellCoord cellTestCurrent = new CellCoord(bodyX,bodyY); // case courante
			
		//	actionTestList.add(new ActionTest(cellTestDownFront,ActionTestTag.DANGER));
			actionTestList.add(new ActionTest(cellTestFront,ActionTestTag.TRAVERSABLE));
			
			actionProcessList.add(new ActionProcess(cellTestCurrent,ActionProcessTag.CREATE)); // cr�er un bloc
			actionProcessList.add(new ActionProcess(null,ActionProcessTag.BLOCK)); // lancer le blockage (suppression du lemming)
		}
		break;
		case Die:
		{
			actionProcessList.add(new ActionProcess(null,ActionProcessTag.DIE)); // cr�er un bloc

		}
			break;
		case Turnback:
		{
			CellCoord cellTestDown = new CellCoord(bodyX,bodyY+1); // case en dessous
			actionTestList.add(new ActionTest(cellTestDown,ActionTestTag.NOT_TRAVERSABLE));
			
			actionProcessList.add(new ActionProcess(null,ActionProcessTag.TURNBACK)); // cr�er un bloc
		}
		break;
		}
		
		// handle the climbing flag
		if(lemmingActionType != LemmingActionType.Climb && lemmingActionType != LemmingActionType.SeHisser) {
			actionProcessList.add(new ActionProcess(null,ActionProcessTag.NOT_CLIMBING));
		}
		else {
			actionProcessList.add(new ActionProcess(null,ActionProcessTag.CLIMBING));
		}
		
		// handle the parachute
		if(lemmingActionType != LemmingActionType.Parachute) {
			actionProcessList.add(new ActionProcess(null,ActionProcessTag.NOT_PARACHUTE));
		}
		builded = true;
	}
	

	public LinkedList<ActionProcess> getActionProcessList() {
		return actionProcessList;
	}

	public void setActionProcessList(LinkedList<ActionProcess> actionProcessList) {
		this.actionProcessList = actionProcessList;
	}

	public LinkedList<ActionTest> getActionTestList() {
		return actionTestList;
	}

	public void setActionTestList(LinkedList<ActionTest> actionTestList) {
		this.actionTestList = actionTestList;
	}



	public void setLemmingActionType(LemmingActionType lemmingActionType) {
		this.lemmingActionType = lemmingActionType;
		if( builded) {
			actionProcessList = null;
			actionTestList = null;
			builded =false;
		}
	}


	
	public LemmingActionType getLemmingActionType() {
		return lemmingActionType;
	}



	@Override
	public int toInt() {
		return getLemmingActionType().ordinal();
	}


	@Override
	public int compareTo(QComparable other) {
		return toInt() - other.toInt();

	}


	@Override
	public QAction clone() {
		return new Action(getActionProcessList(), getActionTestList(), getLemmingActionType());
	}

	public Boolean getBuilded() {
		return builded;
	}

	public void setBuilded(Boolean builded) {
		this.builded = builded;
	}

	@Override
	public String toString() {
		return "Action [lemmingActionType=" + lemmingActionType + "]";
	}

	
	
	
}
