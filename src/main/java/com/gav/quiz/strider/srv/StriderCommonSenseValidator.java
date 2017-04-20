package com.gav.quiz.strider.srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gav.quiz.strider.dto.StairwellDTO;
import com.gav.quiz.strider.srv.cfg.StairwellConfig;
import com.gav.quiz.strider.srv.cfg.StridesConfig;

/**
 * @author alex.gera
 */
@Component
public class StriderCommonSenseValidator implements StriderValidator {
	@Autowired
	private StairwellConfig stairwellConfig;
	@Autowired
	private StridesConfig stridesConfig;

	public void validate(StairwellDTO stairwell, int stepsPerStride) throws UnableToStrideException {
		if(stairwell == null) {
			throw new UnableToStrideException("Nullable stairwell.");
		}
		if(stairwell.getFlights().size() < stairwellConfig.minFlightAmount()) {
			throw new UnableToStrideException("Stairwell length is less then min:" + stairwellConfig.minFlightAmount());
		}
		if(stairwell.getFlights().size() > stairwellConfig.maxFlightAmount()) {
			throw new UnableToStrideException("Stairwell length is greater then max: " + stairwellConfig.maxFlightAmount());
		}
		if(stepsPerStride == 0) {
			throw new UnableToStrideException("Steps per stride (SPS) cannot be zero");
		}
		if(stepsPerStride < stridesConfig.minStrides()) {
			throw new UnableToStrideException("Steps per stride (SPS) cannot be less then min= " + stridesConfig.minStrides());
		}
		if(stepsPerStride > stridesConfig.maxStrides()) {
			throw new UnableToStrideException("Steps per stride (SPS) cannot be greater then max= " + stridesConfig.maxStrides());
		}

		int flightCounter = 0;
		for(Integer stepsInFlight : stairwell.getFlights()) {
			if(stepsInFlight <= 0) {
				throw new UnableToStrideException("Zero or negative steps in fligts [" + flightCounter + "] is not allowed.");
			}
			if(stepsInFlight < stairwellConfig.minStepsInFlightAmount()) {
				throw new UnableToStrideException("Amount of steps in  flight ["
						+ flightCounter
						+ "] cannot be less then min= " + stairwellConfig.minStepsInFlightAmount());
			}
			if(stepsInFlight > stairwellConfig.maxStepsInFlightAmount()) {
				throw new UnableToStrideException("Amount of steps in  flight ["
								+ flightCounter
								+ "] cannot be greater then max= " + stairwellConfig.maxStepsInFlightAmount());
			}
		}
	}
}
