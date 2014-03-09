/**
 * 
 */
package rmblworx.tools.timey;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Abstrakte Klasse fuer alle timey Zeitmessungsimplementierungen.
 * @author mmatthies
 */
abstract class TimeyTimeRunnable implements Runnable {
	/**
	 * Von dieser Timerimplementierung verwendete Lock-Mechanismus.
	 */
	protected final Lock lock = new ReentrantLock();
	/**
	 * Differenz zwischen Startzeit und aktueller Zeit.
	 */
	protected long timeDelta;
	/**
	 * Wertobjekt das die Zeit fuer die GUI kapselt und liefert.
	 */
	protected final TimeDescriptor timeDescriptor;
	/**
	 * Beschreibt die vergangene Zeit in Millisekunden.
	 */
	protected final long timePassed;
	/**
	 * Beschreibt wann der Startvorgang ausgeloest wurde in Millisekunden.
	 */
	protected final long timeStarted;

	/**
	 * Konstruktor.
	 * 
	 * @param descriptor
	 *            Zeitwertobjekt.
	 * @param timePassed
	 *            Die vergangene Zeit.
	 * @param timeStarted
	 *            Ganzzahliger Wert der den Start der Messung beschreibt.
	 */
	public TimeyTimeRunnable(final TimeDescriptor descriptor, final long timePassed, final long timeStarted) {
		this.timeDescriptor = descriptor;
		this.timePassed = timePassed;
		this.timeStarted = timeStarted;
	}

	/**
	 * Welche Berechnungen diese Methode durchfuehrt, haengt von der jeweiligen Implementierung ab.
	 */
	protected abstract void computeTime();

	@Override
	public void run() {

		this.lock.lock();
		try {
			this.computeTime();
		} finally {
			this.lock.unlock();
		}

	}
}
