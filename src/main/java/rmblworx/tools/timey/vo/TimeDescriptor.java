package rmblworx.tools.timey.vo;

import rmblworx.tools.timey.exception.ValueMinimumArgumentException;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Nicht-Thread-sicheres Werteobjekt zum kapseln der Zeitangabe die für den
 * Countdown, den Alarm und die Stopuhr benötigt wird.
 * @author mmatthies
 */
public class TimeDescriptor {

	/**
	 * Kapselt die aktuelle Systemzeit in Millisekunden.
	 */
	private long milliseconds;

	/**
	 * Konstruktor der die direkte Angabe der zu setzenden Millisekunden
	 * ermöglicht.
	 *
	 * @param milliSeconds
	 *            Anzahl der Millisekunden. Es findet keine Pruefung auf
	 *            negative Werte statt.
	 */
	public TimeDescriptor(final long milliSeconds) {
		this.milliseconds = milliSeconds;
	}

	/**
	 * @return Die Anzahl der Millisekunden oder {@code 0} falls sie nie
	 *         gesetzt wurden.
	 */
	public long getMilliSeconds() {
		return this.milliseconds;
	}

	/**
	 * @param currentTimeMillis
	 *            zu setzende Zeit in Millisekunden
	 */
	public void setMilliSeconds(final long currentTimeMillis) {
		if (currentTimeMillis < 0) {
			throw new ValueMinimumArgumentException("Values less than zero are not permitted!");
		}
		this.milliseconds = currentTimeMillis;
	}

	@Override
	public String toString() {
		return "TimeDescriptor (ms): " + this.milliseconds;
	}

}
