/**
 * 
 */
package com.gav.quiz.strider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gav.quiz.strider.dto.StairwellDTO;
import com.gav.quiz.strider.srv.StrideCalculatorSrv;
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
	private StrideCalculatorSrv striderSrv;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String resp(@RequestParam(name="sps", required=true) Integer sps,
									 StairwellDTO stairwell) {
		log.info("Got a request to climb calculation for SPS={} and stairwell: {}", sps, stairwell);
		
		int strideCount = 0;
		try {
			strideCount = striderSrv.calc(stairwell, sps);
		} catch(UnableToStrideException utse) {
			log.error("Fail to count. Cause: {}", utse.getMessage());
			return "Can not proceed with such paramters. Cause:" + utse.getMessage() + ".";
		}
		log.info("Ok. Got minimum stride count: {}", strideCount);

		return "Stariwell " + stairwell + " at steps=" + sps + ". Minimum strides:" + strideCount;		
	}

}
