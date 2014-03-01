package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.ITimeDescriptor;

interface IAlarmClient {

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initSetTimeCommand(ITimeDescriptor td);

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initTurnOffCommand();

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initTurnOnCommand();

}
