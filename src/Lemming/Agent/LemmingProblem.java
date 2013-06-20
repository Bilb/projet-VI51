package Lemming.Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Lemming.CellCoord;
import Lemming.Action.Action;
import Lemming.Action.Action.LemmingActionType;
import Lemming.Environment.TerrainType;
import Lemming.Perception.ExitPerception;
import Lemming.Perception.Perception;
import Lemming.Perception.TerrainPerception;
import fr.utbm.gi.vi51.learning.qlearning.QFeedback;
import fr.utbm.gi.vi51.learning.qlearning.QProblem;

public class LemmingProblem implements QProblem<LemmingProblemState, Action> {


	private LemmingProblemState states[];
	private List<Action> actions;


	private static final float veryverygood = 150f; 
	private static final float verygood = 70f;
	private static final float good = 50f;
	private static final float noimpact = 0.f;
	private static final float bad = -50f;
	private static final float verybad = -100f;

	private static final int NBSTATES = LemmingProblemState.NBSTATE_NO_DIRECTION * 3;


	private LemmingProblemState currentState;
	private Random generator;




	/**
	 * 
	 */
	private static final long serialVersionUID = 453731937056179988L;



	public LemmingProblem() {

		/* ajouter les etats possible de notre système */
		states = new LemmingProblemState[NBSTATES];
		actions = new ArrayList<Action>();

		for(int i = 0; i < NBSTATES ; i++) {
			states[i] = new LemmingProblemState(i);
		}


		actions.add(new Action(LemmingActionType.Die));
		actions.add(new Action(LemmingActionType.Climb));
		actions.add(new Action(LemmingActionType.Dig));
		actions.add(new Action(LemmingActionType.Drill));
		actions.add(new Action(LemmingActionType.Parachute));
		actions.add(new Action(LemmingActionType.Walk));
		actions.add(new Action(LemmingActionType.Turnback));

		currentState = null;
		generator = new Random();
	}


	/**
	 * alpha : facteur d'apprentissage. Plus il est grand plus l'apprentissage passé est important
	 */
	@Override
	public float getAlpha() {
		return 0.7f;
	}

	/**
	 * Gamma : facteur d'amortissement
	 */
	@Override
	public float getGamma() {
		return 0.25f;
	}

	/**
	 * Proba que l'on tire une action au hasard
	 */
	@Override
	public float getRho() {
		return 0.1f;
	}

	/**
	 * Probabillite qu'on doive tirer un state au hasard
	 */
	@Override
	public float getNu() {
		return 0.2f;
	}

	@Override
	public LemmingProblemState getCurrentState() {
		return currentState;
	}

	@Override
	public LemmingProblemState getRandomState() {
		return states[generator.nextInt(NBSTATES)];
	}

	@Override
	public List<LemmingProblemState> getAvailableStates() {
		return Arrays.asList(states);
	}

	@Override
	public List<Action> getAvailableActionsFor(LemmingProblemState state) {
		List<Action> availableForThisState = new ArrayList<Action>();
		switch (state.toInt() % LemmingProblemState.NBSTATE_NO_DIRECTION) {
		case 0:
			availableForThisState.add(new Action(LemmingActionType.Die));
			break;
		case 1:
			availableForThisState.add(new Action(LemmingActionType.Parachute));
			break;
		case 2:
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 3:
			availableForThisState.add(new Action(LemmingActionType.Parachute));
			availableForThisState.add(new Action(LemmingActionType.Climb));
			break;
		case 4:
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			availableForThisState.add(new Action(LemmingActionType.Climb));
			break;
		case 5:
			availableForThisState.add(new Action(LemmingActionType.Walk));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 6:
			availableForThisState.add(new Action(LemmingActionType.Walk));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 7:
			availableForThisState.add(new Action(LemmingActionType.Dig));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 8:
			availableForThisState.add(new Action(LemmingActionType.Drill));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 9:
			availableForThisState.add(new Action(LemmingActionType.Climb));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 10:
			availableForThisState.add(new Action(LemmingActionType.Walk));
			availableForThisState.add(new Action(LemmingActionType.Drill));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 11:
			availableForThisState.add(new Action(LemmingActionType.Dig));
			availableForThisState.add(new Action(LemmingActionType.Climb));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 12:
			availableForThisState.add(new Action(LemmingActionType.Dig));
			availableForThisState.add(new Action(LemmingActionType.Drill));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 13:
			return actions;
		case 14:
			return actions;
		case 15:
			availableForThisState.add(new Action(LemmingActionType.Walk));
			availableForThisState.add(new Action(LemmingActionType.Drill));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 16:
			availableForThisState.add(new Action(LemmingActionType.Walk));
			availableForThisState.add(new Action(LemmingActionType.Drill));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 17:
			availableForThisState.add(new Action(LemmingActionType.Walk));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 18:
			availableForThisState.add(new Action(LemmingActionType.Die));
			break;
		case 19:
			availableForThisState.add(new Action(LemmingActionType.Climb));
			availableForThisState.add(new Action(LemmingActionType.Drill));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 20:
			availableForThisState.add(new Action(LemmingActionType.Dig));
			availableForThisState.add(new Action(LemmingActionType.Climb));
			availableForThisState.add(new Action(LemmingActionType.Drill));
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 21:
			availableForThisState.add(new Action(LemmingActionType.Parachute));
			break;
		case 22:
			availableForThisState.add(new Action(LemmingActionType.Turnback));
			break;
		case 23:
			availableForThisState.add(new Action(LemmingActionType.Die));
			break;
		default:
			System.out.println("returning all actions for state" + state.toInt());
			return actions;
		}
		return availableForThisState;
	}

