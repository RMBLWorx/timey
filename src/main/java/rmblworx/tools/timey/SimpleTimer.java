/**
 */
package rmblworx.tools.timey;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Implementierung eines einfachen Timer's zum ausfuehren einer Zeitmessung.
 * 
 * @author mmatthies
 */
public final class SimpleTimer {

	/**
	 * Scheduler wird verwendet um die Threads zu verwalten und wiederholt
	 * ausfuehren zu lassen.
	 */
	private ScheduledExecutorService scheduler;
	/**
	 * Wertobjekt das die Zeit fuer die GUI kapselt und liefert.
	 */
	private final TimeDescriptor timeDescriptor;
	/**
	 * Die bereits vergangene Zeit in Millisekunden.
	 */
	private long timePassed = 0;

	/**
	 * Konstruktor. Erfordert die Referenz auf das Werteobjekt, welches den
	 * Wert an die GUI liefern wird.
	 * 
	 * @param descriptor
	 *            das zu setzende Werteobjekt. Es findet keine Pruefung
	 *            auf @code{null} statt!
	 */
	public SimpleTimer(final TimeDescriptor descriptor) {
		this.timeDescriptor = descriptor;
	}

	/**
	 * Startet den Stopvorgang.
	 * 
	 * @param amountOfThreads
	 *            Anzahl fuer die Zeitmessung zu verwendender Threads.
	 * @param delayPerThread
	 *            Setzt die Verzoegerung pro Thread. Die Maszeinheit
	 *            wird mittels des TimeUnit-Enum gesetzt.
	 * @param timeUnit
	 *            Setzt die zu verwendende Zeiteinheit fuer die
	 *            wiederholte Aktualisierung des Werteobjekts.
	 * @return Referenz auf das Wertobjekt das die darzustellende Zeit
	 *         kapselt. Es handelt sich hierbei um das im Konstruktor
	 *         uebergebene Objekt.
	 */
	public TimeDescriptor startStopwatch(final int amountOfThreads, final int delayPerThread, final TimeUnit timeUnit) {

		this.scheduler = Executors.newScheduledThreadPool(amountOfThreads);
		this.scheduler.scheduleAtFixedRate(new TimerRunnable(this.timeDescriptor, this.timePassed), 0, delayPerThread,
				timeUnit);

		return this.timeDescriptor;
	}

	/**
	 * Stoppt die Zeitmessung und beendet den/die gestarteten Threads.
	 */
	public void stopStopwatch() {
		this.scheduler.shutdownNow();
		this.timePassed = this.timeDescriptor.getMilliSeconds();
	}
}
