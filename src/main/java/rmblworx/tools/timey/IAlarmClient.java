package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.ITimeDescriptor;

interface IAlarmClient {

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	<T> T initSetTimeCommand(ITimeDescriptor td);

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	<T> T initTurnOffCommand();

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	<T> T initTurnOnCommand();

}
