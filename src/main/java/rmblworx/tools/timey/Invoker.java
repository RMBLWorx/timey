/**
 * 
 */
package rmblworx.tools.timey;

/**
 * PatternBox: "Invoker" implementation.
 * <ul>
 * <li>asks the command to carry out the request.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
public class Invoker {
	/** stores the Command instance of the Invoker */
	private ICommand fCommand;

	/**
	 * Default constructor
	 */
	public Invoker() {
		super();
	}

	/**
	 * Constructor
	 */
	public Invoker(final ICommand cmd) {
		super();
		this.fCommand = cmd;
	}

	/**
	 * This method performs the actions associated with the ConcreteCommand
	 * instance.
	 */
	public <T> T execute() {

		return (T) this.fCommand.execute();

	}

	/**
	 * This method stores a ConcreteCommand instance.
	 */
	public void storeCommand(final ICommand cmd) {

		this.fCommand = cmd;

	}

}
