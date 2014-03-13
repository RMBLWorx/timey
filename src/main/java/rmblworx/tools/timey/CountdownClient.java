/**
 * 
 */
package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Client" implementation.
 * <ul>
 *   <li>creates a ConcreteCommand object and sets its receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author "mmatthies"
 */
public class CountdownClient {

	/** stores the Receiver instance of the Client. */
	private final ICountdown fReceiver;

	/**
	 * This construtor creates a Client instance and stores the given Receiver.
	 * @param receiver Empfaenger-Referenz.
	 */
	public CountdownClient(final ICountdown receiver) {
		super();
		this.fReceiver = receiver;
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver object.
	 * @return Werteobjekt das die Countdownzeit kapselt.
	 */
	public TimeDescriptor initCountdownStartCommand() {
		final CountdownStartCommand cmd = new CountdownStartCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}
	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver object.
	 * @return true wenn erfolgreich sonst false.
	 */
	public Boolean initCountdownStopCommand() {
		final CountdownStopCommand cmd = new CountdownStopCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}
	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver object.
	 * @param descriptor Werteobjekt das die Countdownzeit kapselt.
	 * @return true wenn erfolgreich sonst false.
	 */
	public Boolean initSetCountdownTimeCommand(final TimeDescriptor descriptor) {
		final CountdownSetTimeCommand cmd = new CountdownSetTimeCommand(this.fReceiver, descriptor);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

}
