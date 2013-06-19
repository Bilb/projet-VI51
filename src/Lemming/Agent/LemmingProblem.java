package Lemming.Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Action.Action;
import Action.Action.LemmingActionType;
import Lemming.CellCoord;
import Lemming.Perception.ExitPerception;
import Lemming.Perception.Perception;
import Lemming.Perception.TerrainPerception;
import fr.utbm.gi.vi51.learning.qlearning.QFeedback;
import fr.utbm.gi.vi51.learning.qlearning.QProblem;

public class LemmingProblem implements QProblem<LemmingProblemState, Action> {


	private LemmingProblemState states[];
	private List<Action> actions;


	private static final float verygood = 1f;
	private static final float good = 0.5f;
	private static final float noimpact = 0.f;
	private static final float bad = -0.5f;
	private static final float verybad = -1f;

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
		actions.add(new Action(LemmingActionType.Block));
		actions.add(new Action(LemmingActionType.Dig));
		actions.add(new Action(LemmingActionType.Drill));
		actions.add(new Action(LemmingActionType.Parachute));
		actions.add(new Action(LemmingActionType.Walk));
		//actions.add(new Action(LemmingActionType.Cross));
		actions.add(new Action(LemmingActionType.Turnback));

		currentState = null;
		generator = new Random();
	}


	@Override
	public float getAlpha() {
		return 0.5f;
	}

	@Override
	public float getGamma() {
		return 0.5f;
	}

	@Override
	public float getRho() {
		return 0.5f;
	}

	@Override
	public float getNu() {
		return 0.5f;
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
		return actions;
	}

	@Override
	public QFeedback<LemmingProblemState> takeAction(LemmingProblemState state,
			Action action) {

		switch (state.getExitDirection()) {
		// La sortie est sur le meme axe que nous
		case NONE:
			switch (state.toInt()) {
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
				case Climb:
					return noimpact(state);
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
					return good(state);
				case Parachute:
					return verygood(1);
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
				case Block:
					return bad(18);
				case Walk:
					return verygood(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 6:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				case Walk:
					return bad(1);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// tunnel creusable
			case 7:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
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
				case Block:
					return verybad(18);
				case Drill:
					return bad(19);
				case Turnback:
					return verygood(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 9:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return verygood(state);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// normal et forable
			case 10:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
				case Drill:
					return bad(19);
				case Walk:
					return verygood(state);
				case Turnback:
					return noimpact(state);
				default:
					verybad(state);
				}
				// face a falaise creusable
			case 11:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
				case Climb:
					return good(state);
				case Dig:
					return verygood(state);
				case Turnback:
					noimpact(state);
				default:
					verybad(state);
				}
				// tunnel creusable et forable
			case 12:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(state);
				case Dig:
					return verygood(state);
				case Drill:
					return good(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable face falaise 
			case 13:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return verygood(state);
				case Drill:
					return good(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable face falaise creusable
			case 14:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
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
				}
				// forable danger falaise 
			case 15:
				switch (action.getLemmingActionType()) {
				case Block:
					return good(18);
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
				case Block:
					return verygood(18);
				case Drill:
					return bad(19);
				case Turnback:
					return good(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// danger eau
			case 17:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				case Turnback:
					return good(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// bloque
			case 18:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				default:
					return verybad(state);
				}
				// tunnel vertical forable
			case 19:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return noimpact(state);
				case Drill:
					return good(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable forable
			case 20:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return noimpact(state);
				case Drill:
					return good(19);
				case Dig:
					return good(7);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunel vertical
			case 21:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return verygood(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable
			case 22:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return noimpact(state);
				case Dig:
					return verygood(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
			}

			// La sortie est en dessous de nous
		case BOTTOM:
			switch (state.toInt()-23) {
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
				case Climb:
					return bad(state);
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
					return bad(state);
				case Parachute:
					return verygood(1);
				default :
					return verybad(state);
				}
				// grimpe
			case 4:
				switch (action.getLemmingActionType()) {
				case Climb:
					return noimpact(state);
				case Turnback:
					return bad(1);
				default:
					return verybad(state);
				}
				// normal
			case 5:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Walk:
					return verygood(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 6:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				case Walk:
					return bad(1);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// tunnel creusable
			case 7:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
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
				case Block:
					return verybad(18);
				case Drill:
					return verygood(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 9:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return noimpact(state);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// normal et forable
			case 10:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
				case Drill:
					return verygood(19);
				case Walk:
					return good(state);
				case Turnback:
					return noimpact(state);
				default:
					verybad(state);
				}
				// face a falaise creusable
			case 11:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
				case Climb:
					return bad(state);
				case Dig:
					return verygood(state);
				case Turnback:
					noimpact(state);
				default:
					verybad(state);
				}
				// tunnel creusable et forable
			case 12:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(state);
				case Dig:
					return good(state);
				case Drill:
					return verygood(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable face falaise 
			case 13:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return bad(state);
				case Drill:
					return verygood(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable face falaise creusable
			case 14:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
				case Dig:
					return good(7);
				case Climb:
					return bad(state);
				case Drill:
					return verygood(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// forable danger falaise 
			case 15:
				switch (action.getLemmingActionType()) {
				case Block:
					return good(18);
				case Walk:
					return noimpact(1);
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
				case Block:
					return verygood(18);
				case Drill:
					return noimpact(19);
				case Turnback:
					return good(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// danger eau
			case 17:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				case Turnback:
					return good(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// bloque
			case 18:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				default:
					return verybad(state);
				}
				// tunnel vertical forable
			case 19:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return bad(state);
				case Drill:
					return verygood(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable forable
			case 20:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return bad(state);
				case Drill:
					return verygood(19);
				case Dig:
					return good(7);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunel vertical
			case 21:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return good(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable
			case 22:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return bad(state);
				case Dig:
					return verygood(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
			}

			// la sortie est au dessus de nous
		case TOP:
			switch (state.toInt()) {
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
				case Climb:
					return verygood(state);
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
					return verygood(state);
				case Parachute:
					return good(1);
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
				case Block:
					return bad(18);
				case Walk:
					return verygood(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 6:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				case Walk:
					return bad(1);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// tunnel creusable
			case 7:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
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
				case Block:
					return verybad(18);
				case Drill:
					return bad(19);
				case Turnback:
					return verygood(state);
				default:
					return verybad(state);
				}
				// face a une falaise
			case 9:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return verygood(state);
				case Turnback:
					return good(state);
				default:
					return verybad(state);
				}
				// normal et forable
			case 10:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
				case Drill:
					return bad(19);
				case Walk:
					return verygood(state);
				case Turnback:
					return noimpact(state);
				default:
					verybad(state);
				}
				// face a falaise creusable
			case 11:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
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
				case Block:
					return bad(state);
				case Dig:
					return verygood(state);
				case Drill:
					return bad(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable face falaise 
			case 13:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return verygood(state);
				case Drill:
					return bad(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// sol forable face falaise creusable
			case 14:
				switch (action.getLemmingActionType()) {
				case Block:
					return verybad(18);
				case Dig:
					return good(7);
				case Climb:
					return verygood(state);
				case Drill:
					return bad(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// forable danger falaise 
			case 15:
				switch (action.getLemmingActionType()) {
				case Block:
					return good(18);
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
				case Block:
					return verygood(18);
				case Drill:
					return bad(19);
				case Turnback:
					return good(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// danger eau
			case 17:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				case Turnback:
					return good(state);
				case Walk:
					return verybad(0);
				default:
					return verybad(state);
				}
				// bloque
			case 18:
				switch (action.getLemmingActionType()) {
				case Block:
					return verygood(18);
				default:
					return verybad(state);
				}
				// tunnel vertical forable
			case 19:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return verygood(state);
				case Drill:
					return bad(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable forable
			case 20:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
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
				// tunel vertical
			case 21:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return verygood(state);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
				// tunnel vertical creusable
			case 22:
				switch (action.getLemmingActionType()) {
				case Block:
					return bad(18);
				case Climb:
					return verygood(state);
				case Dig:
					return good(19);
				case Turnback:
					return noimpact(state);
				default:
					return verybad(state);
				}
			}
		}
		return noimpact(state);
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
	public void translateCurrentState(boolean currentFallMaxreached, boolean isClimbing, CellCoord LemmingPos, List<Perception> perceptions) {

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
				/* cas 2 */
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
				/* cas 1 : tomber chute libre sans possibilite de se rattraper*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable) {
					stateId += 1;
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
				/* cas 21 : fond d'un trou, rien de diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 21;
				}
				/* cas 20 : fond d'un trou, fond et cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 20;
				}
				/* cas 19 : fond d'un trou, fond diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 19;
				}
				/* cas 22 : fond d'un trou, cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
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
				}
				/* cas 13 : bord de montagne, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 13;
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
			else if(LemmingPos.getY() > ExitPos.getY()) {
				stateId += 9;

				/* cas ou on est dans une case danger */
				if(terrainPerceptions.get(5).getTerrainElement().isDanger) {
					stateId += 0;
				}
				/* cas 2 */
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
				/* cas 1 : tomber chute libre sans possibilite de se rattraper*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable) {
					stateId += 1;
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
				/* cas 21 : fond d'un trou, rien de diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 21;
				}
				/* cas 20 : fond d'un trou, fond et cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 20;
				}
				/* cas 19 : fond d'un trou, fond diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 19;
				}
				/* cas 22 : fond d'un trou, cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
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
				}
				/* cas 13 : bord de montagne, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 13;
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

				/* cas ou on est dans une case danger */
				if(terrainPerceptions.get(5).getTerrainElement().isDanger) {
					stateId += 0;
				}
				/* cas 2 */
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
				/* cas 1 : tomber chute libre sans possibilite de se rattraper*/
				else if( terrainPerceptions.get(0).getTerrainElement().isTraversable) {
					stateId += 1;
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
				/* cas 21 : fond d'un trou, rien de diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 21;
				}
				/* cas 20 : fond d'un trou, fond et cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 20;
				}
				/* cas 19 : fond d'un trou, fond diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 19;
				}
				/* cas 22 : fond d'un trou, cote diggable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& terrainPerceptions.get(2).getTerrainElement().isDiggable) {
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
				}
				/* cas 13 : bord de montagne, fond creusable */
				else if(!terrainPerceptions.get(0).getTerrainElement().isTraversable
						&& !terrainPerceptions.get(2).getTerrainElement().isTraversable
						&& terrainPerceptions.get(4).getTerrainElement().isTraversable
						&& terrainPerceptions.get(0).getTerrainElement().isDiggable 
						&& !terrainPerceptions.get(2).getTerrainElement().isDiggable) {
					stateId += 13;
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
