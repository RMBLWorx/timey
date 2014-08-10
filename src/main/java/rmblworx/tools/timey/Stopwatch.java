package rmblworx.tools.timey;

import java.util.concurrent.TimeUnit;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Diese Implementierung dient der Steuerung der Stoppuhr.
 * 
 * @author mmatthies
 */
class Stopwatch implements IStopwatch {

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
	 * Konstruktor welcher eine Instanz von diesem Receiver erzeugt.
	 *
	 * @param timer
	 *            Implementierung einer Stoppuhr
	 * @param delay
	 *            Bestimmt den Intervall in welchem die vom Thread gemessene Zeit zurueckgeliefert wird.
	 * @param unit
	 * 			  Zeiteinheit.
	 */
	public Stopwatch(final ITimer timer, final int delay, final TimeUnit unit) {
		if (delay < 1) {
			throw new ValueMinimumArgumentException();
		} else if (timer == null || unit == null) {
			throw new NullArgumentException();
		}
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
		return this.timer.startStopwatch(this.delayPerThread, this.timeUnit);
	}

	@Override
	public Boolean stopStopwatch() {
		if (this.timer != null) {
			this.timer.stopStopwatch();
		}

		return Boolean.TRUE;
	}
}
