package Lemming.Agent;

import Lemming.Direction;
import fr.utbm.gi.vi51.learning.qlearning.QComparable;
import fr.utbm.gi.vi51.learning.qlearning.QComparator;
import fr.utbm.gi.vi51.learning.qlearning.QState;

public class LemmingProblemState implements QState{

	public static final int NBSTATE_NO_DIRECTION = 24;
	private final int number;
	private Direction exitDirection;

	/* danger : le lemming est au bord d'un falaise */
	private boolean cliffDanger;

	/* parachute deploye ou non */
	private boolean parachuteDeploye;

	/* Danger : eau juste devant */
	private boolean realDanger;

	/* cas ou l'on est dans l'etat bloque */
	private boolean blocked;

	private static final long serialVersionUID = 8505584462178781560L;





	public LemmingProblemState(int number) {
		this.number = number;
		cliffDanger = false;
		realDanger = false;
		parachuteDeploye = false;
		blocked = false;

		/* sortie alignee */
		if(number < NBSTATE_NO_DIRECTION) {
			exitDirection = Direction.NONE;
			switch(number) {
			case 0: 
				// die
				break;
			case 1:
				//chute libre (sans parachute)
				break;
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				break;
			case 3:
				// grimper parachute
				break;
			case 4: 
				// grimper demi tour
				break;
			case 5:
				// deplacement demi tour bloquer non falaise
				// non falaise deja faux
				break;
			case 6:
				// deplacement demi tour bloquer falaise
				cliffDanger = true;
				break;
			case 7: 
				// creuser bloquer demi tour
				break;
			case 8:
				// forer demi tour bloquer
				break;
			case 9:
				// grimper bloquer demi tour 
				break;
			case 10:
				// déplacement forer bloquer demi-tour
				break;
			case 11:
				// Creuser + grimper + bloquer + demi-tour  
				break;
			case 12:
				//Creuser + bloquer + forer + demi-tour   
				break;
			case 13:
				//Forer + Grimper + bloquer + demi-tour
				break;
			case 14:
				//Creuser + forer + grimper + bloquer + demi-tour
				break;
			case 15:
				//Deplacement + demi-tour + bloquer + Forer + falaise
				cliffDanger = true;
				break;
			case 16:
				//Déplacement + demi-tour + bloquer + Forer + eau
				realDanger = true;
				break;
			case 17:
				//Déplacement + demi-tour + bloquer + eau
				realDanger = true;
				break;
			case 18://bloqué 
				blocked = true;
				break;
			case 19:
				//Forer + grimper + bloquer + faire demi-tour : dans un trou, au fond : creusable
			case 20:
				//creuser forer grimper bloquer : dans un trou, au fond : creusable et sur le cote aussi
				break;
			case 21:
				//chute avec parachute
				parachuteDeploye = true;
				break;
			case 22:
				//demi-tour tunnel horizontal : fond d'une gallerie horizontale 
				break;
			case 23:
				//sur sortie
				break;
			default:
				System.err.println("case default while creating state : " + number);

			}
		}
		/* sortie en dessous */
		else if(number < NBSTATE_NO_DIRECTION * 2) {
			exitDirection = Direction.BOTTOM;
			switch(number - NBSTATE_NO_DIRECTION) {
			case 0: 
				// die
				break;
			case 1:
				//chute libre (sans parachute)
				break;
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				break;
			case 3:
				// grimper parachute
				break;
			case 4: 
				// grimper demi tour
				break;
			case 5:
				// deplacement demi tour bloquer non falaise
				// non falaise deja faux
				break;
			case 6:
				// deplacement demi tour bloquer falaise
				cliffDanger = true;
				break;
			case 7: 
				// creuser bloquer demi tour
				break;
			case 8:
				// forer demi tour bloquer
				break;
			case 9:
				// grimper bloquer demi tour 
				break;
			case 10:
				// déplacement forer bloquer demi-tour
				break;
			case 11:
				// Creuser + grimper + bloquer + demi-tour  
				break;
			case 12:
				//Creuser + bloquer + forer + demi-tour   
				break;
			case 13:
				//Forer + Grimper + bloquer + demi-tour
				break;
			case 14:
				//Creuser + forer + grimper + bloquer + demi-tour
				break;
			case 15:
				//Deplacement + demi-tour + bloquer + Forer + falaise
				cliffDanger = true;
				break;
			case 16:
				//Déplacement + demi-tour + bloquer + Forer + eau
				realDanger = true;
				break;
			case 17:
				//Déplacement + demi-tour + bloquer + eau
				realDanger = true;
				break;
			case 18://bloqué 
				blocked = true;
				break;
			case 19:
				//Forer + grimper + bloquer + faire demi-tour : dans un trou, au fond : creusable
			case 20:
				//creuser forer grimper bloquer : dans un trou, au fond : creusable et sur le cote aussi
				break;
			case 21:
				//chute avec parachute
				parachuteDeploye = true;
				break;
			case 22:
				//demi-tour tunnel horizontal : fond d'une gallerie horizontale 
				break;
			case 23:
				//sur sortie
				break;
			default:
				System.err.println("case default while creating state : " + number);

			}

			/* sortie au dessus */
		}else if(number < NBSTATE_NO_DIRECTION * 3) {
			exitDirection = Direction.TOP;
			switch(number - NBSTATE_NO_DIRECTION * 2) {
			case 0: 
				// die
				break;
			case 1:
				//chute libre (sans parachute)
				break;
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				break;
			case 3:
				// grimper parachute
				break;
			case 4: 
				// grimper demi tour
				break;
			case 5:
				// deplacement demi tour bloquer non falaise
				// non falaise deja faux
				break;
			case 6:
				// deplacement demi tour bloquer falaise
				cliffDanger = true;
				break;
			case 7: 
				// creuser bloquer demi tour
				break;
			case 8:
				// forer demi tour bloquer
				break;
			case 9:
				// grimper bloquer demi tour 
				break;
			case 10:
				// déplacement forer bloquer demi-tour
				break;
			case 11:
				// Creuser + grimper + bloquer + demi-tour  
				break;
			case 12:
				//Creuser + bloquer + forer + demi-tour   
				break;
			case 13:
				//Forer + Grimper + bloquer + demi-tour
				break;
			case 14:
				//Creuser + forer + grimper + bloquer + demi-tour
				break;
			case 15:
				//Deplacement + demi-tour + bloquer + Forer + falaise
				cliffDanger = true;
				break;
			case 16:
				//Déplacement + demi-tour + bloquer + Forer + eau
				realDanger = true;
				break;
			case 17:
				//Déplacement + demi-tour + bloquer + eau
				realDanger = true;
				break;
			case 18://bloqué 
				blocked = true;
				break;
			case 19:
				//Forer + grimper + bloquer + faire demi-tour : dans un trou, au fond : creusable
			case 20:
				//creuser forer grimper bloquer : dans un trou, au fond : creusable et sur le cote aussi
				break;
			case 21:
				//chute avec parachute
				parachuteDeploye = true;
				break;
			case 22:
				//demi-tour tunnel horizontal : fond d'une gallerie horizontale 
				break;
			case 23:
				//sur sortie
				break;
			default:
				System.err.println("case default while creating state : " + number);
			}
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
