/**
 * 
 */
package rmblworx.tools.timey;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;

/**
 * @author mmatthies
 */
public class SimpleAlarm implements ApplicationContextAware {

	private ScheduledExecutorService scheduler;
	private ApplicationContext springContext;
	private ScheduledFuture<?> alarmFuture;

	/**
	 * Startet die Alarmerkennung.
	 * @param delayPerThread Maszzahl fuer den Ausfuehrungsintervall
	 * @param timeUnit Einheit fuer den Ausfuehrungsintervall
	 */
	public void startAlarmdetection(final int delayPerThread, final TimeUnit timeUnit) {
		if (delayPerThread < 1) {
			throw new ValueMinimumArgumentException();
		} else if (timeUnit == null) {
			throw new NullArgumentException();
		}
		this.scheduler = Executors.newScheduledThreadPool(1);
		final AlarmRunnable alarmDetection = (AlarmRunnable) this.springContext.getBean("alarmRunnable");

		this.alarmFuture = this.scheduler.scheduleAtFixedRate(alarmDetection, 0, delayPerThread, timeUnit);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}
}