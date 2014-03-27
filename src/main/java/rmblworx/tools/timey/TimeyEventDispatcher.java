/**
 * 
 */
package rmblworx.tools.timey;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Verwaltet die Listener und verteilt die Events.
 * @author mmatthies
 */
public class TimeyEventDispatcher {
	private List<TimeyEventListener> listener = new LinkedList<>();

	public synchronized void addEventListener(TimeyEventListener timeyEventListener) {
		this.listener.add(timeyEventListener);
	}

	public synchronized void removeEventListener(TimeyEventListener timeyEventListener) {
		this.listener.remove(timeyEventListener);
	}

	protected synchronized void dispatchEvent(TimeyEvent timeyEvent) {
		Iterator<TimeyEventListener> it = this.listener.iterator();
		while (it.hasNext()) {
			it.next().handleEvent(timeyEvent);
		}
	}
}
