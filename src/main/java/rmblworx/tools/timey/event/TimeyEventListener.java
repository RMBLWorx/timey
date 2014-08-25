package rmblworx.tools.timey.event;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Von einem timey-Eventlistener zu implementierende Schnittstelle. Dieser kann dann über die Fassade registriert werden
 * und wird bei Ereignis zurückgerufen.
 * @author mmatthies
 */
public interface TimeyEventListener {

	/**
	 * Wird vom {@link TimeyEventDispatcher } gerufen. Welche konkreten Aktionen auszuführen sind, bleibt dem
	 * implementierenden Listener-Objekt ueberlassen.
	 *
	 * @param timeyEvent
	 *            das aufgetretene timey-Event
	 */
	void handleEvent(final TimeyEvent timeyEvent);
}
