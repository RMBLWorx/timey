package rmblworx.tools.timey.persistence.service;

import java.util.List;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Schnittstellenbeschreibung fuer den Dienst zum verwalten von {@link AlarmTimestamp Alarmzeitpunkten}.
 * 
 * @author mmatthies
 * @see AlarmTimestamp
 */
public interface IAlarmTimestampService {
	/**
	 * Persistiert den Alarmzeitpunkt.
	 * 
	 * @param descriptor
	 *            Referenz auf das Objekt das den Alarmzeitpunkt beschreibt
	 * @return true wenn erfolgreich sonst false
	 */
	Boolean create(TimeDescriptor descriptor);

	/**
	 * Entfernt den Alarmzeitpunkt aus der Datenbank.
	 * 
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	Boolean delete(TimeDescriptor descriptor);

	/**
	 * Liefert alle Alarmzeitpunkte in der Datenbank.
	 * 
	 * @return unveraenderliche Liste mit allen Alarmzeitpunkten oder leere Liste
	 */
	List<TimeDescriptor> getAll();

	/**
	 * Liefert die Aussage ob der Alarmzeitpunkt aktiv oder inaktiv ist.
	 * 
	 * @param descriptor
	 *            Zeitobjekt das den Alarmzeitpunkt beschreibt
	 * @return true wenn aktiv sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean isActivated(TimeDescriptor descriptor);

	/**
	 * Setzt den Alarmzeitpunkt auf den uebergebenen Status in der Datenbank.
	 * 
	 * @param descriptor
	 *            Alarmzeitpunkt.
	 * @param isActivated
	 *            Referenz auf das booleansche Objekt das den Zustand beschreibt.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean setState(TimeDescriptor descriptor, Boolean isActivated);
}
