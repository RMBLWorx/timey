/**
 * 
 */
package rmblworx.tools.timey.advices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author mmatthies
 */
public class Log4j2Advice {
	/**
	 * Logger.
	 */
	private static final Logger log = LogManager.getLogger(Log4j2Advice.class);


	public void toTraceAround(ProceedingJoinPoint pjp) {
		StringBuilder sb = new StringBuilder();

		sb.append("betrete... " + pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName());
		sb.append("(");
		Object[] obj = pjp.getArgs();
		for (int i = 0; i < obj.length; i++) {
			sb.append("'");
			sb.append(obj[i].toString());
			sb.append("'");
			if (i < obj.length - 1) {
				sb.append(", ");
			}
		}

		sb.append(")");
		log.trace(sb.toString());
		sb = sb.delete(0, sb.length());

		try {
			pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		sb.append("verlasse..." + pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + "()");

		log.trace(sb.toString());
	}

	public void toTraceEntry() {
		log.trace("betrete ---------------->");
	}

	public void toTraceExit() {
		log.trace("<---------------- verlasse");
	}
}
