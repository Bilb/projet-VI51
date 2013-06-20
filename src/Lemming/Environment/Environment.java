package Lemming.Environment;

import java.util.LinkedList;

import javax.vecmath.Point2d;

import Lemming.CellCoord;
import Lemming.Action.Action;
import Lemming.Action.ActionProcess;
import Lemming.Action.ActionTest;
import Lemming.Agent.LemmingBody;
import Lemming.Influence.FallInfluence;
import Lemming.Perception.ExitPerception;
import Lemming.Perception.Perception;
import Lemming.Perception.TerrainPerception;


/* TODO : Faire attention a l'ordre DX DY dans les arrays*/
public class Environment{


	//private CellCoord spawnPos;

	private CellCoord exitPos;

	private  TerrainType[][] map;

	private Point2d envSize;

	private LinkedList<LemmingBody> lemmingBodies;
	private CellCoord movebuffer;

	public Environment (Point2d  size, int [][] envmap, CellCoord spawnPosition, int nbLemmings) {

		envSize = size;
		movebuffer = new CellCoord();
		//We retrieve all the terrain types to easily set them in the map array 
		TerrainType[] terrains = TerrainType.values();

		//Generation of the map
		this.map=new TerrainType[(int)size.y][(int)size.x];
		for(int l=0;l<size.x;l++) {
			for(int c=0;c<size.y;c++) {
				this.map[l][c] = terrains[envmap[l][c]];
				if(envmap[l][c] == TerrainType.EXIT.ordinal())
					exitPos= new CellCoord(c, l);
			}
		}

		// We store the lemmings into the array of agent of the environmnent
		this.lemmingBodies=new LinkedList<LemmingBody>();


	}

	public LinkedList<Perception> getPerceptions(LemmingBody body) {

		CellCoord bodyPosition = body.getCellCoord();
		LinkedList<Perception> percLst = new LinkedList<Perception>();
		int downY = bodyPosition.getY()+1;
		int upY = bodyPosition.getY()-1;
		int frontX = bodyPosition.getX()+body.getSens().dx;
		//Get the data from the frustrum 
		//down
		if(downY <this.envSize.y) {
			percLst.add(new TerrainPerception(this.map[bodyPosition.getY()+1][bodyPosition.getX()]));
			// down next
			if(frontX < this.envSize.x && frontX >= 0)
				percLst.add(new TerrainPerception(this.map[bodyPosition.getY()+1][bodyPosition.getX()+body.getSens().dx]));
			else
				percLst.add(null);
		}
		else {
			percLst.add(null);
			percLst.add(null);
		}
		//next
		if(frontX < this.envSize.x && frontX >= 0) {
			percLst.add(new TerrainPerception(this.map[bodyPosition.getY()][bodyPosition.getX()+body.getSens().dx]));
			//up next
			if(upY >= 0)
				percLst.add(new TerrainPerception(this.map[bodyPosition.getY()-1][bodyPosition.getX()+body.getSens().dx]));
			else {

				percLst.add(null);
			}
		}
		else {
			percLst.add(null);
			percLst.add(null);
		}
		//up
		if(upY >=0)
			percLst.add(new TerrainPerception(this.map[bodyPosition.getY()-1][bodyPosition.getX()]));
		else
			percLst.add(null);
		//bodyPos
		percLst.add(new TerrainPerception(this.map[bodyPosition.getY()][bodyPosition.getX()]));
		//Exit position
		percLst.add(new ExitPerception(exitPos));
		

		return percLst;
	}

	public void kill(LemmingBody lemmingBody) {
		lemmingBodies.remove(lemmingBody);

	}

