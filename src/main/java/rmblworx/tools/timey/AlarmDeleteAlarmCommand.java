package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zum Löschen eines Alarmzeitpunktes.
 * @author mmatthies
 */
class AlarmDeleteAlarmCommand implements ICommand {

	/**
	 * Speichert die Empfänger-Instanz.
	 */
	private final IAlarm fReceiver;
	/**
	 * Referenz auf das Zeitobjekt.
	 */
	private final AlarmDescriptor timeDescriptor;

	/**
	 * Erweiterter Konstruktor.
	 *
	 * @param receiver
	 *            Empfängerimplementierung
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes
	 */
	public AlarmDeleteAlarmCommand(final IAlarm receiver, final AlarmDescriptor descriptor) {
		if (receiver == null || descriptor == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute() {
		return this.fReceiver.removeAlarm(this.timeDescriptor);
	}
}
