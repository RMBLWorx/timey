/**
 * 
 */
package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "ConcreteCommand" implementation.
 * <ul>
 *   <li>defines a binding between a Receiver object and an action.</li>
 *   <li>implements Execute by invoking the corresponding operation(s) on Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author "mmatthies"
 */
public class CountdownSetTimeCommand implements ICommand {

	/** stores the Receiver instance of the ConcreteCommand */
	private final ICountdown fReceiver;
	private final TimeDescriptor timeDescriptor;

	/**
	 * Constructor
	 */
	public CountdownSetTimeCommand(final ICountdown receiver, final TimeDescriptor descriptor) {
		super();
		if (receiver == null || descriptor == null) {
			throw new IllegalArgumentException("References on null are not permitted!");
		}
		this.fReceiver = receiver;
		this.timeDescriptor = descriptor;
	}

	/**
	 * This method executes the command by invoking the corresponding
	 * method of the Receiver instance.
	 */
	@Override
	public <T> T execute() {
		return (T) this.fReceiver.setCountdownTime(this.timeDescriptor);
	}

}
