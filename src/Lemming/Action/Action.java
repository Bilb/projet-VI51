package Lemming.Action;

import java.util.LinkedList;

import Lemming.CellCoord;
import Lemming.Agent.LemmingBody;

import fr.utbm.gi.vi51.learning.qlearning.QAction;
import fr.utbm.gi.vi51.learning.qlearning.QComparable;



/**
 * 
 * La classe Action permet de définir une action par une liste de test à réaliser
 * et une liste de ActionProcess à faire si ces tests sont vrais
 * ActionProcess:
 *  associe une case de l'environnement (CellCoord) à une action réaliser dessus
 * ActionTest:
 *	associe une case de l'environnement (CellCoord) à un test à faire dessus
 *
 */
public class Action implements QAction{


	private static final long serialVersionUID = 8567701969352353019L;
	
	// test caractérisant la possiblité de l'action
	private LinkedList<ActionTest> actionTestList;
	// liste d'ActionProcess à réaliser si les tests sont valides
	private LinkedList<ActionProcess> actionProcessList;
	private Boolean builded = false;
	private long time = 0;
	
	public enum LemmingActionType {
		SeHisser,
		Climb,
		Dig,
		Drill,
		Parachute,
		Walk,
		Turnback,
		Die
	}
	private LemmingActionType lemmingActionType;



	/**
	 * constructeur de base
	 * @param actionProcessList liste de processus que l'action doit executer si les tests sont valides
	 * @param actionTestList liste de tests à faire pour que l'action soit valide
	 * @param lemmingActionType type de l'action
	 */
	public Action(LinkedList<ActionProcess> actionProcessList , LinkedList<ActionTest> actionTestList, LemmingActionType lemmingActionType) {
		this.actionProcessList = actionProcessList;
		this.actionTestList = actionTestList;
		this.lemmingActionType = lemmingActionType;
	}
	
	public Action(LemmingActionType actionType, LemmingBody body) {
		lemmingActionType = actionType;
		actionProcessList = null;
		actionTestList = null;
		// build the action according to body's information
		buildAction(body);
		
	}
	
	/**
	 * Constructeur utile lorsque l'on ne connait pas encore le body
	 * L'action devra être construite par la suite à l'aide de buildAction
	 * @param actionType type 
	 */
	public Action(LemmingActionType actionType) {
		lemmingActionType = actionType;
		actionProcessList = null;
		actionTestList = null;
	}
	
	/**
	 * C'est dans cette fonction que l'on definie une action en fonction de son type
	 * une action se definie par une liste de test devant être valide et une liste de ActionProcess
	 * à faire lorsque les tests sont valides
	 * Les ActionTest définissent les conditions nécessaire à la réalisation de l'action
	 * Les ActionProcess définissent ce que l'action fait
	 * Cette fonction permet de définir les types d'action en créant les ActionTest et ActionProcess
	 * qui la définissent
	 * @param body corps du lemming réalisant l'action
	 */
	public void buildAction(LemmingBody body) {
		actionProcessList = new LinkedList<ActionProcess>();
		actionTestList = new LinkedList<ActionTest>();
		if(body != null) {
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
				setTime(0);
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
				setTime(0);
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
				setTime(800);
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
				setTime(0);
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
				setTime(800);
			}
			break;
			case Parachute:
			{
				actionProcessList.add(new ActionProcess(null,ActionProcessTag.PARACHUTE));
				setTime(0);
			}
			break;
			case Die:
			{
				actionProcessList.add(new ActionProcess(null,ActionProcessTag.DIE)); // crï¿½er un bloc
				setTime(0);
			}
				break;
			case Turnback:
			{
				//CellCoord cellTestDown = new CellCoord(bodyX,bodyY+1); // case en dessous
				//actionTestList.add(new ActionTest(cellTestDown,ActionTestTag.NOT_TRAVERSABLE));
				
				actionProcessList.add(new ActionProcess(null,ActionProcessTag.TURNBACK)); // crï¿½er un bloc
				setTime(0);
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
			
			// handle the drilling flag
			if(lemmingActionType != LemmingActionType.Drill) {
				actionProcessList.add(new ActionProcess(null,ActionProcessTag.NOT_DRILLING));
			}
			else {
				actionProcessList.add(new ActionProcess(null,ActionProcessTag.DRILLING));
			}
			
			//handle the digging flag
			if(lemmingActionType != LemmingActionType.Dig) {
				actionProcessList.add(new ActionProcess(null,ActionProcessTag.NOT_DIGGING));
			}
			else {
				actionProcessList.add(new ActionProcess(null,ActionProcessTag.DIGGING));
			}
			
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	
	
	
}
