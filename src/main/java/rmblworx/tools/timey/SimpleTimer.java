/**
 */
package rmblworx.tools.timey;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Implementierung eines einfachen Timer's zum ausfuehren einer Zeitmessung.
 * 
 * @author mmatthies
 */
final class SimpleTimer implements ITimer {

	/**
	 * Scheduler wird verwendet um die Threads zu verwalten und wiederholt
	 * ausfuehren zu lassen.
	 */
	private ScheduledExecutorService scheduler;
	/**
	 * Wertobjekt das die Zeit fuer die GUI kapselt und liefert.
	 */
	private final TimeDescriptor timeDescriptor;
	/**
	 * Die bereits vergangene Zeit in Millisekunden.
	 */
	private long timePassed = 0;

	/**
	 * Konstruktor. Erfordert die Referenz auf das Werteobjekt, welches den
	 * Wert an die GUI liefern wird.
	 * 
	 * @param descriptor
	 *            das zu setzende Werteobjekt. Es findet keine Pruefung
	 *            auf @code{null} statt!
	 */
	public SimpleTimer(final TimeDescriptor descriptor) {
		this.timeDescriptor = descriptor;
	}

	@Override
	public Boolean resetStopwatch() {
		boolean isRunningAtTheMoment = false;
		if (!this.scheduler.isTerminated()) {
			isRunningAtTheMoment = true;
		}
		this.stopStopwatch();
		this.timePassed = 0;
		this.timeDescriptor.setMilliSeconds(0);
		if (isRunningAtTheMoment) {
			this.startStopwatch(1, 1, TimeUnit.MILLISECONDS);
		}
		return Boolean.TRUE;
	}

	/**
	 * Veranlasst die fuer die Zeitnahme verwendete Implementierung sich zu beenden. Es wird so lange gewartet bis sie
	 * beendet wurde.
	 */
	private void shutdownStopwatch() {
		this.scheduler.shutdownNow();
		while (!this.scheduler.isTerminated()) {
			// wir warten solange bis alle Threads beendet wurden
		}
	}

	/*
	 * (non-Javadoc)
	 * @see rmblworx.tools.timey.ITimer#startStopwatch(int, int, java.util.concurrent.TimeUnit)
	 */
	@Override
	public TimeDescriptor startStopwatch(final int amountOfThreads, final int delayPerThread, final TimeUnit timeUnit) {
		this.scheduler = Executors.newScheduledThreadPool(amountOfThreads);
		final TimerRunnable timer = new TimerRunnable(this.timeDescriptor, this.timePassed);
		// TimerRunnable t = (TimerRunnable) this.applicationContext.getBean("timerRunnable");

		this.scheduler.scheduleAtFixedRate(timer, 0, delayPerThread, timeUnit);

		return this.timeDescriptor;
	}

	/*
	 * (non-Javadoc)
	 * @see rmblworx.tools.timey.ITimer#stopStopwatch()
	 */
	@Override
	public Boolean stopStopwatch() {
		this.shutdownStopwatch();
		this.timePassed = this.timeDescriptor.getMilliSeconds();
		return Boolean.TRUE;
	}
}
