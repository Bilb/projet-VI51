package Lemming;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

public class Game extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4172777326765469302L;

	public static final int CellDim = 60;

	private Level currentLevel = null;

	//private List<Integer> levels = new ArrayList<>();
	private int currentLevelId = 1;

	private Environment environment = null;

	private JPanel panel;
	private LevelPanel level;

	private Lemming lemmings;
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
					currentLevel.getMap() , new CellCoord(10, 10), 0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Game.this, 
					"Impossible de charger le fichier de level n°" + currentLevelId, 
					"Erreur de fichier", 
					JOptionPane.ERROR_MESSAGE);
		}
		


	}

	public void stop() {

	}

	public Game() {
		super("Projet VI51 - Lemmings");
		setUpUi();
		setSize(1000,800);
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

}
