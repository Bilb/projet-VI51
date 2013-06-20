package Lemming.Ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

import Lemming.CellCoord;
import Lemming.Action.Action;
import Lemming.Agent.Lemming;
import Lemming.Agent.LemmingBody;
import Lemming.Agent.LemmingProblemState;
import Lemming.Environment.Environment;
import Lemming.Environment.LemmingGenerator;
import Lemming.Environment.Level;
import Lemming.Environment.TerrainType;
import fr.utbm.gi.vi51.learning.qlearning.QLearning;

public class Game extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4172777326765469302L;

	private static final int nbLevels = 6;
	public static final long frameTime = 33;

	public static final int CellDim = 60;

	private static final int NB_ITER_PER_LEVEL = 3;

	private Level currentLevel = null;



	private int currentLevelId = 1;
	private int nbIterCurrentLevel;

	private Environment environment = null;
	private LemmingGenerator generator = null;

	private JPanel panel;
	private LevelPanel level;

	private List<Lemming> lemmings;

	private boolean demoMode = true;

	private void launch() {
		try {
			currentLevel = new Level(currentLevelId);

			environment = new Environment(new Point2d(currentLevel.getWidth(), currentLevel.getHeight()), 
					currentLevel.getMap() , currentLevel.getSpawnPosition(), 0);
			generator = new LemmingGenerator(
					this
					, currentLevel.getNbLemmings()
					, currentLevel.getTimeBetweenTwoLemmings()
					, currentLevel.getSpawnPosition()
					, environment
					);
			resizeFrameToLevelSize();
			nbIterCurrentLevel = 0;

			new Thread(this).start();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Game.this, 
					"Impossible de charger le fichier de level n°" + currentLevelId, 
					"Erreur de fichier", 
					JOptionPane.ERROR_MESSAGE);
		}



	}

	public Game() {
		super("Projet VI51 - Lemmings");
		lemmings=new LinkedList<Lemming>();

		setUpUi();

		launch();
		new Thread(level).start();
	}



	private void resizeFrameToLevelSize() {
		if(currentLevel != null) {
			setSize(currentLevel.getWidth() * Game.CellDim, currentLevel.getHeight() * Game.CellDim + 20);
			setLocationRelativeTo(null);
		}


	}

	private void setUpUi() {

		panel = new JPanel();
		level = new LevelPanel(this);
		level.setSize(600, 600);
		panel.setSize(100, 100);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,630);



		setLayout(new BorderLayout());
		add(level, BorderLayout.CENTER);


		setLocationRelativeTo(null);
		setVisible(true);
		level.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyCode() == KeyEvent.VK_SPACE) {
					changeLevel();
				}
			}
		});


	}

	private void changeLevel() {
		System.out.println("currentlevel : " + currentLevelId + " next:" + ((currentLevelId)%(nbLevels)+ 1) + " nbLevels:"  +nbLevels);
		currentLevelId = ((currentLevelId)%(nbLevels)+ 1);
		nbIterCurrentLevel = 0;
		
		QLearning<LemmingProblemState,Action> saveQLearning = null;
		if(demoMode) {
			if(lemmings != null && lemmings.size() > 0) {
				Lemming lemming  = lemmings.get(0);
				saveQLearning = lemming.getQLearning();
				kill(lemming);
				addLemming(new Lemming(new LemmingBody(new CellCoord(currentLevel.getSpawnPosition().getX(),currentLevel.getSpawnPosition().getY()), this.environment), saveQLearning));
			}
		}
		try {
			currentLevel = new Level(currentLevelId);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 

		if(generator != null) {
			generator.updateParams(this
					, currentLevel.getNbLemmings()
					, currentLevel.getTimeBetweenTwoLemmings()
					, currentLevel.getSpawnPosition()
					, environment);
		}
		else {
			generator = new LemmingGenerator(
					this
					, currentLevel.getNbLemmings()
					, currentLevel.getTimeBetweenTwoLemmings()
					, currentLevel.getSpawnPosition()
					, environment
					);
		}

		if(environment == null) {
			environment = new Environment(new Point2d(currentLevel.getWidth(), currentLevel.getHeight()), 
					currentLevel.getMap() , currentLevel.getSpawnPosition(), 0);
		}
		else {
			environment.changeLevel(currentLevel);
			if(demoMode && saveQLearning !=null) {
				System.out.println("saveQLearning" +saveQLearning);
				addLemming(new Lemming(new LemmingBody(new CellCoord(currentLevel.getSpawnPosition().getX(),currentLevel.getSpawnPosition().getY()), this.environment), saveQLearning));
			}
			else if (demoMode) {
				addLemming(new Lemming(new LemmingBody(new CellCoord(currentLevel.getSpawnPosition().getX(),currentLevel.getSpawnPosition().getY()), this.environment)));
			}
			
				
		}

		for (Lemming lemmingOld : lemmings) {
			kill(lemmingOld);
		}
		

		resizeFrameToLevelSize();
	}


	public static void main(String[] args) {
		new Game();
	}



	public final TerrainType[][] getCurrentEnvironmentMap() {
		if (environment != null)
			return environment.getMap();
		else
			return null;
	}

	public final Point2d getCurrentEnvironmentSize() {
		if (environment != null)
			return environment.getEnvSize();
		else
			return null;
	}

	public void addLemming(Lemming lemming) {
		lemmings.add(lemming);
		environment.addLemmingBody(lemming.getLemmingBody());
	}

	public List<Lemming> getLemmings() {
		return lemmings;
	}

	public void setLemmings(List<Lemming> lemmings) {
		this.lemmings = lemmings;
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public Environment getEnvironment() {
		return environment;
	}

	@Override
	public void run() {
		while (true) {
			if(generator != null) {
				generator.run();
			}
			try {
				//Thread.currentThread();
				Thread.sleep(frameTime);

				if(lemmings != null) {
					for (Lemming lemming : lemmings) {
						LemmingBody lb = lemming.getLemmingBody();
						if(lb != null) {

							if(lb.getUpdatePixel()) {
								lb.updatePixelPosition(lb.getCellCoord());
							}
							else {
								lemming.live();
							}
							if(!lb.isAlive()) {
								QLearning<LemmingProblemState,Action> saveQLearning = lemming.getQLearning();
								kill(lemming);
								addLemming(new Lemming(new LemmingBody(new CellCoord(currentLevel.getSpawnPosition().getX(),currentLevel.getSpawnPosition().getY()), this.environment), saveQLearning));
								nbIterCurrentLevel++;
								environment.setMap(currentLevel.getMap(),currentLevel.getWidth(), currentLevel.getHeight() );
							}
							
							if(nbIterCurrentLevel > NB_ITER_PER_LEVEL) {
								changeLevel();
							}
						}
						else {
							QLearning<LemmingProblemState,Action> saveQLearning = lemming.getQLearning();
							kill(lemming);
							addLemming(new Lemming(new LemmingBody(new CellCoord(currentLevel.getSpawnPosition().getX(),currentLevel.getSpawnPosition().getY()), this.environment), saveQLearning));
							
							nbIterCurrentLevel++;
							if(nbIterCurrentLevel > NB_ITER_PER_LEVEL) {
								changeLevel();
							}
						}
					}

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void kill(Lemming l) {
		LemmingBody lb = l.getLemmingBody();
		environment.kill(lb);
		lb = null;
		lemmings.remove(l);
		l = null;
	}


}
