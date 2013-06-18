package Lemming;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point2d;


/* TODO : Faire attention a l'ordre DX DY dans les arrays*/
public class Environment{

	
	//private CellCoord spawnPos;
	
	private CellCoord exitPos;

	private  TerrainType[][] map;
	
	private Point2d envSize;

	private LinkedList<LemmingBody> lemmingBodies;
	
	public Environment (Point2d  size, int [][] envmap, CellCoord spawnPosition, int nbLemmings) {
		
		envSize = size;
		
		//We retrieve all the terrain types to easily set them in the map array 
		TerrainType[] terrains = TerrainType.values();
		
		//Generation of the map
		this.map=new TerrainType[(int)size.x][(int)size.y];
		for(int l=0;l<size.x;l++) {
			for(int c=0;c<size.y;c++) {
				this.map[l][c] = terrains[envmap[l][c]];
			}
		}
		
		// We store the lemmings into the array of agent of the environmnent
		this.lemmingBodies=new LinkedList<LemmingBody>();

		
	}

	public List<Perception> getPerceptions(LemmingBody body) {
		
		CellCoord bodyPosition = body.getCellCoord();
		List<Perception> percLst = new LinkedList<Perception>();
		
		int downY = bodyPosition.getY()+1;
		int upY = bodyPosition.getY()-1;
		int frontX = bodyPosition.getX()+body.getSens().dx;
		//Get the data from the frustrum 
		//down
		if(downY <this.envSize.y) {
			percLst.add(new TerrainPerception(this.map[bodyPosition.getY()+1][bodyPosition.getX()]));
			// down next
			if(frontX < this.envSize.x)
				percLst.add(new TerrainPerception(this.map[bodyPosition.getY()+1][bodyPosition.getX()+body.getSens().dx]));
		}
		//next
		if(frontX < this.envSize.x) {
			percLst.add(new TerrainPerception(this.map[bodyPosition.getY()][bodyPosition.getX()+body.getSens().dx]));
			//up next
			if(upY >= 0)
				percLst.add(new TerrainPerception(this.map[bodyPosition.getY()-1][bodyPosition.getX()+body.getSens().dx]));
		}
		//up
		if(upY >=0)
			percLst.add(new TerrainPerception(this.map[bodyPosition.getY()-1][bodyPosition.getX()]));
		//bodyPos
		percLst.add(new TerrainPerception(this.map[bodyPosition.getY()][bodyPosition.getX()]));
		//Exit position
		percLst.add(new ExitPerception(body.getCellCoord()));
				
		return percLst;
	}

	public void kill(LemmingBody lemmingBody) {
		
	}
	
	public void move(LemmingBody body, int dx, int dy) {
		
			CellCoord bodyPosition = body.getCellCoord();

			
			int newX = bodyPosition.getX()+dx;
			int newY = bodyPosition.getY()+dy;	
			int groundOfPos = bodyPosition.getY()+1;
			
			// on tombe ?
			if(groundOfPos < this.envSize.y  && map[groundOfPos][bodyPosition.getX()].isTraversable && dy != -1) {
				applyGravity(body);
			}
			// on avance ?
			else if (Math.abs(dx) == 1 && Math.abs(dy) == 0) {	// mouvement de marche
				if(newX<this.envSize.x && newY<this.envSize.y && groundOfPos < this.envSize.y ) {
					// la case de destination peut acceuilir le lemming
					// la case de départ est soutenu et permet la marche
					if(	map[newY][newX].isTraversable && !map[groundOfPos][bodyPosition.getX()].isTraversable) {
						System.out.println("move: newX: " + newX +  "newY:" + newY);
						body.setUpdatePixel(bodyPosition);
						body.setCellCoord(newX, newY);
					}
				}
			}
			
			
			//Glearning.getActionPerformed(  VOIr comment rï¿½cuperer l'action et le isPrformed
		}
	

	private void applyGravity(LemmingBody body) {
		
		
		CellCoord bodyPosition = body.getCellCoord();
		int newX = bodyPosition.getX();
		int newY = bodyPosition.getY()+1;
		body.setUpdatePixel(bodyPosition);
		body.setCellCoord(newX, newY);
		body.addInfluences(new FallInfluence(1));


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

	public LinkedList<LemmingBody> getLemmingBodies() {
		return lemmingBodies;
	}
	
	
	public Point2d getEnvSize() {
		return envSize;
	}

	/*public void setLemmingBody(List<LemmingBody> lemmingBody) {
		this.lemmingBodies = lemmingBody;
	}*/
	
	public void addLemmingBody(LemmingBody lbody) {
		lemmingBodies.add(lbody);
	}

}
