package Lemming;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

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






	private Game game = null;


	

	public LevelPanel(Game game) {
		super();
		this.game = game;


		/* load images */
		rock = (new ImageIcon("images/rock.png")).getImage();
		ground = (new ImageIcon("images/ground.png")).getImage();
		sky = (new ImageIcon("images/sky.png")).getImage();
		water = (new ImageIcon("images/water.png")).getImage();

		setFocusable(true);
	}





	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(game != null) {
			TerrainType[][] map = game.getCurrentEnvironmentMap();
			Point2d envSize = game.getCurrentEnvironmentSize();
			
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

						default:
							image = null;
							System.err.println("No image to display for terrain " + terrain + " in LevelPanel print");
							break;
						}
						if(image != null)
							g.drawImage(image, x * Game.CellDim, y * Game.CellDim, this);

					}
				}
			}
		}

		/* affichage des agents */

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
