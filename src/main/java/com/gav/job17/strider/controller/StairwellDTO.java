/**
 * 
 */
package com.gav.job17.strider.controller;

import java.util.Arrays;

/**
 * @author alex
 *
 */
public class StairwellDTO {
	private Integer[] stairwell;
	
	public Integer[] getStairwell() {
		return stairwell;
	}

	public void setStairwell(Integer[] stairwell) {
		this.stairwell = stairwell;
	}

	@Override
	public String toString() {
		StringBuilder sb  = new StringBuilder("{Stairwell:");
		sb.append(Arrays.toString(stairwell))
		  .append("}");
		return sb.toString();
	}

}
