package Lemming;

import java.util.List;

import javax.vecmath.Point2d;

import Lemming.Action.LemmingActionType;
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
		//TODO : � voir comment �a se g�re avec le Q learning
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
