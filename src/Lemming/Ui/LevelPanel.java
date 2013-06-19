package Lemming.Ui;


import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

import Lemming.Sens;
import Lemming.Agent.LemmingBody;
import Lemming.Environment.TerrainType;

public class LevelPanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 106948148087030135L;


	/*pacman images*/
	private Image rock;
	private Image ground;
	private Image sky;
	private Image water;
	private Image lemming_right;
	private Image lemming_left;
	private Image lemming_blocked;
	private Image lemming_parachute_right;
	private Image lemming_parachute_left;
	private Image lemming_climbing_right;
	private Image lemming_climbing_left;





	private Game game = null;


	

	public LevelPanel(Game game) {
		super();
		this.game = game;


		/* load images */
		rock = (new ImageIcon("images/rock.png")).getImage();
		ground = (new ImageIcon("images/ground.png")).getImage();
		sky = (new ImageIcon("images/sky.png")).getImage();
		water = (new ImageIcon("images/water.png")).getImage();
		lemming_left = (new ImageIcon("images/lemming_left.png").getImage());
		lemming_right = (new ImageIcon("images/lemming_right.png").getImage());
		lemming_blocked = (new ImageIcon("images/lemming_blocked.png").getImage());
		lemming_parachute_left = (new ImageIcon("images/lemming_parachute_left.png").getImage());
		lemming_parachute_right = (new ImageIcon("images/lemming_parachute_right.png").getImage());
		lemming_climbing_right = (new ImageIcon("images/lemming_climbing_right.png").getImage());
		lemming_climbing_left = (new ImageIcon("images/lemming_climbing_left.png").getImage());
		setFocusable(true);
	}





	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(game != null) {
			TerrainType[][] map = game.getCurrentEnvironmentMap();
			Point2d envSize = (Point2d) game.getCurrentEnvironmentSize();
			
			/* affichage de la map */
			if(map != null && envSize != null) {
				for(int y = 0; y < envSize.y ; y++) {
					for(int x = 0; x < envSize.x ; x++) {
						TerrainType terrain = map[y][x];
						Image image = null;
						switch (terrain) {
						case GROUND:
							image = ground;
							break;
						case ROCK:
							image = rock;
							break;
						case WATER:
							image = water;
							break;
						case EMPTY:
							image = sky;
							break;
						case LEMMING_BLOCKED:
							image = lemming_blocked;
							break;
						default:
							image = null;
							//System.err.println("No image to display for terrain " + terrain + " in LevelPanel print");
							break;
						}
						if(image != null)
							g.drawImage(image, x * Game.CellDim, y * Game.CellDim, this);

					}
				}
			}
		

			/* affichage des agents */
			if(game.getEnvironment() != null) {
				//System.out.println("test");
				LinkedList<LemmingBody> lemmingsBodies = game.getEnvironment().getLemmingBodies();
				if(!lemmingsBodies.isEmpty()) {
					for (LemmingBody lemmingBody : lemmingsBodies) {
						if(lemmingBody != null) {
							Image img = null;
							img = getLemmingImg(lemmingBody);
							//System.out.println("draw lemming at: " + lemmingBody.getPixelCoord().getX() +"-" + lemmingBody.getPixelCoord().getX());
						
							g.drawImage(img, lemmingBody.getPixelCoord().getX(),lemmingBody.getPixelCoord().getY(), this);
					
						}
					}
				}
			}	
		}
	}

	private Image getLemmingImg(LemmingBody lemmingBody) {
		Image img;
		if(lemmingBody.getSens() == Sens.LEFT) {
			img = lemming_left;
			if(lemmingBody.isParachute()) {
				img = lemming_parachute_left;
			}
			else if(lemmingBody.isClimbing()) {
				img = lemming_climbing_left;
			}

		}
		else {
			img = lemming_right;
			if(lemmingBody.isParachute()) {
				img = lemming_parachute_right;
			}
			else if(lemmingBody.isClimbing()) {
				img = lemming_climbing_right;
			}
		}
		return img;
	}





	@Override
	public void run() {

		while(true) {
			repaint();
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
