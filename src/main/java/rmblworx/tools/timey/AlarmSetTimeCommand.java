package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zum Setzen der Alarmzeit.
 * @author mmatthies
 */
class AlarmSetTimeCommand implements ICommand {

	/**
	 * Speichert die Empfänger-Instanz.
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
	 *            Empfängerimplmentierung.
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 */
	public AlarmSetTimeCommand(final IAlarm receiver, final AlarmDescriptor descriptor) {
		if (receiver == null || descriptor == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * @return true wenn erfolgreich sonst false
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute() {
		return this.fReceiver.setAlarm(this.timeDescriptor);
	}
}
