package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Kommando zum Stoppen der Stoppuhr.
 * @author mmatthies
 */
class StopwatchStopCommand implements ICommand {
	/**
	 * Referenz auf die Empfängerimplementierung.
	 */
	private final IStopwatch fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz auf die Empfängerimplementierung die von diesem Kommando gesteuert wird.
	 */
	public StopwatchStopCommand(final IStopwatch receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return true wenn erfolgreich sonst false
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute() {
		return this.fReceiver.stopStopwatch();
	}
}
