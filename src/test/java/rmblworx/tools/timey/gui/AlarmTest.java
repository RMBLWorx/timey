package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDateTime;
import org.junit.Test;

/**
 * Test für {@link Alarm}.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class AlarmTest {

	/**
	 * Testet {@link Alarm#getDateTimeInMillis()}.
	 */
	@Test
	public final void testGetDateTimeInMillis() {
		final long now = System.currentTimeMillis();
		assertEquals(now, new Alarm(new LocalDateTime(now), "alarm").getDateTimeInMillis());
	}

}
