/**
 */
package rmblworx.tools.timey.vo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Nicht-Thread-sicheres Werteobjekt zum kapseln der Zeitangabe die fuer den
 * Countdown, den Alarm und die Stopuhr benoetigt wird.
 * 
 * @author mmatthies
 */
public final class TimeDescriptor {
	/**
	 * Logger.
	 */
	private final Logger log = LogManager.getLogger(TimeDescriptor.class);
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
		this.log.entry();

		this.log.debug("Erzeuge TimeDescriptor mit dem Wert (ms): {}", this.milliseconds);
		this.milliseconds = milliSeconds;
		this.log.exit();
	}

	/**
	 * @return Die Anzahl der Millisekunden oder {@code 0} falls sie nie
	 *         gesetzt wurden.
	 */
	public long getMilliSeconds() {
		this.log.entry();

		return this.log.exit(this.milliseconds);
	}

	/**
	 * @param currentTimeMillis
	 *            zu setzende Zeit in Millisekunden
	 */
	public void setMilliSeconds(final long currentTimeMillis) {
		this.log.entry();

		this.milliseconds = currentTimeMillis;

		this.log.exit();
	}
}
