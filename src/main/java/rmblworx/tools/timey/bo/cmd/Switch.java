/**
 * 
 */
package rmblworx.tools.timey.bo.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PatternBox: "Invoker" implementation.
 * <ul>
 * <li>asks the command to carry out the request.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class Switch {
	private final Logger log = LogManager.getLogger(Switch.class);

	/** stores the Command instance of the Invoker */
	private ICommand fCommand;

	/**
	 * Default constructor
	 */
	public Switch() {
		super();
	}

	/**
	 * Constructor
	 */
	public Switch(ICommand cmd) {
		super();
		fCommand = cmd;
	}

	/**
	 * This method stores a ConcreteCommand instance.
	 */
	public void storeCommand(ICommand cmd) {
		this.log.entry();

		fCommand = cmd;

		this.log.exit();
	}

	/**
	 * This method performs the actions associated with the ConcreteCommand
	 * instance.
	 */
	public <T> T execute() {
		this.log.entry();

		this.log.exit();
		return (T) fCommand.execute();

	}

}
