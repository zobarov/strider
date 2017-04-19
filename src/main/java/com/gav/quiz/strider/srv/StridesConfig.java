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
public class StridesConfig {
	@Value("${strides.min}")
	private Integer minStrides;
	
	@Value("${strides.max}")
	private Integer maxStrides;
	
	public Integer getMinStrides() {
		return minStrides;
	}
	
	public Integer getMaxStrides() {
		return maxStrides;
	}

}
