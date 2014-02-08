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
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 * 
 */
public class InitSetTimeCommandTest {
	private AlarmClient client;
	private Alarm alarm;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.alarm = new Alarm();
		this.client = new AlarmClient(alarm);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.alarm = null;
		this.client = null;
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmClient#initSetTimeCommand(rmblworx.tools.timey.vo.TimeDescriptor)}
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

		TimeDescriptor actual2 = this.client.initSetTimeCommand(td);
		
		assertEquals(expectedHours, actual2.getHours());
		assertEquals(expectedMinutes, actual2.getMinutes());
		assertEquals(expectedSeconds, actual2.getSeconds());
		assertEquals(expectedMilliseconds, actual2.getMilliSeconds());
	}
}
