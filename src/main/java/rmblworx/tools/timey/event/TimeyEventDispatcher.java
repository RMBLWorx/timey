package rmblworx.tools.timey.event;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Verwaltet die Listener und verteilt die Events.
 *
 * @author mmatthies
 */
public class TimeyEventDispatcher {
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
	public synchronized Boolean addEventListener(final TimeyEventListener timeyEventListener) {
		this.listener.add(timeyEventListener);
		return Boolean.TRUE;
	}

	/**
	 * Verteilt/ benachrichtigt alle registrierten Listener ueber das uebergebene Event.
	 *
	 * @param timeyEvent
	 *            das Event
	 */
	public synchronized void dispatchEvent(final TimeyEvent timeyEvent) {
		final Iterator<TimeyEventListener> it = this.listener.iterator();
		while (it.hasNext()) {
			it.next().handleEvent(timeyEvent);
		}
	}

	/**
	 * Liefert alle von der Implementierung verwalteten Listener zurück.
	 * 
	 * @return unveränderliche Liste mit allen verwalteten Listenern.
	 */
	public synchronized List<TimeyEventListener> getRegistredListener() {
		return Collections.unmodifiableList(this.listener);
	}

	/**
	 * Macht die Registrierung einer Listener-Implementierung wieder rueckgängig. Der Listener wird fortan nicht mehr
	 * benachrichtigt.
	 *
	 * @param timeyEventListener
	 *            Referenz auf die Listener-Implementierung
	 */
	public synchronized void removeEventListener(final TimeyEventListener timeyEventListener) {
		this.listener.remove(timeyEventListener);
	}

}
