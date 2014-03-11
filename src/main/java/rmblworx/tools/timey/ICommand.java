package rmblworx.tools.timey;

/**
 * PatternBox: "Command" implementation.
 * <ul>
 * <li>declares an interface for executing an operation.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
interface ICommand {

	/**
	 * This abstract method must be implemented by the ConcreteCommand
	 * implementation.
	 * 
	 * @param <T>
	 */
	<T> T execute();

}
