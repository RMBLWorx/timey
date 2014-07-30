package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;

/**
 * Kommando zum Stoppen der Stoppuhr.
 *
 * @author mmatthies
 */
class StopwatchStopCommand implements ICommand {
	/**
	 * Referenz auf die Empfaengerimplementierung.
	 */
	private final IStopwatch fReceiver;

	/**
	 * Konstruktor.
	 *
	 * @param receiver
	 *            Referenz auf die Empfaengerimplementierung die von diesem Kommando gesteuert wird.
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
	@Override
	public Boolean execute() {
		return this.fReceiver.stopStopwatch();
	}
}
