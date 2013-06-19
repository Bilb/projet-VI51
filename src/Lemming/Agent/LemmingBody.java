package Lemming.Agent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sun.nio.cs.ext.ISCII91;

import Action.Action;
import Action.Action.LemmingActionType;
import Action.ActionProcessTag;
import Action.ActionTestTag;
import Lemming.CellCoord;
import Lemming.PixelCosmetic;
import Lemming.Sens;
import Lemming.Environment.Environment;
import Lemming.Influence.Influence;
import Lemming.Perception.Perception;

public class LemmingBody extends PixelCosmetic {

	private Sens sens;
	private boolean alive;
	public boolean isAlive() {
		return alive;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
	}


	private boolean climbing;
	private boolean parachute;
	private boolean blocked;
	private final int supportedFall = 3;
	private int currentFall;
	private CellCoord cellCoord;
	private Action currentAction;
	private Action lastAction;
	private WeakReference<Environment> myEnvironment;
	private List<Influence> influences;
	
	
	public LemmingBody(CellCoord cellCoord, Environment environment) {
		alive = true;
		sens = Sens.RIGHT;
		currentFall = 0;
		setParachute(false);
		myEnvironment = new WeakReference<Environment>(environment);
		this.cellCoord = cellCoord;
		previousPosition = null;
		pixelCoord = cellCoord.toPixelCoord();
		currentAction = null;
		lastAction = null;
		influences = new ArrayList<Influence>();
	}
	

	public void doAction(Action action) {
		if(!action.getBuilded()) {
			action.buildAction(this);
			//System.out.println("builded false: " + action.getActionTestList().get(0).getTag());
		}
		currentAction = action;
//		System.out.println("Action: tag :::" + currentAction.getActionTestList().get(0).getTag() + " cell" + currentAction.getActionTestList().get(0).getCell());
//		System.out.println("Action:2 tag :::" + currentAction.getActionTestList().get(1).getTag() + " cell" + currentAction.getActionTestList().get(1).getCell());
//		System.out.println("Action Pr2 tag :::" + currentAction.getActionProcessList().get(0).getTag() + " cell" + currentAction.getActionProcessList().get(0).getCell());
		
		if(!myEnvironment.get().tryExecute(this,currentAction)){
			// action échoué, reset des flags
			climbing = false;
			parachute = false;
		}
		else {
			// action réussie
			lastAction = action;
		}
	}
	
	public LinkedList<Perception> getPerceptions() {
		LinkedList<Perception> perceptions;
		
		perceptions = myEnvironment.get().getPerceptions(this);
		
		return perceptions;
	}
	
	
	public void addInfluences(Influence e) {
		
		influences.add(e);
	}
	
	
	
	public void suicide() {
		myEnvironment.get().kill(this);
	}
	
	
	
	
	
	
	
	
	
//---------- Getters ----------/
	public Sens getSens() {
		return sens;
	}

	public int getSupportedFall() {
		return supportedFall;
	}
	
	public int getCurrentFall() {
		return currentFall;
	}
	
	public CellCoord getCellCoord() {
		return cellCoord;
	}
	
	public Action getCurrentAction() {
		return currentAction;
	}
	
//---------- Setters ----------/
	
	public void setSens(Sens sens) {
		this.sens = sens;
	}
	
	public void setCurrentFall(int supportedFall) {
		this.currentFall = supportedFall;
	}
	
	public void setCellCoord(CellCoord cellCoord) {
		this.cellCoord = cellCoord;
		updatePixel = true;
	}
	
	public void setCellCoord(int x, int y) {
		cellCoord.set(x, y);
		//pixelCoord = cellCoord.toPixelCoord();
		updatePixel = true;
	}

	public void updatePixel() {
		updatePixel = true;
	}

	public void setCurrentAction(Action currentAction) {
		this.currentAction = currentAction;
	}

	public List<Influence> getInfluences() {
		return Collections.unmodifiableList(influences);
	}


	public boolean isParachute() {
		return parachute;
	}


	public void setParachute(boolean parachute) {
		this.parachute = parachute;
	}


	public boolean isBlocked() {
		return blocked;
	}


	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
		
	}
	
	public Boolean executeTestTag(ActionTestTag tag) {
		switch (tag) {
		case PARACHUTE:
			return isParachute();
		case NOT_FALLING:
			return currentFall == 0;
		case CLIMBING:
			return isClimbing();
		case NOT_CLIMBING:
			return !isClimbing();
		}
		return blocked;
		
	}
	
	public Boolean executeProcessTag(ActionProcessTag tag) {
		switch (tag) {
		case BLOCK:
			setBlocked(true);
		break;
		case PARACHUTE:
			setParachute(true);
		break;
		case NOT_PARACHUTE:
			setParachute(false);
		break;
		case NOT_CLIMBING:
			setClimbing(false);
		break;
		case CLIMBING:
			setClimbing(true);
		break;
		case TURNBACK:
			 sens = (sens == Sens.LEFT) ? Sens.RIGHT: Sens.LEFT;
		break;
		case DIE:
			alive = false;
			break;
		}
		return true;
		
	}


	public boolean isClimbing() {
		return climbing;
	}


	public void setClimbing(boolean climbing) {
		this.climbing = climbing;
	}


	public void setPreviousPosition(CellCoord cellCoord2) {
		previousPosition = cellCoord2;
		
	}
}
