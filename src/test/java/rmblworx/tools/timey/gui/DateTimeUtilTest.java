package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Test für Hilfsmethoden zum Umgang mit Datum/Zeit-Werten.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class DateTimeUtilTest {

	private static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");
	private static final TimeZone TZ_BERLIN = TimeZone.getTimeZone("Europe/Berlin");

	/**
	 * Testet {@link DateTimeUtil#getDatePart(Calendar)}.
	 */
	@Test
	public final void testGetDatePart() {
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014").getTime(),
				DateTimeUtil.getDatePart(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00")).getTime());
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014", TZ_UTC).getTime(),
				DateTimeUtil.getDatePart(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00", TZ_UTC)).getTime());
		assertEquals(DateTimeUtil.getCalendarForString("24.12.2014", TZ_BERLIN).getTime(),
				DateTimeUtil.getDatePart(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00", TZ_BERLIN)).getTime());
	}

	/**
	 * Testet {@link DateTimeUtil#getTimePart(Calendar)}.
	 */
	@Test
	public final void testGetTimePart() {
		assertEquals(DateTimeUtil.getCalendarForString("12:00:00").getTime(),
				DateTimeUtil.getTimePart(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00")).getTime());
		assertEquals(DateTimeUtil.getCalendarForString("12:00:00", TZ_UTC).getTime(),
				DateTimeUtil.getTimePart(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00", TZ_UTC)).getTime());
		assertEquals(DateTimeUtil.getCalendarForString("12:00:00", TZ_BERLIN).getTime(),
				DateTimeUtil.getTimePart(DateTimeUtil.getCalendarForString("24.12.2014 12:00:00", TZ_BERLIN)).getTime());
	}

	/**
	 * Testet {@link DateTimeUtil#getCalendarForString(String, TimeZone))}.
	 */
	@Test
	public final void testGetCalendarForString() {
		assertEquals(0, DateTimeUtil.getCalendarForString("01.01.1970", TZ_UTC).getTimeInMillis());
		assertEquals(0, DateTimeUtil.getCalendarForString("01.01.1970 00:00:00", TZ_UTC).getTimeInMillis());
		assertEquals(0, DateTimeUtil.getCalendarForString("00:00:00", TZ_UTC).getTimeInMillis());

		assertEquals(-3600000, DateTimeUtil.getCalendarForString("01.01.1970", TZ_BERLIN).getTimeInMillis());
		assertEquals(-3600000, DateTimeUtil.getCalendarForString("01.01.1970 00:00:00", TZ_BERLIN).getTimeInMillis());
		assertEquals(-3600000, DateTimeUtil.getCalendarForString("00:00:00", TZ_BERLIN).getTimeInMillis());
	}

}
