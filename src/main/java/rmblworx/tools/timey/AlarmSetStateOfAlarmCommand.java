package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zum Aktivieren eines Alarmzeitpunktes.
 * @author mmatthies
 */
class AlarmSetStateOfAlarmCommand implements ICommand {

	/**
	 * Speichert die Empfänger-Instanz.
	 */
	private final IAlarm fReceiver;
	/**
	 * Status des Alarmzeitpunktes.
	 */
	private final Boolean isActivated;
	/**
	 * Beschreibung des Alarmzeitpunktes.
	 */
	private final AlarmDescriptor timeDescriptor;

	/**
	 * Erweiterter Konstruktor.
	 *
	 * @param receiver
	 *            Empfängerimplementierung
	 * @param descriptor
	 *            Beschreibung des Alarmzeitpunktes.
	 * @param isActivated
	 *            Aussage ob der Alarmzeitpunkt aktiviert sein soll
	 */
	public AlarmSetStateOfAlarmCommand(final IAlarm receiver, final AlarmDescriptor descriptor, final Boolean isActivated) {
		if (receiver == null || descriptor == null || isActivated == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
		this.isActivated = isActivated;
	}

	/**
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute() {
		return this.fReceiver.setStateOfAlarm(this.timeDescriptor, this.isActivated);
	}
}
