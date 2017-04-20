package com.gav.quiz.strider.srv;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gav.quiz.strider.dto.StairwellDTO;
import com.gav.quiz.strider.srv.cfg.StridesConfig;
import com.gav.quiz.strider.util.Loggable;

import ch.qos.logback.classic.Logger;

/**
 * @author alex.gera
 */
@Service
public class StriderClimpingJ8Service implements StridingServiceInteface {
	@Loggable
	private Logger log;

	@Autowired
	private StriderValidator prerequisiteValidator;
	@Autowired
	private StridesConfig stridesConfig;
	
	@Override
	public void doValidation(StairwellDTO stairwell, int stepsPerStride) {
		if(prerequisiteValidator == null) {
			log.warn("DISABLED Pre validation for StriderClimpingService.");
			return;
		}
		log.info("Pre validation for Strider is: {}", prerequisiteValidator.getClass());
		prerequisiteValidator.validate(stairwell, stepsPerStride);
	}

	@Override
	public int doClimbing(StairwellDTO stairwell, int stepsPerStride) {		
		StepsInFlightConsumer passFlightAction = new  StepsInFlightConsumer(stepsPerStride);
		stairwell.getFlights().forEach(passFlightAction);

		int resultStepsAmount = passFlightAction.resultStepsAmount();
		
		//no turn steps on the top:
		if(resultStepsAmount > stridesConfig.stridesToTurn()) {
			resultStepsAmount -= stridesConfig.stridesToTurn();
		}

		return resultStepsAmount;
	}

	public void setPreValidator(StriderValidator preValidator) {
		this.prerequisiteValidator = preValidator;
	}
	
	/**
	 * Consumer to encapsulate acceptance logic.
	 */
	private class StepsInFlightConsumer implements Consumer<Integer> {
		private int flightCounter = 0;
		private int resultStepsAmount = 0;
		
		int stepsPerStride;
		
		public StepsInFlightConsumer(Integer sps) {
			this.stepsPerStride = sps;
		}

		@Override
		public void accept(Integer stepsInFlight) {
			flightCounter++;
			double stepsNeeded = (double) stepsInFlight / stepsPerStride;
			int actualStepsNeededWithRerminder = (int) Math.ceil(stepsNeeded);
			log.debug("Actual steps needed to pass flight {}: {} / {} = {}", flightCounter, stepsInFlight, stepsPerStride, actualStepsNeededWithRerminder);

			resultStepsAmount += actualStepsNeededWithRerminder;
			//assuming a turn:
			resultStepsAmount += stridesConfig.stridesToTurn();			
		}
		
		public Integer resultStepsAmount() {
			return resultStepsAmount;
		}
	}	
}
