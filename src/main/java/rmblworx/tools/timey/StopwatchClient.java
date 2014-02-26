/**
 * 
 */
package rmblworx.tools.timey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PatternBox: "Client" implementation.
 * <ul>
 * <li>creates a ConcreteCommand object and sets its receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class StopwatchClient {
	/**
	 * Logger.
	 */
	private static final Logger LOG = LogManager.getLogger(StopwatchClient.class);

	/**
	 * stores the Receiver instance of the Client.
	 * */
	private final Stopwatch fReceiver;

	/**
	 * This construtor creates a Client instance and stores the given
	 * Receiver.
	 * @param receiver Referenz auf die Implementierung die dieser Client steuern soll.
	 */
	public StopwatchClient(final Stopwatch receiver) {
		super();
		this.fReceiver = receiver;
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 * @return Das Ergebnis wird vom implementierten Kommando festgelegt.
	 */
	public <T> T initStopwatchResetCommand() {
		LOG.entry();

		final StopwatchResetCommand cmd = new StopwatchResetCommand(this.fReceiver);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		LOG.exit();

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 * @return Das Ergebnis wird vom implementierten Kommando festgelegt.
	 */
	public final <T> T initStopwatchStartCommand() {
		LOG.entry();

		final StopwatchStartCommand cmd = new StopwatchStartCommand(this.fReceiver);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		LOG.exit();

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 * @return Das Ergebnis wird vom implementierten Kommando festgelegt.
	 */
	public final <T> T initStopwatchStopCommand() {
		LOG.entry();

		final StopwatchStopCommand cmd = new StopwatchStopCommand(this.fReceiver);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		LOG.exit();

		return invoker.execute();
	}

}
