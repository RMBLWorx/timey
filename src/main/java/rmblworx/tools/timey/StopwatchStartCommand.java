/**
 * 
 */
package rmblworx.tools.timey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.vo.TimeDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptorTest;

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
public class StopwatchStartCommand implements ICommand {
	private final Logger log = LogManager
			.getLogger(StopwatchStartCommand.class);

	/** stores the Receiver instance of the ConcreteCommand */
	private final Stopwatch fReceiver;

	/**
	 * Constructor
	 */
	public StopwatchStartCommand(Stopwatch receiver) {
		super();
		fReceiver = receiver;
	}

	/**
	 * This method executes the command by invoking the corresponding method of
	 * the Receiver instance.
	 */
	public <T> T execute() {
		this.log.entry();

		this.log.exit();
		return (T)fReceiver.startStopwatch();


		// return (T) Boolean.TRUE;
	}

}
