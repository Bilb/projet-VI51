package Lemming.Agent;

import java.util.List;

import javax.vecmath.Point2d;

import Lemming.CellCoord;
import Lemming.Action.Action;
import Lemming.Action.Action.LemmingActionType;
import Lemming.Influence.FallInfluence;
import Lemming.Influence.Influence;
import Lemming.Perception.Perception;
import Lemming.Perception.TerrainPerception;
import fr.utbm.gi.vi51.learning.qlearning.QLearning;

/**
 * Cette classe represente l'agent Lemming evoluant dans un environnement
 *
 */
public class Lemming {

	private final static int NUMBER_OF_QLEARNING_ITERATIONS = 6;

	private LemmingBody lemmingBody;
	private Action action;
	/** The instance of the learning problem to use.
	 */
	private final LemmingProblem qProblem;

	/** The learning algorithm.
	 */
	private final QLearning<LemmingProblemState,Action> qLearning;



	/**
	 * Construit le lemming est lui associe le body
	 * @param lemmingBody_ le body du lemming
	 */
	public Lemming(LemmingBody lemmingBody_) {
		lemmingBody = lemmingBody_;
		qProblem = new LemmingProblem();
		qLearning = new QLearning<LemmingProblemState,Action>(qProblem);

	}
	
	/**
	 * Construit le lemming est lui associe le body, ainsi qu'un graphe pre existant 
	 * @param lemmingBody_ le body a associe
	 * @param newQLearning le graphe de qlearning a associe a cet agent.
	 */
	public Lemming(LemmingBody lemmingBody_, QLearning<LemmingProblemState,Action> newQLearning) {
		lemmingBody = lemmingBody_;
		qProblem = new LemmingProblem();
		qLearning = newQLearning;

	}

	
	/**
	 * Appeler pour faire prendre des decisions a l'agent
	 */
	public void live() {
		if(getLemmingBody() != null) {
			// recuperer et extraire les influences de l'environnement
			consumeInfluences();
			
			List<Perception> perceptions = lemmingBody.getPerceptions();
			CellCoord position = new CellCoord((int) getPosition().x, (int)getPosition().y);
			
			
			/* traductions de tout cela en un etat du probleme */
			qProblem.translateCurrentState(lemmingBody.getParachute(),
					lemmingBody.getCurrentFall() > lemmingBody.getSupportedFall(),
					lemmingBody.isClimbing(), position, perceptions);
			
			/* apprentissage de l'algo de qlearning */
			qLearning.learn(NUMBER_OF_QLEARNING_ITERATIONS);

			Action action = qLearning.getBestAction(qProblem.getCurrentState());

			if(action != null) {
				executeAction(new Action(action.getLemmingActionType()));
			}
		}

	}


	/**
	 * Retourne la position de ce lemming
	 * @return la position du lemming
	 */
	private Point2d getPosition() {
		CellCoord cellCoord = lemmingBody.getCellCoord();
		return new Point2d(cellCoord.getX(), cellCoord.getY());
	}

	/**
	 * Lit et traite les influences deposee par l'environnement.
	 * Dans notre cas, seule les FallInfluences nous interesse.
	 */
	private void consumeInfluences() {
		boolean fallInfluencePresent = false;
		
		/* est ce que une influence de chute est presente ? */
		for (Influence influence : lemmingBody.getInfluences()) {
			if(influence instanceof FallInfluence) {
				FallInfluence fallInfluence= (FallInfluence) influence;
				
				//si oui, on ajoute une case a notre chute courante
				lemmingBody.setCurrentFall(lemmingBody.getCurrentFall() + fallInfluence.getNbCell());
				fallInfluencePresent = true;
			}
		}

		/* si il n'y a pas de FallInfluence, c'est que l'on ne tombe pas, ou plus. 
		 * On reset le currentFall du body */
		if(!fallInfluencePresent) {
			lemmingBody.setCurrentFall(0);
		}
		lemmingBody.resetInfluences();
	}

	public void executeAction(Action action) {
		if(action.getLemmingActionType() == LemmingActionType.Climb) {
			// check pour voir si c'est se hisser
			List<Perception> perceptions = lemmingBody.getPerceptions();
			TerrainPerception tp2 = (TerrainPerception) perceptions.get(2);
			TerrainPerception tp3 = (TerrainPerception) perceptions.get(3);

			if(tp2 != null && !tp2.getTerrainElement().isTraversable 
					&& tp3 != null && tp3.getTerrainElement().isTraversable) {
					action.setLemmingActionType(LemmingActionType.SeHisser);
			}

		}
		//System.out.println("executing action : " + action);
		lemmingBody.doAction(action);
	}

	/**
	 * Suicide ce lemming.
	 */
	public void suicide() {
		lemmingBody.suicide();
		lemmingBody = null;
	}

	/**
	 * retourne le qlearning associe a ce lemming. Utile pour l'implanter dans un nouveau Lemming
	 * @return
	 */
	public QLearning<LemmingProblemState, Action> getQLearning() {
		return qLearning;
	}

	/**
	 * Retourne le body de ce lemming
	 * @return
	 */
	public LemmingBody getLemmingBody() {
		return lemmingBody;
	}


}
