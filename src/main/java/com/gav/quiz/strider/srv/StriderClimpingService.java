package com.gav.quiz.strider.srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gav.quiz.strider.dto.StairwellDTO;
import com.gav.quiz.strider.srv.cfg.StridesConfig;
import com.gav.quiz.strider.util.Loggable;

import ch.qos.logback.classic.Logger;

@Service
public class StriderClimpingService {
	@Loggable
	private Logger log;

	@Autowired
	private StriderValidator prerequisiteValidator;
	@Autowired
	private StridesConfig stridesConfig;

	public int climpToTheTop(StairwellDTO stairwell, int stepsPerStride) {
		doValidation(stairwell, stepsPerStride);

		int flightCounter = 0;
		int resultStepsAmount = 0;

		for(Integer stepsInFlight : stairwell.getFlights()) {
			flightCounter++;
			double stepsNeeded = (double) stepsInFlight / stepsPerStride;
			int actualStepsNeededWithRerminder = (int) Math.ceil(stepsNeeded);
			log.debug("Actual steps needed to pass flight {}: {} / {} = {}", flightCounter, stepsInFlight, stepsPerStride, actualStepsNeededWithRerminder);

			resultStepsAmount += actualStepsNeededWithRerminder;
			//assuming a turn:
			resultStepsAmount += stridesConfig.stridesToTurn();
		}
		//no turn steps on the top:
		if(resultStepsAmount > stridesConfig.stridesToTurn()) {
			resultStepsAmount -= stridesConfig.stridesToTurn();
		}

		return resultStepsAmount;
	}

	public void setPreValidator(StriderValidator preValidator) {
		this.prerequisiteValidator = preValidator;
	}

	protected void doValidation(StairwellDTO stairwell, int stepsPerStride) {
		if(prerequisiteValidator == null) {
			log.warn("DISABLED Pre validation for StriderClimpingService.");
			return;
		}
		log.info("Pre validation for Strider is: {}", prerequisiteValidator.getClass());
		prerequisiteValidator.validate(stairwell, stepsPerStride);
	}
}
