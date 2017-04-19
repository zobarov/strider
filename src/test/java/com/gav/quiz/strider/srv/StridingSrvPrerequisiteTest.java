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
public class StridingSrvPrerequisiteTest {	
	@Autowired
	private StrideCalculatorSrv strideSrvUnderTest;
	
	@Autowired
	private StairwellConfig stairwellConfig;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void shouldThrowNullStairwell() {
		//given:
		StairwellDTO stairwell = null;
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage("Nullable stairwell");

		strideSrvUnderTest.climpToTheTop(stairwell, 10);
		//then:
		assertTrue("In case of null stairwell should not be here.", false);
	}
	
	@Test
	public void shouldBeAwareOfMinFlightsAmount() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage(containsString("less"));
		
		strideSrvUnderTest.climpToTheTop(stairwell, 10);		
		//then:
		assertTrue("In case of none flights in stairwell should not be here.", false);
	}
	
	@Test
	public void shouldBeAwareOfMaxFlightsAmount() {
		//given:		
		ArrayList<Integer> tempList = new ArrayList<Integer>(stairwellConfig.getMaxFlightAmount() + 1);
		for(int i = 0; i <= stairwellConfig.getMaxFlightAmount() + 1; i++) {
			tempList.add(i);			
		}
		StairwellDTO stairwell = new StairwellDTO(tempList);
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage(containsString("greater"));
			
		strideSrvUnderTest.climpToTheTop(stairwell, 10);		
		//then:
		assertTrue("In case of enormous flights in stairwell should not be here.", false);
	}
	
	@Test
	public void shouldThrowZeroStepsPerFlight() {
		StairwellDTO stairwell = new StairwellDTO();
		int result = strideSrvUnderTest.climpToTheTop(stairwell, 0);
		assertEquals("In case of null stairwell should return 0", 0, result);
	}
	
	

}
