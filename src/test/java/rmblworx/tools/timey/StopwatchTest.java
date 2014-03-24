/**
 * 
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
public class StopwatchTest {
	private IStopwatch stopwatch;
	@Mock
	private ITimer mockedTimer;
	@Mock
	private TimeDescriptor mockedDescriptor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.stopwatch = new Stopwatch(this.mockedTimer, (byte) 1, 1, TimeUnit.MILLISECONDS);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.mockedTimer = null;
		this.stopwatch = null;
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.Stopwatch#Stopwatch(rmblworx.tools.timey.ITimer, byte, int, java.util.concurrent.TimeUnit)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testStopwatchShouldFailBecauseTimerIsNull() {
		this.stopwatch = new Stopwatch(null, (byte) 1, 1, TimeUnit.MILLISECONDS);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.Stopwatch#Stopwatch(rmblworx.tools.timey.ITimer, byte, int, java.util.concurrent.TimeUnit)}
	 * .
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public final void testStopwatchShouldFailBecauseAmountOfThreadsIsLessThanOne() {
		this.stopwatch = new Stopwatch(this.mockedTimer, (byte) 0, 1, TimeUnit.MILLISECONDS);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.Stopwatch#Stopwatch(rmblworx.tools.timey.ITimer, byte, int, java.util.concurrent.TimeUnit)}
	 * .
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public final void testStopwatchShouldFailBecauseDelayIsLessThanOne() {
		this.stopwatch = new Stopwatch(this.mockedTimer, (byte) 1, 0, TimeUnit.MILLISECONDS);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.Stopwatch#Stopwatch(rmblworx.tools.timey.ITimer, byte, int, java.util.concurrent.TimeUnit)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testStopwatchShouldFailBecauseTimeUnitIsNull() {
		this.stopwatch = new Stopwatch(this.mockedTimer, (byte) 1, 1, null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Stopwatch#resetStopwatch()}.
	 */
	@Test
	public final void testResetStopwatch() {
		this.stopwatch.startStopwatch();
		assertTrue(this.stopwatch.resetStopwatch());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Stopwatch#startStopwatch()}.
	 */
	@Test
	public final void testStartStopwatch() {
		when(this.mockedTimer.startStopwatch(1, 1, TimeUnit.MILLISECONDS)).thenReturn(this.mockedDescriptor);
		assertNotNull(this.stopwatch.startStopwatch());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Stopwatch#stopStopwatch()}.
	 */
	@Test
	public final void testStopStopwatch() {
		assertTrue(this.stopwatch.stopStopwatch());
	}
}
