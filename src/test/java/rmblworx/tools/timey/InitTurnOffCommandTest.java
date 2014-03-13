package rmblworx.tools.timey;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-timey-context.xml"})
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
	public void setUp() throws Exception {
		this.alarm = new Alarm();
		this.client = new AlarmClient(this.alarm);
	}

	/**
	 * @throws java.lang.Exception
	 *             Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.alarm = null;
		this.client = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmClient#initTurnOffCommand()}.
	 */
	@Test
	public final void testInitTurnOffCommand() {
		final Boolean expectedValue = Boolean.TRUE;
		final Boolean actual = this.client.initTurnOffCommand();

		assertEquals(expectedValue, actual);
	}

}
