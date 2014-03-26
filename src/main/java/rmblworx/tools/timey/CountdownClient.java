package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Erzeugt die konkreten Kommandoimplementierungen und setzt deren Empfaengerimplementierungen.
 * 
 * @author "mmatthies"
 */
public class CountdownClient {

	/** Speichert die Empfaenger Instanz des Klient. */
	private final ICountdown fReceiver;

	/**
	 * Erzeugt eine Klient-Instanz und speichert die uebergebene Empfaengerimplementierung.
	 * 
	 * @param receiver
	 *            Empfaenger-Referenz.
	 */
	public CountdownClient(final ICountdown receiver) {
		this.fReceiver = receiver;
	}

	/**
	 * @return Werteobjekt das die Countdownzeit kapselt.
	 */
	public TimeDescriptor initCountdownStartCommand() {
		final CountdownStartCommand cmd = new CountdownStartCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @return true wenn erfolgreich sonst false.
	 */
	public Boolean initCountdownStopCommand() {
		final CountdownStopCommand cmd = new CountdownStopCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * @param descriptor
	 *            Werteobjekt das die Countdownzeit kapselt.
	 * @return true wenn erfolgreich sonst false.
	 */
	public Boolean initSetCountdownTimeCommand(final TimeDescriptor descriptor) {
		final CountdownSetTimeCommand cmd = new CountdownSetTimeCommand(this.fReceiver, descriptor);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

}
