package Lemming.Agent;

import Lemming.Direction;
import Lemming.Agent.Action.LemmingActionType;
import fr.utbm.gi.vi51.learning.qlearning.QComparable;
import fr.utbm.gi.vi51.learning.qlearning.QComparator;
import fr.utbm.gi.vi51.learning.qlearning.QState;

public class LemmingProblemState implements QState{

	private final int number;
	private Direction exitDirection;

	private Action action;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505584462178781560L;





	public LemmingProblemState(int number) {
		this.number = number;


		/* sortie alignee */
		if(number < 9) {
			exitDirection = Direction.NONE;
			switch(number) {
			case 0:
				action = new Action(LemmingActionType.Die);
			case 1:
				action = new Action(LemmingActionType.Walk);
			case 2:
				action = new Action(LemmingActionType.Dig);
			case 3:
				action = new Action(LemmingActionType.Drill);
			case 4:
				action = new Action(LemmingActionType.Climb);
			case 5:
				action = new Action(LemmingActionType.Parachute);
			case 6:
				action = new Action(LemmingActionType.Block);
			case 7:
				action = new Action(LemmingActionType.Turnback);
			case 8:
				action = new Action(LemmingActionType.Cross);

			}
		}
		/* sortie en dessous */
		else if(number < 18) {
			exitDirection = Direction.BOTTOM;

			switch(number - 9) {
			case 0:
				action = new Action(LemmingActionType.Die);
			case 1:
				action = new Action(LemmingActionType.Walk);

			case 2:
				action = new Action(LemmingActionType.Dig);
			case 3:
				action = new Action(LemmingActionType.Drill);

			case 4:
				action = new Action(LemmingActionType.Climb);
			case 5:
				action = new Action(LemmingActionType.Parachute);
			case 6:
				action = new Action(LemmingActionType.Block);
			case 7:
				action = new Action(LemmingActionType.Turnback);
			case 8:
				action = new Action(LemmingActionType.Cross);

			}

			/* sortie au dessus */
		}else if(number < 27) {
			switch(number - 18) {
			case 0:
				action = new Action(LemmingActionType.Die);
			case 1:
				action = new Action(LemmingActionType.Walk);

			case 2:
				action = new Action(LemmingActionType.Dig);
			case 3:
				action = new Action(LemmingActionType.Drill);

			case 4:
				action = new Action(LemmingActionType.Climb);
			case 5:
				action = new Action(LemmingActionType.Parachute);
			case 6:
				action = new Action(LemmingActionType.Block);
			case 7:
				action = new Action(LemmingActionType.Turnback);
			case 8:
				action = new Action(LemmingActionType.Cross);

			}
			exitDirection = Direction.TOP;
		}


	}












	/**
	 * {@inheritDoc}
	 */
	@Override
	public LemmingProblemState clone() {
		try {
			return (LemmingProblemState)super.clone();
		}
		catch(Throwable e) {
			throw new Error(e);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int toInt() {
		return this.number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(QComparable o) {
		return QComparator.QCOMPARATOR.compare(this, o);
	}

	
	public Action getAction() {
		return action;
	}
	
	public Direction getExitDirection() {
		return exitDirection;
	}

	
	
}
