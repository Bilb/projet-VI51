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




	public Lemming(LemmingBody lemmingBody_) {
		lemmingBody = lemmingBody_;
		qProblem = new LemmingProblem();
		qLearning = new QLearning<LemmingProblemState,Action>(qProblem);

	}
	
	public Lemming(LemmingBody lemmingBody_, QLearning<LemmingProblemState,Action> newQLearning) {
		lemmingBody = lemmingBody_;
		qProblem = new LemmingProblem();
		qLearning = newQLearning;

	}

	public void live() {
		if(getLemmingBody() != null && !getLemmingBody().isDoingAction()) {
			consumeInfluences();
			List<Perception> perceptions = lemmingBody.getPerceptions();
			CellCoord position = new CellCoord((int) getPosition().x, (int)getPosition().y);
			
			
			qProblem.translateCurrentState(lemmingBody.getParachute(),
					lemmingBody.getCurrentFall() > lemmingBody.getSupportedFall(),
					lemmingBody.isClimbing(), position, perceptions);
			
			qLearning.learn(NUMBER_OF_QLEARNING_ITERATIONS);

			Action action = qLearning.getBestAction(qProblem.getCurrentState());

			if(action != null) {
				executeAction(new Action(action.getLemmingActionType()));
			}
		}
		else {
			getLemmingBody().doActionTimer();
		}

	}


	private Point2d getPosition() {
		CellCoord cellCoord = lemmingBody.getCellCoord();
		return new Point2d(cellCoord.getX(), cellCoord.getY());
	}

	private void consumeInfluences() {
		boolean fallInfluencePresent = false;
		
		for (Influence influence : lemmingBody.getInfluences()) {
			if(influence instanceof FallInfluence) {
				FallInfluence fallInfluence= (FallInfluence) influence;
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
		System.out.println("executing action : " + action);
		lemmingBody.doAction(action);
	}

	public void suicide() {
		lemmingBody.suicide();
		lemmingBody = null;
	}

	public List<Perception> getPerceptions() {

		return null;
	}

	public QLearning<LemmingProblemState, Action> getQLearning() {
		return qLearning;
	}

	public LemmingBody getLemmingBody() {
		return lemmingBody;
	}

	public void setLemmingBody(LemmingBody lemmingBody) {
		this.lemmingBody = lemmingBody;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
