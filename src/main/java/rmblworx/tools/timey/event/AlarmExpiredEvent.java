package rmblworx.tools.timey.event;

import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Informiert den Listener ueber den Ablauf eines Alarms.
 * @author mmatthies
 */
public class AlarmExpiredEvent implements TimeyEvent {

	/**
	 * Alarmobjekt.
	 */
	private final AlarmDescriptor alarmDescriptor;

	/**
	 * Erweiterter Konstruktor. Der Alarm, welcher dieses Ereignis ausloeste kann direkt uebergeben werden.
	 *
	 * @param alarmDescriptor
	 *            Beschreibung des Alarmzeitpunktes
	 */
	public AlarmExpiredEvent(final AlarmDescriptor alarmDescriptor) {
		this.alarmDescriptor = alarmDescriptor;
	}

	/**
	 * Liefert das Alarmzeitpunktobjekt.
	 *
	 * @return Alarmzeitpunktobjekt oder {@code null} wenn nicht gesetzt bei Instantiierung
	 */
	public AlarmDescriptor getAlarmDescriptor() {
		return this.alarmDescriptor;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(1024);
		String result;
		sb.append("By AlarmExpiredEvent capsuled AlarmDescriptor-State:");
		sb.append(this.alarmDescriptor);
		result = sb.toString();
		sb = null;

		return result;

	}
}
