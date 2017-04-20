package com.gav.quiz.strider.srv.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author alex.gera
 */
@Configuration
public class StairwellConfig {
	@Value("${stairwell.flights.amount.min}")
	private Integer minFlightsAmount;
	@Value("${stairwell.flights.amount.max}")
	private Integer maxFlightsAmount;

	@Value("${stairwell.steps.amount.min}")
	private Integer minStepsInFlightAmount;
	@Value("${stairwell.steps.amount.max}")
	private Integer maxStepsInFlightAmount;

	public Integer minFlightAmount() {
		return minFlightsAmount;
	}

	public Integer maxFlightAmount() {
		return maxFlightsAmount;
	}

	public Integer minStepsInFlightAmount() {
		return minStepsInFlightAmount;
	}

	public Integer maxStepsInFlightAmount() {
		return maxStepsInFlightAmount;
	}
}
