package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Kommando zur Erfragung des Aktivierungsstatus eines Alarmzeitpunktes.
 * 
 * @author "mmatthies"
 */
public class AlarmGetStateOfAlarmCommand implements ICommand {

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
	 *            Empfaengerimplementierung
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes
	 */
	public AlarmGetStateOfAlarmCommand(final IAlarm receiver, final TimeDescriptor descriptor) {
		if (receiver == null || descriptor == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * @return true oder false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	@Override
	public Boolean execute() {
		return this.fReceiver.isAlarmtimestampActivated(this.timeDescriptor);
	}

}
