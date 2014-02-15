/**
 *
 */
package rmblworx.tools.timey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Client" implementation.
 * <ul>
 * <li>creates a ConcreteCommand object and sets its receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class AlarmClient {
	/** stores the Receiver instance of the Client */
	private final Alarm fReceiver;

	private final Logger log = LogManager.getLogger(AlarmClient.class);

	/**
	 * This construtor creates a Client instance and stores the given Receiver.
	 */
	public AlarmClient(final Alarm receiver) {
		super();
		this.fReceiver = receiver;
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initSetTimeCommand(final TimeDescriptor td) {
		this.log.entry();

		final SetTimeCommand cmd = new SetTimeCommand(this.fReceiver, td);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initTurnOffCommand() {
		this.log.entry();

		final TurnOffCommand cmd = new TurnOffCommand(this.fReceiver);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();
		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initTurnOnCommand() {
		this.log.entry();

		final TurnOnCommand cmd = new TurnOnCommand(this.fReceiver);
		final Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();
		return invoker.execute();
	}

}
