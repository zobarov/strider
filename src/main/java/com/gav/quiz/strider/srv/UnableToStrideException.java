/**
 * 
 */
package com.gav.quiz.strider.srv;

/**
 * @author alex
 *
 */
public class UnableToStrideException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnableToStrideException(String reason) {
		super(reason);
	}

}
