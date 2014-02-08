/**
 * 
 */
package rmblworx.tools.timey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PatternBox: "Receiver" implementation.
 * <ul>
 * <li>knows how to perform the operations associated with carrying out a
 * request. Any class may serve as a Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class Stopwatch implements IStopwatch {
	private final Logger log = LogManager.getLogger(Stopwatch.class);

	/**
	 * This construtor creates a Receiver instance.
	 */
	public Stopwatch() {
		super();
	}

	@Override
	public Boolean startStopwatch() {
		// TODO Auto-generated method stub
		this.log.entry();
		
		// TODO Write your action code here ...
		System.out.println("startStopwatch");
		
		this.log.exit();
		return Boolean.TRUE;
	}

	@Override
	public Boolean stopStopwatch() {
		// TODO Auto-generated method stub
		this.log.entry();
		
		// TODO Write your action code here ...
		System.out.println("stopStopwatch");
		
		this.log.exit();
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean resetStopwatch(){
		this.log.entry();
		
		// TODO Write your action code here ...
		System.out.println("resetStopwatch");
		
		this.log.exit();
		return Boolean.TRUE;
	}

}
