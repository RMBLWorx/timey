package rmblworx.tools.timey;

import java.util.concurrent.TimeUnit;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Receiver" implementation.
 * <ul>
 * <li>knows how to perform the operations associated with carrying out a request. Any class may serve as a Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
class Stopwatch implements IStopwatch {

	/**
	 * Anzahl der Threads die Zeit messen sollen.
	 */
	private final byte amountOfThreads;
	/**
	 * Gibt die Maszzahl fuer die Zeiteinheit an.
	 * 
	 * @see #timeUnit
	 */
	private final int delayPerThread;
	/**
	 * Die genutzte Zeitmessimplementierung.
	 */
	private final ITimer timer;
	/**
	 * Gibt die Zeiteinheit an in welchem der Intervall die gemessene Zeit geliefert wird.
	 */
	private final TimeUnit timeUnit;

	/**
	 * Konstruktor welcher eine Instanz dieses Receiver erzeugt.
	 * 
	 * @param timer
	 *            Implementierung einer Stoppuhr
	 * @param amount
	 *            Anzahl der Zeitmessungs-Threads
	 * @param delay
	 *            Bestimmt den Intervall in welchem die vom Thread gemessene Zeit zurueckgeliefert wird.
	 * @param unit
	 *            Maszeinheit fuer den Intervall.
	 */
	public Stopwatch(final ITimer timer, final byte amount, final int delay, final TimeUnit unit) {
		if (timer == null || amount < 1 || delay < 1 || unit == null) {
			throw new IllegalArgumentException("Values less than one or references on null are not permittted!");
		}
		this.amountOfThreads = amount;
		this.delayPerThread = delay;
		this.timeUnit = unit;
		this.timer = timer;
	}

	@Override
	public Boolean resetStopwatch() {
		if (this.timer != null) {
			this.timer.resetStopwatch();
		}

		return Boolean.TRUE;
	}

	@Override
	public TimeDescriptor startStopwatch() {
		return this.timer.startStopwatch(this.amountOfThreads, this.delayPerThread, this.timeUnit);
	}

	@Override
	public Boolean stopStopwatch() {
		if (this.timer != null) {
			this.timer.stopStopwatch();
		}

		return Boolean.TRUE;
	}

}
