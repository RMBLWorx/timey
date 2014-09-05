package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zum Setzen des Zustands eines Alarmzeitpunktes.
 *
 * @author mmatthies
 */
class AlarmSetStateInAlarmDescriptorCommand implements ICommand {

	/**
	 * Beschreibung des Alarmzeitpunktes.
	 */
	private final AlarmDescriptor alarmDescriptor;
	/**
	 * Speichert die Empfänger-Instanz.
	 */
	private final Alarm fReceiver;
	/**
	 * Status des Alarmzeitpunktes.
	 */
	private final Boolean isActivated;

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
	public AlarmSetStateInAlarmDescriptorCommand(final Alarm receiver, final AlarmDescriptor descriptor,
			final Boolean isActivated) {
		if (receiver == null || descriptor == null || isActivated == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.alarmDescriptor = descriptor;
		this.isActivated = isActivated;
	}

	/**
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Void execute() {
		this.fReceiver.setStateInAlarmDescriptor(this.alarmDescriptor, this.isActivated);
		return null;
	}
}
