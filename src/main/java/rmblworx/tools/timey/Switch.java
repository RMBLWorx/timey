/**
 * 
 */
package rmblworx.tools.timey;

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
	/** stores the Command instance of the Invoker */
	private ICommand fCommand;

	private static final Logger LOG = LogManager.getLogger(Switch.class);

	/**
	 * Default constructor
	 */
	public Switch() {
		super();
	}

	/**
	 * Constructor
	 */
	public Switch(final ICommand cmd) {
		super();
		this.fCommand = cmd;
	}

	/**
	 * This method performs the actions associated with the ConcreteCommand
	 * instance.
	 */
	public <T> T execute() {
		LOG.entry();

		LOG.exit();
		return (T) this.fCommand.execute();

	}

	/**
	 * This method stores a ConcreteCommand instance.
	 */
	public void storeCommand(final ICommand cmd) {
		LOG.entry();

		this.fCommand = cmd;

		LOG.exit();
	}

}
