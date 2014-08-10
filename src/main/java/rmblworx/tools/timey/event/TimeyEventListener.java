package rmblworx.tools.timey.event;

/**
 * Von einem timey-Eventlistener zu implementierende Schnittstelle. Dieser kann dann über die Fassade registriert werden
 * und wird bei Ereignis zurueckgerufen.
 *
 * @author mmatthies
 */
public interface TimeyEventListener {

	/**
	 * Wird vom {@link TimeyEventDispatcher } gerufen. Welche konkreten Aktionen auszufuehren sind, bleibt dem
	 * implementierenden Listener-Objekt ueberlassen.
	 *
	 * @param timeyEvent
	 *            das aufgetretene timey-Event
	 */
	void handleEvent(final TimeyEvent timeyEvent);
}
