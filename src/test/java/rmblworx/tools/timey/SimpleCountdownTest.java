package rmblworx.tools.timey;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(locations = { "/spring-timey-context.xml", "/countdown-spring-timey-context.xml" })
public class SimpleCountdownTest {

	private static final long COUNTDOWN_START = 10000L;
	private static final int TIME_TO_WAIT = 1000;
	@Autowired
	private SimpleCountdown countdown;
	@Autowired
	private TimeDescriptor descriptor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.descriptor.setMilliSeconds(COUNTDOWN_START);
		this.countdown.setCountdownTime(this.descriptor);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.countdown = null;
		this.descriptor = null;
	}

	/**
	 * @throws InterruptedException
	 *             wenn dieser Thread nicht schlafen gelegt werden konnte.
	 */
	@Test
	public void testComputeTime() throws InterruptedException {
		final long time = System.currentTimeMillis() + COUNTDOWN_START;

		this.countdown.startCountdown(1, TimeUnit.MILLISECONDS);
		Thread.sleep(TIME_TO_WAIT);
		this.countdown.stopCountdown();
		final long actualCountdownTime = this.descriptor.getMilliSeconds();

		assertTrue(this.descriptor.getMilliSeconds() + "", actualCountdownTime < time);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleCountdown#setCountdownTime(TimeDescriptor)}.
	 */
	@Test(expected = NullArgumentException.class)
	public void testShouldFailBecauseTimeDescriptorIsNull() {
		this.countdown.setCountdownTime(null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleCountdown#startCountdown(int, TimeUnit)}.
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public void testShouldFailBecauseDelayLessThanOne() {
		this.countdown.startCountdown(0, TimeUnit.MILLISECONDS);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleCountdown#startCountdown(int, TimeUnit)}.
	 */
	@Test(expected = NullArgumentException.class)
	public void testShouldFailBecauseTimeUnitIsNull() {
		this.countdown.startCountdown(1, null);
	}

}
