package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zum setzen der Countdown-Zeit.
 * @author mmatthies
 */
class CountdownSetTimeCommand implements ICommand {

	/**
	 * Referenz der Empfängerimplementierung.
	 */
	private final ICountdown fReceiver;
	/**
	 * Kapselt die Ergebnisse der Zeitmessung.
	 */
	private final TimeDescriptor timeDescriptor;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz der Empfängerimplementierung.
	 * @param descriptor
	 *            Referenz des Zeitbeschreibungsobjektes.
	 */
	public CountdownSetTimeCommand(final ICountdown receiver, final TimeDescriptor descriptor) {
		if (receiver == null || descriptor == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * @return true wenn die Countdownzeit erfolgreich gesetzt werden konnte sonst false.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute() {
		return this.fReceiver.setCountdownTime(this.timeDescriptor);
	}
}
