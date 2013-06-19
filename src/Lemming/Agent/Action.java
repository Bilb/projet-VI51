package Lemming.Agent;

import fr.utbm.gi.vi51.learning.qlearning.QAction;
import fr.utbm.gi.vi51.learning.qlearning.QComparable;

public class Action implements QAction{
	

	private static final long serialVersionUID = 8567701969352353019L;


	public enum LemmingActionType {
		Die,
		Climb,
		Dig,
		Drill,
		Parachute,
		Walk,
		Block,
		Turnback,
		Cross
	}
	
	private int dy;
	private LemmingActionType lemmingActionType;

	
	public Action(LemmingActionType actionType) {
		lemmingActionType = actionType;
		switch(lemmingActionType) {
		case Climb:
			dy = -1;
			break;
		case Drill:
			dy = +1;
			break;
		default:
			dy = 0;
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
		return new Action(getLemmingActionType());
	}

	
	public int getDy() {
		return dy;
	}

}
