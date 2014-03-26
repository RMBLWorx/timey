package rmblworx.tools.timey;

import java.util.List;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.persistence.service.IAlarmTimestampService;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Diese Implementierung dient der Steuerung des Alarmsystems.
 * 
 * @author mmatthies
 */
public class Alarm implements IAlarm {

	/**
	 * Service zur Verwaltung der Alarmzeitpunkte in der Datenbank.
	 */
	private final IAlarmTimestampService service;

	/**
	 * Erweiterter Konstruktor.
	 * 
	 * @param service
	 *            Von dieser Klasse zu verwendende Serviceimplementierung
	 */
	public Alarm(final IAlarmTimestampService service) {
		if (service == null) {
			throw new NullArgumentException();
		}
		this.service = service;
	}

	@Override
	public List<TimeDescriptor> getAllAlarmtimestamps() {
		return this.service.getAll();
	}

	@Override
	public Boolean isAlarmtimestampActivated(final TimeDescriptor descriptor) {
		return this.service.isActivated(descriptor);
	}

	@Override
	public Boolean removeAlarmtimestamp(final TimeDescriptor descriptor) {
		return this.service.delete(descriptor);
	}

	@Override
	public Boolean setAlarmtimestamp(final TimeDescriptor descriptor) {
		return this.service.create(descriptor);
	}

	@Override
	public Boolean setStateOfAlarmtimestamp(final TimeDescriptor descriptor, final Boolean isActivated) {
		return this.service.setState(descriptor, isActivated);
	}
}
