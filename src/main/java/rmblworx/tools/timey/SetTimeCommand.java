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
 * <li>implements Execute by invoking the corresponding operation(s) on
 * Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class SetTimeCommand implements ICommand {
	private final Logger log = LogManager.getLogger(SetTimeCommand.class);

	/** stores the Receiver instance of the ConcreteCommand */
	private final Alarm fReceiver;
	private TimeDescriptor td;

	/**
	 * Constructor
	 */
	public SetTimeCommand(Alarm receiver, TimeDescriptor td) {
		super();
		fReceiver = receiver;
		this.td = td;
	}

	/**
	 * This method executes the command by invoking the corresponding method of
	 * the Receiver instance.
	 * 
	 * @param <T>
	 * @return
	 */
	public <T> T execute() {
		this.log.entry();
		this.log.debug("Führe SetTimeCommand aus!");
		fReceiver.setAlarmTime(td);
		this.log.exit();

		return (T) td;

	}

}
