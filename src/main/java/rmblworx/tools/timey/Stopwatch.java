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
	private final byte amountOfThreads;
	private ApplicationContext context;
	private final int delayPerThread;
	/**
	 * Logger.
	 */
	private final Logger log = LogManager.getLogger(Stopwatch.class);
	//	/**
	//	 * Wertobjekt das die Zeit fuer die GUI kapselt und liefert.
	//	 */
	//	private final TimeDescriptor timeDescriptor = new TimeDescriptor(0);
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
		this.log.entry();

		this.log.debug("Action: resetStopwatch");
		this.timer.resetStopwatch();
		this.timer = null;

		return this.log.exit(Boolean.TRUE);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public TimeDescriptor startStopwatch() {
		this.log.entry();

		TimeDescriptor result;

		this.log.debug("Action: startStopwatch");
		if (this.timer == null) {
			this.timer = (ITimer) this.context.getBean("timer");
		}

		result = this.timer.startStopwatch(this.amountOfThreads, this.delayPerThread, this.timeUnit);

		return this.log.exit(result);
	}

	@Override
	public Boolean stopStopwatch() {
		this.log.entry();

		this.log.debug("Action: stopStopwatch");
		if (this.timer != null) {
			this.timer.stopStopwatch();
		}

		return this.log.exit(Boolean.TRUE);
	}
}
