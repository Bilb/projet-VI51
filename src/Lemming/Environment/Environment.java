package Lemming.Environment;

import java.util.LinkedList;

import javax.vecmath.Point2d;

import Lemming.CellCoord;
import Lemming.Action.Action;
import Lemming.Action.Action.LemmingActionType;
import Lemming.Action.ActionProcess;
import Lemming.Action.ActionTest;
import Lemming.Agent.LemmingBody;
import Lemming.Influence.FallInfluence;
import Lemming.Perception.ExitPerception;
import Lemming.Perception.Perception;
import Lemming.Perception.TerrainPerception;

/**
 * 
 * définie un environnement dans lequel sons suceptibles d'évoluer des agents (ici des lemmings)
 * l'environnement gère notamment la gravité et contient une liste d'agent
 * dans notre cas les agents sont des lemmings
 *
 */
public class Environment{


	private CellCoord exitPos;

	private  TerrainType[][] map;

	private Point2d envSize;

	private LinkedList<LemmingBody> lemmingBodies;
	private CellCoord movebuffer;

	/**
	 * 
	 * @param size taille de l'environnement
	 * @param envmap tableau des types de terrains pour définir la carte
	 * 
	 */
	public Environment (Point2d  size, int [][] envmap) {

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
	
/**
 * Fonction qui remplie la liste des perceptions de l'agents
 * c'est ici que la définition de son frustrum prend du sens
 * a noté que dans notre cas l'agent est un lemming
 * @param body corps de l'agent qui perçoit
 * @return liste des perceptions du body
 */
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

	/**
	 * Permet de supprimer un LemmingBody de l'environnement
	 * @param lemmingBody body à supprimer de l'environnement
	 */
	public void kill(LemmingBody lemmingBody) {
		lemmingBodies.remove(lemmingBody);

	}

	/**
	 * Permet d'appliquer la gravité sur une position
	 * Cette méthode modifie directement le paramètre pos
	 * Cette méthode vérifie que après application de la gravité, la position reste dans carte
	 * et reste une case traversable (les élements mobiles ne peuvent se situer sur une case non traversables)
	 * @param pos position subissant la force
	 */
	private boolean applyGravity(CellCoord pos) {
		int Yg = pos.getY()+1;
		if(Yg < envSize.y && map[Yg][pos.getX()].isTraversable) {
			pos.setY(Yg);
			return true;
		}
		return false;
	}

	/**
	 * Permet d'appliquer une force sur une position
	 * Cette méthode modifie directement le paramètre pos
	 * @param force force à appliquer
	 * @param pos position subissant la force
	 */
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

	/**
	 * Fonction qui realise les test de l'action passe en parametre
	 * si les tests sont valides: les actions process de l'action sont executes
	 * @param body corps du lemming subissant l'action
	 * @param action action a traiter
	 * @return vrai si lest tests de l'action sont tous verifies, faux sinon
	 */
	public Boolean tryExecute(LemmingBody body,Action action) {
		movebuffer= new CellCoord(body.getCellCoord().getX(),body.getCellCoord().getY());
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
			// TODO comment le gerer correctement ?
			if(applyGravity(movebuffer) && action.getLemmingActionType() == LemmingActionType.Parachute) {
				body.addInfluences(new FallInfluence(1));
			}
			
			body.setPreviousPosition(body.getCellCoord());
			body.setCellCoord(movebuffer);
		}
		return ok;
	}

	/**
	 * fonction faisant la correspondance entre les ActionProcessTag destine a l'environnement
	 * et leurs realisations.
	 * Cette fonction execute egalement les actions
	 * @param process
	 * @param body
	 */
	private void executeProcess(ActionProcess process, LemmingBody body) {
		switch(process.getTag()) {
		case CREATE:
			map[process.getCell().getY()][process.getCell().getX()] = TerrainType.LEMMING_BLOCKED;
			return;
		case DESTROY:
			map[process.getCell().getY()][process.getCell().getX()] = TerrainType.EMPTY;
			return;
		case MOVE:
			applyForce(process.getCell(), movebuffer);

			return;
		default:
			break;
		}
	}
	
	/**
	 * fonction faisant la correspondance entre les ActionTestTag destine a l'environnement
	 * et leurs realisations.
	 * Cette fonction execute egalement les tests
	 * @param process
	 * @param body
	 * @return valeur du test
	 */
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
