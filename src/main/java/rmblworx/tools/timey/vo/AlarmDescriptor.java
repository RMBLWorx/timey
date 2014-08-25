package rmblworx.tools.timey.vo;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Werteobjekt zur Beschreibung eines Alarms.
 *
 * @author mmatthies
 */
public class AlarmDescriptor {
	/**
	 * Definiert die Größe bei Initialisierung des StringBuilder-Objekts.
	 */
	private static final int SIZE = 1024;
	/**
	 * Beschreibung des eigentlichen Alarmzeitpunktes.
	 */
	private TimeDescriptor alarmtime;
	/**
	 * Beschreibung zum Alarmzeitpunkt.
	 */
	private String description;
	/**
	 * Beschreibt ob der Alarm aktiv/ inaktiv ist.
	 */
	private Boolean isActive;
	/**
	 * Beschreibung des Zeitpunktes an dem der Alarm wiederholt werden soll.
	 */
	private TimeDescriptor snooze;
	/**
	 * Abzuspielender Sound beim Eintreten des Alarms.
	 */
	private String sound;

	/**
	 * Erweiterter Konstruktor.
	 *
	 * @param timeDescriptor
	 *            Beschreibung des Alarmzeitpunktes
	 * @param isActive
	 *            Gibt an ob der Alarm aktiviert ist
	 * @param description
	 *            Beschreibungstext zum Alarmzeitpunkt
	 * @param soundToPlay
	 *            Pfad zum abzuspielenden Sound wenn der Alarmzeitpunkt eintritt
	 * @param snooze
	 *            Beschreibung des Zeitpunktes, an welchem der Alarm erneut ausgefuehrt werden soll
	 */
	public AlarmDescriptor(final TimeDescriptor timeDescriptor, final Boolean isActive, final String description,
			final String soundToPlay, final TimeDescriptor snooze) {
		this.alarmtime = timeDescriptor;
		this.isActive = isActive;
		this.description = description;
		this.sound = soundToPlay;
		this.snooze = snooze;
	}

	/**
	 * Liefert die Alarmzeit.
	 *
	 * @return Wert >= 0
	 */
	public TimeDescriptor getAlarmtime() {
		return this.alarmtime;
	}

	/**
	 * Liefert den nutzerdefinierten Beschreibungstext zum Alarm.
	 *
	 * @return Beschreibungstext oder <code>null</code>
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Liefert den Status des Alarms.
	 *
	 * @return true wenn aktiviert, sonst false oder <code>null</code>
	 */
	public Boolean getIsActive() {
		return this.isActive;
	}

	/**
	 * Liefert den Zeitpunkt an dem der Alarm erneut ausgelöst werden soll.
	 *
	 * @return Werteobjekt zur Kapselung des Zeitpunktes oder <code>null</code>.
	 */
	public TimeDescriptor getSnooze() {
		return this.snooze;
	}

	/**
	 * Liefert den optional, definierbaren Klang der im Falle des Alarmzeitpunktes abgespielt werden soll.
	 *
	 * @return Pfad zur Klangdatei oder <code>null</code>
	 */
	public String getSound() {
		return this.sound;
	}

	/**
	 * Setzt die Alarmzeit.
	 *
	 * @param timeDescriptor
	 *            Werteobjekt zum Beschreiben des Alarmzeitpunktes. Es wird nicht auf die Referenzierung von
	 *            <code>null</code> geprueft!
	 */
	public void setAlarmtime(final TimeDescriptor timeDescriptor) {
		this.alarmtime = timeDescriptor;
	}

	/**
	 * Setzt den optionalen Beschreibungstext zum Alarm.
	 *
	 * @param description
	 *            zu verwendender Beschreibungstext. Es wird nicht auf die Referenzierung von <code>null</code>
	 *            geprueft!
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Setzt den Zustand des Alarms.
	 *
	 * @param isActive
	 *            true wenn aktiviert sonst false. Es wird nicht auf die Referenzierung von <code>null</code> geprueft!
	 */
	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Setzt den Zeitpunkt an welchem der bereits mindestens einmal ausgelöste Alarm wieder erneut ausgelöst werden
	 * soll.
	 *
	 * @param snooze
	 *            Werteobjekt zur Beschreibung des Zeitpunkts. Es wird nicht auf die Referenzierung von
	 *            <code>null</code> geprueft!
	 */
	public void setSnooze(final TimeDescriptor snooze) {
		this.snooze = snooze;
	}

	/**
	 * Setzt den Klang der beim Eintreten des Alarmzeitpunktes abgespielt werden soll.
	 *
	 * @param sound
	 *            Pfad zur Klanngdatei. Es wird nicht auf die Referenzierung von <code>null</code> geprueft!
	 */
	public final void setSound(final String sound) {
		this.sound = sound;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(SIZE);
		String result;
		sb.append("AlarmDescriptor-State:");
		sb.append("Description:");
		sb.append(this.description);
		sb.append(",");
		sb.append("isActive:");
		sb.append(this.isActive);
		sb.append(",");
		sb.append("Snooze:");
		sb.append(this.snooze);
		sb.append(",");
		sb.append("Sound:");
		sb.append(this.sound);
		sb.append(",");
		sb.append("Alarmtime:");
		sb.append(this.alarmtime);
		result = sb.toString();
		sb = null;

		return result;
	}
}
