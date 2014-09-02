package rmblworx.tools.timey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml", "/stopwatch-spring-timey-context.xml" })
public class SimpleTimerTest {
	/**
	 * Beschreibt die Zeit für die der Thread ruhen soll.
	 */
	private static final long DELAY = 1000L;
	/**
	 * Referenz auf das gemockte TimeDescriptor-Objekt.
	 */
	@Mock
	private TimeDescriptor mockedDescriptor;
	/**
	 * Referenz auf den Spring-Anwendungskontext.
	 */
	@Autowired
	private ApplicationContext springContext;
	/**
	 * Referenz auf die zu testende Implementierung.
	 */
	@Autowired
	private SimpleTimer timer;

	/**
	 * @throws java.lang.Exception
	 *             wenn beim setzen des Spring-Anwendungskontextes eine Ausnahme auftrat
	 */
	@Before
	public final void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.timer = new SimpleTimer(this.mockedDescriptor);
		this.timer.setApplicationContext(this.springContext);
	}

	@After
	public final void tearDown() {
		this.mockedDescriptor = null;
		this.timer = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#resetStopwatch()}.
	 */
	@Test
	public final void testResetStopwatch() {
		this.timer.startStopwatch(1, TimeUnit.NANOSECONDS);
		assertTrue(this.timer.resetStopwatch());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#startStopwatch(int, java.util.concurrent.TimeUnit)}.
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public final void testShouldFailBecauseDelayIsLessThanOne() {
		this.timer.startStopwatch(0, TimeUnit.NANOSECONDS);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#SimpleTimer(rmblworx.tools.timey.vo.TimeDescriptor)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeDescriptorIsNull() {
		this.timer = new SimpleTimer(null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#startStopwatch(int, java.util.concurrent.TimeUnit)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeUnitIsNull() {
		this.timer.startStopwatch(1, null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#startStopwatch(int, java.util.concurrent.TimeUnit)}.
	 */
	@Test
	public final void testStartStopwatch() {
		assertNotNull(this.timer.startStopwatch(1, TimeUnit.NANOSECONDS));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#stopStopwatch()}.
	 */
	@Test
	public final void testStopStopwatch() {
		this.timer.startStopwatch(1, TimeUnit.NANOSECONDS);
		assertTrue(this.timer.stopStopwatch());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#toggleTimeModeInStopwatch()}.
	 *
	 * @throws InterruptedException
	 *             wenn beim unterbrechen des Threads ein Ausnahme auftrat
	 */
	@Test
	public final void testToggleTimeModeInStopwatchExtendedCase() throws InterruptedException {
		// testet ob TIME-Modus zuverlässig funktioniert wenn zwischendurch die Uhr gestoppt wurde während TIME-Modus
		// aktiv
		TimeDescriptor td = new TimeDescriptor(0L);
		this.timer = new SimpleTimer(td);
		this.timer.setApplicationContext(this.springContext);

		td = this.timer.startStopwatch(1, TimeUnit.NANOSECONDS);
		Thread.sleep(DELAY);
		assertTrue("Stoppuhr läuft nicht!", td.getMilliSeconds() > 0);

		// TIME-Mode ein
		this.timer.toggleTimeModeInStopwatch();
		final long expected = td.getMilliSeconds();
		Thread.sleep(DELAY);
		long actual = td.getMilliSeconds();
		assertEquals("Stoppuhr zählt weiter obwohl TIME-Mode aktiv!", expected, actual);

		// STOP gedrueckt - Zeitmessung stoppt aber eingefrorene Zeit bleibt im Vordergrund
		if (this.timer.stopStopwatch()) {
			assertEquals("Werte nicht identisch obwohl TIME-Mode aktiv!", expected, actual);
			actual = td.getMilliSeconds();
		} else {
			fail("Uhr konnte nicht angehalten werden!");
		}

		// TIME-Mode aus - Uhr noch angehalten - es muss nun der letzte Wert bei Stop geliefert werden
		this.timer.toggleTimeModeInStopwatch();
		Thread.sleep(1);
		actual = td.getMilliSeconds();
		assertNotEquals("Stoppuhr hat den TIME-Modus nicht deaktiviert!", expected, actual);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#toggleTimeModeInStopwatch()}.
	 *
	 * @throws InterruptedException
	 *             wenn beim schlafenlegen des Threads eine Ausnahme auftrat
	 */
	@Test
	public final void testToggleTimeModeInStopwatchSimpleCase() throws InterruptedException {
		TimeDescriptor td = new TimeDescriptor(0L);
		this.timer = new SimpleTimer(td);
		this.timer.setApplicationContext(this.springContext);

		td = this.timer.startStopwatch(1, TimeUnit.NANOSECONDS);
		Thread.sleep(DELAY);
		assertTrue("Stoppuhr läuft nicht!", td.getMilliSeconds() > 0);

		// TIME-Mode ein
		this.timer.toggleTimeModeInStopwatch();
		long expected = td.getMilliSeconds();
		Thread.sleep(DELAY);
		long actual = td.getMilliSeconds();
		assertEquals("Stoppuhr zählt weiter obwohl TIME-Mode aktiv!", expected, actual);

		// TIME-Mode aus
		this.timer.toggleTimeModeInStopwatch();
		expected = td.getMilliSeconds();
		Thread.sleep(DELAY);
		actual = td.getMilliSeconds();
		assertNotEquals("Stoppuhr hat den TIME-Modus nicht deaktiviert!", expected, actual);

		this.timer.stopStopwatch();
	}
}
