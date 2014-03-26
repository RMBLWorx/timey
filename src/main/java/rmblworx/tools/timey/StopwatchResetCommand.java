package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;

/**
 * Kommando zum Zuruecksetzen der Stoppuhr.
 * 
 * @author mmatthies
 */
public class StopwatchResetCommand implements ICommand {

	/**
	 * Speichert die Empfaenger-Instanz.
	 */
	private final IStopwatch fReceiver;

	/**
	 * Konstruktor.
	 * 
	 * @param receiver
	 *            Referenz auf die Empfaengerimplementierung die von diesem Kommando gesteuert wird.
	 */
	public StopwatchResetCommand(final IStopwatch receiver) {
		if (receiver == null) {
			throw new NullArgumentException();
		}
		this.fReceiver = receiver;
	}

	/**
	 * @return true wenn erfolgreich zurueckgesetzt werden konnte sonst false
	 */
	@Override
	public Boolean execute() {
		return this.fReceiver.resetStopwatch();
	}
}
