package rmblworx.tools.timey.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modell eines Alarmzeitpunktes.
 * 
 * @author mmatthies
 */
@Entity
@Table(name = "ALARMTIMESTAMP")
public final class AlarmTimestamp implements Serializable {

	/**
	 * Serial-Version UID.
	 */
	private static final long serialVersionUID = -7172433465696734868L;

	/**
	 * Referenz auf den Alarmzeitpunkt.
	 */
	@Column(nullable = false)
	private Timestamp alarmTimestamp;
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
	 * Liefert den Alarmzeitpunkt.
	 * 
	 * @return Zeitpunkt an welchem der Alarm auszulösen ist insofern aktiv.
	 */
	public Timestamp getAlarmTimestamp() {
		return this.alarmTimestamp;
	}

	/**
	 * Liefert die eineindeutige Id des Alarmzeitpunktes.
	 * 
	 * @return Id des AlarmTimestamp-Objektes
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
	 * Setzt den Alarmzeitpunkt.
	 * 
	 * @param alarmTimestamp
	 *            zu setzender Alarmzeitpunkt.
	 */
	public void setAlarmTimestamp(final Timestamp alarmTimestamp) {
		this.alarmTimestamp = alarmTimestamp;
	}

	/**
	 * Setzt die eineindeutige Id des AlarmTimestamp-Objektes.
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
}
