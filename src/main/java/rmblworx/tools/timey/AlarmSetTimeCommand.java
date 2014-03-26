package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Kommando zum Setzen der Alarmzeit.
 * 
 * @author mmatthies
 */
public class AlarmSetTimeCommand implements ICommand {

	/**
	 * Speichert die Empfaenger-Instanz.
	 */
	private final IAlarm fReceiver;
	/**
	 * Beschreibung des Alarmzeitpunktes.
	 */
	private final TimeDescriptor timeDescriptor;

	/**
	 * Erweiterter Konstruktor.
	 * 
	 * @param receiver
	 *            Empfaengerimplmentierung.
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 */
	public AlarmSetTimeCommand(final IAlarm receiver, final TimeDescriptor descriptor) {
		if (receiver == null || descriptor == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * @return true wenn erfolgreich sonst false
	 */
	@Override
	public Boolean execute() {
		return this.fReceiver.setAlarmtimestamp(this.timeDescriptor);
	}
}
