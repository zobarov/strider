/**
 * 
 */
package com.gav.quiz.strider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gav.quiz.strider.dto.StairwellDTO;
import com.gav.quiz.strider.srv.StriderClimpingService;
import com.gav.quiz.strider.srv.UnableToStrideException;
import com.gav.quiz.strider.util.Loggable;

import ch.qos.logback.classic.Logger;

/**
 * @author alex
 *
 */
@RestController
@RequestMapping("/strider")
public class StriderMainController {
	@Loggable
	private Logger log;
	
	@Autowired
	private StriderClimpingService striderSrv;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String resp(@RequestParam(name="sps", required=true) Integer sps,
									 @RequestParam(name="stairwell", required=true) List<Integer> stairwellFlights) {
		log.info("Got a request to climb calculation for SPS={} and stairwell: {}", sps, stairwellFlights);
		
		int strideCount = 0;
		try {
			StairwellDTO stairwell = new StairwellDTO(stairwellFlights);
			strideCount = striderSrv.climpToTheTop(stairwell, sps);
		} catch(UnableToStrideException utse) {
			log.error("Fail to count. Cause: {}", utse.getMessage());
			return "Can not proceed with such paramters. Cause:" + utse.getMessage() + ".";
		}
		log.info("Ok. Got minimum stride count: {}", strideCount);
		return "Stariwell " + stairwellFlights + " at steps=" + sps + ". Minimum strides:" + strideCount;		
	}

}
