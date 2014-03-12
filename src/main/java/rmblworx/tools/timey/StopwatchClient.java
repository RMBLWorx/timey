/**
 * 
 */
package rmblworx.tools.timey;

/**
 * PatternBox: "Client" implementation.
 * <ul>
 * <li>creates a ConcreteCommand object and sets its receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
public class StopwatchClient {
	/**
	 * stores the Receiver instance of the Client.
	 */
	private final IStopwatch fReceiver;

	/**
	 * This construtor creates a Client instance and stores the given
	 * Receiver.
	 * 
	 * @param receiver
	 *            Referenz auf die Implementierung die dieser Client steuern soll.
	 */
	public StopwatchClient(final IStopwatch receiver) {
		super();
		this.fReceiver = receiver;
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 * 
	 * @return Das Ergebnis wird vom implementierten Kommando festgelegt.
	 */
	public <T> T initStopwatchResetCommand() {

		final StopwatchResetCommand cmd = new StopwatchResetCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 * 
	 * @return Das Ergebnis wird vom implementierten Kommando festgelegt.
	 */
	public <T> T initStopwatchStartCommand() {
		final StopwatchStartCommand cmd = new StopwatchStartCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a
	 * Receiver object.
	 * 
	 * @return Das Ergebnis wird vom implementierten Kommando festgelegt.
	 */
	public <T> T initStopwatchStopCommand() {
		final StopwatchStopCommand cmd = new StopwatchStopCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}
}
