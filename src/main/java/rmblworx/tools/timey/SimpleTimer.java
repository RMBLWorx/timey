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
public final class SimpleTimer implements ITimer {

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

	/* (non-Javadoc)
	 * @see rmblworx.tools.timey.ITimer#startStopwatch(int, int, java.util.concurrent.TimeUnit)
	 */
	@Override
	public TimeDescriptor startStopwatch(final int amountOfThreads, final int delayPerThread, final TimeUnit timeUnit) {
		this.scheduler = Executors.newScheduledThreadPool(amountOfThreads);
		TimerRunnable t = new TimerRunnable(this.timeDescriptor, this.timePassed);
		//		TimerRunnable t = (TimerRunnable) this.applicationContext.getBean("timerRunnable");

		this.scheduler.scheduleAtFixedRate(t, 0, delayPerThread, timeUnit);

		return this.timeDescriptor;
	}

	/* (non-Javadoc)
	 * @see rmblworx.tools.timey.ITimer#stopStopwatch()
	 */
	@Override
	public void stopStopwatch() {
		this.scheduler.shutdownNow();
		this.timePassed = this.timeDescriptor.getMilliSeconds();
	}

	@Override
	public void resetStopwatch() {
		// TODO Auto-generated method stub
	}
}
