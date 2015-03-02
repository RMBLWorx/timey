package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
public class StopwatchToggleTimeModeCommand implements ICommand {
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
	public StopwatchToggleTimeModeCommand(final IStopwatch receiver) {
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
		return this.fReceiver.toggleTimeModeInStopwatch();
	}
}
