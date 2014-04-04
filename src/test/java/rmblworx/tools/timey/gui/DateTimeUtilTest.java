package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test für Hilfsmethoden zum Umgang mit Datum/Zeit-Werten.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class DateTimeUtilTest {

	/**
	 * Testet {@link DateTimeUtil#getDatePart(org.joda.time.LocalDateTime)}.
	 */
	@Test
	public final void testGetDatePart() {
		for (final DateTimePartsValue testCase : getDateTimeParts()) {
			assertEquals(DateTimeUtil.getLocalDateForString(testCase.date),
					DateTimeUtil.getDatePart(DateTimeUtil.getLocalDateTimeForString(testCase.dateTime)));
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getTimePart(org.joda.time.LocalDateTime)}.
	 */
	@Test
	public final void testGetTimePart() {
		for (final DateTimePartsValue testCase : getDateTimeParts()) {
			assertEquals(DateTimeUtil.getLocalTimeForString(testCase.time),
					DateTimeUtil.getTimePart(DateTimeUtil.getLocalDateTimeForString(testCase.dateTime)));
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getLocalDateTimeForString(String)}.
	 */
	@Test
	public final void testGetLocalDateTimeForString() {
		final String[] strings = new String[] {
			"01.01.1970 00:00:00",
			"01.01.1970",
			"00:00:00",
		};
		for (final String string : strings) {
			assertEquals(String.format("Failed parsing and evaluating date/time string '%s'.", string),
					0, DateTimeUtil.getLocalDateTimeForString(string).getMillisOfDay());
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getLocalDateForString(String)}.
	 */
	@Test
	public final void testGetLocalDateForString() {
		final String[] strings = new String[] {
			"01.01.1970",
		};
		for (final String string : strings) {
			assertEquals(String.format("Failed parsing and evaluating date string '%s'.", string),
					0, DateTimeUtil.getLocalDateForString(string).toDateTimeAtStartOfDay().getMillisOfDay());
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getLocalTimeForString(String)}.
	 */
	@Test
	public final void testGetLocalTimeForString() {
		final String[] strings = new String[] {
			"00:00:00",
		};
		for (final String string : strings) {
			assertEquals(String.format("Failed parsing and evaluating time string '%s'.", string),
					0, DateTimeUtil.getLocalTimeForString(string).getMillisOfDay());
		}
	}

	/**
	 * @return Testfälle
	 */
	private DateTimePartsValue[] getDateTimeParts() {
		return new DateTimePartsValue[] {
			new DateTimePartsValue("24.12.2014", "12:00:00"), // Zeitpunkt in Zeitzone "CET"
			new DateTimePartsValue("04.04.2014", "12:00:00"), // Zeitpunkt in Zeitzone "CEST"
		};
	}

	private final class DateTimePartsValue {

		public final String date;
		public final String time;
		public final String dateTime;

		public DateTimePartsValue(final String date, final String time) {
			this.date = date;
			this.time = time;
			this.dateTime = String.format("%s %s", date, time);
		}

	}

}
