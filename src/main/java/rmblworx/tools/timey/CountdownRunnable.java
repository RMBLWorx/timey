package rmblworx.tools.timey;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Diese Thread-sichere Implementierung setzt einen Countdown-Zähler um. Zeitnahme findet in Millisekunden statt.
 * 
 * @author mmatthies
 */
public class CountdownRunnable extends TimeyTimeRunnable implements ApplicationContextAware {
	/**
	 * Die vom Nutzer gesetzte, herunter zu zaehlende Zeit in Millisekunden.
	 */
	private final long timeCountdown;
	private ApplicationContext springContext;
	private TimeyEventDispatcher eventDispatcher;

	/**
	 * @param descriptor
	 *            Referenz auf das Wertobjekt das die Zeit in
	 *            Millisekunden an die konsumierende Implementierung
	 *            liefern soll.
	 * @param passedTime
	 *            Vergangene Zeit in Millisekunden.
	 */
	public CountdownRunnable(final TimeDescriptor descriptor, final long passedTime) {
		super(descriptor, passedTime, System.currentTimeMillis());
		this.timeCountdown = descriptor.getMilliSeconds();
	}

	/**
	 * Berechnet und schreibt die noch verbleibende Countdown-Zeit in Millisekunden in das
	 * Wertobjekt.
	 */
	@Override
	protected void computeTime() {
		this.timeDelta = 0;
		final long currentTimeMillis = System.currentTimeMillis();
		this.timeDelta = this.timeStarted - currentTimeMillis;
		this.timeDescriptor.setMilliSeconds(this.timeCountdown + this.timeDelta);
	}

	@Override
	public void run() {
		this.lock.lock();
		try {
			this.computeTime();
			} else {
				this.eventDispatcher = (TimeyEventDispatcher) this.springContext.getBean("timeyEventDispatcher");
				this.eventDispatcher.dispatchEvent(new CountdownExpiredEvent());
		} finally {
			this.lock.unlock();
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}
}
