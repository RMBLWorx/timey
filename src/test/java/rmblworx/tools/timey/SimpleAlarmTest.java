/**
 *
 */
package rmblworx.tools.timey;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Test für die SimpleAlarm-Klasse ob sie die Exceptions bei fehlerhaften Argumenten wirft.
 *
 * @author mmatthies
 */
public class SimpleAlarmTest {
	/**
	 * Fehlermeldungstext.
	 */
	private static final String KEINE_AUSNAHME = "Es wurde keine Ausnahme geworfen!";
	/**
	 * Zu testende Klasse.
	 */
	private SimpleAlarm simpleAlarm;

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@Before
	public void setUp() throws Exception {
		this.simpleAlarm = new SimpleAlarm();
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public void tearDown() throws Exception {
		this.simpleAlarm = null;
	}

	/**
	 * Test method for {@link SimpleAlarm#startAlarmdetection(int, java.util.concurrent.TimeUnit)}.
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public final void testStartAlarmdetection() {
		this.simpleAlarm.startAlarmdetection(0, null);
		fail(KEINE_AUSNAHME);
	}

	/**
	 * Test method for {@link SimpleAlarm#startAlarmdetection(int, java.util.concurrent.TimeUnit)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testStartAlarmdetectionShouldThrowNullArgumentException() {
		this.simpleAlarm.startAlarmdetection(1, null);
		fail(KEINE_AUSNAHME);
	}
}
