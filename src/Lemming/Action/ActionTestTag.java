package Lemming.Action;


/**
 * un ActionTestTag equivaux a un test fait par l'agent ou l'environnement et qui retourne une valeur booleenne
 * test qui doivent etre executer par l'environnement ou l'agent en fonction de l'argument
 * 1 agent, 0 environnement
 * ces tag sont traites par l'environnement qui en focntion de l'argument appel 
 * executeTestTag de l'agent ou executeTestTag de l'environnement
 * ce système permet a l'environnement d'ignorer le comportement de l'agent ou les possibilites de l'agent
 */

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
