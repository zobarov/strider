package com.gav.quiz.strider.srv;

import org.springframework.stereotype.Component;

import com.gav.quiz.strider.dto.StairwellDTO;

/**
 * @author alex.gera
 */
@Component
public interface StriderValidator {

	/**
	 * Validates input parameters for {@link StriderClimpingService}.
	 * In all invalid cases throws an {@link UnableToStrideException} to signal potential incorrect climbing.
	 * 
	 * @param stairwell DTO bean the target instance to validate.
	 * @param stepsPerStride number the target instance to process
	 * @throws UnableToStrideException
	 */
	void validate(StairwellDTO stairwell, int stepsPerStride) throws UnableToStrideException;

}
