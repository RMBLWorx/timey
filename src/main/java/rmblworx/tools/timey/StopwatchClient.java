package rmblworx.tools.timey;

/**
 * Erzeugt die konkreten Kommandoimplementierungen und setzt deren Empfaengerimplementierungen.
 *
 * @author mmatthies
 */
class StopwatchClient {

	/**
	 * Referenz auf die Empfaengerimplementierung.
	 */
	private final IStopwatch fReceiver;

	/**
	 * Erzeugt eine Instanz dieses Klients und speichert die Referenz auf die Empfaengerimplementierung.
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
	public <T> T initStopwatchResetCommand() {
		final StopwatchResetCommand cmd = new StopwatchResetCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @return Zeitwertobjekt das die gemessene Zeit kapselt.
	 */
	public <T> T initStopwatchStartCommand() {
		final StopwatchStartCommand cmd = new StopwatchStartCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @return true wenn erfolgreich gestoppt sonst false
	 */
	public <T> T initStopwatchStopCommand() {
		final StopwatchStopCommand cmd = new StopwatchStopCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

}
