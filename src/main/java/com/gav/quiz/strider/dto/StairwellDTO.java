/**
 * 
 */
package com.gav.quiz.strider.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alex
 *
 */
public class StairwellDTO {
	private ArrayList<Integer> flights;
	private Integer stepsOnFlightTurn = 2;

	public StairwellDTO() {
		this.flights = new ArrayList<Integer>();
	}

	public StairwellDTO(List<Integer> flightsList) {
		this.flights = new ArrayList<Integer>(flightsList.size());
		flights.addAll(flightsList);
	}

	public List<Integer> getFlights() {
		return flights;
	}

	public void addFlight(Integer stepsInFlight) {
		flights.add(stepsInFlight);
	}

	@Override
	public String toString() {
		StringBuilder sb  = new StringBuilder("{Stairwell:");
		sb.append(flights)
		  .append("}");
		return sb.toString();
	}
}
