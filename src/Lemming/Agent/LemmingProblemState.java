package Lemming.Agent;

import Lemming.Direction;
import fr.utbm.gi.vi51.learning.qlearning.QComparable;
import fr.utbm.gi.vi51.learning.qlearning.QComparator;
import fr.utbm.gi.vi51.learning.qlearning.QState;


/**
 * Represente les etat possible de l'environnement percu par un Lemming
 * @author Audric Ackermann
 *
 */
public class LemmingProblemState implements QState{

	/**
	 * Nombre de states, sans que la direction de la sortie ne soit prise en compte.
	 * Ce nombre est en fait le nombre d'etats differents des terrains autour du Lemming interessants pour lui.
	 * Le nombre d'etats complet est en fait NBSTATE_NO_DIRECTION * 3
	 */
	public static final int NBSTATE_NO_DIRECTION = 24;

	/**
	 * Numero de cet etat, doit être unique. C'est ce qui identifie un etat
	 */
	private final int number;

	/**
	 * Direction de la sortie : seule l'axe des Y nous interesse.
	 * Trois possibilie : None (meme plan que nous)
	 * Top : au dessus de nous
	 * Bottom : en dessous
	 */
	private Direction exitDirection;

	private static final long serialVersionUID = 8505584462178781560L;


	/**
	 * Contient un condense des informations necessaire a identifier cet etat
	 */
	private String description;



