package rmblworx.tools.timey;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.event.AlarmExpiredEvent;
import rmblworx.tools.timey.event.AlarmsModifiedEvent;
import rmblworx.tools.timey.event.TimeyEvent;
import rmblworx.tools.timey.event.TimeyEventDispatcher;
import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.persistence.service.IAlarmService;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Diese Thread-sichere Implementierung setzt einen Countdown-Zähler um. Zeitnahme findet in Millisekunden statt.
 *
 * @author mmatthies
 */
class AlarmRunnable implements Runnable, ApplicationContextAware, TimeyEventListener {
	/**
	 * Referenz auf die AlarmClient-Implementierung.
	 */
	private AlarmClient alarmClient;
	/**
	 * Referenz auf den Alarmservice.
	 */
	private IAlarmService alarmService;
	/**
	 * Liste mit allen Alarmzeitpunkten.
	 */
	private List<AlarmDescriptor> allAlarms;
	/**
	 * Referenz auf die Events-verteilende Implementierung.
	 */
	private TimeyEventDispatcher eventDispatcher;
	/**
	 * Von dieser Timerimplementierung verwendete Lock-Mechanismus.
	 */
	private final Lock lock = new ReentrantLock();
	/**
	 * Merker. Kennzeichnet ob es neue oder modifizierte Alarme gibt. Beeinflusst das DB-Abfrageverhalten.
	 */
	private boolean newOrModifiedAlarmsAvailable = false;
	/**
	 * Spring-Anwendungskontext.
	 */
	private ApplicationContext springContext;

	/**
	 */
	public AlarmRunnable() {
	}

	/**
	 * Prueft ob ein Alarm zeitlich eingetreten ist.
	 *
	 * @return das den Alarm beschreibende Werteobjekt.
	 */
	private AlarmDescriptor detectAlarm() {
		final long currentTimeMillis = System.currentTimeMillis();
		AlarmDescriptor result = null;
		for (final AlarmDescriptor alarm : this.allAlarms) {
			if (alarm.getIsActive()) {
				if (alarm.getAlarmtime().getMilliSeconds() <= currentTimeMillis) {
					result = alarm;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void handleEvent(final TimeyEvent timeyEvent) {
		if (timeyEvent instanceof AlarmsModifiedEvent) {
			this.newOrModifiedAlarmsAvailable = true;
		}
	}

	@Override
	public void run() {
		Thread.currentThread().setName("timey-Alarm");
		this.lock.lock();
		try {
			// alarme aus der db
			if (this.allAlarms == null || this.newOrModifiedAlarmsAvailable) {
				this.allAlarms = this.alarmService.getAll();
				this.newOrModifiedAlarmsAvailable = false;
			}
			// alarme abgleichen mit aktueller Systemzeit
			final AlarmDescriptor result = this.detectAlarm();
			if (result != null) {
				// wenn erreicht event feuern sonst weiter abgleichen
				this.alarmClient.initAlarmSetStateOfAlarmCommand(result, false);
				this.alarmClient.initAlarmSetStateInAlarmDescriptorCommand(result, false);

				this.eventDispatcher.dispatchEvent(new AlarmExpiredEvent(result));
			}
		} finally {
			this.lock.unlock();
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
		this.eventDispatcher = (TimeyEventDispatcher) this.springContext.getBean("timeyEventDispatcher");
		this.eventDispatcher.addEventListener(this);
		this.alarmService = (IAlarmService) this.springContext.getBean("alarmService");
		this.alarmClient = (AlarmClient) this.springContext.getBean("alarmClient");
	}
}
