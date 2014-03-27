package rmblworx.tools.timey.persistence.dao;

import java.util.List;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Schnittstellenbeschreibung fuer das Datenzugriffobjekt zum persistieren von AlarmTimestamp-Objekten.
 * 
 * @author mmatthies
 */
public interface IAlarmTimestampDao {
	/**
	 * Schreibt den Alarmzeitpunkt in die Datenbank.
	 * 
	 * @param descriptor
	 *            Der zu persistierende Alarmzeitpunkt.
	 * @return true wenn erfolgreich, sonst false.
	 */
	Boolean createAlarmTimestamp(TimeDescriptor descriptor);

	/**
	 * Entfernt das {@link AlarmTimestamp}-Objekt aus der Datenbank.
	 * 
	 * @param descriptor
	 *            der zu entfernende Alarmzeitpunkt.
	 * @return true wenn erfolgreich, sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	Boolean deleteAlarmTimestamp(TimeDescriptor descriptor);

	/**
	 * Liefert alle Alarmzeitpunkte.
	 * 
	 * @return unveraenderliche Liste mit allen Alarmzeitpunkten.
	 */
	List<TimeDescriptor> findAll();

	/**
	 * Gibt Auskunft ob der Alarmzeitpunkt aktiviert ist.
	 * 
	 * @param descriptor
	 *            der Alarmzeitpunkt
	 * @return true wenn aktiviert sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean isActivated(TimeDescriptor descriptor);

	/**
	 * Ermoeglicht das Aktivierung/ Deaktivierung des {@link AlarmTimestamp}-Objekts.
	 * 
	 * @param descriptor
	 *            der Alarmzeitpunkt
	 * @param isActivated
	 *            true wenn der Alarm aktiviert werden soll, sonst false.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean setIsActivated(TimeDescriptor descriptor, Boolean isActivated);
}
