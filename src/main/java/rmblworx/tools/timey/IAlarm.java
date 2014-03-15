package rmblworx.tools.timey;

import java.util.List;

import rmblworx.tools.timey.vo.TimeDescriptor;

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
	List<TimeDescriptor> getAllAlarmtimestamps();

	/**
	 * Liefert die Aussage, ob die Alarmzeit scharf oder unscharf geschalten ist.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true oder false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	Boolean isAlarmtimestampActivated(TimeDescriptor descriptor);

	/**
	 * Loescht den Alarmzeitpunkt.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean removeAlarmtimestamp(TimeDescriptor descriptor);

	/**
	 * Setzt die Alarmzeit wobei jede nur einmalig vorkommen kann.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt bereits vorhanden
	 */
	Boolean setAlarmtimestamp(TimeDescriptor descriptor);

	/**
	 * Stellt die Alarmzeit unscharf bzw. scharf.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @param isActivated
	 *            Booleanscher Wert ob der Alarmzeitpunkt aktiviert werden soll
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean setStateOfAlarmtimestamp(TimeDescriptor descriptor, Boolean isActivated);

}
