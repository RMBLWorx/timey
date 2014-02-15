/**
 *
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mmatthies
 */
public class InitTurnOffCommandTest {
	/**
	 * Empfaenger fuer das Alarm-Kommando.
	 */
	private Alarm alarm;
	/**
	 * Client fuer das Alarm-Kommando.
	 */
	private AlarmClient client;

	/**
	 * @throws java.lang.Exception
	 *             Exception
	 */
	@Before
	public final void setUp() throws Exception {
		this.alarm = new Alarm();
		this.client = new AlarmClient(this.alarm);

	}

	/**
	 * @throws java.lang.Exception
	 *             Exception
	 */
	@After
	public final void tearDown() throws Exception {
		this.alarm = null;
		this.client = null;

	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmClient#initTurnOffCommand()}.
	 */
	@Test
	public final void testInitTurnOffCommand() {
		Boolean expectedValue = Boolean.TRUE;

		Boolean actual = this.client.initTurnOffCommand();
		assertEquals(expectedValue, actual);
	}
}
