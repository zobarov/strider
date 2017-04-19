/**
 * 
 */
package com.gav.quiz.strider.dto;

import java.util.Arrays;

/**
 * @author alex
 *
 */
public class StairwellDTO {
	private Integer[] flights;
	
	public StairwellDTO() {
		this.flights = new Integer[] {};
	}
	
	
	public Integer[] getFlights() {
		return flights;
	}

	public void setFligts(Integer[] flightsArray) {
		this.flights = flightsArray;
	}
	
	/*public void addFlight() {
		Collections.
	}*/

	@Override
	public String toString() {
		StringBuilder sb  = new StringBuilder("{Stairwell:");
		sb.append(Arrays.toString(flights))
		  .append("}");
		return sb.toString();
	}

}
