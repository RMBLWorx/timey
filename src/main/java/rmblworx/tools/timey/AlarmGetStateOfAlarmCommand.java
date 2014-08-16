package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zur Erfragung des Aktivierungsstatus eines Alarmzeitpunktes.
 * @author mmatthies
 */
class AlarmGetStateOfAlarmCommand implements ICommand {

	/**
	 * Speichert die Empfaenger-Instanz.
	 */
	private final IAlarm fReceiver;
	/**
	 * Beschreibung des Alarmzeitpunktes.
	 */
	private final AlarmDescriptor timeDescriptor;

	/**
	 * Erweiterter Konstruktor.
	 *
	 * @param receiver
	 *            Empfaengerimplementierung
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes
	 */
	public AlarmGetStateOfAlarmCommand(final IAlarm receiver, final AlarmDescriptor descriptor) {
		if (receiver == null || descriptor == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * @return true oder false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute() {
		return this.fReceiver.isAlarmActivated(this.timeDescriptor);
	}

}
