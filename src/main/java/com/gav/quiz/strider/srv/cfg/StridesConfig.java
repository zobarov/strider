/**
 * 
 */
package com.gav.quiz.strider.srv.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author alex
 *
 */
@Configuration
public class StridesConfig {
	@Value("${strides.min}")
	private Integer minStrides;	
	@Value("${strides.max}")
	private Integer maxStrides;
	@Value("${strides.to.turn}")
	private Integer stridesToTurn;
	
	public Integer minStrides() {
		return minStrides;
	}
	
	public Integer maxStrides() {
		return maxStrides;
	}
	
	public Integer stridesToTurn() {
		return stridesToTurn;
	}

}
