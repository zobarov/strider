/**
 * 
 */
package com.gav.quiz.strider.srv;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.gav.quiz.strider.dto.StairwellDTO;
import com.gav.quiz.strider.srv.cfg.StairwellConfig;
import com.gav.quiz.strider.srv.cfg.StridesConfig;

/**
 * @author alex
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:./app_common.test.properties")
public class StriderCommonSenseValidatorTest {
	@Autowired
	private StriderCommonSenseValidator validatorUnderTest;

	@Autowired
	private StairwellConfig stairwellConfig;
	@Autowired
	private StridesConfig stridesConfig;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private int stsForStairwellInRange = 0;
	private int stepsInFlightsInRange = 0;

	@Before
	public void setUp() {
		stsForStairwellInRange = new Random()
				.nextInt((stridesConfig.maxStrides() - stridesConfig.minStrides()) + 1) + stridesConfig.minStrides();

		stepsInFlightsInRange = new Random()
				.nextInt((stairwellConfig.maxStepsInFlightAmount() - stairwellConfig.minStepsInFlightAmount()) + 1) + stairwellConfig.minStepsInFlightAmount();
	}

	///////////////Stairwell checks:

	@Test
	public void shouldStairwell_ThrowIfNull() {
		//given:
		StairwellDTO stairwell = null;
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage("Nullable stairwell");

		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("In case of null stairwell should not be here.", false);
	}

	@Test
	public void shouldStairwell_BeAwareOfMinFlightsAmount() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage(containsString("less"));

		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("In case of none flights in stairwell should not be here.", false);
	}

	@Test
	public void shouldStairwell_BeAwareOfMaxFlightsAmount() {
		//given:
		ArrayList<Integer> tempList = new ArrayList<Integer>(stairwellConfig.maxFlightAmount() + 1);
		for(int i = 0; i <= stairwellConfig.maxFlightAmount() + 1; i++) {
			tempList.add(i);
		}
		StairwellDTO stairwell = new StairwellDTO(tempList);
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage(containsString("greater"));

		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("In case of enormous flights in stairwell should not be here.", false);
	}

	@Test
	public void shouldStairwell_BeOkForMaxFlightsAmount_Inclusively() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		for(int i = 0; i < stairwellConfig.maxFlightAmount(); i++) {
			stairwell.addFlight(stepsInFlightsInRange);
		}
		//when:
		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("Inclusive maximum to pass test with no exceptions.", true);
	}

	@Test
	public void shouldStrides_BeOkForMinFlightsAmount_Inclusively() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		for(int i = 0; i <= stairwellConfig.minFlightAmount(); i++) {
			stairwell.addFlight(stepsInFlightsInRange);
		}
		//when:
		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("Inclusive minimum to pass test with no exceptions.", true);
	}

	@Test
	public void shouldStairwell_BeOkInRange() {
		//given:
		int flightsAmountInRange = new Random()
				.nextInt((stridesConfig.maxStrides() - stridesConfig.minStrides()) + 1) + stridesConfig.minStrides();
		StairwellDTO stairwell = new StairwellDTO();
		for(int i = 0; i <= flightsAmountInRange; i++) {
			stairwell.addFlight(stepsInFlightsInRange);
		}
		//when:
		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("Inclusive in range to pass test with no exceptions.", true);
	}

	///////////////Steps checks:
	@Test
	public void shouldSteps_ThrowIfZeroStepsFlight() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stepsInFlightsInRange);
		stairwell.addFlight(0);
		stairwell.addFlight(stepsInFlightsInRange);
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage("Zero or negative");

		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("In case of Zero or negative steps in flights should not be here.", false);
	}

	@Test
	public void shouldSteps_ThrowIfNegativeStepsFlight() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stepsInFlightsInRange);
		stairwell.addFlight(-1);
		stairwell.addFlight(stepsInFlightsInRange);
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage("Zero or negative");

		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("In case of Zero or negative steps in flights should not be here.", false);
	}

	@Test
	public void shouldSteps_BeAwareOfMinFlightsAmount() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stepsInFlightsInRange);
		stairwell.addFlight(stairwellConfig.minStepsInFlightAmount() - 1);
		stairwell.addFlight(stepsInFlightsInRange);
		//when:
		expectedException.expect(UnableToStrideException.class);
		//expectedException.expectMessage(containsString("less"));  0 in common case here

		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("In case of less then minimum steps in a flight should not be here.", false);
	}

	@Test
	public void shouldSteps_BeAwareOfMaxFlightsAmount() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stepsInFlightsInRange);
		stairwell.addFlight(stairwellConfig.maxStepsInFlightAmount() + 1);
		stairwell.addFlight(stepsInFlightsInRange);
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage(containsString("greater"));

		validatorUnderTest.validate(stairwell, stsForStairwellInRange);		
		//then:
		assertTrue("In case of greater then maximum steps in a flight should not be here.", false);
	}

	@Test
	public void shouldSteps_BeOkForMaxStepsInFlightsAmount_Inclusively() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stairwellConfig.maxStepsInFlightAmount());
		//when:
		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("Inclusive maximum to pass test with no exceptions.", true);
	}

	@Test
	public void shouldSteps_BeOkForMinFlightsAmount_Inclusively() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stairwellConfig.minStepsInFlightAmount());
		//when:
		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("Inclusive minimum to pass test with no exceptions.", true);
	}

	@Test
	public void shouldSteps_BeOkInRange_One() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		int oneMoreStepsInRange = new Random()
				.nextInt((stairwellConfig.maxStepsInFlightAmount() - stairwellConfig.minStepsInFlightAmount()) + 1) + stairwellConfig.minStepsInFlightAmount();
		stairwell.addFlight(oneMoreStepsInRange);
		//when:
		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("Inclusive in range to pass test with no exceptions.", true);
	}

	@Test
	public void shouldSteps_BeOkInRange_Max() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		for(int i = 0; i < stairwellConfig.maxFlightAmount(); i++) {
			int oneMoreStepsInRange = new Random()
					.nextInt((stairwellConfig.maxStepsInFlightAmount() - stairwellConfig.minStepsInFlightAmount()) + 1) + stairwellConfig.minStepsInFlightAmount();
			stairwell.addFlight(oneMoreStepsInRange);
		}
		//when:
		validatorUnderTest.validate(stairwell, stsForStairwellInRange);
		//then:
		assertTrue("Inclusive in range to pass test with no exceptions.", true);
	}


	///////////////Strides checks:

	@Test
	public void shouldStrides_ThrowIfZeroStepsPerStride() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(3);
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage(containsString("zero"));

		validatorUnderTest.validate(stairwell, 0);		
		//then:
		assertTrue("In case of zero steps per stride should not be here.", false);
	}

	@Test
	public void shouldStrides_BeAwareOfMaxStepsPerStrideAmount() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(3);
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage(containsString("greater"));
			
		validatorUnderTest.validate(stairwell, 200);		
		//then:
		assertTrue("In case of enormous SPS should not be here.", false);
	}
	
	@Test
	public void shouldStrides_BeAwareOfMinStepsPerStrideAmount() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(3);
		//when:
		expectedException.expect(UnableToStrideException.class);
		expectedException.expectMessage(containsString("less"));
			
		validatorUnderTest.validate(stairwell, -1);		
		//then:
		assertTrue("In case of negative or less SPS should not be here.", false);
	}
	
	@Test
	public void shouldStrides_BeOkForMaxStepsPerStrideAmount_Inclusively() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(3);
		//when:
		validatorUnderTest.validate(stairwell, stridesConfig.maxStrides());		
		//then:
		assertTrue("Inclusive maximum to pass test with no exceptions.", true);
	}
	
	@Test
	public void shouldStrides_BeOkForMinStepsPerStrideAmount_Inclusively() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(3);
		//when:
		validatorUnderTest.validate(stairwell, stridesConfig.minStrides());		
		//then:
		assertTrue("Inclusive minimum to pass test with no exceptions.", true);
	}
	
	@Test
	public void shouldStrides_BeOkInRange() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(3);
		
		int stsInRange = new Random()
				.nextInt((stridesConfig.maxStrides() - stridesConfig.minStrides()) + 1) + stridesConfig.minStrides();
		//when:
		validatorUnderTest.validate(stairwell, stsInRange);		
		//then:
		assertTrue("Inclusive in range to pass test with no exceptions.", true);
	}
}
