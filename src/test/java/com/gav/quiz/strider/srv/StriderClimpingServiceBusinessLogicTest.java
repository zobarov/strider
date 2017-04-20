package com.gav.quiz.strider.srv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.gav.quiz.strider.dto.StairwellDTO;
import com.gav.quiz.strider.srv.cfg.StairwellConfig;
import com.gav.quiz.strider.srv.cfg.StridesConfig;

/**
 * @author alex.gera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:./app_common.test.properties")
public class StriderClimpingServiceBusinessLogicTest {
	@Autowired
	private StriderClimpingService strideSrvUnderTest;
	@Autowired
	private StairwellConfig stairwellConfig;
	@Autowired
	private StridesConfig stridesConfig;

	//One flight:
	@Test
	public void shouldClimpOneFlight_MinStrides() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stridesConfig.minStrides());
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, stridesConfig.minStrides());
		//then:
		assertEquals("Minimum steps flight should be passed in one stride.", 1, actualStrideAmount);
	}

	@Test
	public void shouldClimpOneFlight_WithReminder() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(17);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("For the reminder we need one extra step.", 6, actualStrideAmount);
	}

	@Test
	public void shouldClimpOneFlight_WithNoReminder() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(15);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("With no remined no any extra steps to be.", 5, actualStrideAmount);
	}

	@Test
	public void shouldClimpOneFlight_ShorterThenStrideRange() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(2);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Shorter then steps range flights should be passed in one step.", 1, actualStrideAmount);
	}

	//Three flights:

	@Test
	public void shouldClimpThreeFlights_NoReminders() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(6);
		stairwell.addFlight(12);
		stairwell.addFlight(9);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 2steps+2+4steps+2+3steps = 13", 13, actualStrideAmount);
	}

	@Test
	public void shouldClimpThreeFlights_VariousReminders() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(20);
		stairwell.addFlight(1);
		stairwell.addFlight(15);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 7steps+2+1steps+2+5steps = 17", 17, actualStrideAmount);
	}

	@Test
	public void shouldClimpThreeFlights_EqualFlights() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(3);
		stairwell.addFlight(3);
		stairwell.addFlight(3);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 1steps+2+1steps+2+1steps = 7", 7, actualStrideAmount);
	}

	@Test
	public void shouldClimpThreeFlights_MaxStepsInFlights() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stairwellConfig.maxStepsInFlightAmount()); //20 steps
		stairwell.addFlight(stairwellConfig.maxStepsInFlightAmount());
		stairwell.addFlight(stairwellConfig.maxStepsInFlightAmount());
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 7steps+2+7steps+2+7steps = 25", 25, actualStrideAmount);
	}

	@Test
	public void shouldClimpThreeFlights_MinStepsInFlights() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(stairwellConfig.minStepsInFlightAmount()); //1 step
		stairwell.addFlight(stairwellConfig.minStepsInFlightAmount());
		stairwell.addFlight(stairwellConfig.minStepsInFlightAmount());
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 1steps+2+1steps+2+1steps = 7", 7, actualStrideAmount);
	}

	//Boundaries flights:
	@Test
	public void shouldClimpBoundaires_MaxFligthsAndMaxSteps() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		for(int i = 0; i < stairwellConfig.maxFlightAmount(); i++) { //30 flights
			stairwell.addFlight(stairwellConfig.maxStepsInFlightAmount()); //per 20steps
		}

		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 30f*7st + 29turns*2 = 268", 268, actualStrideAmount);
	}

	@Test
	public void shouldClimpBoundaires_MaxFligthsAndMinSteps() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		for(int i = 0; i < stairwellConfig.maxFlightAmount(); i++) { //30 flights
			stairwell.addFlight(stairwellConfig.minStepsInFlightAmount()); //per 1steps
		}

		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 30f*1st + 29turns*2 = 88", 88, actualStrideAmount);
	}

	@Test
	public void shouldClimpBoundaires_MinFligthsAndMinSteps() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		for(int i = 0; i < stairwellConfig.minFlightAmount(); i++) { //1 flights
			stairwell.addFlight(stairwellConfig.minStepsInFlightAmount()); //per 1steps
		}

		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 1f*1st + NO TURNS = 1", 1, actualStrideAmount);
	}

	@Test
	public void shouldClimpBoundaires_MinFligthsAndMaxSteps() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		for(int i = 0; i < stairwellConfig.minFlightAmount(); i++) { //1 flights
			stairwell.addFlight(stairwellConfig.maxStepsInFlightAmount()); //per 20steps
		}

		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Should be 1f*7st + no turns = 7", 7, actualStrideAmount);
	}

	//////Outline examples:
	@Test
	public void shouldClimp_ExampleOne() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(17);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Example One expecting strides amount = 6", 6, actualStrideAmount);
	}

	@Test
	public void shouldClimp_ExampleTwo() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(17);
		stairwell.addFlight(17);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 3);
		//then:
		assertEquals("Example Two expecting strides amount = 14", 14, actualStrideAmount);
	}

	@Test
	public void shouldClimp_ExampleThree() {
		//given:
		StairwellDTO stairwell = new StairwellDTO();
		stairwell.addFlight(4);
		stairwell.addFlight(9);
		stairwell.addFlight(8);
		stairwell.addFlight(11);
		stairwell.addFlight(7);
		stairwell.addFlight(20);
		stairwell.addFlight(14);
		//when:
		int actualStrideAmount = strideSrvUnderTest.climpToTheTop(stairwell, 2);
		//then:
		assertEquals("Example Three expecting strides amount = 50", 50, actualStrideAmount);
	}
}
