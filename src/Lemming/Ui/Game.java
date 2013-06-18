package Lemming.Ui;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

import Lemming.Agent.Lemming;
import Lemming.Agent.LemmingBody;
import Lemming.Environment.Environment;
import Lemming.Environment.LemmingGenerator;
import Lemming.Environment.Level;
import Lemming.Environment.TerrainType;

public class Game extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4172777326765469302L;
	public static final long frameTime = 33;

	public static final int CellDim = 60;

	private Level currentLevel = null;



	//private List<Integer> levels = new ArrayList<>();
	private int currentLevelId = 1;

	private Environment environment = null;
	private LemmingGenerator generator = null;

	private JPanel panel;
	private LevelPanel level;

	private List<Lemming> lemmings;

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
		setUpUi();
		
		
		launch();
		
		
		lemmings=new LinkedList<Lemming>();
		//SwingUtilities.invokeLater(level);
		new Thread(level).start();

	}


	
	private void resizeFrameToLevelSize() {
		if(currentLevel != null) {
			setSize(currentLevel.getWidth() * Game.CellDim, currentLevel.getHeight() * Game.CellDim);
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
		// TODO Auto-generated method stub
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
						if(lb.getUpdatePixel()) {
							//System.out.println("pixel update true");
							lb.updatePixelPosition(lb.getCellCoord());
						}
						else {
							lemming.live();
							//System.out.println("live");
						}
					}
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
