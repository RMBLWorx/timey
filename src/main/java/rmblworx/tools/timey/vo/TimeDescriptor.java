package rmblworx.tools.timey.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rmblworx.tools.timey.exception.ValueMinimumArgumentException;

/**
 * Nicht-Thread-sicheres Werteobjekt zum kapseln der Zeitangabe die fuer den
 * Countdown, den Alarm und die Stopuhr benoetigt wird.
 * 
 * @author mmatthies
 */
public class TimeDescriptor {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(TimeDescriptor.class);
	/**
	 * Kapselt die aktuelle Systemzeit in Millisekunden.
	 */
	private long milliseconds;

	/**
	 * Konstruktor der die direkte Angabe der zu setzenden Millisekunden
	 * ermoeglicht.
	 * 
	 * @param milliSeconds
	 *            Anzahl der Millisekunden. Es findet keine Pruefung auf
	 *            negative Werte statt.
	 */
	public TimeDescriptor(final long milliSeconds) {
		// LOG.debug("Erzeuge TimeDescriptor mit dem Wert (ms): {}", this.milliseconds);
		this.milliseconds = milliSeconds;
	}

	/**
	 * @return Die Anzahl der Millisekunden oder {@code 0} falls sie nie
	 *         gesetzt wurden.
	 */
	public long getMilliSeconds() {
		return this.milliseconds;
	}

	/**
	 * @param currentTimeMillis
	 *            zu setzende Zeit in Millisekunden
	 */
	public void setMilliSeconds(final long currentTimeMillis) {
		if (currentTimeMillis < 0) {
			throw new ValueMinimumArgumentException("Values less than zero are not permitted!");
		}
		this.milliseconds = currentTimeMillis;
	}

	@Override
	public String toString() {
		return "TimeDescriptor (ms): " + this.milliseconds;
	}

}
