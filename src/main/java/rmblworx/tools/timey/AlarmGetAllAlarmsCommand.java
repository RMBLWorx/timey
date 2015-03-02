package rmblworx.tools.timey;

import java.util.List;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando ermöglicht das Abrufen aller erfassten Alarmzeitpunkte.
 * @author mmatthies
 */
class AlarmGetAllAlarmsCommand implements ICommand {

	/**
	 * Speichert die Empfänger-Instanz.
	 */
	private final IAlarm fReceiver;

	/**
	 * Erweiterter Konstruktor.
	 *
	 * @param receiver
	 *            Empfängerimplementierung
	 */
	public AlarmGetAllAlarmsCommand(final IAlarm receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return unveränderliche Liste mit den bekannten Alarmzeitpunkten oder leere Liste
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AlarmDescriptor> execute() {
		return this.fReceiver.getAllAlarms();
	}
}
