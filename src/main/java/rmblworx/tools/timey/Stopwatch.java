/**
 */
package rmblworx.tools.timey;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Receiver" implementation.
 * <ul>
 * <li>knows how to perform the operations associated with carrying out a request. Any class may serve as a Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class Stopwatch implements IStopwatch, ApplicationContextAware {
	/**
	 * Anzahl der Threads die Zeit messen sollen.
	 */
	private final byte amountOfThreads;
	/**
	 * Spring Context.
	 */
	private ApplicationContext context;
	/**
	 * Gibt die Maszzahl fuer die Zeiteinheit an.
	 * @see #timeUnit
	 */
	private final int delayPerThread;
	/**
	 * Logger.
	 */
	private static final Logger LOG = LogManager.getLogger(Stopwatch.class);
	/**
	 * Die genutzte Zeitmessimplementierung.
	 */
	private ITimer timer;
	/**
	 * Gibt die Zeiteinheit an in welchem der Intervall die gemessene Zeit geliefert wird.
	 */
	private final TimeUnit timeUnit;

	/**
	 * Konstruktor welcher eine Instanz dieses Receiver erzeugt.
	 * 
	 * @param amount
	 *            Anzahl der Zeitmessungs-Threads
	 * @param delay
	 *            Bestimmt den Intervall in welchem die vom Thread gemessene Zeit zurueckgeliefert wird.
	 * @param unit
	 *            Maszeinheit fuer den Intervall.
	 */
	public Stopwatch(final byte amount, final int delay, final TimeUnit unit) {
		this.amountOfThreads = amount;
		this.delayPerThread = delay;
		this.timeUnit = unit;
	}

	@Override
	public Boolean resetStopwatch() {
		LOG.entry();

		LOG.debug("Action: resetStopwatch");
		this.timer.resetStopwatch();
		this.timer = null;

		return LOG.exit(Boolean.TRUE);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public TimeDescriptor startStopwatch() {
		LOG.entry();

		TimeDescriptor result;

		LOG.debug("Action: startStopwatch");
		if (this.timer == null) {
			this.timer = (ITimer) this.context.getBean("timer");
		}

		result = this.timer.startStopwatch(this.amountOfThreads, this.delayPerThread, this.timeUnit);

		return LOG.exit(result);
	}

	@Override
	public Boolean stopStopwatch() {
		LOG.entry();

		LOG.debug("Action: stopStopwatch");
		if (this.timer != null) {
			this.timer.stopStopwatch();
		}

		return LOG.exit(Boolean.TRUE);
	}
}
