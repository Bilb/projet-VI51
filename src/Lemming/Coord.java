package Lemming;

public abstract class Coord {

	private int x;
	private int y;
	
	public Coord() {
		this.x = 0;
		this.y = 0;
	}
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(int x, int y) {
		this.x+=x;
		this.y+=y;
	}
}
