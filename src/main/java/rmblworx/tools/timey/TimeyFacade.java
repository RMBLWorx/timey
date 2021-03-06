package rmblworx.tools.timey;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rmblworx.tools.timey.event.TimeyEventDispatcher;
import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Fassade für das Backendsystem von timey. Bietet das API für sämtliche für das Frontend (GUI-Implementierung)
 * relevanten timey-Funktionen an.
 *
 * @author mmatthies
 */
public final class TimeyFacade implements ITimey {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(TimeyFacade.class);
	/**
	 * Referenz auf den Alarm-Klienten.
	 */
	private AlarmClient alarmClient;
	/**
	 * Kapselt die Attribute der timey-Anwendung in diesem Objekt.
	 */
	private ApplicationProperties applicationProperties = new ApplicationProperties();

	/**
	 * Countdown-Klient.
	 */
	private CountdownClient countdownClient;
	/**
	 * Verwaltet die sich registrierenden Listener und erzeugt die Events.
	 */
	private final TimeyEventDispatcher eventDispatcher;
	/**
	 * Referenz auf den Spring Context.
	 */
	private ApplicationContext springContext;
	/**
	 * Stoppuhr-Klient.
	 * Referenz auf den Stoppuhr-Klienten.
	 */
	private StopwatchClient stopwatchClient;

	/**
	 * Standardkonstruktor.
	 */
	public TimeyFacade() {
		try {
			this.springContext = new ClassPathXmlApplicationContext("spring-timey-context.xml");
		} catch (final BeansException e) {
			LOG.error(e.getMessage());
			throw e;
		}
		this.eventDispatcher = (TimeyEventDispatcher) this.springContext.getBean("timeyEventDispatcher");
	}

	/**
	 * Erweiterter Konstruktor. Nur für Testzwecke!
	 *
	 * @param alarmClient
	 *            Referenz auf die gemockte AlarmClient-Implementierung.
	 * @param stopwatchClient
	 *            Referenz auf die gemockte StopwatchClient-Implementierung.
	 * @param countdownClient
	 *            Referenz auf die gemockte CountdownClient-Implementierung.
	 * @param eventDispatcher
	 *            Referenz auf die gemockte TimeyEventDispatcher-Implementierung.
	 * @param applicationProperties
	 *            Referenz auf die gemockte timey-Attribut-Implementierung.
	 */
	TimeyFacade(final AlarmClient alarmClient, final StopwatchClient stopwatchClient,
			final CountdownClient countdownClient, final TimeyEventDispatcher eventDispatcher,
			final ApplicationProperties applicationProperties) {
		this.alarmClient = alarmClient;
		this.stopwatchClient = stopwatchClient;
		this.countdownClient = countdownClient;
		this.eventDispatcher = eventDispatcher;
		this.applicationProperties = applicationProperties;
	}

	@Override
	public Boolean addEventListener(final TimeyEventListener timeyEventListener) {
		return this.eventDispatcher.addEventListener(timeyEventListener);
	}

	@Override
	public List<AlarmDescriptor> getAllAlarms() {
		this.initAlarmContext();
		return this.alarmClient.initGetAllAlarms();
	}

	@Override
	public String getVersion() {
		return this.applicationProperties.getVersion();
	}

	/**
	 * Initiiert den Spring-Anwendungskontext für die Alarmfunktionalität.
	 */
	@SuppressWarnings("resource")
	private void initAlarmContext() {
		if (this.alarmClient == null) {
			final ApplicationContext alarmSpringContext;
			try {
				alarmSpringContext = new ClassPathXmlApplicationContext(
						new String[] { "alarm-spring-timey-context.xml" }, this.springContext);
			} catch (final BeansException e) {
				LOG.error(e.getMessage());
				throw e;
			}
			this.alarmClient = (AlarmClient) alarmSpringContext.getBean("alarmClient");
		}
	}

	private void initStopwatchContext() {
		if (this.stopwatchClient == null) {
			final ApplicationContext stopwatchSpringContext;
			try {
				stopwatchSpringContext = new ClassPathXmlApplicationContext(
						new String[] { "stopwatch-spring-timey-context.xml" }, this.springContext);
			} catch (final BeansException e) {
				LOG.error(e.getMessage());
				throw e;
			}
			this.stopwatchClient = (StopwatchClient) stopwatchSpringContext.getBean("stopwatchClient");
		}
	}

	/**
	 * Initiiert den Spring-Anwendungskontext für die Countdownfunktionalität.
	 */
	@SuppressWarnings("resource")
	private void intiCountdownContext() {
		if (this.countdownClient == null) {
			final ApplicationContext countdownSpringContext;
			try {
				countdownSpringContext = new ClassPathXmlApplicationContext(
						new String[] { "countdown-spring-timey-context.xml" }, this.springContext);
			} catch (final BeansException e) {
				LOG.error(e.getMessage());
				throw e;
			}
			this.countdownClient = (CountdownClient) countdownSpringContext.getBean("countdownClient");
		}
	}

	@Override
	public Boolean isAlarmActivated(final AlarmDescriptor descriptor) {
		this.initAlarmContext();
		return this.alarmClient.initAlarmGetStateOfAlarmCommand(descriptor);
	}

	@Override
	public Boolean removeAlarm(final AlarmDescriptor descriptor) {
		this.initAlarmContext();
		return this.alarmClient.initAlarmDeleteAlarm(descriptor);
	}

	@Override
	public Boolean resetStopwatch() {
		this.initStopwatchContext();
		return this.stopwatchClient.initStopwatchResetCommand();
	}

	@Override
	public Boolean setAlarm(final AlarmDescriptor descriptor) {
		this.initAlarmContext();
		return this.alarmClient.initSetAlarmCommand(descriptor);
	}

	@Override
	public Boolean setCountdownTime(final TimeDescriptor descriptor) {
		this.intiCountdownContext();
		return this.countdownClient.initSetCountdownTimeCommand(descriptor);
	}

	@Override
	public Boolean setStateOfAlarm(final AlarmDescriptor descriptor, final Boolean isActivated) {
		this.initAlarmContext();
		return this.alarmClient.initAlarmSetStateOfAlarmCommand(descriptor, isActivated);
	}

	@Override
	public TimeDescriptor startCountdown() {
		this.intiCountdownContext();
		return this.countdownClient.initCountdownStartCommand();
	}

	@Override
	public TimeDescriptor startStopwatch() {
		this.initStopwatchContext();
		return this.stopwatchClient.initStopwatchStartCommand();
	}

	@Override
	public Boolean stopCountdown() {
		this.intiCountdownContext();
		return this.countdownClient.initCountdownStopCommand();
	}

	@Override
	public Boolean stopStopwatch() {
		this.initStopwatchContext();
		return this.stopwatchClient.initStopwatchStopCommand();
	}

	@Override
	public Boolean toggleTimeModeInStopwatch() {
		this.initStopwatchContext();
		return this.stopwatchClient.initStopwatchToggleTimeModeCommand();
	}
}