	//	public void move(LemmingBody body, int dx, int dy) {
	//
	//		CellCoord bodyPosition = body.getCellCoord();
	//
	//
	//		int newX = bodyPosition.getX()+dx;
	//		int newY = bodyPosition.getY()+dy;	
	//		int groundOfPos = bodyPosition.getY()+1;
	//		boolean turnOffParachute = true;
	//		// si on tombe
	//		if(groundOfPos < this.envSize.y  && map[groundOfPos][bodyPosition.getX()].isTraversable && dy != -1) {
	//			System.out.println("applying gravity");
	//			turnOffParachute = false;
	//			applyGravity(body);
	//		}
	//		else if(body.getCurrentAction().getLemmingActionType() == LemmingActionType.Block) {
	//			body.setBlocked(true);
	//			map[bodyPosition.getY()][bodyPosition.getX()] = TerrainType.LEMMING_BLOCKED;
	//		}
	//		else if(body.getCurrentAction().getLemmingActionType() == LemmingActionType.Dig) {
	//			int front = bodyPosition.getX()+1;
	//			int ground = bodyPosition.getY()+1;
	//			if(front < envSize.x && ground < envSize.y
	//					&& map[bodyPosition.getY()][front].isDiggable) {
	//				map[bodyPosition.getY()][front] = TerrainType.EMPTY;
	//			}
	//		}
	//		// si on avance
	//		else if (Math.abs(dx) == 1 && Math.abs(dy) == 0) {	// mouvement de marche
	//			if(newX<this.envSize.x 
	//					&& newX	>=	0 
	//					&& groundOfPos < this.envSize.y ) {
	//				// la case de destination peut acceuilir le lemming
	//				// la case de départ est soutenu et permet la marche
	//				if(	map[newY][newX].isTraversable && map[groundOfPos][bodyPosition.getX()].isSolid) {
	//					System.out.println("move: newX: " + newX +  "newY:" + newY);
	//					body.setUpdatePixel(bodyPosition);
	//					body.setCellCoord(newX, newY);
	//				}
	//			}
	//		}
	//
	//		
	//		// on grimpe? 
	//		else if(dy == -1) {
	//			int senseX = bodyPosition.getX() + dx;
	//			int cX = bodyPosition.getX();
	//			int up = bodyPosition.getY()-1;
	//
	//			// on regarde si on a trouv� une plateforme pour grimper
	//			if(senseX<this.envSize.x && senseX >= 0 && up >= 0 && up < this.envSize.y
	//					&&	map[up][senseX].isSolid && map[bodyPosition.getY()][senseX].isSolid) {
	//				body.setParachute(false);
	//				body.setUpdatePixel(bodyPosition);
	//				body.setCellCoord(cX, up);
	//				System.out.println("climb: " + up + " " + cX + " dx :" + dx);
	//			}
	//		}
	//		// on creuse ?
	//		else if(dy == 1) {
	//			int down = bodyPosition.getY()+1;
	//			int cX = bodyPosition.getX();
	//			System.out.println("drillAct: " + down + " " + cX);
	//			if(down < envSize.y && map[down][cX].isDiggable) {
	//				map[down][cX] = TerrainType.EMPTY;
	//				body.setUpdatePixel(bodyPosition);
	//				body.setCellCoord(cX, down);
	//				System.out.println("drill: " + down + " " + cX);
	//			}
	//		}
	//		if(turnOffParachute) {
	//			body.setParachute(false);
	//		}
	//
	//
	//		//Glearning.getActionPerformed(  VOIr comment rï¿½cuperer l'action et le isPrformed
	//	}


	private boolean applyGravity(CellCoord pos) {
		int Yg = pos.getY()+1;
		if(Yg < envSize.y && map[Yg][pos.getX()].isTraversable) {
			pos.setY(Yg);
			return true;
		}
		return false;
	}

	private void applyForce(CellCoord force, CellCoord pos) {
		pos.setX(pos.getX()+force.getX());
		pos.setY(pos.getY()+force.getY());
	}



	public CellCoord getExitPos() {
		return exitPos;
	}

	public void setExitPos(CellCoord exitPos) {
		this.exitPos = exitPos;
	}

	public TerrainType[][] getMap() {
		return this.map;
	}

	public void setMap(TerrainType[][] map) {
		this.map = map;
	}
	
	public void setMap(int[][] map, int width, int height) {
		TerrainType[] terrains = TerrainType.values();

		//Generation of the map
		this.map=new TerrainType[height][width];
		for(int l=0;l<height;l++) {
			for(int c=0;c<width;c++) {
				this.map[l][c] = terrains[map[l][c]];
			}
		}
		
	}

