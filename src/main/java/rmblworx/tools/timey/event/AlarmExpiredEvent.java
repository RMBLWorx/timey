package rmblworx.tools.timey.event;

import rmblworx.tools.timey.vo.AlarmDescriptor;

/**
 * Informiert den Listener ueber den Ablauf eines Alarms.
 * 
 * @author mmatthies
 */
public class AlarmExpiredEvent implements TimeyEvent {

	/**
	 * Alarmobjekt.
	 */
	private final AlarmDescriptor alarmDescriptor;

	/**
	 * Erweiterter Konstruktor. Der Alarm, welcher dieses Ereignis ausloeste kann direkt uebergeben werden.
	 * 
	 * @param alarmDescriptor Beschreibung des Alarmzeitpunktes
	 */
	public AlarmExpiredEvent(final AlarmDescriptor alarmDescriptor) {
		this.alarmDescriptor = alarmDescriptor;
	}

	/**
	 * Liefert das Alarmzeitpunktobjekt.
	 * @return Alarmzeitpunktobjekt oder {@code null} wenn nicht gesetzt bei Instantiierung
	 */
	public AlarmDescriptor getAlarmDescriptor() {
		return this.alarmDescriptor;
	}
}
