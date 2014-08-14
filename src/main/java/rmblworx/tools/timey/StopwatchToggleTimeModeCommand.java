/**
 *
 */
package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.NullArgumentException;

/**
 * @author mmatthies
 *
 */
public class StopwatchToggleTimeModeCommand implements ICommand{
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
	public StopwatchToggleTimeModeCommand(final IStopwatch receiver) {
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
		return this.fReceiver.toggleTimeModeInStopwatch();
	}
}
