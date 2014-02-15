/**
 * 
 */
package rmblworx.tools.timey;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Diese Thread-sichere Implementierung dient der Zeitmessung in Millisekunden.
 * 
 * @author mmatthies
 */
final class TimerRunnable implements Runnable {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat();
	private static final String FORMAT_STRING = "HH:mm:ss.SSS";
	private static final String UTC = "UTC";
	/**
	 * Von dieser Timerimplementierung verwendete Lock-Mechanismus.
	 */
	private final Lock lock = new ReentrantLock();
	/**
	 * Logger.
	 */
	private final Logger log = LogManager.getLogger(TimerRunnable.class);
	/**
	 * Differenz zwischen Startzeit und aktueller Zeit.
	 */
	private long timeDelta;
	/**
	 * Wertobjekt das die Zeit fuer die GUI kapselt und liefert.
	 */
	private final TimeDescriptor timeDescriptor;
	/**
	 * Beschreibt die vergangene Zeit in Millisekunden.
	 */
	private final long timePassed;
	/**
	 * Beschreibt wann der Startvorgang ausgeloest wurde in Millisekunden.
	 */
	private final long timeStarted;

	/**
	 * @param descriptor
	 *            Referenz auf das Wertobjekt das die Zeit in
	 *            Millisekunden an die konsumierende Implementierung
	 *            liefern soll.
	 * @param passedTime
	 *            Vergangene Zeit in Millisekunden.
	 */
	public TimerRunnable(final TimeDescriptor descriptor, final long passedTime) {
		this.log.entry();

		this.timeDescriptor = descriptor;
		this.timePassed = passedTime;
		this.timeStarted = System.currentTimeMillis();
		DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone(UTC));
		DATE_FORMATTER.applyPattern(FORMAT_STRING);

		this.log.exit();
	}

	/**
	 * Berechnet die Stopzeit und schreibt den Wert in Millisekunden in das
	 * Wertobjekt.
	 */
	private void computeTime() {
		this.log.entry();

		this.timeDelta = 0;
		final long currentTimeMillis = System.currentTimeMillis();

		this.timeDelta = currentTimeMillis - this.timeStarted;
		this.timeDescriptor.setMilliSeconds(this.timePassed + this.timeDelta);

		this.log.debug("current (UTC): " + DATE_FORMATTER.format(currentTimeMillis));
		this.log.exit();
	}

	@Override
	public void run() {
		this.log.entry();

		this.lock.lock();
		this.computeTime();
		this.lock.unlock();

		this.log.exit();
	}
}
