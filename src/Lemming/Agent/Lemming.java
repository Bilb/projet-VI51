package Lemming.Agent;

import java.util.List;

import javax.vecmath.Point2d;

import Action.Action;
import Action.Action.LemmingActionType;
import Lemming.CellCoord;
import Lemming.Influence.FallInfluence;
import Lemming.Influence.Influence;
import Lemming.Influence.MoveInfluence;
import Lemming.Perception.Perception;
import Lemming.Perception.TerrainPerception;
import fr.utbm.gi.vi51.learning.qlearning.DefaultQState;
import fr.utbm.gi.vi51.learning.qlearning.QLearning;

public class Lemming {

	private final static int NUMBER_OF_QLEARNING_ITERATIONS = 5;

	private LemmingBody lemmingBody;
	private Action action;
	/** The instance of the learning problem to use.
	 */
	private final LemmingProblem qProblem;

	/** The learning algorithm.
	 */
	private final QLearning<DefaultQState,Action> qLearning;

	private boolean test = true;




	public Lemming(LemmingBody lemmingBody_) {
		lemmingBody = lemmingBody_;
		qProblem = new LemmingProblem();
		qLearning = new QLearning<>(qProblem);


	}

	public void live() {
		if(!lemmingBody.isBlocked()) {
			consumeInfluences();
			List<Perception> perceptions = lemmingBody.getPerceptions();

			TerrainPerception tp0 = (TerrainPerception) perceptions.get(0);
			TerrainPerception tp1 = (TerrainPerception) perceptions.get(1);
			TerrainPerception tp2 = (TerrainPerception) perceptions.get(2);
			if(tp0 != null && tp0.getTerrainElement().isDiggable) {
				System.out.println("DRILL");
				executeAction(LemmingActionType.Drill);
			}
			else if(tp2 != null &&tp2.getTerrainElement().isDiggable){			
				System.out.println("DIG");
				executeAction(LemmingActionType.Dig);
			}
//			else if(tp1 != null && tp1.getTerrainElement().isTraversable
//					&& tp2 != null && tp2.getTerrainElement().isTraversable){
//				System.out.println("BLOCK");
//				executeAction(LemmingActionType.Block);
//			}

			else if(tp2 != null && !tp2.getTerrainElement().isTraversable && tp2.getTerrainElement().isSolid){
				executeAction(LemmingActionType.Climb);
				System.out.println("CLIMB");
			}

			else if(tp2 != null && !tp2.getTerrainElement().isTraversable){	
				System.out.println("TURN");
				executeAction(LemmingActionType.Turnback);
			}
			else if(tp2 != null){					
	System.out.println("WALK");
	executeAction(LemmingActionType.Walk);
			}	
			else {
				executeAction(LemmingActionType.Turnback);
			}
			 if( lemmingBody.getCurrentFall() > 0){
				System.out.println("PARA");
				//executeAction(new Action(LemmingActionType.Parachute));
				lemmingBody.setParachute(true);
			}
			else if(lemmingBody.getCurrentFall() == 0 ){
				lemmingBody.setParachute(false);
			}
			// TODO
			/*qProblem.translateCurrentState(getPosition(), perceptions);
		qLearning.learn(NUMBER_OF_QLEARNING_ITERATIONS);

		Action action = qLearning.getBestAction(qProblem.getCurrentState());


		if(action != null) {
			if(action.getLemmingActionType() == LemmingActionType.Walk) {

			}
		}*/
		}
		else {
			suicide();
		}
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
		// ici, il ne se rendra jamais compte qu'il s'ecrase comme une merde ! ben des fois il s'écrase pas..
		if(!fallInfluencePresent) {
			lemmingBody.setCurrentFall(0);
		}
	}

	public Action chooseAction(List<Perception> perceptions) {

		// QLEARNING?????
		return action;
	}

	public void executeAction(LemmingActionType action) {
		if(action == LemmingActionType.Climb) {
			// check pour voir si c'est se hisser
			List<Perception> perceptions = lemmingBody.getPerceptions();

			TerrainPerception tp0 = (TerrainPerception) perceptions.get(0);
			TerrainPerception tp1 = (TerrainPerception) perceptions.get(1);
			TerrainPerception tp2 = (TerrainPerception) perceptions.get(2);
			TerrainPerception tp3 = (TerrainPerception) perceptions.get(3);
			
			if(tp2 != null && !tp2.getTerrainElement().isTraversable 
					&& tp3 != null && tp3.getTerrainElement().isTraversable) {
					action = LemmingActionType.SeHisser;
			}
			
		}
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
