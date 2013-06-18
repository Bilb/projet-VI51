package Lemming.Agent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import Lemming.CellCoord;
import Lemming.PixelCosmetic;
import Lemming.Sens;
import Lemming.Agent.Action.LemmingActionType;
import Lemming.Environment.Environment;
import Lemming.Influence.Influence;
import Lemming.Perception.Perception;

public class LemmingBody extends PixelCosmetic {

	private Sens sens;
	private boolean parachute;
	private boolean blocked;
	private final int supportedFall = 3;
	private int currentFall;
	private CellCoord cellCoord;
	private Action currentAction;
	private WeakReference<Environment> myEnvironment;
	private List<Influence> influences;
	
	
	public LemmingBody(CellCoord cellCoord, Environment environment) {
		sens = Sens.RIGHT;
		currentFall = 0;
		currentAction = new Action(LemmingActionType.Walk);
		setParachute(false);
		myEnvironment = new WeakReference<Environment>(environment);
		this.cellCoord = cellCoord;
		previousPosition = cellCoord;
		pixelCoord = cellCoord.toPixelCoord();
		influences = new ArrayList<Influence>();
	}
	

	public void doAction(Action action) {
		int dx = sens.dx;
		int dy = action.getDy();
		currentAction = action;
		myEnvironment.get().move(this, dx, dy);
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
	}
	
	public void setCellCoord(int x, int y) {
		cellCoord.set(x, y);
		//pixelCoord = cellCoord.toPixelCoord();
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
}
