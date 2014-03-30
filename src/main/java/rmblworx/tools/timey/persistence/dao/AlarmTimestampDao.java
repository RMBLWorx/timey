package rmblworx.tools.timey.persistence.dao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rmblworx.tools.timey.persistence.model.AlarmTimestamp;
import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Data Access Object-Implementierung zum verwalten der Alarmzeiten in timey.
 * 
 * @author mmatthies
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AlarmTimestampDao implements IAlarmTimestampDao {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AlarmTimestampDao.class);
	/**
	 * SessionFactory.
	 */
	private final SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 *            Referenz auf die SessionFactory.
	 */
	@Autowired
	public AlarmTimestampDao(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean createAlarmTimestamp(final AlarmDescriptor descriptor) {
		Boolean result = Boolean.FALSE;
		if (descriptor != null) {
			final AlarmTimestamp entity = new AlarmTimestamp();
			final Timestamp timestamp = new Timestamp(descriptor.getAlarmtime().getMilliSeconds());
			final String sound = descriptor.getSound().toString();
			Timestamp snooze = null;
			if (descriptor.getSnooze() != null) {
				snooze = new Timestamp(descriptor.getSnooze().getMilliSeconds());
			}
			entity.setAlarmTimestamp(timestamp);
			entity.setIsActivated(descriptor.getIsActive());
			entity.setDescription(descriptor.getDescription());
			entity.setSnooze(snooze);
			entity.setSound(sound);
			this.currentSession().save(entity);
			result = Boolean.TRUE;
		}
		// TODO: Noch umzusetzen : Jeder Zeitpunkt darf nur von einem Alarm beschrieben und persistent sein
		return result;
	}

	/**
	 * @return die aktuelle Hibernate-Session
	 */
	private Session currentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean deleteAlarmTimestamp(final AlarmDescriptor descriptor) {
		Boolean result = Boolean.FALSE;
		if (null != descriptor) {
			final List<AlarmTimestamp> allTimestamps = this.getAll();
			try {
				for (AlarmTimestamp alarmTimestamp : allTimestamps) {
					final long milliseconds = alarmTimestamp.getAlarmTimestamp().getTime();
					if (milliseconds == descriptor.getAlarmtime().getMilliSeconds()) {
						this.currentSession().delete(alarmTimestamp);
						result = Boolean.TRUE;
					}
				}
			} catch (final Exception e) {
				// TODO: throwing Advice implementieren und somit per Interceptor die Exceptions behandeln
				LOG.error(e.getMessage());
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}

	@Override
	public List<AlarmDescriptor> findAll() {
		final List<AlarmTimestamp> all = this.getAll();
		final List<AlarmDescriptor> result = new ArrayList<AlarmDescriptor>(all.size());
		for (AlarmTimestamp alarmTimestamp : all) {
			final TimeDescriptor timeDescriptor = new TimeDescriptor(alarmTimestamp.getAlarmTimestamp().getTime());
			final Boolean isActive = alarmTimestamp.getIsActivated();
			final String description = alarmTimestamp.getDescription();
			TimeDescriptor snooze = null;
			if (alarmTimestamp.getSnooze() != null) {
				snooze = new TimeDescriptor(alarmTimestamp.getSnooze().getTime());
			}
			final Path sound = Paths.get(alarmTimestamp.getSound());
			final AlarmDescriptor descriptor = new AlarmDescriptor(timeDescriptor, isActive, description, sound, snooze);
			result.add(descriptor);
		}
		return Collections.unmodifiableList(result);
	}

	/**
	 * @return Liste mit allen persistenten Alarmzeit-Objekten.
	 */
	@SuppressWarnings("unchecked")
	private List<AlarmTimestamp> getAll() {
		final List<AlarmTimestamp> allTimestamps = this.currentSession().createCriteria(AlarmTimestamp.class).list();

		return Collections.unmodifiableList(allTimestamps);
	}

	/**
	 * Liefert das Alarmzeitpunktobjekt das den vom Deskriptor beschriebenen Alarmzeitpunkt enthaelt.
	 * 
	 * @param descriptor
	 *            der Alarmzeitpunkt
	 * @return das Alarmzeitpunktobjekt oder {@code null} wenn nicht gefunden
	 */
	private AlarmTimestamp getByTime(final AlarmDescriptor descriptor) {
		AlarmTimestamp result = null;
		if (null != descriptor) {
			final List<AlarmTimestamp> all = this.getAll();
			for (AlarmTimestamp alarmTimestamp : all) {
				if (alarmTimestamp.getAlarmTimestamp().getTime() == descriptor.getAlarmtime().getMilliSeconds()) {
					result = alarmTimestamp;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public Boolean isActivated(final AlarmDescriptor descriptor) {
		Boolean result = null;
		if (null != descriptor) {
			final AlarmTimestamp timestamp = this.getByTime(descriptor);
			if (timestamp != null) {
				result = timestamp.getIsActivated();
			}
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean setIsActivated(final AlarmDescriptor descriptor, final Boolean isActivated) {
		Boolean result = Boolean.FALSE;
		if (null != descriptor && null != isActivated) {
			try {
				final AlarmTimestamp timestamp = this.getByTime(descriptor);
				timestamp.setIsActivated(isActivated);
				this.currentSession().save(timestamp);
				result = Boolean.TRUE;
			} catch (final Exception e) {
				LOG.error(e.getMessage());
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}
}
