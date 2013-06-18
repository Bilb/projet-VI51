package Lemming;

public class LemmingGenerator {

	private int nbLemmings;
	private int nbGeneratedLemmings = 0;
	private long generationTimer; // in ms system clock dependent
	private long lastTime;
	private CellCoord pos;
	private Game game;
	private Environment env;
	
	public void run() {
		if(game != null) {
			if(System.currentTimeMillis() - lastTime > generationTimer)
			{
				lastTime = System.currentTimeMillis(); //System.nanoTime();
				if(nbLemmings - nbGeneratedLemmings > 0) {
				// TODO Auto-generated method stub	
					game.addLemming(new Lemming(pos,new LemmingBody(new CellCoord(pos.getX(),pos.getY()), env)));
					nbGeneratedLemmings++;
					System.out.println("generator: pos = " + pos.getX() + " " + pos.getY());
				}
							
			}
		}
	}
		
	// tbd
	public LemmingGenerator(Game game_, int nbLemmings_, long generationTimer_, CellCoord pos_, Environment env_){
		game = game_;
		nbLemmings = nbLemmings_;
		generationTimer = generationTimer_;
		pos = pos_;
		System.out.println("generator CONSTR: pos = " + pos.getX() + " " + pos.getY());
		env = env_;
	}
}
