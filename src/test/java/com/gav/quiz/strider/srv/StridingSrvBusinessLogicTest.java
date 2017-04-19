/**
 * 
 */
package com.gav.quiz.strider.srv;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gav.quiz.strider.dto.StairwellDTO;

/**
 * @author alex
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StridingSrvBusinessLogicTest {	
	@Autowired
	private StrideCalculatorSrv strideSrvUnderTest;	
	@Autowired
	private StairwellConfig stairwellConfig;	
	@Autowired
	private StridesConfig stridesConfig;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void shouldClimpOneFlight_MinStrides() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stridesConfig.minStrides());
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, stridesConfig.minStrides());
		//then:
		assertEquals("In case of null stairwell should not be here.", 1, actualStrideAmount);
	}
	
	@Test
	public void shouldClimpOneFlight_WithReminder() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(17);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("In case of null stairwell should not be here.", 6, actualStrideAmount);
	}
	
	@Test
	public void shouldClimpOneFlight_WithNoReminder() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(15);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("In case of null stairwell should not be here.", 5, actualStrideAmount);
	}


}