	@Override
	public QFeedback<LemmingProblemState> takeAction(LemmingProblemState state,
			Action action) {

		switch (state.getExitDirection()) {
		// La sortie est sur le meme axe que nous
		case NONE:
			switch (state.toInt() ) {
			// Mort -> Etat terminal
			case 0:
				switch (action.getLemmingActionType()) {
				case Die:
					return verygood(0);
				default:
					return verybad(state);
				}

				// chute et paracute ferme
			case 1:
				switch (action.getLemmingActionType()) {
				case Parachute:
					return verygood(21);
				default:
					return verybad(state);
				}

				// Grimpe et bloque : se tape contre le plafond en grimpant
			case 2:
				switch (action.getLemmingActionType()) {
				case Turnback:
					return verygood(1);
				default:
					return verybad(state);
				}
				// tombe pres d'une falaise
			case 3:
				switch (action.getLemmingActionType()) {
				case Climb:
					return verybad(4);
				case Parachute:
					return verygood(21);
				default :
					return verybad(state);
				}
				// grimpe
			case 4:
				switch (action.getLemmingActionType()) {
				case Climb:
					return good(state);
				case Turnback:
					return bad(1);
				default:
					return verybad(state);
				}
				// normal
			case 5:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 6:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(1);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// tunnel creusable
			case 7:
				switch (action.getLemmingActionType()) {
				case Dig:
					return verygood(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel forable
			case 8:
				switch (action.getLemmingActionType()) {
				case Drill:
					return verygood(19);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 9:
				switch (action.getLemmingActionType()) {
				case Climb:
					return verygood(state);
				case Turnback:
					return verybad(state);
				default:
					return verybad(state);
				}
				// normal et forable
			case 10:
				switch (action.getLemmingActionType()) {
				case Drill:
					return good(19);
				case Walk:
					return verygood(state);
				case Turnback:
					return verybad(state);
				default:
					verybad(state);
				}
				// face a falaise creusable
			case 11:
				switch (action.getLemmingActionType()) {
				case Climb:
					return good(state);
				case Dig:
					return verygood(state);
				case Turnback:
					bad(state);
				default:
					verybad(state);
				}
				// tunnel creusable et forable
			case 12:
				switch (action.getLemmingActionType()) {
				case Dig:
					return verygood(state);
				case Drill:
					return good(19);
				case Turnback:
					return bad(state);
				default:
					return verybad(state);
				}
				// sol forable face falaise 
				//			case 13:
				//				switch (action.getLemmingActionType()) {
				////				case Block:
				////					return verybad(18);
				//				case Climb:
				//					return verygood(state);
				//				case Drill:
				//					return good(19);
				//				case Turnback:
				//					return noimpact(state);
				//				default:
				//					return verybad(state);
				//				}
				// sol forable face falaise creusable
				//			case 14:
				//				switch (action.getLemmingActionType()) {
				////				case Block:
				////					return verybad(18);
				//				case Dig:
				//					return verygood(7);
				//				case Climb:
				//					return good(state);
				//				case Drill:
				//					return bad(19);
				//				case Turnback:
				//					return noimpact(state);
				//				default:
				//					return verybad(state);
				//				}
				// forable danger falaise 
			case 15:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(1);
				case Drill:
					return good(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable + danger eau
			case 16:
				switch (action.getLemmingActionType()) {
				//				case Block:
				//					return verygood(18);
				case Drill:
					return good(19);
				case Turnback:
					return veryverygood(state); //question de vie ou de mort!
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// danger eau
			case 17:
				switch (action.getLemmingActionType()) {
				case Turnback:
					return veryverygood(state);//question de vie ou de mort!
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// bloque
				//			case 18:
				//				switch (action.getLemmingActionType()) {
				//				case Block:
				//					return verygood(18);
				//				default:
				//					return verybad(state);
				//				}
				// tunnel vertical forable
			case 19:
				switch (action.getLemmingActionType()) {
				case Climb:
					return good(state);
				case Drill:
					return good(19);
				case Turnback:
					return bad(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable forable
			case 20:
				switch (action.getLemmingActionType()) {
				//				case Block:
				//					return verybad(18);
				case Climb:
					return good(state);
				case Drill:
					return good(19);
				case Dig:
					return good(7);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// chute avec parachute
			case 21:
				switch (action.getLemmingActionType()) {
				case Parachute:
					return veryverygood(21);
				default:
					return verybad(state);
				}
				// tunnel horizontal
			case 22:
				switch (action.getLemmingActionType()) {
				case Turnback:
					return verygood(state);
				default:
					return verybad(state);
				}
				// Sortie
			case 23:
				switch (action.getLemmingActionType()) {
				case Die:
					return veryverygood(state);
				default:
					return verybad(state);
				}
			}



			// La sortie est en dessous de nous
		case BOTTOM:
			switch (state.toInt()-LemmingProblemState.NBSTATE_NO_DIRECTION) {
			// Mort -> Etat terminal
			case 0:
				switch (action.getLemmingActionType()) {
				case Die:
					return verygood(0);
				default:
					return verybad(state);
				}

				// Parachute
			case 1:
				switch (action.getLemmingActionType()) {
				case Parachute:
					return verygood(21);
				default:
					return verybad(state);
				}

				// Grimpe et bloque
			case 2:
				switch (action.getLemmingActionType()) {
				case Turnback:
					return verygood(1);
				default:
					return verybad(state);
				}
				// tombe pres d'une falaise
			case 3:
				switch (action.getLemmingActionType()) {
				case Climb:
					return verybad(4);
				case Parachute:
					return verygood(21);
				default :
					return verybad(state);
				}
				// grimpe
			case 4:
				switch (action.getLemmingActionType()) {
				case Climb:
					return good(state);
				case Turnback:
					return bad(1);
				default:
					return verybad(state);
				}
				// normal
			case 5:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(state);
				case Turnback:
					return verybad(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 6:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(1);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// tunnel creusable
			case 7:
				switch (action.getLemmingActionType()) {
				case Dig:
					return good(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel forable
			case 8:
				switch (action.getLemmingActionType()) {
				case Drill:
					return verygood(19);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 9:
				switch (action.getLemmingActionType()) {
				case Climb:
					return verygood(state);
				case Turnback:
					return verybad(state);
				default:
					return verybad(state);
				}
				// normal et forable
			case 10:
				switch (action.getLemmingActionType()) {
				case Drill:
					return verygood(19);
				case Walk:
					return noimpact(state);
				case Turnback:
					return verybad(state);
				default:
					verybad(state);
				}
				// face a falaise creusable
			case 11:
				switch (action.getLemmingActionType()) {
				//				case Block:
				//					return verybad(18);
				case Climb:
					return good(state);
				case Dig:
					return verygood(state);
				case Drill:
					return verygood(state);
				case Turnback:
					noimpact(state);
				default:
					verybad(state);
				}
				// tunnel creusable et forable
			case 12:
				switch (action.getLemmingActionType()) {
				case Dig:
					return good(state);
				case Drill:
					return verygood(19);
				case Turnback:
					return bad(state);
				default:
					return verybad(state);
				}
				/* sol forable face falaise 
							case 13:
								switch (action.getLemmingActionType()) {
				//				case Block:
				//					return verybad(18);
								case Climb:
									return verygood(state);
								case Drill:
									return good(19);
								case Turnback:
									return noimpact(state);
								default:
									return verybad(state);
								}
				 sol forable face falaise creusable
							case 14:
								switch (action.getLemmingActionType()) {
				//				case Block:
				//					return verybad(18);
								case Dig:
									return verygood(7);
								case Climb:
									return good(state);
								case Drill:
									return bad(19);
								case Turnback:
									return noimpact(state);
								default:
									return verybad(state);
								}*/
				//forable danger falaise 
			case 15:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(1);
				case Drill:
					return verygood(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable + danger eau
			case 16:
				switch (action.getLemmingActionType()) {
				case Drill:
					return verygood(19);
				case Turnback:
					return veryverygood(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// danger eau
			case 17:
				switch (action.getLemmingActionType()) {
				//				case Block:
				//					return verygood(18);
				case Turnback:
					return veryverygood(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// tunnel vertical forable
			case 19:
				switch (action.getLemmingActionType()) {
				case Climb:
					return bad(state);
				case Drill:
					return verygood(19);
				case Turnback:
					return bad(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable forable
			case 20:
				switch (action.getLemmingActionType()) {
				case Climb:
					return bad(state);
				case Drill:
					return verygood(19);
				case Dig:
					return verygood(7);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// chute avec parachute
			case 21:
				switch (action.getLemmingActionType()) {
				case Parachute:
					return veryverygood(21);
				default:
					return verybad(state);
				}
				// tunnel horizontal
			case 22:
				switch (action.getLemmingActionType()) {
				case Turnback:
					return verygood(state);
				default:
					return verybad(state);
				}
				// Sortie
			case 23:
				switch (action.getLemmingActionType()) {
				case Die:
					return veryverygood(state);
				default:
					return verybad(state);
				}
			}


			// la sortie est au dessus de nous
		case TOP:
			switch (state.toInt()-2*LemmingProblemState.NBSTATE_NO_DIRECTION) {
			// Mort -> Etat terminal
			case 0:
				switch (action.getLemmingActionType()) {
				case Die:
					return verygood(0);
				default:
					return verybad(state);
				}

				// Parachute
			case 1:
				switch (action.getLemmingActionType()) {
				case Parachute:
					return verygood(21);
				default:
					return verybad(state);
				}

				// Grimpe et bloque
			case 2:
				switch (action.getLemmingActionType()) {
				case Turnback:
					return verygood(1);
				default:
					return verybad(state);
				}
				// tombe pres d'une falaise
			case 3:
				switch (action.getLemmingActionType()) {
				case Climb:
					return noimpact(4);
				case Parachute:
					return verygood(21);
				default :
					return verybad(state);
				}
				// grimpe
			case 4:
				switch (action.getLemmingActionType()) {
				case Climb:
					return verygood(state);
				case Turnback:
					return bad(1);
				default:
					return verybad(state);
				}
				// normal
			case 5:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(state);
				case Turnback:
					return verybad(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 6:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(1);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// tunnel creusable
			case 7:
				switch (action.getLemmingActionType()) {
				case Dig:
					return good(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel forable
			case 8:
				switch (action.getLemmingActionType()) {
				case Drill:
					return bad(19);
				case Climb:
					good(state);
				case Turnback:
					return verygood(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 9:
				switch (action.getLemmingActionType()) {
				case Walk:
					return good(state);
				case Turnback:
					return verybad(state);
				default:
					return verybad(state);
				}
				// normal et forable
			case 10:
				switch (action.getLemmingActionType()) {
				case Drill:
					return bad(19);
				case Walk:
					return verygood(state);
				case Turnback:
					return verybad(state);
				default:
					verybad(state);
				}
				// face a falaise creusable
			case 11:
				switch (action.getLemmingActionType()) {
				case Climb:
					return verygood(state);
				case Dig:
					return good(state);
				case Turnback:
					noimpact(state);
				default:
					verybad(state);
				}
				// tunnel creusable et forable
			case 12:
				switch (action.getLemmingActionType()) {
				//				case Block:
				//					return bad(state);
				case Dig:
					return verygood(state);
				case Drill:
					return bad(19);
				case Turnback:
					return bad(state);
				default:
					return verybad(state);
				}
				// sol forable face falaise 
				//			case 13:
				//				switch (action.getLemmingActionType()) {
				////				case Block:
				////					return verybad(18);
				//				case Climb:
				//					return verygood(state);
				//				case Drill:
				//					return good(19);
				//				case Turnback:
				//					return noimpact(state);
				//				default:
				//					return verybad(state);
				//				}
				// sol forable face falaise creusable
				//			case 14:
				//				switch (action.getLemmingActionType()) {
				////				case Block:
				////					return verybad(18);
				//				case Dig:
				//					return verygood(7);
				//				case Climb:
				//					return good(state);
				//				case Drill:
				//					return bad(19);
				//				case Turnback:
				//					return noimpact(state);
				//				default:
				//					return verybad(state);
				//				}
				// forable danger falaise 
			case 15:
				switch (action.getLemmingActionType()) {
				case Walk:
					return verygood(1);
				case Drill:
					return bad(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable + danger eau
			case 16:
				switch (action.getLemmingActionType()) {
				case Drill:
					return bad(19);
				case Turnback:
					return veryverygood(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// danger eau
			case 17:
				switch (action.getLemmingActionType()) {
				//				case Block:
				//					return verygood(18);
				case Turnback:
					return veryverygood(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// tunnel vertical forable
			case 19:
				switch (action.getLemmingActionType()) {
				case Climb:
					return good(state);
				case Drill:
					return bad(19);
				case Turnback:
					return bad(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable forable
			case 20:
				switch (action.getLemmingActionType()) {
				case Climb:
					return verygood(state);
				case Drill:
					return bad(19);
				case Dig:
					return good(7);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// chute avec parachute
			case 21:
				switch (action.getLemmingActionType()) {
				case Parachute:
					return veryverygood(21);
				default:
					return verybad(state);
				}
				// tunnel horizontal
			case 22:
				switch (action.getLemmingActionType()) {
				case Turnback:
					return verygood(state);
				default:
					return verybad(state);
				}
				// Sortie
			case 23:
				switch (action.getLemmingActionType()) {
				case Die:
					return veryverygood(state);
				default:
					return verybad(state);
				}
			}

		}
		return bad(state);
	}


	private QFeedback<LemmingProblemState> veryverygood(int state) {
		return new QFeedback<LemmingProblemState>(new LemmingProblemState(state), veryverygood);
	}

	private QFeedback<LemmingProblemState> veryverygood(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, veryverygood);
	}


	private QFeedback<LemmingProblemState> verygood(int state) {
		return new QFeedback<LemmingProblemState>(new LemmingProblemState(state), verygood);
	}

	private QFeedback<LemmingProblemState> verygood(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, verygood);
	}

	private QFeedback<LemmingProblemState> good(int state) {
		return new QFeedback<LemmingProblemState>(new LemmingProblemState(state), good);
	}

	private QFeedback<LemmingProblemState> good(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, good);
	}

	private QFeedback<LemmingProblemState> noimpact(int state) {
		return new QFeedback<LemmingProblemState>(new LemmingProblemState(state), noimpact);
	}

	private QFeedback<LemmingProblemState> noimpact(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, noimpact);
	}

	private QFeedback<LemmingProblemState> bad(int state) {
		return new QFeedback<LemmingProblemState>(new LemmingProblemState(state), bad);
	}

	private QFeedback<LemmingProblemState> bad(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, bad);
	}

	private QFeedback<LemmingProblemState> verybad(int state) {
		return new QFeedback<LemmingProblemState>(new LemmingProblemState(state), verybad);
	}

	private QFeedback<LemmingProblemState> verybad(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, verybad);
	}

	/**
	 * Appeler par un agent pour setter le current state par rapport au perception
	 * @param position de l'agent
	 * @param percepts liste des perceptions de cet agent
	 */
	public void translateCurrentState(boolean parachuteOpen, boolean currentFallMaxreached, boolean isClimbing, CellCoord LemmingPos, List<Perception> perceptions) {

		int stateId = 0;

		/* cas ou on a chuter sans  ouvrir le parachute est qu'on est au sol, avec une chute trop haute : mort */
		if(currentFallMaxreached) {
			if(perceptions.get(0) instanceof TerrainPerception) {
				TerrainPerception terrain = (TerrainPerception) perceptions.get(0);
				if(!terrain.getTerrainElement().isTraversable) {
					/* rien a faire! on est deja a 0 */
				}
			}
		}
		else {
			/* extraction de la position de la sortie */
			ExitPerception exitPerception = null;

			for (Perception perception : perceptions) {
				if(perception instanceof ExitPerception) {
					exitPerception = (ExitPerception) perception;
				}
			}
			CellCoord ExitPos = exitPerception.getExitPosition();

			/* on créé une liste de terrainPerception. 
			 * Nous permet de ne pas avoir à faire des instanceof à tour de bras */
			List<TerrainPerception> terrainPerceptions = new ArrayList<TerrainPerception>();

			for (int i = 0; i< perceptions.size() -1 ; i++) {
				terrainPerceptions.add((TerrainPerception) perceptions.get(i));
			}


			if(LemmingPos.getY() == ExitPos.getY()) {

				/* cas 0 : cas ou on est dans une case danger */
				if(terrainPerceptions.get(5).getTerrainElement().isDanger) {
					stateId += 0;
				}
				/* cas 23 : sur la sortie */
				else if(terrainPerceptions.get(5).getTerrainElement() == TerrainType.EXIT) {
					stateId += 23;
				}
				/* cas 2 : cas ou l'on est en train de grimper et qu'on tape le plafond !*/
				else if(!terrainPerceptions.get(4).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 2;
				}
				/* cas 4 */
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable && isClimbing) {
					stateId += 4;
				}
				/* cas 3 */
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable && !isClimbing) {
					stateId += 3;
				}
				/* cas 1 : tomber chute libre, parachute non ouvert*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& parachuteOpen == false) {
					stateId += 1;
				}
				/* cas 21 : tomber chute libre, parachute ouvert*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& parachuteOpen == true) {
					stateId += 21;
				}
				/* cas 16 bord de danger : eau,  creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isDanger
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 16;
				}
				/* cas 17 : bord de danger : eau */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isDanger
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 17;
				}
				/* cas 15 : bord de falaise creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isTraversable
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 15;
				}
				/* cas 6 : bord de falaise */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isTraversable
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 6;
				}
				/* cas 20 : fond d'un trou, fond et cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable) {
					stateId += 20;

				}
				/* cas 19 : fond d'un trou, fond diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable) {
					stateId += 19;
				}
				/* cas 22 : fond gallerie tunnel horizontal */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable ) {
					stateId += 22;
				}
				/* cas 12 : fond d'une gallerie, cote et fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 12;
				}
				/* cas 7 : fond d'une gallerie, cote creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 7;
				}
				/* cas 8 : fond d'une gallerie, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 8;
				}
				/* cas 14 : bord de montagne, cote et fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 14;
					System.err.println("State " + stateId + "not used. Should never be print...");
				}
				/* cas 13 : bord de montagne, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 13;
					System.err.println("State " + stateId + "not used. Should never be print...");
				}
				/* cas 11 : bord de montagne, cote creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 11;
				}
				/* cas 9 : bord de montagne */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 9;
				}
				/* cas 5 : marche, sol pas creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 5;
				}
				/* cas 10 : marche, sol creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 10;
				}
				else
					System.err.println("Impossible de determiner le current state avec ces percetpions !");







			}
			else if(LemmingPos.getY() < ExitPos.getY()) {
				stateId += LemmingProblemState.NBSTATE_NO_DIRECTION;


				/* cas 0 : cas ou on est dans une case danger */
				if(terrainPerceptions.get(5).getTerrainElement().isDanger) {
					stateId += 0;
				}
				/* cas 23 : sur la sortie */
				else if(terrainPerceptions.get(5).getTerrainElement() == TerrainType.EXIT) {
					stateId += 23;
				}
				/* cas 2 : cas ou l'on est en train de grimper et qu'on tape le plafond !*/
				else if(!terrainPerceptions.get(4).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 2;
				}
				/* cas 4 */
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable && isClimbing) {
					stateId += 4;
				}
				/* cas 3 */
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable && !isClimbing) {
					stateId += 3;
				}
				/* cas 1 : tomber chute libre, parachute non ouvert*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& parachuteOpen == false
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 1;
				}
				/* cas 21 : tomber chute libre, parachute ouvert*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& parachuteOpen == true) {
					stateId += 21;
				}
				/* cas 16 bord de danger : eau,  creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isDanger
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 16;
				}
				/* cas 17 : bord de danger : eau */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isDanger
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 17;
				}
				/* cas 15 : bord de falaise creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isTraversable
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 15;
				}
				/* cas 6 : bord de falaise */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isTraversable
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 6;
				}
				/* cas 20 : fond d'un trou, fond et cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable) {
					stateId += 20;

				}
				/* cas 19 : fond d'un trou, fond diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable) {
					stateId += 19;
				}
				/* cas 22 : fond gallerie tunnel horizontal */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable ) {
					stateId += 22;
				}
				/* cas 12 : fond d'une gallerie, cote et fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 12;
				}
				/* cas 7 : fond d'une gallerie, cote creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 7;
				}
				/* cas 8 : fond d'une gallerie, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 8;
				}
				/* cas 14 : bord de montagne, cote et fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 14;
					System.err.println("State " + stateId + "not used. Should never be print...");
				}
				/* cas 13 : bord de montagne, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 13;
					System.err.println("State " + stateId + "not used. Should never be print...");
				}
				/* cas 11 : bord de montagne, cote creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 11;
				}
				/* cas 9 : bord de montagne */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 9;
				}
				/* cas 5 : marche, sol pas creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 5;
				}
				/* cas 10 : marche, sol creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 10;
				}
				else
					System.err.println("Impossible de determiner le current state avec ces percetpions !");




			}
			else {
				stateId += LemmingProblemState.NBSTATE_NO_DIRECTION*2;


				/* cas 0 : cas ou on est dans une case danger */
				if(terrainPerceptions.get(5).getTerrainElement().isDanger) {
					stateId += 0;
				}
				/* cas 23 : sur la sortie */
				else if(terrainPerceptions.get(5).getTerrainElement() == TerrainType.EXIT) {
					stateId += 23;
				}
				/* cas 2 : cas ou l'on est en train de grimper et qu'on tape le plafond !*/
				else if(!terrainPerceptions.get(4).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 2;
				}
				/* cas 4 */
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable && isClimbing) {
					stateId += 4;
				}
				/* cas 3 */
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable
						&&  !terrainPerceptions.get(2).getTerrainElement().isTraversable && !isClimbing) {
					stateId += 3;
				}
				/* cas 1 : tomber chute libre, parachute non ouvert*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& parachuteOpen == false) {
					stateId += 1;
				}
				/* cas 21 : tomber chute libre, parachute ouvert*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& parachuteOpen == true) {
					stateId += 21;
				}
				/* cas 16 bord de danger : eau,  creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isDanger
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 16;
				}
				/* cas 17 : bord de danger : eau */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isDanger
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 17;
				}
				/* cas 15 : bord de falaise creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isTraversable
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 15;
				}
				/* cas 6 : bord de falaise */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable 
						&& terrainPerceptions.get(1).getTerrainElement().isTraversable
						&& terrainPerceptions.get(2).getTerrainElement().isTraversable) {
					stateId += 6;
				}
				/* cas 20 : fond d'un trou, fond et cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable) {
					stateId += 20;

				}
				/* cas 19 : fond d'un trou, fond diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable) {
					stateId += 19;
				}
				/* cas 22 : fond gallerie tunnel horizontal */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable ) {
					stateId += 22;
				}
				/* cas 12 : fond d'une gallerie, cote et fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 12;
				}
				/* cas 7 : fond d'une gallerie, cote creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 7;
				}
				/* cas 8 : fond d'une gallerie, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 8;
				}
				/* cas 14 : bord de montagne, cote et fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 14;
					System.err.println("State " + stateId + "not used. Should never be print...");
				}
				/* cas 13 : bord de montagne, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 13;
					System.err.println("State " + stateId + "not used. Should never be print...");
				}
				/* cas 11 : bord de montagne, cote creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 11;
				}
				/* cas 9 : bord de montagne */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 9;
				}
				/* cas 5 : marche, sol pas creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 5;
				}
				/* cas 10 : marche, sol creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable) {
					stateId += 10;
				}
				else
					System.err.println("Impossible de determiner le current state avec ces percetpions !");



			}

		}

		currentState = new LemmingProblemState(stateId);
	//	System.out.println("currentState determined : " + currentState.toInt() + " soit:" +( currentState.toInt()%LemmingProblemState.NBSTATE_NO_DIRECTION));
	}



	@Override
	public LemmingProblem clone() {
		try {
			return (LemmingProblem) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
