package rmblworx.tools.timey.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ALARMTIMESTAMP")
public final class AlarmTimestamp implements Serializable {

	/**
	 * Serial-Version UID.
	 */
	private static final long serialVersionUID = -7172433465696734868L;

	@Column(nullable=false)
	private Timestamp alarmTimestamp;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private Boolean isActivated;

	public Timestamp getAlarmTimestamp() {
		return this.alarmTimestamp;
	}

	public Long getId() {
		return this.id;
	}

	public Boolean getIsActivated() {
		return this.isActivated;
	}

	public void setAlarmTimestamp(final Timestamp alarmTimestamp) {
		this.alarmTimestamp = alarmTimestamp;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setIsActivated(final Boolean isActivated) {
		this.isActivated = isActivated;
	}
}
