/**
 * 
 */
package rmblworx.tools.timey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PatternBox: "ConcreteCommand" implementation.
 * <ul>
 *   <li>defines a binding between a Receiver object and an action.</li>
 *   <li>implements Execute by invoking the corresponding operation(s) on Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
public class StopwatchResetCommand implements ICommand {
	private final Logger log = LogManager.getLogger(StopwatchResetCommand.class);

	/** stores the Receiver instance of the ConcreteCommand */
	private final Stopwatch fReceiver;

	/** 
	 * Constructor
	 */
	public StopwatchResetCommand(Stopwatch receiver) {
		super();
		fReceiver = receiver;
	}

	/** 
	 * This method executes the command by invoking the corresponding
	 * method of the Receiver instance.
	 * @return 
	 */
	public <T> T execute() {
		this.log.entry();

		fReceiver.resetStopwatch();

		this.log.exit();

		return (T) Boolean.TRUE;
	}

}
