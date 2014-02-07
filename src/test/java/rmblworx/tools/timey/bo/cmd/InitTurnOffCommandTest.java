/**
 * 
 */
package rmblworx.tools.timey.bo.cmd;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	 * {@link rmblworx.tools.timey.bo.cmd.AlarmClient#initTurnOffCommand()}.
	 */
	@Test
	public final void testInitTurnOffCommand() {
		Boolean expectedValue = Boolean.TRUE;
		
		Boolean actual = this.steuerung.initTurnOffCommand();
		assertEquals(expectedValue, actual);
	}
}
