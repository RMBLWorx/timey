package rmblworx.tools.timey.persistence.dao;

import rmblworx.tools.timey.persistence.model.AlarmTimestamp;

/**
 * @author mmatthies
 */
public interface TimeyDao {
	/**
	 * Schreibt das {@link AlarmTimestamp}-Objekt in die Datenbank.
	 * 
	 * @param entity
	 *            Die zu persistierende Entitaet.
	 * @return true wenn erfolgreich, sonst false.
	 */
	Boolean createAlarmTimestamp(AlarmTimestamp entity);

	/**
	 * Entfernt das {@link AlarmTimestamp}-Objekt aus der Datenbank.
	 * @param id
	 *            die eineindeutige Id des {@link AlarmTimestamp}-Objektes.
	 * @return true wenn erfolgreich, sonst false.
	 */
	Boolean deleteAlarmTimestamp(Long id);

	/**
	 * Liefert - falls in der Datenbank vorhanden - das {@link AlarmTimestamp}-Objekt mit der angegebenen Id.
	 * 
	 * @param id
	 *            die eineindeutige Id des {@link AlarmTimestamp}-Objektes.
	 * @return das {@link AlarmTimestamp}-Objekt.
	 */
	AlarmTimestamp findById(Long id);

	/**
	 * Ermoeglicht das Aktivierung/ Deaktivierung des {@link AlarmTimestamp}-Objekts.
	 * 
	 * @param id
	 *            die Id vom {@link AlarmTimestamp}-Objekt
	 * @param isActivated
	 *            true wenn der Alarm aktiviert werden soll, sonst false.
	 */
	void setIsActivated(Long id, Boolean isActivated);

	/**
	 * Aktualisiert das uebergebene {@link AlarmTimestamp}-Objekt.
	 * @param entity zu aktualisierendes {@link AlarmTimestamp}-Objekt.
	 * @return true wenn erfolgreich, sonst false
	 */
	Boolean updateAlarmTimestamp(AlarmTimestamp entity);
}
