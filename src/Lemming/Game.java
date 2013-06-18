package Lemming;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

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
	private JButton startButton;
	private JButton stopButton;

	public void launch() {
		try {
			currentLevel = new Level(currentLevelId);
			//TODO : load dans environement la map, créer les agents, ...
			
			/*int[][] envma = new int[3][3];
			for(int i = 0; i< 3 ; i++) {
				for (int j = 0; j < 3 ; j++) {
					envma[i][j] = 5;
				}
			}*/
			
			environment = new Environment(new Point2d(currentLevel.getWidth(), currentLevel.getHeight()), 
					currentLevel.getMap() , currentLevel.getSpawnPosition(), 0);
			generator = new LemmingGenerator(
					this
					, currentLevel.getNbLemmings()
					, currentLevel.getTimeBetweenTwoLemmings()
					, currentLevel.getSpawnPosition()
					, environment
			);
			new Thread(this).start();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Game.this, 
					"Impossible de charger le fichier de level n°" + currentLevelId, 
					"Erreur de fichier", 
					JOptionPane.ERROR_MESSAGE);
		}
		


	}

	public void stop() {

			Thread.currentThread().interrupt();		
	}

	public Game() {
		super("Projet VI51 - Lemmings");
		setUpUi();
		setSize(1000,800);
		lemmings=new LinkedList<Lemming>();
		//SwingUtilities.invokeLater(level);
		new Thread(level).start();

	}



	private void setUpUi() {

		panel = new JPanel();
		level = new LevelPanel(this);
		level.setSize(600, 600);
		//level.setBackground(Color.BLACK);
		panel.setSize(100, 100);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("startclick");
				launch();
			}
		});
		stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("stopclick");
				stop();
			}
		});


		

		setLayout(new BorderLayout());
		panel.add(startButton, BorderLayout.PAGE_START);
		panel.add(stopButton, BorderLayout.PAGE_START);
		add(panel,BorderLayout.PAGE_START);
		add(level, BorderLayout.CENTER);

		
		setVisible(true);
		
		setLocationRelativeTo(null);

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
