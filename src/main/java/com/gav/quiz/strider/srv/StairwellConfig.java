/**
 * 
 */
package com.gav.quiz.strider.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author alex
 *
 */
@Configuration
public class StairwellConfig {
	
	@Value("${stairwell.flights.amount.min}")
	private Integer minFlightsAmount;
	
	@Value("${stairwell.flights.amount.max}")
	private Integer maxFlightsAmount;
	
	public Integer getMinFlightAmount() {
		return minFlightsAmount;
	}
	
	public Integer getMaxFlightAmount() {
		return maxFlightsAmount;
	}
}
