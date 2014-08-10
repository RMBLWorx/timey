/**
 *
 */
package ch.nydi.aop.interceptor;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author mmatthies
 *
 */
public abstract class AbstractInterceptor {
	String flattenArgument(Object argument) {
		if (null == argument) {
			return "null";
		}

		if (argument instanceof Object[]) {
			if (ArrayUtils.isEmpty((Object[]) argument)) {
				return "no arguments";
			}
			return StringUtils.join((Object[]) argument, ", ");
		}
		return argument.toString();
	}
}
