package rmblworx.tools.timey.persistence.dao;

import java.util.List;

import rmblworx.tools.timey.persistence.model.AlarmTimestamp;
import rmblworx.tools.timey.vo.AlarmDescriptor;

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
	Boolean createAlarmTimestamp(AlarmDescriptor descriptor);

	/**
	 * Entfernt das {@link AlarmTimestamp}-Objekt aus der Datenbank.
	 * 
	 * @param descriptor
	 *            der zu entfernende Alarmzeitpunkt.
	 * @return true wenn erfolgreich, sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	Boolean deleteAlarmTimestamp(AlarmDescriptor descriptor);

	/**
	 * Liefert alle Alarmzeitpunkte.
	 * 
	 * @return unveraenderliche Liste mit allen Alarmzeitpunkten.
	 */
	List<AlarmDescriptor> findAll();

	/**
	 * Gibt Auskunft ob der Alarmzeitpunkt aktiviert ist.
	 * 
	 * @param descriptor
	 *            der Alarmzeitpunkt
	 * @return true wenn aktiviert sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean isActivated(AlarmDescriptor descriptor);

	/**
	 * Ermoeglicht das Aktivierung/ Deaktivierung des {@link AlarmTimestamp}-Objekts.
	 * 
	 * @param descriptor
	 *            der Alarmzeitpunkt
	 * @param isActivated
	 *            true wenn der Alarm aktiviert werden soll, sonst false.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean setIsActivated(AlarmDescriptor descriptor, Boolean isActivated);
}
