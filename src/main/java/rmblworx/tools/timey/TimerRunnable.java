package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Diese Thread-sichere Implementierung dient der Zeitmessung in Millisekunden.
 * 
 * @author mmatthies
 */
public class TimerRunnable extends TimeyTimeRunnable implements Runnable {
	/**
	 * @param descriptor
	 *            Referenz auf das Wertobjekt das die Zeit in
	 *            Millisekunden an die konsumierende Implementierung
	 *            liefern soll.
	 * @param passedTime
	 *            Vergangene Zeit in Millisekunden.
	 */
	public TimerRunnable(final TimeDescriptor descriptor, final long passedTime) {
		super(descriptor, passedTime, System.currentTimeMillis());
	}

	/**
	 * Berechnet die Stoppzeit und schreibt den Wert in Millisekunden in das
	 * Wertobjekt.
	 */
	@Override
	protected void computeTime() {
		this.timeDelta = 0;
		final long currentTimeMillis = System.currentTimeMillis();

		this.timeDelta = currentTimeMillis - this.timeStarted;
		this.timeDescriptor.setMilliSeconds(this.timePassed + this.timeDelta);
	}

	@Override
	public void run() {
		this.lock.lock();
		try {
			this.computeTime();
		} finally {
			this.lock.unlock();
		}
	}

}
