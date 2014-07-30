package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;

/**
 * Kommandoimplementierung zum stoppen eines Countdowns.
 *
 * @author "mmatthies"
 */
class CountdownStopCommand implements ICommand {

	/**
	 * Referenz der Empfaengerimplementierung.
	 */
	private final ICountdown fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz der Empfaengerimplementierung
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
	@Override
	public Boolean execute() {
		return this.fReceiver.stopCountdown();
	}
}
