/**
 * 
 */
package com.gav.job17.strider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gav.job17.strider.util.Loggable;

import ch.qos.logback.classic.Logger;

/**
 * @author alex
 *
 */
@Controller
public class StriderMainController {
	@Loggable
	private Logger log;
	
	@RequestMapping({"/strider"})
	public @ResponseBody String resp(@RequestParam(value="sps", defaultValue="3") Integer stepsPerStride,
									 StairwellDTO stairwell) {
		log.info("Got a request to climb calculation for SPS={} and stairwell: {}", stepsPerStride, stairwell);
		
		
		return "Stariwell " + stairwell + " at steps=" + stepsPerStride;		
	}

}
