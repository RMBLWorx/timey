/**
 * 
 */
package rmblworx.tools.timey.vo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Unveraenderliches, Thread-sicheres Werteobjekt zum kapseln der Zeitangaben
 * die fuer den Countdown benoetigt werden. Die Instantiierung wird mittels des
 * Builder vorgenommen.
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
	private int milliseconds;

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
		private int milliseconds = 0;

		public Builder(int hours, int minutes, int seconds) {
			this.hours = hours;
			this.minutes = minutes;
			this.seconds = seconds;
		}

		public Builder milliseconds(int milliseconds) {
			this.log.entry();

			this.milliseconds = milliseconds;

			this.log.exit();
			return this;
		}

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
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @return the seconds
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * @return the milliseconds or {@code 0} if it was not set before.
	 */
	public int getMilliSeconds() {
		return milliseconds;
	}

}
