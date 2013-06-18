package Lemming;

public enum Action {

	Climb(-1) {
	},

	Dig(0) {
	},
	
	Drill(-1) {
	},

	Parachute(0) {
	},

	Walk(0) {
	},

	Block(0) {
	};
	
	
	Action(int dy) {
		this.dy = dy;
	}
	
	public int dy;


}
