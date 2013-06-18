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

/**
 * Default implementation of a QState.
 * 
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class DefaultQState implements QState {
	
	private static final long serialVersionUID = -6376982706299950686L;
	
	private final int number;
	private String description;
	
	/**
	 * @param number is the number associated to this state.
	 */
	public DefaultQState(int number) {
		this(number, null);
	}

	/**
	 * @param number is the number associated to this state.
	 * @param description describes the state.
	 */
	public DefaultQState(int number, String description) {
		this.number = number;
		if (description==null || description.isEmpty()) {
			this.description = "STATE_"+this.number; //$NON-NLS-1$
		}
		else {
			this.description = description;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DefaultQState clone() {
		try {
			return (DefaultQState)super.clone();
		}
		catch(Throwable e) {
			throw new Error(e);
		}
	}
	
	/** Change the description of the state.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		if (description==null || description.isEmpty()) {
			this.description = "STATE_"+this.number; //$NON-NLS-1$
		}
		else {
			this.description = description;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int toInt() {
		return this.number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(QComparable o) {
		return QComparator.QCOMPARATOR.compare(this, o);
	}
	
}