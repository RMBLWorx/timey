/**
 * 
 */
package rmblworx.tools.timey.vo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mmatthies
 *
 */
public class TimeDescriptorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCorrectBuildingTheVo() {
		int expHours=1;
		int expMinutes = 2;
		int expSeconds = 60;
		int expMilliSec = 100;
		TimeDescriptor td = new TimeDescriptor.Builder(expHours, expMinutes, expSeconds).milliseconds(expMilliSec).build();
		assertEquals("Test fehlgeschlagen: Stunden falsch!",expHours, td.getHours());
		assertEquals("Test fehlgeschlagen: Minuten falsch!",expMinutes, td.getMinutes());
		assertEquals("Test fehlgeschlagen: Sekunden falsch!",expSeconds, td.getSeconds());
		assertEquals("Test fehlgeschlagen: Millisekunden falsch!",expMilliSec, td.getMilliSeconds());
	}

}
