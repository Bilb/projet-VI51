package Lemming.Environment;

import Lemming.CellCoord;
import Lemming.Agent.Lemming;
import Lemming.Agent.LemmingBody;
import Lemming.Ui.Game;

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
				lastTime = System.currentTimeMillis(); 
				if(nbLemmings - nbGeneratedLemmings > 0) {
					game.addLemming(new Lemming(new LemmingBody(new CellCoord(pos.getX(),pos.getY()), env)));
					nbGeneratedLemmings++;
				}

			}
		}
	}

	public LemmingGenerator(Game game_, int nbLemmings_, long generationTimer_, CellCoord pos_, Environment env_){
		game = game_;
		nbLemmings = nbLemmings_;
		generationTimer = generationTimer_;
		pos = pos_;
		env = env_;
	}

	public void updateParams(Game game_, int nbLemmings_,
			int timeBetweenTwoLemmings, CellCoord spawnPosition,
			Environment environment) {
		game = game_;
		nbLemmings = nbLemmings_;
		generationTimer = timeBetweenTwoLemmings;
		pos = spawnPosition;
		env = environment;
		//nbGeneratedLemmings = 0;
		//lastTime = generationTimer;
	}
}
