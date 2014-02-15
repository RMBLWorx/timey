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
public class StopwatchClient {
	/** stores the Receiver instance of the Client */
	private final Stopwatch fReceiver;

	private final Logger log = LogManager.getLogger(StopwatchClient.class);

	/**
	 * This construtor creates a Client instance and stores the given
	 * Receiver.
	 */
	public StopwatchClient(final Stopwatch receiver) {
		super();
		this.fReceiver = receiver;
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 */
	public final <T> T initStopwatchResetCommand() {
		this.log.entry();

		final StopwatchResetCommand cmd = new StopwatchResetCommand(this.fReceiver);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 */
	public final <T> T initStopwatchStartCommand() {
		this.log.entry();

		final StopwatchStartCommand cmd = new StopwatchStartCommand(this.fReceiver);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 */
	public final <T> T initStopwatchStopCommand() {
		this.log.entry();

		final StopwatchStopCommand cmd = new StopwatchStopCommand(this.fReceiver);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();

		return invoker.execute();
	}

}
