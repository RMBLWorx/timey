package rmblworx.tools.timey;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
class SimpleAlarm implements ApplicationContextAware {

	/**
	 * Groesze des Thread-Pools.
	 */
	private static final int THREAD_POOL_SIZE = 1;
	/**
	 * Spring-Anwendungskontext.
	 */
	private ApplicationContext springContext;

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}

	/**
	 * Startet die Alarmerkennung.
	 *
	 * @param delayPerThread
	 *            Maszzahl fuer den Ausfuehrungsintervall
	 * @param timeUnit
	 *            Einheit fuer den Ausfuehrungsintervall
	 */
	public void startAlarmdetection(final int delayPerThread, final TimeUnit timeUnit) {
		if (delayPerThread < 1) {
			throw new ValueMinimumArgumentException();
		} else if (timeUnit == null) {
			throw new NullArgumentException();
		}
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
		final AlarmRunnable alarmDetection = (AlarmRunnable) this.springContext.getBean("alarmRunnable");

		scheduler.scheduleAtFixedRate(alarmDetection, 0, delayPerThread, timeUnit);
	}
}
