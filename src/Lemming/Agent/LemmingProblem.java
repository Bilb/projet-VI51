package Lemming.Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Lemming.CellCoord;
import Lemming.Agent.Action.LemmingActionType;
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
		actions.add(new Action(LemmingActionType.Cross));
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
		
		// TODO
		switch (state.toInt()) {
		case 0:
			switch (action.getLemmingActionType()) {
			case Block:
			case Climb:
			case Dig:
			case Drill:
			case Parachute:
				return verygood(state);
			case Walk:
			case Cross:
			case Die:
			case Turnback:
				break;
			}
		default:
			break;
		}

		return null;
	}



	private QFeedback<LemmingProblemState> verygood(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, verygood);
	}

	@SuppressWarnings("unused")
	private QFeedback<LemmingProblemState> good(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, good);
	}

	@SuppressWarnings("unused")
	private QFeedback<LemmingProblemState> noimpact(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, noimpact);
	}

	@SuppressWarnings("unused")
	private QFeedback<LemmingProblemState> bad(LemmingProblemState newState) {
		return new QFeedback<LemmingProblemState>(newState, bad);
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
			List<TerrainPerception> terrainPerceptions = new ArrayList<>();
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
				stateId += LemmingProblemState.NBSTATE_NO_DIRECTION;
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
			else {
				stateId += LemmingProblemState.NBSTATE_NO_DIRECTION*2;
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
