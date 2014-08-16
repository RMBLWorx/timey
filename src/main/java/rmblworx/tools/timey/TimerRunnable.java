package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Diese Thread-sichere Implementierung dient der Zeitmessung in Millisekunden.
 * @author mmatthies
 */
class TimerRunnable extends TimeyTimeRunnable implements Runnable {
	private boolean isTimeModeActive = false;

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
		if(!this.isTimeModeActive){
			this.timeDescriptor.setMilliSeconds(this.timePassed + this.timeDelta);
		}
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

	public Boolean toggleTimeMode() {
		this.isTimeModeActive = this.isTimeModeActive ? false : true;
		return this.isTimeModeActive;
	}
}
