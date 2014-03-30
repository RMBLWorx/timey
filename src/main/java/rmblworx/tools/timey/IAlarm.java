package rmblworx.tools.timey;

import java.util.List;

import rmblworx.tools.timey.vo.AlarmDescriptor;

/**
 * Schnittstellenbeschreibung des Alarmsystems.
 * 
 * @author mmatthies
 */
interface IAlarm {

	/**
	 * Liefert alle Alarmzeitpunkte.
	 * 
	 * @return unveraenderliche Liste mit den bekannten Alarmzeitpunkten oder leere Liste
	 */
	List<AlarmDescriptor> getAllAlarmtimestamps();

	/**
	 * Liefert die Aussage, ob die Alarmzeit scharf oder unscharf geschalten ist.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true oder false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	Boolean isAlarmtimestampActivated(AlarmDescriptor descriptor);

	/**
	 * Loescht den Alarmzeitpunkt.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean removeAlarmtimestamp(AlarmDescriptor descriptor);

	/**
	 * Setzt die Alarmzeit wobei jede nur einmalig vorkommen kann.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt bereits vorhanden
	 */
	Boolean setAlarmtimestamp(AlarmDescriptor descriptor);

	/**
	 * Stellt die Alarmzeit unscharf bzw. scharf.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @param isActivated
	 *            Booleanscher Wert ob der Alarmzeitpunkt aktiviert werden soll
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean setStateOfAlarmtimestamp(AlarmDescriptor descriptor, Boolean isActivated);
}
