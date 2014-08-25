package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Diese Kommandoimplementierung ermöglicht das Starten eines Countdowns.
 * @author mmatthies
 */
class CountdownStartCommand implements ICommand {

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
	public CountdownStartCommand(final ICountdown receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return Referenz auf das Zeitbeschreibungsobjekt welches die noch verbleibende Zeit uebermittelt.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TimeDescriptor execute() {
		return this.fReceiver.startCountdown();
	}
}
