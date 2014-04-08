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

/**
 * Diese Implementierung dient der Steuerung des Alarmsystems.
 * 
 * @author mmatthies
 */
public class Alarm implements IAlarm, ApplicationContextAware {

	/**
	 * Service zur Verwaltung der Alarmzeitpunkte in der Datenbank.
	 */
	private final IAlarmService service;
	private ApplicationContext springContext;
	private SimpleAlarm alarmdetector;
	private final int delayPerThread;
	private final TimeUnit timeUnit;

	/**
	 * Erweiterter Konstruktor.
	 * 
	 * @param service
	 *            Von dieser Klasse zu verwendende Serviceimplementierung
	 * @param delay
	 *            Maszzahl fuer den Erkennungsintervall
	 * @param unit
	 *            Einheit fuer den Erkennungsintervall
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

	private void startAlarmdetection() {
		this.initAlarmdetection();
		this.alarmdetector.startAlarmdetection(this.delayPerThread, this.timeUnit);
	}

	private void initAlarmdetection() {
		this.alarmdetector = (SimpleAlarm) this.springContext.getBean("simpleAlarm");
	}

	@Override
	public List<AlarmDescriptor> getAllAlarms() {
		return this.service.getAll();
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
	public Boolean setStateOfAlarm(final AlarmDescriptor descriptor, final Boolean isActivated) {
		return this.service.setState(descriptor, isActivated);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
		this.startAlarmdetection();
	}
}
