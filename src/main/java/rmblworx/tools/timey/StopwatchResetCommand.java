/**
 * 
 */
package rmblworx.tools.timey;

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
public class StopwatchResetCommand implements ICommand {
	/**
	 * stores the Receiver instance of the ConcreteCommand.
	 */
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
		this.fReceiver.resetStopwatch();

		return (T) Boolean.TRUE;
	}
}
