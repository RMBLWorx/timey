package rmblworx.tools.timey.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Modell eines Alarmzeitpunktes.
 * @author mmatthies
 */
@Entity
@Table(name = "ALARM")
public final class AlarmEntity implements Serializable {

	/**
	 * Serial-Version UID.
	 */
	private static final long serialVersionUID = -7172433465696734868L;

	/**
	 * Referenz auf den Alarmzeitpunkt.
	 */
	@Column(nullable = false)
	private Timestamp alarm;
	/**
	 * Beschreibungstext zum Alarmzeitpunkt.
	 */
	@Column(nullable = true)
	private String description;
	/**
	 * Primaerschluessel des Alarmzeitpunktes.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Referenz auf das Objekt das den Status kennzeichnet.
	 * <ul>
	 * <li>true wenn Zeitstempel aktiv.</li>
	 * <li>false wenn inaktiv.</li>
	 * </ul>
	 */
	@Column(nullable = false)
	private Boolean isActivated;

	/**
	 * Zeitpunkt an welchem der Alarm wiederholt werden soll.
	 */
	@Column(nullable = true)
	private Timestamp snooze;

	/**
	 * Pfad zum abzuspielenden Sound bei Eintritt des Alarmzeitpunktes.
	 */
	@Column(nullable = true)
	private String sound;

	/**
	 * Liefert den Alarmzeitpunkt.
	 *
	 * @return Zeitpunkt an welchem der Alarm auszulösen ist insofern aktiv.
	 */
	public Timestamp getAlarm() {
		return this.alarm;
	}

	/**
	 * Liefert die Beschreibung zum Alarmzeitpunkt.
	 * @return die Beschreibung
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Liefert die eineindeutige Id des Alarmzeitpunktes.
	 *
	 * @return Id des Alarm-Objektes
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Gibt Auskunft ob der Alarmzeitpunkt aktiv ist.
	 *
	 * @return true wenn aktiv, sonst false.
	 */
	public Boolean getIsActivated() {
		return this.isActivated;
	}

	/**
	 * Liefert den Zeitpunkt, an welchem der Alarm wiederholt werden soll.
	 * @return Wiederholungszeitpunkt
	 */
	public Timestamp getSnooze() {
		return this.snooze;
	}

	/**
	 * Liefert den Pfad zum Sound der bei Eintritt des Alarmzeitpunktes abgespielt werden soll.
	 *
	 * @return Abzuspielenden Sound inklusive Pfad
	 */
	public String getSound() {
		return this.sound;
	}

	/**
	 * Setzt den Alarmzeitpunkt.
	 *
	 * @param alarm
	 *            zu setzender Alarmzeitpunkt.
	 */
	public void setAlarm(final Timestamp alarm) {
		this.alarm = alarm;
	}

	/**
	 * Setzt den Beschreibungstext zum ALarmzeitpunkt.
	 * @param description
	 *            zu setzende Beschreibung
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Setzt die eineindeutige Id des Alarm-Objektes.
	 *
	 * @param id
	 *            Id fuer dieses Objekt.
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Ermoeglicht das Setzen des Aktivierungsstatus.
	 *
	 * @param isActivated
	 *            booleaschen Wert.
	 */
	public void setIsActivated(final Boolean isActivated) {
		this.isActivated = isActivated;
	}

	/**
	 * Setzt den Zeitpunkt an welchem der Alarm wiederholt ausgeloest werden soll.
	 * @param snooze
	 *            Der zu setzende Wiederholungszeitpunkt
	 */
	public void setSnooze(final Timestamp snooze) {
		this.snooze = snooze;
	}

	/**
	 * Setzt den Pfad vom abzuspielenden Sound bei Alarmeintritt.
	 *
	 * @param sound
	 *            Pfad zum Sound
	 */
	public void setSound(final String sound) {
		this.sound = sound;
	}
}
