/**
 * 
 */
package rmblworx.tools.timey.vo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Unveraenderliches, Thread-sicheres Werteobjekt zum kapseln der Zeitangaben
 * die fuer den Countdown, Alarm benoetigt werden. Die Instantiierung wird
 * mittels des Builder vorgenommen.
 * 
 * @author mmatthies
 * 
 */
public final class TimeDescriptor {
	// erforderliche Parameter
	private final int hours;
	private final int minutes;
	private final int seconds;
	// optionale Parameter
	private long milliseconds;

	private TimeDescriptor(Builder builder) {
		// erforderliche Parameter
		hours = builder.hours;
		minutes = builder.minutes;
		seconds = builder.seconds;
		// optionale Parameter
		milliseconds = builder.milliseconds;
	}

	public static class Builder {
		private final Logger log = LogManager.getLogger(Builder.class);
		// erforderliche Parameter
		private int hours;
		private int minutes;
		private int seconds;
		// optionale Parameter
		private long milliseconds = 0;

		/**
		 * Konstruktor der die nicht-optionalen Werte benoetigt.
		 * 
		 * @param hours
		 *            Anzahl der Stunden. Es findet keine Pruefung auf negative
		 *            Werte statt.
		 * @param minutes
		 *            Anzahl der Minuten. Es findet keine Pruefung auf negative
		 *            Werte statt.
		 * @param seconds
		 *            Anzahl der Sekunden. Es findet keine Pruefung auf negative
		 *            Werte statt.
		 */
		public Builder(int hours, int minutes, int seconds) {
			this.hours = hours;
			this.minutes = minutes;
			this.seconds = seconds;
		}

		/**
		 * Ermöglicht das Setzen eines optionalen Wertes.
		 * 
		 * @param milliseconds
		 *            Anzahl der Millisekunden. Es findet keine Pruefung auf
		 *            negative Werte statt.
		 * @return Referenz auf dieses Erzeuger-Objekt.
		 */
		public Builder milliseconds(long milliseconds) {
			this.log.entry();

			this.milliseconds = milliseconds;

			this.log.exit();
			return this;
		}

		/**
		 * Erzeugt das Werteobjekt anhand der gegebenen Werte.
		 * 
		 * @return unveränderliches Werteobjekt, welches die Zeitwerte kapselt.
		 */
		public TimeDescriptor build() {
			this.log.entry();

			this.log.debug(
					"\n\t Erzeuge Objekt mit den Werten: \n\t hours:{}\n\t minutes:{}\n\t seconds:{}\n\t milliseconds:{}",
					hours, minutes, seconds, milliseconds);

			this.log.exit();
			return new TimeDescriptor(this);
		}
	}

	/**
	 * @return Die Anzahl der Stunden.
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * @return Die Anzahl der Minuten.
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @return Die Anzahl der Sekunden.
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * @return Die Anzahl der Millisekunden oder {@code 0} falls sie nie gesetzt
	 *         wurden da diese Angabe optional ist.
	 */
	public long getMilliSeconds() {
		return milliseconds;
	}

}
