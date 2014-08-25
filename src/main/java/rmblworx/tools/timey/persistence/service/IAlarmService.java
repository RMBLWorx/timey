package rmblworx.tools.timey.persistence.service;

import java.util.List;

import rmblworx.tools.timey.persistence.model.AlarmEntity;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Schnittstellenbeschreibung für den Dienst zum verwalten von {@link AlarmEntity Alarmzeitpunkten}.
 * @author mmatthies
 * @see AlarmEntity
 */
public interface IAlarmService {
	/**
	 * Persistiert den Alarmzeitpunkt.
	 *
	 * @param descriptor
	 *            Referenz auf das Objekt das den Alarmzeitpunkt beschreibt
	 * @return true wenn erfolgreich sonst false
	 */
	Boolean create(AlarmDescriptor descriptor);

	/**
	 * Entfernt den Alarmzeitpunkt aus der Datenbank.
	 *
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	Boolean delete(AlarmDescriptor descriptor);

	/**
	 * Liefert alle Alarmzeitpunkte in der Datenbank.
	 *
	 * @return unveränderliche Liste mit allen Alarmzeitpunkten oder leere Liste
	 */
	List<AlarmDescriptor> getAll();

	/**
	 * Liefert die Aussage ob der Alarmzeitpunkt aktiv oder inaktiv ist.
	 *
	 * @param descriptor
	 *            Zeitobjekt das den Alarmzeitpunkt beschreibt
	 * @return true wenn aktiv sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean isActivated(AlarmDescriptor descriptor);

	/**
	 * Setzt den Alarmzeitpunkt auf den uebergebenen Status in der Datenbank.
	 *
	 * @param descriptor
	 *            Alarmzeitpunkt.
	 * @param isActivated
	 *            Referenz auf das booleansche Objekt das den Zustand beschreibt.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean setState(AlarmDescriptor descriptor, Boolean isActivated);
}
