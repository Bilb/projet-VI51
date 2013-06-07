package Lemming;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4172777326765469302L;

	private Level currentLevel = null;

	private List<Integer> levels = new ArrayList<>();

	private Environment environment = null;

	private Lemming lemmings;

	public void launch(Level level) {
		if(levels.contains(level)) {
			
		}
		else 
			System.err.println("");

		
	}

	public void stop() {

	}
	
	public Game() {
		super("test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
