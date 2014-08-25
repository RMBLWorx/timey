package rmblworx.tools.timey;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;
import rmblworx.tools.timey.persistence.service.IAlarmService;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Diese Implementierung dient der Steuerung des Alarmsystems.
 * @author mmatthies
 */
class Alarm implements IAlarm, ApplicationContextAware {

	/**
	 * Referenz auf die verwendete Alarmimplementierung.
	 */
	private SimpleAlarm alarmdetector;
	/**
	 * Maßzahl für den Ausfuehrungsintervall.
	 */
	private final int delayPerThread;
	/**
	 * Service zur Verwaltung der Alarmzeitpunkte in der Datenbank.
	 */
	private final IAlarmService service;
	/**
	 * Spring Anwendungskontext.
	 */
	private ApplicationContext springContext;
	/**
	 * Maßeinheit für den Ausfuehrungsintervall.
	 */
	private final TimeUnit timeUnit;

	/**
	 * Erweiterter Konstruktor.
	 *
	 * @param service
	 *            Von dieser Klasse zu verwendende Serviceimplementierung
	 * @param delay
	 *            Maßzahl für den Erkennungsintervall
	 * @param unit
	 *            Einheit für den Erkennungsintervall
	 */
	public Alarm(final IAlarmService service, final int delay, final TimeUnit unit) {
		if (service == null || unit == null) {
			throw new NullArgumentException();
		}
		if (delay < 1) {
			throw new ValueMinimumArgumentException();
		}
		this.delayPerThread = delay;
		this.timeUnit = unit;
		this.service = service;
	}

	@Override
	public List<AlarmDescriptor> getAllAlarms() {
		return this.service.getAll();
	}

	/**
	 * Initialisiert die Implementation für die Alarmerkennung.
	 */
	private void initAlarmdetection() {
		this.alarmdetector = (SimpleAlarm) this.springContext.getBean("simpleAlarm");
	}

	@Override
	public Boolean isAlarmActivated(final AlarmDescriptor descriptor) {
		return this.service.isActivated(descriptor);
	}

	@Override
	public Boolean removeAlarm(final AlarmDescriptor descriptor) {
		return this.service.delete(descriptor);
	}

	@Override
	public Boolean setAlarm(final AlarmDescriptor descriptor) {
		return this.service.create(descriptor);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
		this.startAlarmdetection();
	}

	@Override
	public Boolean setStateOfAlarm(final AlarmDescriptor descriptor, final Boolean isActivated) {
		return this.service.setState(descriptor, isActivated);
	}

	/**
	 * Startet die Alarmerkennung.
	 */
	private void startAlarmdetection() {
		this.initAlarmdetection();
		this.alarmdetector.startAlarmdetection(this.delayPerThread, this.timeUnit);
	}
}
