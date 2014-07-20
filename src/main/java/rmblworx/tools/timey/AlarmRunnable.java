package rmblworx.tools.timey;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.event.*;
import rmblworx.tools.timey.persistence.service.IAlarmService;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/**
 * Diese Thread-sichere Implementierung setzt einen Countdown-Zähler um. Zeitnahme findet in Millisekunden statt.
 * 
 * @author mmatthies
 */
public class AlarmRunnable implements Runnable, ApplicationContextAware, TimeyEventListener {
	private TimeyEventDispatcher eventDispatcher;
	/**
	 * Von dieser Timerimplementierung verwendete Lock-Mechanismus.
	 */
	private final Lock lock = new ReentrantLock();
	private IAlarmService alarmService;
	private List<AlarmDescriptor> allAlarms;
	private boolean newOrModifiedAlarmsAvailable = false;

	/**
	 */
	public AlarmRunnable() {
	}

	private AlarmDescriptor detectAlarm() {
		long currentTimeMillis = System.currentTimeMillis();
		AlarmDescriptor result = null;
		for (AlarmDescriptor alarm : this.allAlarms) {
			if (alarm.getIsActive()) {
				if (alarm.getAlarmtime().getMilliSeconds() <= currentTimeMillis) {
					result = alarm;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("timey-Alarm");
		this.lock.lock();
		try {
			// alarme aus der db
			if(this.allAlarms == null || this.newOrModifiedAlarmsAvailable){
				this.allAlarms = this.alarmService.getAll();
				this.newOrModifiedAlarmsAvailable = false;
			}
			// alarme abgleichen mit aktueller Systemzeit
			final AlarmDescriptor result = this.detectAlarm();
			if (result != null) {
				// wenn erreicht event feuern sonst weiter abgleichen
				this.alarmService.setState(result, false);
				this.eventDispatcher.dispatchEvent(new AlarmExpiredEvent(result));
			}
		} finally {
			this.lock.unlock();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		final ApplicationContext springContext = applicationContext;
		this.eventDispatcher = (TimeyEventDispatcher) springContext.getBean("timeyEventDispatcher");
		this.eventDispatcher.addEventListener(this);
		this.alarmService = (IAlarmService) springContext.getBean("alarmService");
	}

	@Override
	public void handleEvent(final TimeyEvent timeyEvent) {
		if(timeyEvent instanceof AlarmsModifiedEvent){
			this.newOrModifiedAlarmsAvailable = true;
		}
	}
}
