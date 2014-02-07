/**
 * 
 */
package rmblworx.tools.timey.bo.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PatternBox: "ConcreteCommand" implementation.
 * <ul>
 * <li>defines a binding between a Receiver object and an action.</li>
 * <li>implements Execute by invoking the corresponding operation(s) on
 * Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class TurnOffCommand implements ICommand {
	private final Logger log = LogManager.getLogger(TurnOffCommand.class);

	/** stores the Receiver instance of the ConcreteCommand */
	private final Alarm fReceiver;

	/**
	 * Constructor
	 */
	public TurnOffCommand(Alarm receiver) {
		super();
		fReceiver = receiver;
	}

	/**
	 * This method executes the command by invoking the corresponding method of
	 * the Receiver instance.
	 * 
	 * @param <T>
	 * @return
	 */
	public <T> T execute() {
		this.log.entry();

		fReceiver.turnOff();

		this.log.exit();

		return (T) Boolean.TRUE;
	}

}
