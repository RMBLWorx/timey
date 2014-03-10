package rmblworx.tools.timey;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.persistence.service.IAlarmTimestampService;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Receiver" implementation.
 * <ul>
 * <li>knows how to perform the operations associated with carrying out a request. Any class may serve as a Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
public class Alarm implements IAlarm, ApplicationContextAware {

	/**
	 * Spring-Kontext.
	 */
	private ApplicationContext context;

	/**
	 * Service zur Verwaltung der Alarmzeitpunkte in der Datenbank.
	 */
	private IAlarmTimestampService service;
	/**
	 * This construtor creates a Receiver instance.
	 */
	public Alarm() {
		super();
	}

	@Override
	public List<TimeDescriptor> getAllAlarmtimestamps() {
		this.initService();
		return this.service.getAll();
	}

	private void initService() {
		if (this.service == null) {
			this.service = (IAlarmTimestampService) this.context.getBean("alarmTimestampService");
		}
	}


	@Override
	public Boolean isAlarmtimestampActivated(final TimeDescriptor descriptor) {
		return this.service.isActivated(descriptor);
	}

	@Override
	public Boolean removeAlarmtimestamp(TimeDescriptor descriptor) {
		return this.service.delete(descriptor);
	}

	@Override
	public Boolean setAlarmtimestamp(final TimeDescriptor descriptor) {
		this.initService();
		return this.service.create(descriptor);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public Boolean setStateOfAlarmtimestamp(final TimeDescriptor descriptor, final Boolean isActivated) {
		return this.service.setState(descriptor, isActivated);
	}
}
