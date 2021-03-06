/* 
 * $Id$
 * 
 * Copyright (c) 2011-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.gi.vi51.learning.qlearning;

import java.io.Serializable;
import java.util.List;

/**
 * This is the abstraction of a QLearning problem to work on.
 * 
 * @param <S> is the type of the states
 * @param <A> is the type of the actions
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface QProblem<S extends QState, A extends QAction> extends Cloneable, Serializable {

	/** Clone this QProblem.
	 * 
	 * @return a clone of this QProblem.
	 */
	public QProblem<S,A> clone();
	
	/** Replies the alpha constant used in the QLearning algo.
	 * 
	 * @return alpha
	 */
	public float getAlpha();
	
	/** Replies the gamma constant used in the QLearning algo.
	 * 
	 * @return gamma
	 */
	public float getGamma();

	/** Replies the rho constant used in the QLearning algo.
	 * 
	 * @return rho
	 */
	public float getRho();
	
	/** Replies the nu constant used in the QLearning algo.
	 * 
	 * @return nu
	 */
	public float getNu();
	
	/** Replies the current state of the problem.
	 * 
	 * @return the current state of the problem.
	 */
	public S getCurrentState();

	/** Replies a randomly selected state of the problem.
	 * 
	 * @return a randomly selected state of the problem.
	 */
	public S getRandomState();

	/** Replies all the states of the problem.
	 * 
	 * @return all the states of the problem.
	 */
	public List<S> getAvailableStates();

	/** Replies all the actions available from the given state.
	 * 
	 * @param state is the selection state
	 * @return all the actions for the given <var>state</var>.
	 */
	public List<A> getAvailableActionsFor(S state);
	
	/** 
	 * Evaluate the action executed from the given state and replies
	 * the feedback.
	 *  
	 * @param state
	 * @param action
	 * @return the feedback
	 */
	public QFeedback<S> takeAction(S state, A action);

}