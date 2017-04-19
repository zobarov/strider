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
	
	public int calc(StairwellDTO stairwell, int stepsPerFlight) {
		boolean validToGo = prerequisiteValidator.validate(stairwell, stepsPerFlight);
		if(!validToGo) {
			log.warn("Prerequisite validation for Strider is not passed.");
			return 0;
		}
		
		return 0;
	}
	
	public void setPreValidator(StrideValidator preValidator) {
		this.prerequisiteValidator = preValidator;
	}
}
