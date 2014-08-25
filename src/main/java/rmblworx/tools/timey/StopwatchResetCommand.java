package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zum Zuruecksetzen der Stoppuhr.
 * @author mmatthies
 */
class StopwatchResetCommand implements ICommand {

	/**
	 * Speichert die Empfänger-Instanz.
	 */
	private final IStopwatch fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz auf die Empfängerimplementierung die von diesem Kommando gesteuert wird.
	 */
	public StopwatchResetCommand(final IStopwatch receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return true wenn erfolgreich zurückgesetzt werden konnte sonst false
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute() {
		return this.fReceiver.resetStopwatch();
	}
}
