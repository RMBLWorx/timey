package rmblworx.tools.timey.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Hilfsmethoden zum Umgang mit Datum/Zeit-Werten.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public final class DateTimeUtil {

	/**
	 * @param dateTime Datum-/Zeit-Wert
	 * @return Datumsanteil in ms
	 */
	public static Calendar getDatePart(final Calendar dateTime) {
		final Calendar date = (Calendar) dateTime.clone();

		// Zeitanteil entfernen
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);

		return date;

//		// Zeitanteil entfernen
//		return DateUtils.truncate(dateTime, Calendar.DATE);
	}

	/**
	 * @param dateTime Datum-/Zeit-Wert
	 * @return Zeitanteil in ms
	 */
	public static Calendar getTimePart(final Calendar dateTime) {
		final Calendar time = (Calendar) dateTime.clone();

		// Datumsanteil entfernen, @see http://stackoverflow.com/a/1363358
		final long millisPerDay = 24 * 60 * 60 * 1000;
		time.setTimeInMillis(dateTime.getTimeInMillis() % millisPerDay);

		return time;
	}

	public static Calendar getCalendarForString(final String string) {
		return getCalendarForString(string, TimeZone.getDefault());
	}

	public static Calendar getCalendarForString(final String string, final TimeZone timeZone) {
		try {
			return getCalendarForDateTimeString(string, timeZone);
		} catch (final RuntimeException e1) {
			try {
				return getCalendarForDateString(string, timeZone);
			} catch (final RuntimeException e2) {
				try {
					return getCalendarForTimeString(string, timeZone);
				} catch (final RuntimeException e3) {
					throw new RuntimeException(e3);
				}
			}
		}
	}

	public static Calendar getCalendarForDateTimeString(final String dateTimeString, final TimeZone timeZone) {
		return parseString(dateTimeString, timeZone, "dd.MM.yyyy HH:mm:ss");
	}

	public static Calendar getCalendarForDateString(final String dateString, final TimeZone timeZone) {
		return parseString(dateString, timeZone, "dd.MM.yyyy");
	}

	public static Calendar getCalendarForTimeString(final String timeString, final TimeZone timeZone) {
		return parseString(timeString, timeZone, "HH:mm:ss");
	}

	private static Calendar parseString(final String string, final TimeZone timeZone, final String format) {
		try {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			dateFormat.setTimeZone(timeZone);
			final Date dateTime = dateFormat.parse(string);
			final Calendar calDate = Calendar.getInstance(timeZone);
			calDate.setTime(dateTime);
			return calDate;
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Privater Konstruktor.
	 */
	private DateTimeUtil() {
	}

}
