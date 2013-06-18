package Lemming.Agent;

import java.util.List;

import javax.vecmath.Point2d;

import Lemming.CellCoord;
import Lemming.Agent.Action.LemmingActionType;
import Lemming.Influence.FallInfluence;
import Lemming.Influence.Influence;
import Lemming.Influence.MoveInfluence;
import Lemming.Perception.Perception;
import fr.utbm.gi.vi51.learning.qlearning.DefaultQState;
import fr.utbm.gi.vi51.learning.qlearning.QLearning;

public class Lemming {

	private final static int NUMBER_OF_QLEARNING_ITERATIONS = 5;

	//private CellCoord cellCoord;
	private LemmingBody lemmingBody;
	private Action action;
	/** The instance of the learning problem to use.
	 */
	private final LemmingProblem qProblem;

	/** The learning algorithm.
	 */
	private final QLearning<DefaultQState,Action> qLearning;




	public Lemming(CellCoord cellCoord_, LemmingBody lemmingBody_) {
		lemmingBody = lemmingBody_;
		qProblem = new LemmingProblem();
		qLearning = new QLearning<>(qProblem);


	}

	public void live() {
		consumeInfluences();
		List<Perception> perceptions = lemmingBody.getPerceptions();
		
		


		// TODO
		/*qProblem.translateCurrentState(getPosition(), perceptions);
		qLearning.learn(NUMBER_OF_QLEARNING_ITERATIONS);

		Action action = qLearning.getBestAction(qProblem.getCurrentState());


		if(action != null) {
			if(action.getLemmingActionType() == LemmingActionType.Walk) {
				
			}
		}*/


		executeAction(new Action(LemmingActionType.Walk));
	}


	@SuppressWarnings("unused")
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
			else if(influence instanceof MoveInfluence) {
				MoveInfluence moveInfluence= (MoveInfluence) influence;

				if(moveInfluence.getMovementSucess()) {
					//TODO : faire quelque chose avec ca : bad ou bon qlearning par exemple!
				}
				else {

				}
			}
		}

		/* si il n'y a pas de FallInfluence, c'est que l'on ne tombe pas, ou plus. 
		 * On reset le currentFall du body */
		//TODO : a voir comment ca se gere avec le Q learning : peut être que si on le reset
		// ici, il ne se rendra jamais compte qu'il s'ecrase comme une merde !
		if(!fallInfluencePresent) {
			lemmingBody.setCurrentFall(0);
		}
	}

	public Action chooseAction(List<Perception> perceptions) {

		// QLEARNING?????
		return action;
	}

	public void executeAction(Action action) {
		lemmingBody.doAction(action);
	}

	public void suicide() {
		lemmingBody.suicide();
		lemmingBody = null;
	}

	public List<Perception> getPerceptions() {

		return null;
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
