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

	/**
	 * Konvertiert einen Datum/Zeit-String in ein Kalender-Objekt.
	 * @param string Datum/Zeit-Wert
	 * @return Kalender mit entsprechendem Datum/Zeit-Wert
	 */
	public static Calendar getCalendarForString(final String string) {
		return getCalendarForString(string, TimeZone.getDefault());
	}

	/**
	 * Konvertiert einen Datum/Zeit-String in ein Kalender-Objekt.
	 * @param string Datum/Zeit-Wert
	 * @param timeZone Zeitzone
	 * @return Kalender mit entsprechendem Datum/Zeit-Wert
	 */
	public static Calendar getCalendarForString(final String string, final TimeZone timeZone) {
		final String[] patterns = {
			"dd.MM.yyyy HH:mm:ss",
			"dd.MM.yyyy",
			"HH:mm:ss",
		};

		final SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.setTimeZone(timeZone);

		for (final String pattern : patterns) {
			dateFormat.applyPattern(pattern);

			try {
				final Date dateTime = dateFormat.parse(string);

				// wird nur aufgerufen, sobald keine Gefahr einer ParseException mehr besteht
				final Calendar result = Calendar.getInstance(timeZone);
				result.setTime(dateTime);
				return result;
			} catch (final ParseException e) {
				continue;
			}
		}

		throw new RuntimeException(String.format("Unparseable date/time: %s", string));
	}

	/**
	 * Privater Konstruktor.
	 */
	private DateTimeUtil() {
	}

}
