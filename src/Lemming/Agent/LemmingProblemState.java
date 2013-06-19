package Lemming.Agent;

import java.util.ArrayList;
import java.util.List;

import Action.Action;
import Action.Action.LemmingActionType;
import Lemming.Direction;
import fr.utbm.gi.vi51.learning.qlearning.QComparable;
import fr.utbm.gi.vi51.learning.qlearning.QComparator;
import fr.utbm.gi.vi51.learning.qlearning.QState;

public class LemmingProblemState implements QState{

	public static final int NBSTATE_NO_DIRECTION = 23;
	private final int number;
	private Direction exitDirection;

	/* danger : le lemming est au bord d'un falaise */
	private boolean cliffDanger;

	/* Danger : eau juste devant */
	private boolean realDanger;

	/* cas ou l'on est dans l'etat bloque */
	private boolean blocked;

	private List<Action> actions;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505584462178781560L;





	public LemmingProblemState(int number) {
		this.number = number;
		cliffDanger = false;
		realDanger = false;
		blocked = false;

		actions = new ArrayList<Action>();
		/* sortie alignee */
		if(number < NBSTATE_NO_DIRECTION) {
			exitDirection = Direction.NONE;
			switch(number) {
			case 0: 
				// die
				actions.add(new Action(LemmingActionType.Die));
			case 1:
				//parachute
				actions.add(new Action(LemmingActionType.Parachute));
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				actions.add(new Action(LemmingActionType.Turnback));
			case 3:
				// grimper parachute
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Parachute));
			case 4: 
				// grimper demi tour
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
			case 5:
				// deplacement demi tour bloquer non falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				// non falaise deja faux
			case 6:
				// deplacement demi tour bloquer falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				cliffDanger = true;
			case 7: 
				// creuser bloquer demi tour
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 8:
				// forer demi tour bloquer
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 9:
				// grimper bloquer demi tour 
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 10:
				// déplacement forer bloquer demi-tour
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 11:
				// Creuser + grimper + bloquer + demi-tour  
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 12:
				//Creuser + bloquer + forer + demi-tour   
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 13:
				//Forer + Grimper + bloquer + demi-tour
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 14:
				//Creuser + forer + grimper + bloquer + demi-tour
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 15:
				//Deplacement + demi-tour + bloquer + Forer + falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Drill));
				cliffDanger = true;

			case 16:
				//Déplacement + demi-tour + bloquer + Forer + eau
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Drill));
				realDanger = true;


			case 17:
				//Déplacement + demi-tour + bloquer + eau
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				realDanger = true;

			case 18://bloqué : 
				blocked = true;

			case 19:
				//Forer + grimper + bloquer + faire demi-tour : dans un trou, au fond : creusable
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Turnback));

			case 20:
				//creuser forer grimper bloquer : dans un trou, au fond : creusable et sur le cote aussi
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
			case 21:
				//Grimper + bloquer : dans un trou, au fond : creusable nulle part
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
			case 22:
				//Creuser + grimper + bloquer : dans un trou, au fond : creusable sur le cote 
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));

			}
		}
		/* sortie en dessous */
		else if(number < NBSTATE_NO_DIRECTION * 2) {
			exitDirection = Direction.BOTTOM;

			switch(number - NBSTATE_NO_DIRECTION) {
			case 0: 
				// die
				actions.add(new Action(LemmingActionType.Die));
			case 1:
				//parachute
				actions.add(new Action(LemmingActionType.Parachute));
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				actions.add(new Action(LemmingActionType.Turnback));
			case 3:
				// grimper parachute
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Parachute));
			case 4: 
				// grimper demi tour
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
			case 5:
				// deplacement demi tour bloquer non falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				// non falaise deja faux
			case 6:
				// deplacement demi tour bloquer falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				cliffDanger = true;
			case 7: 
				// creuser bloquer demi tour
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 8:
				// forer demi tour bloquer
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 9:
				// grimper bloquer demi tour 
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 10:
				// déplacement forer bloquer demi-tour
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 11:
				// Creuser + grimper + bloquer + demi-tour  
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 12:
				//Creuser + bloquer + forer + demi-tour   
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 13:
				//Forer + Grimper + bloquer + demi-tour
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 14:
				//Creuser + forer + grimper + bloquer + demi-tour
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 15:
				//Deplacement + demi-tour + bloquer + Forer + falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Drill));
				cliffDanger = true;

			case 16:
				//Déplacement + demi-tour + bloquer + Forer + eau
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Drill));
				realDanger = true;


			case 17:
				//Déplacement + demi-tour + bloquer + eau
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				realDanger = true;

			case 18://bloqué : 
				blocked = true;

			case 19:
				//Forer + grimper + bloquer + faire demi-tour : dans un trou, au fond : creusable
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Turnback));

			case 20:
				//creuser forer grimper bloquer : dans un trou, au fond : creusable et sur le cote aussi
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
			case 21:
				//Grimper + bloquer : dans un trou, au fond : creusable nulle part
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
			case 22:
				//Creuser + grimper + bloquer : dans un trou, au fond : creusable sur le cote 
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));

			}


			/* sortie au dessus */
		}else if(number < NBSTATE_NO_DIRECTION * 3) {
			switch(number - NBSTATE_NO_DIRECTION * 2) {

			case 0: 
				// die
				actions.add(new Action(LemmingActionType.Die));
			case 1:
				//parachute
				actions.add(new Action(LemmingActionType.Parachute));
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				actions.add(new Action(LemmingActionType.Turnback));
			case 3:
				// grimper parachute
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Parachute));
			case 4: 
				// grimper demi tour
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
			case 5:
				// deplacement demi tour bloquer non falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				// non falaise deja faux
			case 6:
				// deplacement demi tour bloquer falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				cliffDanger = true;
			case 7: 
				// creuser bloquer demi tour
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 8:
				// forer demi tour bloquer
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 9:
				// grimper bloquer demi tour 
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 10:
				// déplacement forer bloquer demi-tour
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 11:
				// Creuser + grimper + bloquer + demi-tour  
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 12:
				//Creuser + bloquer + forer + demi-tour   
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 13:
				//Forer + Grimper + bloquer + demi-tour
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));

			case 14:
				//Creuser + forer + grimper + bloquer + demi-tour
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
			case 15:
				//Deplacement + demi-tour + bloquer + Forer + falaise
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Drill));
				cliffDanger = true;

			case 16:
				//Déplacement + demi-tour + bloquer + Forer + eau
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Drill));
				realDanger = true;


			case 17:
				//Déplacement + demi-tour + bloquer + eau
				actions.add(new Action(LemmingActionType.Walk));
				actions.add(new Action(LemmingActionType.Turnback));
				actions.add(new Action(LemmingActionType.Block));
				realDanger = true;

			case 18://bloqué : 
				blocked = true;

			case 19:
				//Forer + grimper + bloquer + faire demi-tour : dans un trou, au fond : creusable
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
				actions.add(new Action(LemmingActionType.Turnback));

			case 20:
				//creuser forer grimper bloquer : dans un trou, au fond : creusable et sur le cote aussi
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Drill));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
			case 21:
				//Grimper + bloquer : dans un trou, au fond : creusable nulle part
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));
			case 22:
				//Creuser + grimper + bloquer : dans un trou, au fond : creusable sur le cote 
				actions.add(new Action(LemmingActionType.Dig));
				actions.add(new Action(LemmingActionType.Climb));
				actions.add(new Action(LemmingActionType.Block));


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


	//public Action getAction() {
	//	return action;
	//}

	public Direction getExitDirection() {
		return exitDirection;
	}



	public boolean isCliffDanger() {
		return cliffDanger;
	}





	public boolean isRealDanger() {
		return realDanger;
	}











	@Override
	public String toString() {
		return "LemmingProblemState [number=" + number + ", exitDirection="
				+ exitDirection + "]";
	}
	
	


}
