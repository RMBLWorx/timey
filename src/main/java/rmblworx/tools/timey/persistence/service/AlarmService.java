package rmblworx.tools.timey.persistence.service;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rmblworx.tools.timey.event.AlarmsModifiedEvent;
import rmblworx.tools.timey.event.TimeyEventDispatcher;
import rmblworx.tools.timey.persistence.dao.IAlarmDao;
import rmblworx.tools.timey.persistence.model.AlarmEntity;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/**
 * Serviceimplementierung zur Persistierung von {@link AlarmEntity Alarmzeitpunkt}en.
 *
 * @author mmatthies
 */
@Service
@Transactional
class AlarmService implements IAlarmService, ApplicationContextAware {

	/**
	 * Referenz auf das Datenzugriffsobjekt.
	 */
	@Autowired
	private IAlarmDao dao;
	private TimeyEventDispatcher eventDispatcher;
	private ApplicationContext springContext;

	@Override
	public Boolean create(final AlarmDescriptor descriptor) {
		Boolean result;
		result = this.getDao().createAlarm(descriptor);
		this.fireAlarmModifiedEvent();

		return result;
	}

	private void fireAlarmModifiedEvent() {
		final AlarmsModifiedEvent alarmsModifiedEvent = (AlarmsModifiedEvent) this.springContext
				.getBean("alarmsModifiedEvent");
		this.eventDispatcher.dispatchEvent(alarmsModifiedEvent);
	}

	@Override
	public Boolean delete(final AlarmDescriptor descriptor) {
		Boolean result;
		result = this.getDao().deleteAlarm(descriptor);
		this.fireAlarmModifiedEvent();

		return result;
	}

	@Override
	public List<AlarmDescriptor> getAll() {
		return this.getDao().findAll();
	}

	/**
	 * @return Referenz auf das Datenzugriffsobjekt.
	 */
	private IAlarmDao getDao() {
		return this.dao;
	}

	@Override
	public Boolean isActivated(final AlarmDescriptor descriptor) {
		Boolean result;
		result = this.dao.isActivated(descriptor);
		this.fireAlarmModifiedEvent();

		return result;
	}

	@Override
	public Boolean setState(final AlarmDescriptor descriptor, final Boolean isActivated) {
		Boolean result;
		result = this.dao.setIsActivated(descriptor, isActivated);
		this.fireAlarmModifiedEvent();

		return result;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
		this.eventDispatcher = (TimeyEventDispatcher) this.springContext.getBean("timeyEventDispatcher");
	}
}