	public LemmingProblemState(int number) {
		this.number = number;


		/* sortie alignee */
		if(number < NBSTATE_NO_DIRECTION) {
			exitDirection = Direction.NONE;
			switch(number) {
			case 0: 
				// die
				description = "Mort";
				break;
			case 1:
				//chute libre (sans parachute)
				description = "chute libre (sans parachute)";
				break;
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				description = "chute libre (sans parachute)";
				break;
			case 3:
				// grimper parachute
				description = "grimper parachute";
				break;
			case 4: 
				// grimper demi tour
				description = "grimper demi tour";
				break;
			case 5:
				// deplacement demi tour   non falaise
				// non falaise deja faux
				description = "deplacement demi tour   pas de falaise";
				break;
			case 6:
				// deplacement demi tour   falaise
				description = "deplacement demi tour   falaise";
				break;
			case 7: 
				// creuser   demi tour
				description = "creuser   demi tour";
				break;
			case 8:
				// forer demi tour  
				description = "forer demi tour  ";
				break;
			case 9:
				// grimper   demi tour 
				description = "grimper   demi tour ";
				break;
			case 10:
				// déplacement forer   demi-tour
				description = "déplacement forer   demi-tour";
				break;
			case 11:
				// Creuser + grimper +   + demi-tour 
				description = "Creuser + grimper +   + demi-tour ";
				break;
			case 12:
				//Creuser +   + forer + demi-tour  
				description = "Creuser +   + forer + demi-tour";
				break;
			case 13:
				//Forer + Grimper +   + demi-tour
				description = "Forer + Grimper +   + demi-tour";
				break;
			case 14:
				//Creuser + forer + grimper +   + demi-tour
				description = "Creuser + forer + grimper +   + demi-tour";
				break;
			case 15:
				//Deplacement + demi-tour +   + Forer + falaise
				description = "Deplacement + demi-tour +   + Forer + falaise";
				break;
			case 16:
				//Déplacement + demi-tour +   + Forer + eau
				description = "Déplacement + demi-tour +   + Forer + eau";
				break;
			case 17:
				//Déplacement + demi-tour +   + eau
				description = "Déplacement + demi-tour +   + eau";
				break;
			case 18://bloqué 
				description = "bloqué";
				break;
			case 19:
				//Forer + grimper +   + faire demi-tour : dans un trou, au fond : creusable
				description = "Forer + grimper +   + faire demi-tour : dans un trou, au fond : creusable";
			case 20:
				//creuser forer grimper   : dans un trou, au fond : creusable et sur le cote aussi
				description = "creuser forer grimper   : dans un trou, au fond : creusable et sur le cote aussi";
				break;
			case 21:
				//chute avec parachute
				description = "chute avec parachute";
				break;
			case 22:
				//demi-tour tunnel horizontal : fond d'une gallerie horizontale 
				description = "demi-tour tunnel horizontal : fond d'une gallerie horizontale";
				break;
			case 23:
				//sur sortie
				description = "sur sortie";
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
				description = "Mort";
				break;
			case 1:
				//chute libre (sans parachute)
				description = "chute libre (sans parachute)";
				break;
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				description = "chute libre (sans parachute)";
				break;
			case 3:
				// grimper parachute
				description = "grimper parachute";
				break;
			case 4: 
				// grimper demi tour
				description = "grimper demi tour";
				break;
			case 5:
				// deplacement demi tour   non falaise
				// non falaise deja faux
				description = "deplacement demi tour   pas de falaise";
				break;
			case 6:
				// deplacement demi tour   falaise
				description = "deplacement demi tour   falaise";
				break;
			case 7: 
				// creuser   demi tour
				description = "creuser   demi tour";
				break;
			case 8:
				// forer demi tour  
				description = "forer demi tour  ";
				break;
			case 9:
				// grimper   demi tour 
				description = "grimper   demi tour ";
				break;
			case 10:
				// déplacement forer   demi-tour
				description = "déplacement forer   demi-tour";
				break;
			case 11:
				// Creuser + grimper +   + demi-tour 
				description = "Creuser + grimper +   + demi-tour ";
				break;
			case 12:
				//Creuser +   + forer + demi-tour  
				description = "Creuser +   + forer + demi-tour";
				break;
			case 13:
				//Forer + Grimper +   + demi-tour
				description = "Forer + Grimper +   + demi-tour";
				break;
			case 14:
				//Creuser + forer + grimper +   + demi-tour
				description = "Creuser + forer + grimper +   + demi-tour";
				break;
			case 15:
				//Deplacement + demi-tour +   + Forer + falaise
				description = "Deplacement + demi-tour +   + Forer + falaise";
				break;
			case 16:
				//Déplacement + demi-tour +   + Forer + eau
				description = "Déplacement + demi-tour +   + Forer + eau";
				break;
			case 17:
				//Déplacement + demi-tour +   + eau
				description = "Déplacement + demi-tour +   + eau";
				break;
			case 18://bloqué 
				description = "bloqué";
				break;
			case 19:
				//Forer + grimper +   + faire demi-tour : dans un trou, au fond : creusable
				description = "Forer + grimper +   + faire demi-tour : dans un trou, au fond : creusable";
			case 20:
				//creuser forer grimper   : dans un trou, au fond : creusable et sur le cote aussi
				description = "creuser forer grimper   : dans un trou, au fond : creusable et sur le cote aussi";
				break;
			case 21:
				//chute avec parachute
				description = "chute avec parachute";
				break;
			case 22:
				//demi-tour tunnel horizontal : fond d'une gallerie horizontale 
				description = "demi-tour tunnel horizontal : fond d'une gallerie horizontale";
				break;
			case 23:
				//sur sortie
				description = "sur sortie";
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
				description = "Mort";
				break;
			case 1:
				//chute libre (sans parachute)
				description = "chute libre (sans parachute)";
				break;
			case 2:
				// demi tour uniquement : cas ou l'on est en train de grimper et qu'on tape le plafond !
				description = "chute libre (sans parachute)";
				break;
			case 3:
				// grimper parachute
				description = "grimper parachute";
				break;
			case 4: 
				// grimper demi tour
				description = "grimper demi tour";
				break;
			case 5:
				// deplacement demi tour   non falaise
				// non falaise deja faux
				description = "deplacement demi tour   pas de falaise";
				break;
			case 6:
				// deplacement demi tour   falaise
				description = "deplacement demi tour   falaise";
				break;
			case 7: 
				// creuser   demi tour
				description = "creuser   demi tour";
				break;
			case 8:
				// forer demi tour  
				description = "forer demi tour  ";
				break;
			case 9:
				// grimper   demi tour 
				description = "grimper   demi tour ";
				break;
			case 10:
				// déplacement forer   demi-tour
				description = "déplacement forer   demi-tour";
				break;
			case 11:
				// Creuser + grimper +   + demi-tour 
				description = "Creuser + grimper +   + demi-tour ";
				break;
			case 12:
				//Creuser +   + forer + demi-tour  
				description = "Creuser +   + forer + demi-tour";
				break;
			case 13:
				//Forer + Grimper +   + demi-tour
				description = "Forer + Grimper +   + demi-tour";
				break;
			case 14:
				//Creuser + forer + grimper +   + demi-tour
				description = "Creuser + forer + grimper +   + demi-tour";
				break;
			case 15:
				//Deplacement + demi-tour +   + Forer + falaise
				description = "Deplacement + demi-tour +   + Forer + falaise";
				break;
			case 16:
				//Déplacement + demi-tour +   + Forer + eau
				description = "Déplacement + demi-tour +   + Forer + eau";
				break;
			case 17:
				//Déplacement + demi-tour +   + eau
				description = "Déplacement + demi-tour +   + eau";
				break;
			case 18://bloqué 
				description = "bloqué";
				break;
			case 19:
				//Forer + grimper +   + faire demi-tour : dans un trou, au fond : creusable
				description = "Forer + grimper +   + faire demi-tour : dans un trou, au fond : creusable";
			case 20:
				//creuser forer grimper   : dans un trou, au fond : creusable et sur le cote aussi
				description = "creuser forer grimper   : dans un trou, au fond : creusable et sur le cote aussi";
				break;
			case 21:
				//chute avec parachute
				description = "chute avec parachute";
				break;
			case 22:
				//demi-tour tunnel horizontal : fond d'une gallerie horizontale 
				description = "demi-tour tunnel horizontal : fond d'une gallerie horizontale";
				break;
			case 23:
				//sur sortie
				description = "sur sortie";
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



	@Override
	public String toString() {
		return "LemmingProblemState [number=" + number + ", exitDirection="
				+ exitDirection + ", description= " + description + "]";
	}



	public Direction getExitDirection() {
		return exitDirection;
	}

}