	public LinkedList<LemmingBody> getLemmingBodies() {
		return lemmingBodies;
	}


	public Point2d getEnvSize() {
		return envSize;
	}

	public void addLemmingBody(LemmingBody lbody) {
		lemmingBodies.add(lbody);
	}

	public Boolean tryExecute(LemmingBody body,Action action) {
		movebuffer= new CellCoord(body.getCellCoord().getX(),body.getCellCoord().getY());
		//movebuffer.setY();
		Boolean ok = false;
		if(action != null) {
			ok = true; // init
			LinkedList<ActionTest> testList = action.getActionTestList();
			int testIt = 0;
			// test each test in testList
			while (ok && testIt < testList.size()) {
				ActionTest test = testList.get(testIt);
				if(test != null) {
					// test de l'environnement
					if(test.getTag().target == 0) {
						ok = executeTestTag(test);
					}
					//test de l'agent
					else if (test.getTag().target == 1) {
						ok = body.executeTestTag(test.getTag());
					}
				}
				testIt ++;
			}

			// test passe avec succes?
			if(ok) {
				LinkedList<ActionProcess> processList = action.getActionProcessList();
				for (ActionProcess process : processList) {
					if(process.getTag().target == 0) {
						executeProcess(process,body);
					} else {
						body.executeProcessTag(process.getTag());
					}
				}
			}
			if(applyGravity(movebuffer)) {
				body.addInfluences(new FallInfluence(1));
			}
			body.setPreviousPosition(body.getCellCoord());
			body.setCellCoord(movebuffer);
		}
		return ok;
	}

	private void executeProcess(ActionProcess process, LemmingBody body) {
		switch(process.getTag()) {
		case CREATE:
			map[process.getCell().getY()][process.getCell().getX()] = TerrainType.LEMMING_BLOCKED;
			return;
		case DESTROY:
			map[process.getCell().getY()][process.getCell().getX()] = TerrainType.EMPTY;
			return;
		case MOVE:
			//CellCoord pos = new CellCoord(body.getCellCoord().getX(),body.getCellCoord().getY());
			applyForce(process.getCell(), movebuffer);

			return;
		default:
			break;
		}
	}

	private Boolean executeTestTag(ActionTest test) {
		TerrainType t;
		if(test.getCell().getY() < envSize.y && test.getCell().getY() >= 0 && 
				test.getCell().getX() < envSize.x && test.getCell().getX() >= 0) {
			t  = map[test.getCell().getY()][test.getCell().getX()];
		}
		else {
			return false;
		}
		switch (test.getTag()) {
		case DANGER:
			return t.isDanger;
		case TRAVERSABLE:
			return t.isTraversable;
		case DIGGABLE:
			return t.isDiggable;
		case NOT_BLOCKED_LEMMING:
			switch (t) {
			case LEMMING_BLOCKED:
				return false;
			default:
				return true;
			}
		case NOT_SOLID:
			return !t.isSolid;
		case NOT_TRAVERSABLE:
			return !t.isTraversable;
		case SOLID:
			return t.isSolid;
		default:
			break;
		}
		return null;
	}

	
	
	public void changeLevel(Level newLevel) {

			envSize =new Point2d(newLevel.getWidth(), newLevel.getHeight());
			//We retrieve all the terrain types to easily set them in the map array 
			TerrainType[] terrains = TerrainType.values();

			//Generation of the map
			this.map=new TerrainType[(int)envSize.y][(int)envSize.x];
			for(int l=0;l<envSize.x;l++) {
				for(int c=0;c<envSize.y;c++) {
					this.map[l][c] = terrains[newLevel.getMap()[l][c]];
					if(newLevel.getMap()[l][c] == TerrainType.EXIT.ordinal())
						exitPos= new CellCoord(c, l);
				}
			}

			// We store the lemmings into the array of agent of the environmnent
			this.lemmingBodies=new LinkedList<LemmingBody>();
	}
}
