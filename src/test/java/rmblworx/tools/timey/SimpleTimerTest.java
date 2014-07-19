/**
 *
 */
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

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml", "/stopwatch-spring-timey-context.xml" })
public class SimpleTimerTest {
	@Autowired
	ApplicationContext springContext;
	@Autowired
	private SimpleTimer timer;
	@Mock
	private TimeDescriptor mockedDescriptor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.timer = new SimpleTimer(this.mockedDescriptor);
		this.timer.setApplicationContext(this.springContext);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.mockedDescriptor = null;
		this.timer = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#SimpleTimer(rmblworx.tools.timey.vo.TimeDescriptor)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeDescriptorIsNull() {
		this.timer = new SimpleTimer(null);
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
	@Test
	public final void testStartStopwatch() {
		assertNotNull(this.timer.startStopwatch(1, TimeUnit.NANOSECONDS));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#startStopwatch(int, java.util.concurrent.TimeUnit)}.
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public final void testShouldFailBecauseDelayIsLessThanOne() {
		this.timer.startStopwatch(0, TimeUnit.NANOSECONDS);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#startStopwatch(int, java.util.concurrent.TimeUnit)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeUnitIsNull() {
		this.timer.startStopwatch(1, null);
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
	 */
	@Test
	public final void testToggleTimeModeInStopwatchSimpleCase() throws InterruptedException {
		TimeDescriptor td = new TimeDescriptor(0l);
		this.timer = new SimpleTimer(td);
		this.timer.setApplicationContext(this.springContext);

		td = this.timer.startStopwatch(1, TimeUnit.NANOSECONDS);
		Thread.sleep(10);
		assertTrue("Stoppuhr laeuft nicht!", td.getMilliSeconds() > 0);

		// TIME-Mode ein
		this.timer.toggleTimeModeInStopwatch();
		long expected = td.getMilliSeconds();
		Thread.sleep(10);
		long actual = td.getMilliSeconds();
		assertEquals("Stoppuhr zaehlt weiter obwohl TIME-Mode aktiv!", expected, actual);

		// TIME-Mode aus
		this.timer.toggleTimeModeInStopwatch();
		expected = td.getMilliSeconds();
		Thread.sleep(10);
		actual = td.getMilliSeconds();
		assertNotEquals("Stoppuhr hat den TIME-Modus nicht deaktiviert!", expected, actual);

		this.timer.stopStopwatch();
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.SimpleTimer#toggleTimeModeInStopwatch()}.
	 *
	 * @throws InterruptedException
	 */
	@Test
	public final void testToggleTimeModeInStopwatchExtendedCase() throws InterruptedException {
		// testet ob TIME-Modus zuverlaessig funktioniert wenn zwischendurch die Uhr gestoppt wurde waehrend TIME-Modus
		// aktiv
		TimeDescriptor td = new TimeDescriptor(0l);
		this.timer = new SimpleTimer(td);
		this.timer.setApplicationContext(this.springContext);

		td = this.timer.startStopwatch(1, TimeUnit.NANOSECONDS);
		Thread.sleep(10);
		assertTrue("Stoppuhr laeuft nicht!", td.getMilliSeconds() > 0);

		// TIME-Mode ein
		this.timer.toggleTimeModeInStopwatch();
		long expected = td.getMilliSeconds();
		Thread.sleep(10);
		long actual = td.getMilliSeconds();
		assertEquals("Stoppuhr zaehlt weiter obwohl TIME-Mode aktiv!", expected, actual);

		//STOP gedrueckt - Zeitmessung stoppt aber eingefrorene Zeit bleibt im Vordergrund
		if(this.timer.stopStopwatch()) {
			assertEquals("Werte nicht identisch obwohl TIME-Mode aktiv!", expected, actual);
		} else {
			fail("Uhr konnte nicht angehalten werden!");
		}

		// TIME-Mode aus - Uhr noch angehalten - es muss nun der letzte Wert bei Stop geliefert werden
		this.timer.toggleTimeModeInStopwatch();
		actual = td.getMilliSeconds();
		assertNotEquals("Stoppuhr hat den TIME-Modus nicht deaktiviert!", expected, actual);

	}
}
