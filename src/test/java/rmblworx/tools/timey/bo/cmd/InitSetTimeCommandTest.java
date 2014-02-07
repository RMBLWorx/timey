/**
 * 
 */
package rmblworx.tools.timey.bo.cmd;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 * 
 */
public class InitSetTimeCommandTest {
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
	 * {@link rmblworx.tools.timey.bo.cmd.AlarmClient#initSetTimeCommand(rmblworx.tools.timey.vo.TimeDescriptor)}
	 * .
	 */
	@Test
	public final void testInitSetTimeCommand() {
		int expectedHours = 1;
		int expectedMinutes = 2;
		int expectedSeconds = 3;
		int expectedMilliseconds = 0;

		TimeDescriptor td = new TimeDescriptor.Builder(expectedHours,
				expectedMinutes, expectedSeconds).build();

		TimeDescriptor actual2 = this.steuerung.initSetTimeCommand(td);
		
		assertEquals(expectedHours, actual2.getHours());
		assertEquals(expectedMinutes, actual2.getMinutes());
		assertEquals(expectedSeconds, actual2.getSeconds());
		assertEquals(expectedMilliseconds, actual2.getMilliSeconds());
	}
}
