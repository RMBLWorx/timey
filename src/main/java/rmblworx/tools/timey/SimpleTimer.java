package rmblworx.tools.timey;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Implementierung eines einfachen Timer's zum ausführen einer Zeitmessung.
 *
 * @author mmatthies
 */
class SimpleTimer implements ITimer, ApplicationContextAware {

	/**
	 * Maßzahl für die Ausfuehrungsverzögerung des Threads.
	 */
	private static final int DELAY = 1;
	/**
	 * Größe des Thread-Pools.
	 */
	private static final int THREAD_POOL_SIZE = 1;
	/**
	 * Scheduler wird verwendet um die Threads zu verwalten und wiederholt
	 * ausführen zu lassen.
	 */
	private ScheduledExecutorService scheduler;
	/**
	 * Spring-Kontext.
	 */
	private ApplicationContext springContext;
	/**
	 * Wertobjekt das die Zeit für die GUI kapselt und liefert.
	 */
	private final TimeDescriptor timeDescriptor;
	/**
	 * Die bereits vergangene Zeit in Millisekunden.
	 */
	private long timePassed = 0;
	/**
	 * Referenz auf die Zeitmessungsimplementierung.
	 */
	private TimerRunnable timer;

	/**
	 * Referenz auf das Future-Objekt der aktuellen Zeitmessung.
	 */
	private ScheduledFuture<?> timerFuture;
	/**
	 * Merker. Gibt an ob der Timer bereits zuvor gestoppt wurde.
	 */
	private boolean wasStoppedBefore = false;

	/**
	 * Konstruktor. Erfordert die Referenz auf das Werteobjekt, welches den
	 * Wert an die GUI liefern wird.
	 *
	 * @param descriptor
	 *            das zu setzende Werteobjekt. Es findet keine Pruefung
	 *            auf @code{null} statt!
	 */
	public SimpleTimer(final TimeDescriptor descriptor) {
		if (descriptor == null) {
			throw new NullArgumentException();
		}
		this.timeDescriptor = descriptor;
	}

	@Override
	public Boolean resetStopwatch() {
		boolean isRunningAtTheMoment = false;
		if (this.scheduler != null && !this.scheduler.isTerminated()) {
			isRunningAtTheMoment = true;
			this.stopStopwatch();
		}
		this.timePassed = 0;
		this.timeDescriptor.setMilliSeconds(0);
		if (isRunningAtTheMoment) {
			this.startStopwatch(1, TimeUnit.MILLISECONDS);
		}
		return isRunningAtTheMoment;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}

	/*
	 * (non-Javadoc)
	 * @see rmblworx.tools.timey.ITimer#startStopwatch(int, int, java.util.concurrent.TimeUnit)
	 */
	@Override
	public TimeDescriptor startStopwatch(final int delayPerThread, final TimeUnit timeUnit) {
		if (delayPerThread < 1) {
			throw new ValueMinimumArgumentException();
		} else if (timeUnit == null) {
			throw new NullArgumentException();
		}
		this.timer = (TimerRunnable) this.springContext.getBean("timerRunnable", this.timeDescriptor, this.timePassed);

		this.scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
		this.timerFuture = this.scheduler.scheduleAtFixedRate(this.timer, 0, delayPerThread, timeUnit);
		this.wasStoppedBefore = false;

		return this.timeDescriptor;
	}

	/*
	 * (non-Javadoc)
	 * @see rmblworx.tools.timey.ITimer#stopStopwatch()
	 */
	@Override
	public Boolean stopStopwatch() {
		if (this.scheduler != null) {
			final TaskStopper stopRunnable = new TaskStopper(this.scheduler, this.timerFuture);
			this.scheduler.schedule(stopRunnable, DELAY, TimeUnit.MILLISECONDS);
		}
		this.timePassed = this.timeDescriptor.getMilliSeconds();
		this.wasStoppedBefore = true;
		return Boolean.TRUE;
	}

	@Override
	public Boolean toggleTimeModeInStopwatch() {
		if (this.wasStoppedBefore) {
			this.timeDescriptor.setMilliSeconds(this.timePassed);
		}
		return this.timer.toggleTimeMode();
	}
}
