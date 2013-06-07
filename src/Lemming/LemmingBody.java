package Lemming;

import java.lang.ref.WeakReference;
import java.util.List;

public class LemmingBody {

	private Sens sens;
	private final int supportedFall = 3;
	private int currentFall;
	private CellCoord cellCoord;
	private PixelCoord pixelCoord;
	private Action currentAction;
	private WeakReference<Environment> myEnvironment;
	
	
	public LemmingBody(CellCoord cellCoord, Environment environment) {
		sens = Sens.RIGHT;
		currentFall = 0;
		currentAction = Action.Walk;
		myEnvironment = new WeakReference<Environment>(environment);
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
		CellCoord newCellCoord = new CellCoord(x, y);
		setCellCoord(newCellCoord);
	}

	public void setPixelCoord(PixelCoord pixelCoord) {
		this.pixelCoord = pixelCoord;
	}
	
	public void setCurrentAction(Action currentAction) {
		this.currentAction = currentAction;
	}
}
