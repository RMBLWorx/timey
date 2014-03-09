/**
 * 
 */
package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Diese Thread-sichere Implementierung setzt einen Countdown-Zähler um. Zeitnahme findet in Millisekunden statt.
 * 
 * @author mmatthies
 */
public class CountdownRunnable extends TimeyTimeRunnable {
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
	}

	/**
	 * Berechnet und schreibt die noch verbleibende Countdown-Zeit in Millisekunden in das
	 * Wertobjekt.
	 */
	@Override
	protected void computeTime() {
		this.timeDelta = 0;
		final long currentTimeMillis = System.currentTimeMillis();
		this.timeDelta = (this.timeStarted - currentTimeMillis);
		this.timeDescriptor.setMilliSeconds(this.timeStarted + this.timeDelta);
	}
}
