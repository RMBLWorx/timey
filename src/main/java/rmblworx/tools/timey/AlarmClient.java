package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Erzeugt die konkreten Kommandoimplementierungen und setzt deren Empfaengerimplementierungen.
 * @author mmatthies
 */
class AlarmClient {
	/**
	 * Speichert die Empfaenger-Instanz.
	 */
	private final IAlarm fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz auf die Empfaenger-Instanz.
	 */
	public AlarmClient(final IAlarm receiver) {
		this.fReceiver = receiver;
	}

	/**
	 * @param descriptor
	 *            zu löschender Alarmzeitpunkt
	 * @return true wenn erfolgreich sonst false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	public Boolean initAlarmDeleteAlarm(final AlarmDescriptor descriptor) {
		final AlarmDeleteAlarmCommand cmd = new AlarmDeleteAlarmCommand(this.fReceiver, descriptor);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();

	}

	/**
	 * @param descriptor
	 *            Referenz auf das Zeitobjekt
	 * @return true oder false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	public Boolean initAlarmGetStateOfAlarmCommand(final AlarmDescriptor descriptor) {
		final AlarmGetStateOfAlarmCommand cmd = new AlarmGetStateOfAlarmCommand(this.fReceiver, descriptor);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	public Boolean initAlarmSetStateOfAlarmCommand(AlarmDescriptor descriptor, Boolean isActivated) {
		final AlarmSetStateOfAlarmCommand cmd = new AlarmSetStateOfAlarmCommand(this.fReceiver, descriptor, isActivated);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	public <T> T initGetAllAlarms() {
		final AlarmGetAllAlarmsCommand cmd = new AlarmGetAllAlarmsCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initSetAlarmCommand(final AlarmDescriptor td) {
		final AlarmSetTimeCommand cmd = new AlarmSetTimeCommand(this.fReceiver, td);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}
}
