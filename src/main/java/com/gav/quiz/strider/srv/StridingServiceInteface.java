package com.gav.quiz.strider.srv;

import com.gav.quiz.strider.dto.StairwellDTO;

/**
 * @author alex.gera
 */
public interface StridingServiceInteface {
	
	/**
	 * Forces users to do validation first.
	 */
	public default int climpToTheTop(StairwellDTO stairwell, int stepsPerStride) {
		doValidation(stairwell, stepsPerStride);
		return doClimbing(stairwell, stepsPerStride);
	}
	
	void doValidation(StairwellDTO stairwell, int stepsPerStride);
	
	int doClimbing(StairwellDTO stairwell, int stepsPerStride);
}
