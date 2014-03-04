/**
 * 
 */
package rmblworx.tools.timey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PatternBox: "ConcreteCommand" implementation.
 * <ul>
 * <li>defines a binding between a Receiver object and an action.</li>
 * <li>implements Execute by invoking the corresponding operation(s) on Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class StopwatchResetCommand implements ICommand {
	/**
	 * Logger.
	 */
	private static final Logger LOG = LogManager.getLogger(StopwatchResetCommand.class);

	/**
	 * stores the Receiver instance of the ConcreteCommand.
	 * */
	private final Stopwatch fReceiver;

	/**
	 * Constructor.
	 */
	public StopwatchResetCommand(final Stopwatch receiver) {
		super();
		this.fReceiver = receiver;
	}

	/**
	 * This method executes the command by invoking the corresponding method
	 * of the Receiver instance.
	 * 
	 * @return
	 */
	@Override
	public <T> T execute() {
		LOG.entry();

		this.fReceiver.resetStopwatch();

		LOG.exit();

		return (T) Boolean.TRUE;
	}

}
