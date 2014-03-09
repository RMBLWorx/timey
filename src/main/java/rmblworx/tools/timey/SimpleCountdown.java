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
public class SimpleCountdown implements ICountdown {

	/**
	 * Scheduler wird verwendet um die Threads zu verwalten und wiederholt
	 * ausfuehren zu lassen.
	 */
	private ScheduledExecutorService scheduler;
	/**
	 * Wertobjekt das die Zeit fuer die GUI kapselt und liefert.
	 */
	private TimeDescriptor timeDescriptor;
	/**
	 * Die bereits vergangene Zeit in Millisekunden.
	 */
	private long timePassed = 0;

	@Override
	public TimeDescriptor setCountdownTime(TimeDescriptor descriptor) {
		this.timeDescriptor = descriptor;
		return descriptor;
	}

	@Override
	public Boolean startCountdown() {
		int amountOfThreads = 1;
		int delayPerThread = 1;
		TimeUnit timeUnit = TimeUnit.SECONDS;

		this.scheduler = Executors.newScheduledThreadPool(amountOfThreads);
		final CountdownRunnable countdown = new CountdownRunnable(this.timeDescriptor, this.timePassed);

		this.scheduler.scheduleAtFixedRate(countdown, 0, delayPerThread, timeUnit);

		return !this.scheduler.isTerminated();
	}

	@Override
	public Boolean stopCountdown() {
		TimeyUtils.shutdownScheduler(this.scheduler);
		this.timePassed = this.timeDescriptor.getMilliSeconds();

		return Boolean.TRUE;
	}
}
