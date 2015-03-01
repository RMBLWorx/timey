package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommandoimplementierung zum stoppen eines Countdowns.
 * @author mmatthies
 */
class CountdownStopCommand implements ICommand {

	/**
	 * Referenz der Empfängerimplementierung.
	 */
	private final ICountdown fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz der Empfängerimplementierung
	 */
	public CountdownStopCommand(final ICountdown receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return true wenn der Countdown gestoppt werden konnte sonst false
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute() {
		return this.fReceiver.stopCountdown();
	}
}
