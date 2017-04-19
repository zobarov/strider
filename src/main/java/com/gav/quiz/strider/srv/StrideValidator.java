/**
 * 
 */
package com.gav.quiz.strider.srv;

import org.springframework.stereotype.Component;

import com.gav.quiz.strider.dto.StairwellDTO;

/**
 * @author alex
 *
 */
@Component
public interface StrideValidator {
	
	boolean validate(StairwellDTO stairwell, int stepsPerFlight) throws UnableToStrideException;

}
