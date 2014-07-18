package rmblworx.tools.timey.event;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Verwaltet die Listener und verteilt die Events.
 *
 * @author mmatthies
 */
public class TimeyEventDispatcher {
	private static TimeyEventDispatcher instance;
	/**
	 * Liste aller registrierten Event-Listener.
	 */
	private final List<TimeyEventListener> listener = new LinkedList<>();

	/**
	 * Registriert bei Dispatcher eine Listener-Implementierung.
	 *
	 * @param timeyEventListener
	 *            Referenz auf die Listener-Implementierung
	 */
	public synchronized void addEventListener(final TimeyEventListener timeyEventListener) {
		this.listener.add(timeyEventListener);
	}

	/**
	 * Macht die Registrierung einer Listener-Implementierung wieder rueckgaengig. Der Listener wird fortan nicht mehr
	 * benachrichtigt.
	 *
	 * @param timeyEventListener
	 *            Referenz auf die Listener-Implementierung
	 */
	public synchronized void removeEventListener(final TimeyEventListener timeyEventListener) {
		this.listener.remove(timeyEventListener);
	}

	/**
	 * Verteilt/ benachrichtigt alle registrierten Listener ueber das uebergebene Event.
	 *
	 * @param timeyEvent
	 *            das Event
	 */
	public synchronized void dispatchEvent(final TimeyEvent timeyEvent) {
		Iterator<TimeyEventListener> it = this.listener.iterator();
		while (it.hasNext()) {
			it.next().handleEvent(timeyEvent);
		}
	}

	/*
	 * TODO
	 * Diese Fabrikmethode dient derzeit als Workaround. Da die getrennten ApplicationContexte jedesmal eine eigene
	 * EventDispatcher-Instanz bilden wird dies hiermit explizit unterbunden. Der saubere Ansatz waere den
	 * EventDispatcher in einer Parent xml zu deklarieren und in den jeweiligen Alarm- und Countdown-Spring-XML's darauf
	 * zu referenzieren - folgt noch
	 */
	public static synchronized TimeyEventDispatcher getInstance() {
		if (instance == null) {
			instance = new TimeyEventDispatcher();
		}

		return instance;
	}
}
