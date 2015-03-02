package rmblworx.tools.timey;

import java.util.List;

import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Schnittstellenbeschreibung des Alarmsystems.
 *
 * @author mmatthies
 */
interface IAlarm {

	/**
	 * Liefert alle Alarmzeitpunkte.
	 *
	 * @return unveränderliche Liste mit den bekannten Alarmzeitpunkten oder leere Liste
	 */
	List<AlarmDescriptor> getAllAlarms();

	/**
	 * Liefert die Aussage, ob die Alarmzeit scharf oder unscharf geschalten ist.
	 *
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true oder false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	Boolean isAlarmActivated(AlarmDescriptor descriptor);

	/**
	 * Löscht den Alarmzeitpunkt.
	 *
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean removeAlarm(AlarmDescriptor descriptor);

	/**
	 * Setzt die Alarmzeit wobei jede nur einmalig vorkommen kann.
	 *
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt bereits vorhanden
	 */
	Boolean setAlarm(AlarmDescriptor descriptor);

	/**
	 * Stellt die Alarmzeit unscharf bzw. scharf.
	 *
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @param isActivated
	 *            Booleanscher Wert ob der Alarmzeitpunkt aktiviert werden soll
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	Boolean setStateOfAlarm(AlarmDescriptor descriptor, Boolean isActivated);
}
