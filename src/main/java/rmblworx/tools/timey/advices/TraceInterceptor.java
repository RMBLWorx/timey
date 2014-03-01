/**
 * 
 */
package rmblworx.tools.timey.advices;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;

/**
 * @author mmatthies
 *
 */
public class TraceInterceptor extends CustomizableTraceInterceptor{
	/**
	 * Logger.
	 */
	private static final Logger LOG = LogManager.getLogger(TraceInterceptor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -8425517182666528402L;


	/*
	 * Diese Methode musste ueberschrieben werden, da die abstrakte Elternklasse
	 * die Apache Common Logging API verwendet. Obwohl Referenz auf Logger und
	 * korrekte Konfiguration zur Laufzeit vorhanden, gibt die dort
	 * implementierte Logik false zurueck und verhindert somit das Logging per
	 * Log4j.
	 * 
	 *  (non-Javadoc)
	 * @see org.springframework.aop.interceptor.AbstractTraceInterceptor#
	 * isInterceptorEnabled(org.aopalliance.intercept.MethodInvocation,
	 * org.apache.commons.logging.Log)
	 */
	@Override
	protected final boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
		final boolean answer = LOG.isTraceEnabled();

		return answer;
	}
}
