package Lemming;

import java.util.LinkedList;
import java.util.List;

public class Lemming {

	private LemmingBody lemmingBody;
	private Action action;
	private Boolean test = true;
	Lemming(LemmingBody lemmingBody_) {
		lemmingBody = lemmingBody_;
		
	}

	public void live() {
		consumeInfluences();
		LinkedList<Perception> perc = lemmingBody.getPerceptions();

		/*if(( (TerrainType)perc.get(5)).isDanger || (lemmingBody.getCurrentFall() > lemmingBody.getSupportedFall())) { // 5 is the perception of the terrain where is the lemming
			suicide();
		}
		else {
			executeAction(choseAction(perc));
		}*/
		//TODO
		//if(test){



			TerrainPerception tp = (TerrainPerception) perc.get(2);
				if(!tp.getTerrainElement().isTraversable) {
					executeAction(Action.Climb);
				}
		else{			
			executeAction(Action.Walk);
		}
			//test = false;
		//}
	}
	
	
	private void consumeInfluences() {
		boolean fallInfluencePresent = false;
		for (Influence influence : lemmingBody.getInfluences()) {
			if(influence instanceof FallInfluence) {
				FallInfluence fallInfluence= (FallInfluence) influence;
				lemmingBody.setCurrentFall(lemmingBody.getCurrentFall() + fallInfluence.getNbCell());
				fallInfluencePresent = true;
			}
			else if(influence instanceof MoveInfluence) {
				MoveInfluence moveInfluence= (MoveInfluence) influence;
				 
				if(moveInfluence.getMovementSucess()) {
					//TODO : faire quelque chose avec ça : bad ou bon qlearning par exemple!
				}
				else {
					
				}
			}
		}
		
		/* si il n'y a pas de FallInfluence, c'est que l'on ne tombe pas, ou plus. 
		 * On reset le currentFall du body */
		//TODO : à voir comment ça se gère avec le Q learning
		if(!fallInfluencePresent) {
			lemmingBody.setCurrentFall(0);
		}
	}
	
	public Action chooseAction(List<Perception> perceptions) {
		
		// QLEARNING?????
		return action;
	}

	public void executeAction(Action action) {
		lemmingBody.doAction(action);
	}

	public void suicide() {
		lemmingBody.suicide();
		lemmingBody = null;
	}

	public List<Perception> getPerceptions() {
		
		return null;
	}

	public LemmingBody getLemmingBody() {
		return lemmingBody;
	}

	public void setLemmingBody(LemmingBody lemmingBody) {
		this.lemmingBody = lemmingBody;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	


	
}
