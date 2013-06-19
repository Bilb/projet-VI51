package Action;

public enum ActionProcessTag {
	// action qui doivent être executer par l'environnement ou l'agent en fonction de l'argument
	// 1 agent, 0 environnement
	// ces tag sont traités par l'environnement qui en focntion de l'argument appel matchProcessTagEnvironnement
	// de l'environnement
	// ou matchProccessTagAgent(AgentBody ), fonctions qui permettent de convertir le tag en action à réaliser
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
		DIE(1) {
		},
		MOVE(0)
		;
		
		ActionProcessTag( int target){
			this.target = target;
		}
		public final int target; // 0 environnement, 1 agent
	}
	

