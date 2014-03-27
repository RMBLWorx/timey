package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Kommando zum Loeschen eines Alarmzeitpunktes.
 * 
 * @author "mmatthies"
 */
public class AlarmDeleteAlarmCommand implements ICommand {

	/**
	 * Speichert die Empfaenger-Instanz.
	 */
	private final IAlarm fReceiver;
	/**
	 * Referenz auf das Zeitobjekt.
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
	public AlarmDeleteAlarmCommand(final IAlarm receiver, final TimeDescriptor descriptor) {
		if (receiver == null || descriptor == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	@Override
	public Boolean execute() {
		return this.fReceiver.removeAlarmtimestamp(this.timeDescriptor);
	}
}
