package rmblworx.tools.timey;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Implementierung eines einfachen Timer's zum ausfuehren einer Zeitmessung.
 * 
 * @author mmatthies
 */
public class SimpleCountdown implements ICountdownTimer, TimeyEventListener {

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
	/**
	 * Referenz auf den Event-Dispatcher.
	 */
	private TimeyEventDispatcher dispatcher;

	/**
	 * Erweiterter Konstruktor.
	 * @param timeyEventDispatcher Referenz auf den Event-Dispatcher
	 */
	public SimpleCountdown(final TimeyEventDispatcher timeyEventDispatcher) {
		this.dispatcher = timeyEventDispatcher;
		timeyEventDispatcher.addEventListener(this);
	}

	@Override
	public Boolean setCountdownTime(final TimeDescriptor descriptor) {
		if (descriptor == null) {
			throw new NullArgumentException();
		}
		this.timeDescriptor = descriptor;
		return Boolean.TRUE;
	}

	@Override
	public TimeDescriptor startCountdown(int amountOfThreads, int delayPerThread, TimeUnit timeUnit) {
		if (amountOfThreads < 1 || delayPerThread < 1) {
			throw new ValueMinimumArgumentException();
		} else if (timeUnit == null) {
			throw new NullArgumentException();
		}
		this.scheduler = Executors.newScheduledThreadPool(amountOfThreads);
		final CountdownRunnable countdown = new CountdownRunnable(this.timeDescriptor, this.timePassed);

		this.scheduler.scheduleAtFixedRate(countdown, 0, delayPerThread, timeUnit);

		return this.timeDescriptor;
	}

	@Override
	public Boolean stopCountdown() {
		TimeyUtils.shutdownScheduler(this.scheduler);
		this.timePassed = this.timeDescriptor.getMilliSeconds();

		return Boolean.TRUE;
	}

	@Override
	public void handleEvent(final TimeyEvent timeyEvent) {
		if (timeyEvent instanceof CountdownExpiredEvent) {
			this.stopCountdown();
		}
	}
}
