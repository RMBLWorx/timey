package rmblworx.tools.timey.vo;

import java.nio.file.Path;

/**
 * Werteobjekt zur Beschreibung eines Alarms.
 * 
 * @author mmatthies
 */
public class AlarmDescriptor {
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
	private Path sound;
	/**
	 * Beschreibung des eigentlichen Alarmzeitpunktes.
	 */
	private TimeDescriptor alarmtime;

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
			final Path soundToPlay, final TimeDescriptor snooze) {
		this.alarmtime = timeDescriptor;
		this.isActive = isActive;
		this.description = description;
		this.sound = soundToPlay;
		this.snooze = snooze;
	}

	public String getDescription() {
		return this.description;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public TimeDescriptor getSnooze() {
		return this.snooze;
	}

	public Path getSound() {
		return this.sound;
	}

	public TimeDescriptor getAlarmtime() {
		return this.alarmtime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setSnooze(TimeDescriptor snooze) {
		this.snooze = snooze;
	}

	public void setSound(Path sound) {
		this.sound = sound;
	}

	public void setAlarmtime(TimeDescriptor timeDescriptor) {
		this.alarmtime = timeDescriptor;
	}
}
