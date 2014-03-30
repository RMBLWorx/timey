package rmblworx.tools.timey;

import java.util.List;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/**
 * Kommando ermoeglicht das Abrufen aller erfassten Alarmzeitpunkte.
 * 
 * @author "mmatthies"
 */
public class AlarmGetAllAlarmtimestampsCommand implements ICommand {

	/**
	 * Speichert die Empfaenger-Instanz.
	 */
	private final IAlarm fReceiver;

	/**
	 * Erweiterter Konstruktor.
	 * 
	 * @param receiver
	 *            Empfaengerimplementierung
	 */
	public AlarmGetAllAlarmtimestampsCommand(final IAlarm receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return unveraenderliche Liste mit den bekannten Alarmzeitpunkten oder leere Liste
	 */
	@Override
	public List<AlarmDescriptor> execute() {
		return this.fReceiver.getAllAlarmtimestamps();
	}
}
