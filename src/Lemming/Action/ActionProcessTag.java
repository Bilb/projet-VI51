package Lemming.Action;

/**
 * un tag equivaux a une action de l'environnement ou d'un agent.
 * l'enum contient les actions qui doivent etre executer par l'environnement ou l'agent en fonction de l'argument
 * 1 agent, 0 environnement
 * ces tag sont traites par l'environnement qui en focntion de l'argument appel 
 * executeProcessTag de l'agent ou executeProcessTag de l'environnement
 * ce système permet a l'environnement d'ignorer le comportement de l'agent ou les possibilites de l'agent
 */

public enum ActionProcessTag {

		DESTROY(0){
		},
		PARACHUTE(1){
		},
		NOT_PARACHUTE(1) {
		},
		BLOCK(1){
		},
		CREATE(0) {
		},
		TURNBACK(1) {
		},
		CLIMBING(1) { // to set the flag
		},
		NOT_CLIMBING(1) {
		},
		DIGGING(1) {
			
		},
		NOT_DIGGING(1) {
			
		},
		DRILLING(1) {
			
		},
		NOT_DRILLING(1) {
			
		},
		DIE(1) {
		},
		MOVE(0)
		;
		
		ActionProcessTag( int target){
			this.target = target;
		}
		public final int target; // 0 environnement, 1 agent
	}
	

