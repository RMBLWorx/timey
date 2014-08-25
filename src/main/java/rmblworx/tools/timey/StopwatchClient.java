package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Erzeugt die konkreten Kommandoimplementierungen und setzt deren Empfängerimplementierungen.
 *
 * @author mmatthies
 */
class StopwatchClient {

	/**
	 * Referenz auf die Empfängerimplementierung.
	 */
	private final IStopwatch fReceiver;

	/**
	 * Erzeugt eine Instanz dieses Klients und speichert die Referenz auf die Empfängerimplementierung.
	 *
	 * @param receiver
	 *            Referenz auf die Implementierung die dieser Client den Kommandos uebergeben soll.
	 */
	public StopwatchClient(final IStopwatch receiver) {
		this.fReceiver = receiver;
	}

	/**
	 * @return true wenn erfolgreich sonst false
	 */
	public Boolean initStopwatchResetCommand() {
		final StopwatchResetCommand cmd = new StopwatchResetCommand(this.fReceiver);
		final Invoker<Boolean> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @return Zeitwertobjekt das die gemessene Zeit kapselt.
	 */
	public TimeDescriptor initStopwatchStartCommand() {
		final StopwatchStartCommand cmd = new StopwatchStartCommand(this.fReceiver);
		final Invoker<TimeDescriptor> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @return true wenn erfolgreich gestoppt sonst false
	 */
	public Boolean initStopwatchStopCommand() {
		final StopwatchStopCommand cmd = new StopwatchStopCommand(this.fReceiver);
		final Invoker<Boolean> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @return true wenn Time-Modus erfolgreich aktiviert wurde sonst false
	 */
	public Boolean initStopwatchToggleTimeModeCommand() {
		final StopwatchToggleTimeModeCommand cmd = new StopwatchToggleTimeModeCommand(this.fReceiver);
		final Invoker<Boolean> invoker = new Invoker<>();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

}
