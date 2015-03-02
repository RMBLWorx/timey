package rmblworx.tools.timey.persistence.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
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

import rmblworx.tools.timey.persistence.model.AlarmEntity;
import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Data Access Object-Implementierung zum verwalten der Alarmzeiten in timey.
 *
 * @author mmatthies
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
class AlarmDao implements IAlarmDao {

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(AlarmDao.class);
	/**
	 * SessionFactory.
	 */
	private final SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 *            Referenz auf die SessionFactory.
	 */
	@Autowired
	public AlarmDao(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Instantiiert ein neues AlarmDao. Dieser Konstruktor ist ausschließlich für Testzwecke gedacht.
	 *
	 * @param sessionFactory die gemockte Session Factory
	 * @param logger der gemockte Logger
	 */
	AlarmDao(final SessionFactory sessionFactory, final Logger logger) {
		this(sessionFactory);
		AlarmDao.log = logger;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean createAlarm(final AlarmDescriptor descriptor) {
		Boolean result = Boolean.FALSE;
		if (descriptor != null) {
			try {
				final AlarmEntity entity = new AlarmEntity();
				final Timestamp timestamp = new Timestamp(descriptor.getAlarmtime().getMilliSeconds());
				final String sound = descriptor.getSound();
				Timestamp snooze = null;
				if (descriptor.getSnooze() != null) {
					snooze = new Timestamp(descriptor.getSnooze().getMilliSeconds());
				}
				entity.setAlarm(timestamp);
				entity.setIsActivated(descriptor.getIsActive());
				entity.setDescription(descriptor.getDescription());
				entity.setSnooze(snooze);
				entity.setSound(sound);
				this.currentSession().save(entity);
				result = Boolean.TRUE;
			} catch (final Exception e) {
				this.logError(e);
			}
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
	public Boolean deleteAlarm(final AlarmDescriptor descriptor) {
		Boolean result = Boolean.FALSE;
		if (null != descriptor) {
			try {
				final List<AlarmEntity> allAlarms = this.getAll();
				for (final AlarmEntity alarm : allAlarms) {
					final long milliseconds = alarm.getAlarm().getTime();
					if (milliseconds == descriptor.getAlarmtime().getMilliSeconds()) {
						this.currentSession().delete(alarm);
						this.currentSession().delete(alarm);
						result = Boolean.TRUE;
					}
				}
			} catch (final Exception e) {
				// TODO: throwing Advice implementieren und somit per Interceptor die Exceptions behandeln
				this.logError(e);
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}

	@Override
	public List<AlarmDescriptor> findAll() {
		List<AlarmDescriptor> result = new ArrayList<AlarmDescriptor>();
		try {
			final List<AlarmEntity> entities = this.getAll();
			result = new ArrayList<>(entities.size());
			for (final AlarmEntity alarm : entities) {
				final TimeDescriptor timeDescriptor = new TimeDescriptor(alarm.getAlarm().getTime());
				final Boolean isActive = alarm.getIsActivated();
				final String description = alarm.getDescription();
				TimeDescriptor snooze = null;
				if (alarm.getSnooze() != null) {
					snooze = new TimeDescriptor(alarm.getSnooze().getTime());
				}
				final String sound = alarm.getSound();
				final AlarmDescriptor descriptor = new AlarmDescriptor(timeDescriptor, isActive, description, sound,
						snooze);
				result.add(descriptor);
			}
		} catch (final Exception e) {
			this.logError(e);
		}
		return Collections.unmodifiableList(result);
	}

	/**
	 * @return Liste mit allen persistenten Alarmzeit-Objekten.
	 */
	@SuppressWarnings("unchecked")
	private List<AlarmEntity> getAll() {
		final List<AlarmEntity> allTimestamps = this.currentSession().createCriteria(AlarmEntity.class).list();

		return Collections.unmodifiableList(allTimestamps);
	}

	/**
	 * Liefert das Alarmzeitpunktobjekt das den vom Deskriptor beschriebenen Alarmzeitpunkt enthält.
	 *
	 * @param descriptor
	 *            der Alarmzeitpunkt
	 * @return das Alarmzeitpunktobjekt oder {@code null} wenn nicht gefunden
	 */
	private AlarmEntity getByTime(final AlarmDescriptor descriptor) {
		AlarmEntity result = null;
		if (null != descriptor) {
			final List<AlarmEntity> all = this.getAll();
			for (final AlarmEntity alarm : all) {
				if (alarm.getAlarm().getTime() == descriptor.getAlarmtime().getMilliSeconds()) {
					result = alarm;
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
			try {
				final AlarmEntity timestamp = this.getByTime(descriptor);
				if (timestamp != null) {
					result = timestamp.getIsActivated();
				}
			} catch (final Exception e) {
				this.logError(e);
			}
		}
		return result;
	}

	private void logError(final Exception e) {
		final StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		final String excDetails = sw.toString();
		log.error(excDetails);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean setIsActivated(final AlarmDescriptor descriptor, final Boolean isActivated) {
		Boolean result = Boolean.FALSE;
		if (null != descriptor && null != isActivated) {
			try {
				final AlarmEntity timestamp = this.getByTime(descriptor);
				timestamp.setIsActivated(isActivated);
				this.currentSession().save(timestamp);
				result = Boolean.TRUE;
			} catch (final Exception e) {
				this.logError(e);
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}
}
