package com.michaelbaranov.microba.common;

import java.util.EventObject;

/**
 * An event used to indicate a policy (algorithm) has changed.
 * 
 * @author Michael Baranov
 * 
 */
public class PolicyEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            an object whose policy has changed
	 */
	public PolicyEvent(Object source) {
		super(source);
	}

}
