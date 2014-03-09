/**
 * 
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml" })
public class SimpleCountdownTest {
	private static final long COUNTDOWN_START = 10000l;
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
	 * Test method for {@link rmblworx.tools.timey.CountdownRunnable#computeTime()}.
	 * @throws InterruptedException wenn dieser Thread nicht schlafen gelegt werden konnte.
	 */
	@Test
	public void testComputeTime() throws InterruptedException {
		final long time = System.currentTimeMillis() + COUNTDOWN_START;

		this.countdown.startCountdown();
		Thread.sleep(TIME_TO_WAIT);
		this.countdown.stopCountdown();
		final long actualCountdownTime = this.descriptor.getMilliSeconds();

		assertTrue(this.descriptor.getMilliSeconds() + "", actualCountdownTime < time);
	}

}
