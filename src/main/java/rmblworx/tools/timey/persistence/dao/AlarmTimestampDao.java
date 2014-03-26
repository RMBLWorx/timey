package rmblworx.tools.timey.persistence.dao;

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
	public Boolean createAlarmTimestamp(final TimeDescriptor descriptor) {
		Boolean result = Boolean.FALSE;
		if (descriptor != null) {
			final AlarmTimestamp entity = new AlarmTimestamp();
			final Timestamp timestamp = new Timestamp(descriptor.getMilliSeconds());
			entity.setAlarmTimestamp(timestamp);
			entity.setIsActivated(Boolean.FALSE);
			this.currentSession().save(entity);
			result = Boolean.TRUE;
		}
		// TODO: Exceptions per Interceptor abfangen -> throwing Advice implementieren
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
	public Boolean deleteAlarmTimestamp(final TimeDescriptor descriptor) {
		Boolean result = Boolean.FALSE;
		if (null != descriptor) {
			final List<AlarmTimestamp> allTimestamps = this.getAll();
			try {
				for (AlarmTimestamp alarmTimestamp : allTimestamps) {
					final long milliseconds = alarmTimestamp.getAlarmTimestamp().getTime();
					if (milliseconds == descriptor.getMilliSeconds()) {
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
	public List<TimeDescriptor> findAll() {
		final List<AlarmTimestamp> all = this.getAll();
		final List<TimeDescriptor> result = new ArrayList<TimeDescriptor>(all.size());
		for (AlarmTimestamp alarmTimestamp : all) {
			final long milliseconds = alarmTimestamp.getAlarmTimestamp().getTime();
			final TimeDescriptor descriptor = new TimeDescriptor(milliseconds);
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
	 * @param descriptor der Alarmzeitpunkt
	 * @return das Alarmzeitpunktobjekt oder {@code null} wenn nicht gefunden
	 */
	private AlarmTimestamp getByTime(final TimeDescriptor descriptor) {
		AlarmTimestamp result = null;
		if (null != descriptor) {
			final List<AlarmTimestamp> all = this.getAll();
			for (AlarmTimestamp alarmTimestamp : all) {
				if (alarmTimestamp.getAlarmTimestamp().getTime() == descriptor.getMilliSeconds()) {
					result = alarmTimestamp;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public Boolean isActivated(final TimeDescriptor descriptor) {
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
	public Boolean setIsActivated(final TimeDescriptor descriptor, final Boolean isActivated) {
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
		} else{
			result = null;
		}
		return result;
	}
}
