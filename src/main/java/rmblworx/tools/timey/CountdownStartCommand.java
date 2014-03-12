/**
 * 
 */
package rmblworx.tools.timey;


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
public class CountdownStartCommand implements ICommand {

	/** stores the Receiver instance of the ConcreteCommand */
	private final ICountdown fReceiver;

	/**
	 * Constructor
	 */
	public CountdownStartCommand(final ICountdown receiver) {
		super();
		this.fReceiver = receiver;
	}

	/**
	 * This method executes the command by invoking the corresponding
	 * method of the Receiver instance.
	 */
	@Override
	public <T>T execute() {
		return (T) this.fReceiver.startCountdown();
	}

}
