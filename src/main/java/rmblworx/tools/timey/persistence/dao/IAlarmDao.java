package rmblworx.tools.timey.persistence.dao;

import java.util.List;

import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Schnittstellenbeschreibung für das Datenzugriffobjekt zum persistieren von Alarm-Objekten.
 * @author mmatthies
 */
public interface IAlarmDao {
	/**
	 * Schreibt den Alarmzeitpunkt in die Datenbank.
	 *
	 * @param descriptor
	 *            Der zu persistierende Alarmzeitpunkt.
	 * @return true wenn erfolgreich, sonst false.
	 */
	Boolean createAlarm(AlarmDescriptor descriptor);

	/**
	 * Entfernt das {@link rmblworx.tools.timey.persistence.model.AlarmEntity}-Objekt aus der Datenbank.
	 *
	 * @param descriptor
	 *            der zu entfernende Alarmzeitpunkt.
	 * @return true wenn erfolgreich, sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	Boolean deleteAlarm(AlarmDescriptor descriptor);

	/**
	 * Liefert alle Alarmzeitpunkte.
	 *
	 * @return unveränderliche Liste mit allen Alarmzeitpunkten.
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
	 * Ermöglicht das Aktivierung/ Deaktivierung des {@link rmblworx.tools.timey.persistence.model.AlarmEntity}-Objekts.
	 *
	 * @param descriptor
	 *            der Alarmzeitpunkt
	 * @param isActivated
	 *            true wenn der Alarm aktiviert werden soll, sonst false.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean setIsActivated(AlarmDescriptor descriptor, Boolean isActivated);
}
