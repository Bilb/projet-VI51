package Lemming.Agent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import Lemming.CellCoord;
import Lemming.PixelCosmetic;
import Lemming.Sens;
import Lemming.Action.Action;
import Lemming.Action.Action.LemmingActionType;
import Lemming.Action.ActionProcessTag;
import Lemming.Action.ActionTestTag;
import Lemming.Environment.Environment;
import Lemming.Influence.Influence;
import Lemming.Perception.Perception;

/**
 * Cette classe decrit le Body du Lemming
 */
public class LemmingBody extends PixelCosmetic {
	
	private Sens sens; // Indique le sens dans lequel regarde le lemming pour adapter le frustrum
	private boolean alive; // Indique si le Lemming est vivant ou mort
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	private boolean digging; // Indique si le Lemming est en train de creuser
	private boolean drilling; // Indique si le Lemming est en train de forer
	private boolean climbing; // Indique si le Lemming est en train de grimper
	private boolean parachute; // Indique si le Lemming est en train de faire du parachute
	private final int supportedFall = 3; // Hauteur de case pour laquelle une chute est fatale pour le Lemming 
	private int currentFall; // Nombre de case dont chute le lemming actuellement
	private CellCoord cellCoord; 
	private Action currentAction;
	private WeakReference<Environment> myEnvironment;
	private List<Influence> influences;
	
	/**
	 * Constructeur de LemmingBody
	 * @param cellCoord position du Lemming
	 * @param environment environnement dans lequel il sera cree
	 */
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
		influences = new ArrayList<Influence>();
	}
	
	/**
	 * 
	 * @param action l'action a executer par le lemmingBody
	 */
	public void doAction(Action action) {
		if(!action.getBuilded()) {
			action.buildAction(this);
		}
		currentAction = action;
		if(!myEnvironment.get().tryExecute(this,action)){
			// action echoue, reset des flags
			climbing = false;
			parachute = false;
			drilling = false;
			digging = false;
		}
		else {
			// action reussie
		}
		
	}

	/**
	 * 
	 * @return la liste des perceptions du LemmingBody
	 */
	public LinkedList<Perception> getPerceptions() {
		LinkedList<Perception> perceptions;
		
		// ici on utilisise la reference faible sur l'environnement pour appeler sa methode getPerception
		perceptions = myEnvironment.get().getPerceptions(this); 
		
		return perceptions;
	}
	
	/**
	 * 
	 * @param e influence a ajouter
	 */
	public void addInfluences(Influence e) {
		influences.add(e);
	}
	
	/**
	 * Le LemmingBody se suicide en demmandant a l'environnement de le tuer
	 */
	public void suicide() {
		// Ici on utilisise la reference faible sur l'environement 
		myEnvironment.get().kill(this);
	}
	
	/**
	 * On reinitialise la liste des influences en en creant une nouvelle 
	 */
	public void resetInfluences() {
		influences = new ArrayList<Influence>();
	}

	/**
	 * On test la valeur des parametres
	 * @param tag Le tag a tester
	 */
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
		default:
			break;
		}
		return false;
	}
	
	/**
	 * On set le tag passer en paramettre en le transformant en true ou false sur le parametre concerne
	 * @param tag Le tag a setter
	 */
	public Boolean executeProcessTag(ActionProcessTag tag) {
		switch (tag) {
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
		case DRILLING:
			setDrilling(true);
		break;
		case NOT_DRILLING:
			setDrilling(false);
		break;
		case DIGGING:
			setDigging(true);
		break;
		case NOT_DIGGING:
			setDigging(false);
		break;
		case DIE:
			alive = false;
			break;
		default:
			break;
		}
		
		return true;
	}

//---------- Tests ------------/
	public boolean isClimbing() {
		return climbing;
	}
	
	public boolean isParachute() {
		return parachute;
	}
	
	public boolean isDrilling() {
		return drilling;
	}

	public boolean isDigging() {
		return digging;
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
	
	public List<Influence> getInfluences() {
		return Collections.unmodifiableList(influences);
	}
	
	public boolean getParachute() {
		return parachute;
	}
	
//---------- Setters ----------/
	public void setCurrentFall(int supportedFall) {
		this.currentFall = supportedFall;
	}
	
	public void setCellCoord(CellCoord cellCoord) {
		this.cellCoord = cellCoord;
		updatePixel = true;
	}
	
	public void setCellCoord(int x, int y) {
		cellCoord.set(x, y);
		updatePixel = true;
	}

	public void updatePixel() {
		updatePixel = true;
	}

	public void setCurrentAction(Action currentAction) {
		this.currentAction = currentAction;
	}

	public void setParachute(boolean parachute) {
		this.parachute = parachute;
	}
	
	public void setClimbing(boolean climbing) {
		this.climbing = climbing;
	}

	public void setPreviousPosition(CellCoord cellCoord2) {
		previousPosition = cellCoord2;
		
	}
	
	public void setDrilling(boolean drilling) {
		this.drilling = drilling;
	}
	
	public void setDigging(boolean digging) {
		this.digging = digging;
	}
}
