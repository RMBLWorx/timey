/**
 * 
 */
package rmblworx.tools.timey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.vo.TimeDescriptor;

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
final class SetTimeCommand implements ICommand {
	/** stores the Receiver instance of the ConcreteCommand */
	private final Alarm fReceiver;

	private final Logger log = LogManager.getLogger(SetTimeCommand.class);
	private final TimeDescriptor td;

	/**
	 * Constructor
	 */
	public SetTimeCommand(final Alarm receiver, final TimeDescriptor td) {
		super();
		this.fReceiver = receiver;
		this.td = td;
	}

	/**
	 * This method executes the command by invoking the corresponding method
	 * of the Receiver instance.
	 * 
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T execute() {
		this.log.entry();
		this.log.debug("Führe SetTimeCommand aus!");
		this.fReceiver.setAlarmTime(this.td);
		this.log.exit();

		return (T) this.td;

	}

}
