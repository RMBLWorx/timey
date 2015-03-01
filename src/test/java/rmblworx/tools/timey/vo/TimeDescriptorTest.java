package rmblworx.tools.timey.vo;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.exception.ValueMinimumArgumentException;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Testklasse die das Wertobjekt zum kapseln der Zeitwerte testet.
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml", "/alarm-spring-timey-context.xml" })
public class TimeDescriptorTest {

	/**
	 * Konstante für den erwarteten Testwert.
	 */
	private static final int EXPECTED_MILLISECONDS = 100;

	@Autowired
	private TimeDescriptor descriptor;

	@After
	public final void tearDown() {
		this.descriptor = null;
	}

	/**
	 * Test method for {@link TimeDescriptor}.
	 */
	@Test
	public final void testCorrectBehaviorOfTheVo() {
		this.descriptor.setMilliSeconds(EXPECTED_MILLISECONDS);
		final long actualMilliseconds = this.descriptor.getMilliSeconds();

		assertEquals("Test fehlgeschlagen: Millisekunden falsch!", EXPECTED_MILLISECONDS, actualMilliseconds);
	}

	/**
	 * Test method for {@link TimeDescriptor#setMilliSeconds(long)}.
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public final void testShouldFailBecauseValueLessThanZero() {
		this.descriptor.setMilliSeconds(-1);
	}

}
