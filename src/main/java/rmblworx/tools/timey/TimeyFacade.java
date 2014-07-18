package rmblworx.tools.timey;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rmblworx.tools.timey.event.TimeyEventDispatcher;
import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.exception.EmptyArgumentException;
import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Fassade fuer das System timey.
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
	 * Countdown-Klient.
	 */
	private CountdownClient countdownClient;
	/**
	 * Referenz auf die Implementierung zur Erkennung der Programmversion.
	 */
	private JarVersionDetector jarVersionDetector;

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
	 * Verwaltet die sich registrierenden Listener und erzeugt die Events.
	 */
	private TimeyEventDispatcher eventDispatcher;
	private ApplicationContext eventSpringContext;
	private ApplicationContext countdownSpringContext;
	private ApplicationContext stopwatchSpringContext;
	private ApplicationContext alarmSpringContext;

	/**
	 * Standardkonstruktor.
	 */
	public TimeyFacade() {
		LOG.info("loading spring-timey-context");
		try {
			this.springContext = new ClassPathXmlApplicationContext("spring-timey-context.xml");
		} catch (final BeansException e) {
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info("spring-timey-context loaded");
	}

	private void intiTimeyEventDispathcerContext() {
		if(this.eventDispatcher==null) {
			try {
				this.eventSpringContext = new ClassPathXmlApplicationContext("event-spring-timey-context.xml");
			} catch (final BeansException e) {
				LOG.error(e.getMessage());
				throw e;
			}
			this.eventDispatcher = (TimeyEventDispatcher) this.eventSpringContext.getBean("timeyEventDispatcher");
		}
	}

	private void initJarversionDetectorContext() {
		if(this.jarVersionDetector==null) {
			this.jarVersionDetector = (JarVersionDetector) this.springContext.getBean("jarVersionDetector");
		}
	}

	private void intiCountdownContext() {
		if(this.countdownClient==null) {
			try {
				this.countdownSpringContext = new ClassPathXmlApplicationContext("countdown-spring-timey-context.xml");
			} catch (final BeansException e) {
				LOG.error(e.getMessage());
				throw e;
			}
			this.countdownClient = (CountdownClient) this.countdownSpringContext.getBean("countdownClient");
		}
	}

	private void initStopwatchContext() {
		if(this.stopwatchClient==null) {
			try {
				this.stopwatchSpringContext = new ClassPathXmlApplicationContext("stopwatch-spring-timey-context.xml");
			} catch (final BeansException e) {
				LOG.error(e.getMessage());
				throw e;
			}
			this.stopwatchClient = (StopwatchClient) this.stopwatchSpringContext.getBean("stopwatchClient");
		}
	}

	private void initAlarmContext() {
		if(this.alarmClient==null) {
			try {
				this.alarmSpringContext = new ClassPathXmlApplicationContext("alarm-spring-timey-context.xml");
			} catch (final BeansException e) {
				LOG.error(e.getMessage());
				throw e;
			}
			this.alarmClient = (AlarmClient) this.alarmSpringContext.getBean("alarmClient");
		}
	}

	@Override
	public List<AlarmDescriptor> getAllAlarms() {
		this.initAlarmContext();
		return this.alarmClient.initGetAllAlarms();
	}

	@Override
	public String getVersion(final String globPattern) throws IllegalStateException, EmptyArgumentException,
	NullArgumentException {
		this.initJarversionDetectorContext();
		return this.jarVersionDetector.detectJarVersion(globPattern);
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
	public void addEventListener(final TimeyEventListener timeyEventListener) {
		this.intiTimeyEventDispathcerContext();
		this.eventDispatcher.addEventListener(timeyEventListener);
	}
}
