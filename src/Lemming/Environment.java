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
			//boolean isPerformed = false;
			// obtention de la position a tester en pixel
			//PixelCoord p = new PixelCoord(body.getPixelCoord().getX()+dx,body.getPixelCoord().getY()+dy);
			int newpX=body.getPixelCoord().getX()+dx;
			int newpY=body.getPixelCoord().getY()+dy;
			
			int newX = bodyPosition.getX();
			int newY = bodyPosition.getY();
			
			// conversion de la nouvelle position en pixel en position de cellule
			if(newpX > bodyPosition.toPixelCoord().getX()){
				newX += 1;
			}
			else if(newpX < bodyPosition.toPixelCoord().getX()) {
				newX -= 1;
			}
			
			if(newpY > bodyPosition.toPixelCoord().getY()){
				newY += 1;
			}
			else if(newpY < bodyPosition.toPixelCoord().getY()) {
				newY -= 1;
			}
			if(newX<this.envSize.x && newY<this.envSize.y) {
				if(map[newY][newX].isTraversable) {
					System.out.println("move: newX: " + newX +  "newY:" + newY);
					body.setCellCoord(newX, newY);
					body.setPixelCoord(newpX,newpY);
				//	isPerformed=true;
				}				
			}
			this.applyGravity(body);
			
			//Glearning.getActionPerformed(  VOIr comment r�cuperer l'action et le isPrformed
		}
	

	private void applyGravity(LemmingBody body) {
		
		CellCoord bodyPosition = body.getCellCoord();
		
		//TODO stackoverflow :: recursivité ac move
		if(map[bodyPosition.getY()+1][bodyPosition.getX()].isTraversable/* && !body.hasParachute()*/) {
			//move(body, 0, +1);
			//body.setCurrentFall(body.getCurrentFall()+1);
			body.addInfluences(new FallInfluence(1));
		}
		else
		{
			//body.setCurrentFall(0);
			
		}
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
