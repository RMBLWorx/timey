package rmblworx.tools.timey;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.event.CountdownExpiredEvent;
import rmblworx.tools.timey.event.TimeyEventDispatcher;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Diese Thread-sichere Implementierung setzt einen Countdown-Zähler um. Zeitnahme findet in Millisekunden statt.
 * @author mmatthies
 */
class CountdownRunnable extends TimeyTimeRunnable implements ApplicationContextAware {
	/**
	 * Die vom Nutzer gesetzte, herunter zu zaehlende Zeit in Millisekunden.
	 */
	private final long timeCountdown;
	private boolean wasEventFired = false;
	private ApplicationContext springContext;

	/**
	 * @param descriptor
	 *            Referenz auf das Wertobjekt das die Zeit in
	 *            Millisekunden an die konsumierende Implementierung
	 *            liefern soll.
	 * @param passedTime
	 *            Vergangene Zeit in Millisekunden.
	 */
	public CountdownRunnable(final TimeDescriptor descriptor, final long passedTime) {
		super(descriptor, passedTime, System.currentTimeMillis());
		this.timeCountdown = descriptor.getMilliSeconds();
	}

	/**
	 * Berechnet und schreibt die noch verbleibende Countdown-Zeit in Millisekunden in das
	 * Wertobjekt.
	 */
	@Override
	protected void computeTime() {
		this.timeDelta = 0;
		final long currentTimeMillis = System.currentTimeMillis();
		this.timeDelta = this.timeStarted - currentTimeMillis;
		this.timeDescriptor.setMilliSeconds(this.timeCountdown + this.timeDelta);
	}

	@Override
	public void run() {
		Thread.currentThread().setName("timey-Countdown");
		this.lock.lock();
		try {
			if (this.timeDescriptor.getMilliSeconds() > 0) {
				this.computeTime();
			} else {
				if (!this.wasEventFired) {
					final CountdownExpiredEvent countdownExpiredEvent = (CountdownExpiredEvent) this.springContext
							.getBean("countdownExpiredEvent");
					final TimeyEventDispatcher eventDispatcher = (TimeyEventDispatcher) this.springContext
							.getBean("timeyEventDispatcher");
					eventDispatcher.dispatchEvent(countdownExpiredEvent);
					this.wasEventFired = true;
				}
			}
		} finally {
			this.lock.unlock();
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}
}
