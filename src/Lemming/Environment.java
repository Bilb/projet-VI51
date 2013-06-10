package Lemming;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point2d;


/* TODO : Faire attention a l'ordre DX DY dans les arrays*/
public class Environment {

	
	private CellCoord spawnPos;
	
	private CellCoord exitPos;

	private  TerrainType[][] map;
	
	private Point2d envSize;

	private List<LemmingBody> lemmingBodies;
	
	public Environment (Point2d  size, int [][] envmap, CellCoord spawnPosition, int nbLemmings) {
		//Spawn position of all the lemmings in the environment 
		this.spawnPos=spawnPosition;
		
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
		/*for(int nbLem=0;nbLem<nbLemmings; nbLem++)
		{
			this.lemmingBody.add(new LemmingBody());																			//                      TODO : Revoir les param�tres du constructeur
		}*/
		
	}

	public List<Perception> getPerceptions(LemmingBody body) {
		
		CellCoord bodyPosition = body.getCellCoord();
		List<Perception> percLst = new LinkedList<Perception>();
		
		//Get the data from the frustrum 
		//down
		percLst.add(new TerrainPerception(this.map[bodyPosition.getY()+1][bodyPosition.getX()]));
		// down next
		percLst.add(new TerrainPerception(this.map[bodyPosition.getY()+1][bodyPosition.getX()+body.getSens().dx]));
		//next
		percLst.add(new TerrainPerception(this.map[bodyPosition.getY()][bodyPosition.getX()+body.getSens().dx]));
		//up next
		percLst.add(new TerrainPerception(this.map[bodyPosition.getY()-1][bodyPosition.getX()+body.getSens().dx]));
		//up
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
			boolean isPerformed =false;
			int newX=bodyPosition.getX()+dx;
			int newY=bodyPosition.getY()+dy;
			
			if(newX<=this.envSize.x && newY<=this.envSize.y) {
				if(map[newY][newX].isTraversable) {
					body.setCellCoord(newX, newY);
					isPerformed=true;
				}				
			}
			this.applyGravity(body);
			
			//Glearning.getActionPerformed(  VOIr comment r�cuperer l'action et le isPrformed
		}
	

	private void applyGravity(LemmingBody body) {
		
		CellCoord bodyPosition = body.getCellCoord();
		
		/*if(map[bodyPosition.getY()+1][bodyPosition.getX()].isTraversable && !body.hasParachute()) {
			move(body, 0, +1);
			body.setCurrentFall(body.getCurrentFall()+1);
		}
		else
		{
			body.setCurrentFall(0);
		}*/
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

	public List<LemmingBody> getLemmingBody() {
		return this.lemmingBodies;
	}
	
	
	public Point2d getEnvSize() {
		return envSize;
	}

	/*public void setLemmingBody(List<LemmingBody> lemmingBody) {
		this.lemmingBodies = lemmingBody;
	}*/

}
