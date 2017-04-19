/**
 * 
 */
package com.gav.quiz.strider.srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gav.quiz.strider.dto.StairwellDTO;

/**
 * @author alex
 *
 */
@Component
public class StriderCommonSensePreValidator implements StrideValidator {
	@Autowired
	private StairwellConfig stairwellConfig;	
	@Autowired
	private StridesConfig stridesConfig;
	
	public void validate(StairwellDTO stairwell, int stepsPerFlight) throws UnableToStrideException {
		if(stairwell == null) {
			throw new UnableToStrideException("Nullable stairwell.");
		}
		if(stairwell.getFlights().size() < stairwellConfig.getMinFlightAmount()) {
			throw new UnableToStrideException("Stairwell length is less then minimum.");			
		}
		if(stairwell.getFlights().size() > stairwellConfig.getMaxFlightAmount()) {
			throw new UnableToStrideException("Stairwell length is greater then maximum.");			
		}
	}
	

}
