package com.gav.quiz.strider.srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gav.quiz.strider.dto.StairwellDTO;
import com.gav.quiz.strider.util.Loggable;

import ch.qos.logback.classic.Logger;

@Service
public class StrideCalculatorSrv {
	@Loggable
	private Logger log;
	
	@Autowired
	private StrideValidator prerequisiteValidator;
	
	@Autowired
	private StairwellConfig stairwellConfig;
	
	public int climpToTheTop(StairwellDTO stairwell, int stepsPerStride) {
		prerequisiteValidator.validate(stairwell, stepsPerStride);
		
		int flightCounter = 0;
		for(Integer stepsInFlight : stairwell.getFlights()) {
			flightCounter++;
			double stepsNeeded = (double) stepsInFlight / stepsPerStride;			
			int actualStepsNeededWithRerminder = (int) Math.ceil(stepsNeeded);
			log.debug("Actual steps needed to pass flight {}: {} / {} = {}", flightCounter, stepsInFlight, stepsPerStride, actualStepsNeededWithRerminder);
			return actualStepsNeededWithRerminder;			
		}

		
		
		return 0;
	}
	
	public void setPreValidator(StrideValidator preValidator) {
		this.prerequisiteValidator = preValidator;
	}
}
