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
	private final Logger log = LogManager.getLogger(StopwatchClient.class);

	/** stores the Receiver instance of the Client */
	private final Stopwatch fReceiver;

	/**
	 * This construtor creates a Client instance and stores the given Receiver.
	 */
	public StopwatchClient(Stopwatch receiver) {
		super();
		fReceiver = receiver;
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initStopwatchStartCommand() {
		this.log.entry();

		StopwatchStartCommand cmd = new StopwatchStartCommand(fReceiver);
		Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initStopwatchStopCommand() {
		this.log.entry();

		StopwatchStopCommand cmd = new StopwatchStopCommand(fReceiver);
		Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initStopwatchResetCommand() {
		this.log.entry();

		StopwatchResetCommand cmd = new StopwatchResetCommand(fReceiver);
		Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();

		return invoker.execute();
	}

}
