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
	private final Logger log = LogManager.getLogger(AlarmClient.class);

	/** stores the Receiver instance of the Client */
	private final Alarm fReceiver;

	/**
	 * This construtor creates a Client instance and stores the given Receiver.
	 */
	public AlarmClient(Alarm receiver) {
		super();
		fReceiver = receiver;
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initSetTimeCommand(TimeDescriptor td) {
		this.log.entry();

		SetTimeCommand cmd = new SetTimeCommand(fReceiver, td);
		Switch invoker = new Switch();
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

		TurnOffCommand cmd = new TurnOffCommand(fReceiver);
		Switch invoker = new Switch();
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

		TurnOnCommand cmd = new TurnOnCommand(fReceiver);
		Switch invoker = new Switch();
		invoker.storeCommand(cmd);

		this.log.exit();
		return invoker.execute();
	}

}
