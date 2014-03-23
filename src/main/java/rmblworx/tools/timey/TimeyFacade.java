package rmblworx.tools.timey;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rmblworx.tools.timey.exception.EmptyArgumentException;
import rmblworx.tools.timey.exception.NullArgumentException;
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
	private final AlarmClient alarmClient;
	/**
	 * Countdown-Klient.
	 */
	private final CountdownClient countdownClient;
	/**
	 * Referenz auf die Implementierung zur Erkennung der Programmversion.
	 */
	private final JarVersionDetector jarVersionDetector;

	/**
	 * Referenz auf den Spring Context.
	 */
	private final ApplicationContext springContext;
	/**
	 * Stoppuhr-Klient.
	 * Referenz auf den Stoppuhr-Klienten.
	 */
	private final StopwatchClient stopwatchClient;

	/**
	 * Standardkonstruktor.
	 */
	public TimeyFacade() {
		this.springContext = new ClassPathXmlApplicationContext("spring-timey-context.xml");

		this.alarmClient = (AlarmClient) this.springContext.getBean("alarmClient");
		this.stopwatchClient = (StopwatchClient) this.springContext.getBean("stopwatchClient");
		this.countdownClient = (CountdownClient) this.springContext.getBean("countdownClient");
		this.jarVersionDetector = (JarVersionDetector) this.springContext.getBean("jarVersionDetector");
	}

	@Override
	public List<TimeDescriptor> getAllAlarmtimestamps() {
		return this.alarmClient.initGetAllAlarmtimestamps();
	}

	@Override
	public String getVersion(final String globPattern) throws IllegalStateException, EmptyArgumentException, NullArgumentException {
		return this.jarVersionDetector.detectJarVersion(globPattern);
	}

	@Override
	public Boolean isAlarmtimestampActivated(final TimeDescriptor descriptor) {
		return this.alarmClient.initAlarmGetStateOfAlarmCommand(descriptor);
	}

	@Override
	public Boolean removeAlarmtimestamp(final TimeDescriptor descriptor) {
		return this.alarmClient.initAlarmDeleteAlarm(descriptor);
	}

	@Override
	public Boolean resetStopwatch() {
		return this.stopwatchClient.initStopwatchResetCommand();
	}

	@Override
	public Boolean setAlarmtimestamp(final TimeDescriptor descriptor) {
		return this.alarmClient.initSetAlarmtimestampCommand(descriptor);
	}

	@Override
	public Boolean setCountdownTime(final TimeDescriptor descriptor) {
		return this.countdownClient.initSetCountdownTimeCommand(descriptor);
	}

	@Override
	public Boolean setStateOfAlarmtimestamp(final TimeDescriptor descriptor, final Boolean isActivated) {
		return this.alarmClient.initAlarmSetStateOfAlarmCommand(descriptor, isActivated);
	}

	@Override
	public  TimeDescriptor startCountdown() {
		return this.countdownClient.initCountdownStartCommand();
	}

	@Override
	public TimeDescriptor startStopwatch() {
		return this.stopwatchClient.initStopwatchStartCommand();
	}

	@Override
	public  Boolean stopCountdown() {
		return this.countdownClient.initCountdownStopCommand();
	}

	@Override
	public Boolean stopStopwatch() {
		return this.stopwatchClient.initStopwatchStopCommand();
	}
}
