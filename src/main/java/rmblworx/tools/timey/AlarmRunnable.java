package rmblworx.tools.timey;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.event.AlarmExpiredEvent;
import rmblworx.tools.timey.event.TimeyEventDispatcher;
import rmblworx.tools.timey.persistence.service.IAlarmTimestampService;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/**
 * Diese Thread-sichere Implementierung setzt einen Countdown-Zähler um. Zeitnahme findet in Millisekunden statt.
 * 
 * @author mmatthies
 */
public class AlarmRunnable implements Runnable, ApplicationContextAware {
	private ApplicationContext springContext;
	private TimeyEventDispatcher eventDispatcher;
	/**
	 * Von dieser Timerimplementierung verwendete Lock-Mechanismus.
	 */
	private final Lock lock = new ReentrantLock();
	private IAlarmTimestampService alarmTimestampService;
	private List<AlarmDescriptor> allAlarmtimestamps;

	/**
	 */
	public AlarmRunnable() {
	}

	private AlarmDescriptor detectAlarm() {
		long currentTimeMillis = System.currentTimeMillis();
		AlarmDescriptor result = null;
		for (AlarmDescriptor alarm : this.allAlarmtimestamps) {
			if (alarm.getAlarmtime().getMilliSeconds() == currentTimeMillis) {
				result = alarm;
			}
			break;
		}
		return result;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("timey-Alarm");
		this.lock.lock();
		try {
			// alarme aus der db holen
			this.allAlarmtimestamps = this.alarmTimestampService.getAll();
			// alarme abgleichen mit aktueller Systemzeit
			final AlarmDescriptor result = this.detectAlarm();
			if (result != null) {
				// wenn erreicht event feuern sonst weiter abgleichen
				this.eventDispatcher.dispatchEvent(new AlarmExpiredEvent(result));
			}
		} finally {
			this.lock.unlock();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
		this.eventDispatcher = (TimeyEventDispatcher) this.springContext.getBean("timeyEventDispatcher");
		this.alarmTimestampService = (IAlarmTimestampService) this.springContext.getBean("alarmTimestampService");
	}
}
