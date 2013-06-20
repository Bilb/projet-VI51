package Lemming.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import Lemming.CellCoord;



public class Level {
	public static final String FILE_PREFIX = "levels"+ File.separator + "lemming1-" ;
	public static final String FILE_SUFFIX = ".txt" ;


	public static final String WIDTH_CONFIG = "WIDTH";

	public static final String HEIGHT_CONFIG = "HEIGHT";

	public static final String LEMMING_NB_CONFIG = "NB_LEMMING";
	public static final String LEMMING_TIME_CONFIG = "TIME_LEMMING";

	public static final String MAP_CONFIG = "MAP";

	public static final String SPAWN_POSITION = "SPAWN_POSITION";

	private int width = -1;

	private int height = -1;
	
	private CellCoord spawnPosition;






	private int[][] map;

	private int nbLemmings;

	private int timeBetweenTwoLemmings;
	
	public Level(int numLevel) throws FileNotFoundException {
		load(numLevel);
	}

	private void load(int numLevel) throws FileNotFoundException {

		File levelFile = new File(FILE_PREFIX + numLevel + FILE_SUFFIX);

		if(!levelFile.exists()) {
			throw new FileNotFoundException("fichier '" + FILE_PREFIX + numLevel + FILE_SUFFIX + "'  impossible à charger");
		}

		FileInputStream inStream = new FileInputStream(levelFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		String line;
		String rest;
		int mapHeightRead = 0, mapWidthRead = 0;

		try {
			while( (line = reader.readLine()) != null) {

				if(line.contains(WIDTH_CONFIG)) {
					rest = line.substring(WIDTH_CONFIG.length() + 1);
					width = Integer.parseInt(rest);

					if(width != -1 && height != -1) {
						map = new int[height][width];
					}
				}
				else if(line.contains(HEIGHT_CONFIG)) {
					rest = line.substring(HEIGHT_CONFIG.length() + 1);
					height = Integer.parseInt(rest);

					if(width != -1 && height != -1) {
						map = new int[height][width];
					}
				}
				else if(line.contains(LEMMING_NB_CONFIG)) {
					rest = line.substring(LEMMING_NB_CONFIG.length() + 1);
					nbLemmings = Integer.parseInt(rest);

				}
				else if(line.contains(LEMMING_TIME_CONFIG)) {
					rest = line.substring(LEMMING_TIME_CONFIG.length() + 1);
					timeBetweenTwoLemmings = Integer.parseInt(rest);
				}
				else if(line.contains(MAP_CONFIG)) {
					line = line.substring(MAP_CONFIG.length() + 1);
					String currentValue;

					for(mapWidthRead = 0; mapWidthRead < width; mapWidthRead++ ) {
						currentValue = line.substring(mapWidthRead*2, mapWidthRead*2+1);
						if(currentValue != null && currentValue.length() != 0) 
							map[mapHeightRead][mapWidthRead] = Integer.parseInt(currentValue);
					}


					mapHeightRead++;


				}
				else if(line.contains(SPAWN_POSITION)) {
					rest = line.substring(SPAWN_POSITION.length() + 1);
					String currentValue;
					currentValue = rest.substring(0,1);
					int x = Integer.parseInt(currentValue);
					currentValue = rest.substring(2,3);
					int y = Integer.parseInt(currentValue);
					spawnPosition = new CellCoord(x, y);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		System.out.println(" levels " + toString());



	}






	public int getNbLemmings() {
		return nbLemmings;
	}

	public int getTimeBetweenTwoLemmings() {
		return timeBetweenTwoLemmings;
	}

	public int[][] getMap() {
		return map;
	}



	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public CellCoord getSpawnPosition() {
		return spawnPosition;
	}

	public void setSpawnPosition(CellCoord spawnPosition) {
		this.spawnPosition = spawnPosition;
	}






	@Override
	public String toString() {
		StringBuilder mapString = new StringBuilder();
		for(int h = 0; h < height; h++) {
			mapString.append("\t\t");
			for(int w = 0; w < width; w++) {
				mapString.append("" +map[h][w] + "");
				
				if(w != width -1)
					mapString.append(",");
				
				
			}
			mapString.append(System.getProperty("line.separator"));
		}

		
		return "Level " + System.getProperty("line.separator") + 
				"\twidth= " + width +  System.getProperty("line.separator") + 
				"\theight= " + height + System.getProperty("line.separator") + 
				"\ttimeBetweenTwoLemmings= " + timeBetweenTwoLemmings  + System.getProperty("line.separator")
				+ "\tnbLemmings=" + nbLemmings
				+ System.getProperty("line.separator") + "\tmap:" + 
				System.getProperty("line.separator")
				+ mapString;
	}




}
