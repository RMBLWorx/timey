package rmblworx.tools.timey;

import java.util.List;

import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Erzeugt die konkreten Kommandoimplementierungen und setzt deren Empfängerimplementierungen.
 *
 * @author mmatthies
 */
class AlarmClient {
	/**
	 * Speichert die Empfänger-Instanz.
	 */
	private final IAlarm fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz auf die Empfänger-Instanz.
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
		final Invoker<Boolean> invoker = new Invoker<>(cmd);

		return invoker.execute();

	}

	/**
	 * @param descriptor
	 *            Referenz auf das Zeitobjekt
	 * @return true oder false oder {@code null} wenn Alarmzeitpunkt nicht vorhanden
	 */
	public Boolean initAlarmGetStateOfAlarmCommand(final AlarmDescriptor descriptor) {
		final AlarmGetStateOfAlarmCommand cmd = new AlarmGetStateOfAlarmCommand(this.fReceiver, descriptor);
		final Invoker<Boolean> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * Initiiert das Kommando. Ermöglicht das Setzen des Aktivierungszustandes in einem AlarmDescriptor.
	 *
	 * @param descriptor
	 *            Referenz auf das Alarmobjekt.
	 * @param isActivated
	 *            Gibt an, ob der Alarm aktiv oder inaktiv geschaltet werden soll.
	 * @return bearbeitetes AlarmDescriptor-Objekt.
	 */
	public AlarmDescriptor initAlarmSetStateInAlarmDescriptorCommand(final AlarmDescriptor descriptor,
			final Boolean isActivated) {
		final AlarmSetStateInAlarmDescriptorCommand cmd = new AlarmSetStateInAlarmDescriptorCommand(
				(Alarm) this.fReceiver, descriptor, isActivated);
		final Invoker<Void> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return descriptor;
	}

	/**
	 * Initiiert das Kommando. Ermöglicht die Zustandsänderung eines Alarms.
	 *
	 * @param descriptor
	 *            Referenz auf das Alarmobjekt.
	 * @param isActivated
	 *            Gibt an, ob der Alarm aktiv oder inaktiv geschaltet werden soll.
	 * @return true wenn erfolgreich sonst false oder null wenn Alarmzeitpunkt nicht vorhanden
	 */
	public Boolean initAlarmSetStateOfAlarmCommand(final AlarmDescriptor descriptor, final Boolean isActivated) {
		final AlarmSetStateOfAlarmCommand cmd = new AlarmSetStateOfAlarmCommand(this.fReceiver, descriptor, isActivated);
		final Invoker<Boolean> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		final Boolean result = invoker.execute();
		this.initAlarmSetStateInAlarmDescriptorCommand(descriptor, isActivated);

		return result;
	}

	/**
	 * Initiiert das Kommando. Ermöglicht die Abfrage aller Alarmzeitpunkte.
	 *
	 * @return unveränderliche Liste mit den bekannten Alarmzeitpunkten oder leere Liste
	 */
	public List<AlarmDescriptor> initGetAllAlarms() {
		final AlarmGetAllAlarmsCommand cmd = new AlarmGetAllAlarmsCommand(this.fReceiver);
		final Invoker<List<AlarmDescriptor>> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * Initiiert das Kommando zum Setzen eines Alarmzeitpunktes.
	 *
	 * @param alarmDescriptor
	 *            Wertobjekt zur Beschreibung des Alarmzeitpunktes.
	 * @return true wenn erfolgreich sonst false oder null wenn Alarmzeitpunkt bereits vorhanden
	 *         Wertobjekt zur Beschreibung des Alarmzeitpunktes.
	 * @return true wenn erfolgreich sonst false oder null wenn Alarmzeitpunkt bereits vorhanden
	 */
	public Boolean initSetAlarmCommand(final AlarmDescriptor alarmDescriptor) {
		final AlarmSetTimeCommand cmd = new AlarmSetTimeCommand(this.fReceiver, alarmDescriptor);
		final Invoker<Boolean> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}
}
