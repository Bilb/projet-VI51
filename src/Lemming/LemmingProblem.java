package Lemming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.vecmath.Point2d;

import Lemming.Action.LemmingActionType;

import fr.utbm.gi.vi51.learning.qlearning.DefaultQState;
import fr.utbm.gi.vi51.learning.qlearning.QFeedback;
import fr.utbm.gi.vi51.learning.qlearning.QProblem;

public class LemmingProblem implements QProblem<DefaultQState, Action> {


	private DefaultQState states[];
	private List<Action> actions;


	private static final float verygood = 1f;
	private static final float good = 0.5f;
	private static final float noimpact = 0.f;
	private static final float bad = -0.5f;
	private static final float verybad = -1f;


	private DefaultQState currentState;
	private Random generator;




	/**
	 * 
	 */
	private static final long serialVersionUID = 453731937056179988L;



	public LemmingProblem() {

		/* ajouter les etats possible de notre système */
		states = new DefaultQState[16];
		actions = new ArrayList<>();

		states[0] = new DefaultQState(0, "pas de proie présente");
		/* ... */


		actions.add(new Action(LemmingActionType.Climb));
		actions.add(new Action(LemmingActionType.Block));
		actions.add(new Action(LemmingActionType.Dig));
		actions.add(new Action(LemmingActionType.Drill));
		actions.add(new Action(LemmingActionType.Parachute));
		actions.add(new Action(LemmingActionType.Walk));

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
	public DefaultQState getCurrentState() {
		return currentState;
	}

	@Override
	public DefaultQState getRandomState() {
		return states[generator.nextInt(16)];
	}

	@Override
	public List<DefaultQState> getAvailableStates() {
		return Arrays.asList(states);
	}

	@Override
	public List<Action> getAvailableActionsFor(DefaultQState state) {
		return actions;
	}

	@Override
	public QFeedback<DefaultQState> takeAction(DefaultQState state,
			Action action) {
		//todo
		switch (state.toInt()) {
		case 0:
			switch (action.getLemmingActionType()) {
			case Block:
			case Climb:
			case Dig:
			case Drill:
			case Parachute:
			case Walk:
				return verygood(state);
			default:
				return verybad(state);
			}
		default:
			break;
		}


		return null;
	}



	private QFeedback<DefaultQState> verygood(DefaultQState newState) {
		return new QFeedback<DefaultQState>(newState, verygood);
	}

	@SuppressWarnings("unused")
	private QFeedback<DefaultQState> good(DefaultQState newState) {
		return new QFeedback<DefaultQState>(newState, good);
	}

	@SuppressWarnings("unused")
	private QFeedback<DefaultQState> noimpact(DefaultQState newState) {
		return new QFeedback<DefaultQState>(newState, noimpact);
	}

	@SuppressWarnings("unused")
	private QFeedback<DefaultQState> bad(DefaultQState newState) {
		return new QFeedback<DefaultQState>(newState, bad);
	}

	private QFeedback<DefaultQState> verybad(DefaultQState newState) {
		return new QFeedback<DefaultQState>(newState, verybad);
	}

	/**
	 * Appeler par un agent pour setter le current state par rapport au perception
	 * @param position de l'agent
	 * @param percepts liste des perceptions de cet agent
	 */
	public void translateCurrentState(Point2d position, List<Perception> perceptions) {

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
