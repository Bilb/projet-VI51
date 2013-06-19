package Action;

public enum ActionTestTag {
		TRAVERSABLE(0){
		},
		NOT_TRAVERSABLE(0){
		},
		DIGGABLE(0){
		},
		SOLID(0){
		},
		NOT_SOLID(0){
		},
		DANGER(0){	
		},
		PARACHUTE(1){
		},
		CLIMBING(1) {
		},
		NOT_CLIMBING(1) {
		},
		NOT_BLOCKED_LEMMING(0) {
		},
		NOT_FALLING(1) {
		}
		;
	
	
	ActionTestTag(int target){
		this.target = target;
	}
	
	public final int target; // 0 environnement, 1 agent
}
