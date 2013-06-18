package Lemming;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LemmingBody {

	private Sens sens;
	private final int supportedFall = 3;
	private int currentFall;
	private CellCoord cellCoord;
	private PixelCoord pixelCoord;
	private Action currentAction;
	private WeakReference<Environment> myEnvironment;
	private List<Influence> influences;
	
	
	public LemmingBody(CellCoord cellCoord, Environment environment) {
		sens = Sens.RIGHT;
		currentFall = 0;
		currentAction = Action.Walk;
		myEnvironment = new WeakReference<Environment>(environment);
		this.cellCoord = cellCoord;
		this.pixelCoord = cellCoord.toPixelCoord();
		influences = new ArrayList<Influence>();
	}
	

	public void doAction(Action action) {
		int dx = sens.dx;
		int dy = action.dy;
		
		myEnvironment.get().move(this, dx, dy);
	}
	
	public List<Perception> getPerceptions() {
		List<Perception> perceptions;
		
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
	
	public PixelCoord getPixelCoord() {
		return pixelCoord;
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

	public void setPixelCoord(int x, int y) {
		this.pixelCoord.set(x, y);
	}
	
	public void setCurrentAction(Action currentAction) {
		this.currentAction = currentAction;
	}

	public List<Influence> getInfluences() {
		return Collections.unmodifiableList(influences);
	}
}
