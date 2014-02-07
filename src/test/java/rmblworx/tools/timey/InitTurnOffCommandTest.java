/**
 * 
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rmblworx.tools.timey.Alarm;
import rmblworx.tools.timey.AlarmClient;

/**
 * @author mmatthies
 * 
 */
public class InitTurnOffCommandTest {
	private AlarmClient steuerung;
	private Alarm alarm;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.alarm = new Alarm();
		this.steuerung = new AlarmClient(alarm);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.alarm = null;
		this.steuerung = null;
		
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmClient#initTurnOffCommand()}.
	 */
	@Test
	public final void testInitTurnOffCommand() {
		Boolean expectedValue = Boolean.TRUE;
		
		Boolean actual = this.steuerung.initTurnOffCommand();
		assertEquals(expectedValue, actual);
	}
}
