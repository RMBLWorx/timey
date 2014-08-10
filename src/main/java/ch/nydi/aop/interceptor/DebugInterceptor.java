/*
 * Modified by mmatthies.
 * Copyright 2011 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.nydi.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Writes method arguments, return value and the invocation duration to the log output if TRACE severity is enabled.
 * Mainly this class is to show the usage of {@link Interceptors} that chains interceptors, use
 * {@link org.springframework.aop.interceptor.DebugInterceptor} for real debug purposes.
 *
 * @author Daniel Nydegger
 */
public class DebugInterceptor extends AbstractInterceptor implements MethodInterceptor {

	private final Logger logger = LoggerFactory.getLogger(DebugInterceptor.class);

	private final TimerInterceptor timerInterceptor = new TimerInterceptor();
	private final TraceInterceptor traceInterceptor = new TraceInterceptor();

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		if (this.logger.isTraceEnabled()) {
			return Interceptors.create(this.traceInterceptor, this.timerInterceptor, new MethodInterceptor() {

				@Override
				public Object invoke(MethodInvocation invocation) throws Throwable {
					// TODO Verletzung DRY-Prinzip: gesamter Inhalt dieser Methode nahezu identisch mit TraceInterceptor
					final String prefix = "invocation arguments of " + invocation.getMethod().getName() + " ";
					final Object[] arguments = invocation.getArguments();
					if (ArrayUtils.isEmpty(arguments)) {
						DebugInterceptor.this.logger.debug(prefix + "{no arguments}");
					} else {
						final StringBuilder flattenArguments = new StringBuilder();
						flattenArguments.append("{");
						for (int i = 0; i < arguments.length; i++) {
							if (i > 0) {
								flattenArguments.append(" | ");
							}
							flattenArguments.append(DebugInterceptor.this.flattenArgument(arguments[i]));
						}
						flattenArguments.append("}");
						DebugInterceptor.this.logger.debug(prefix + flattenArguments.toString());
					}
					// DebugInterceptor.this.logger.info("Greetings from a anonymous interceptor");
					return invocation.proceed();
				}
			}).invoke(invocation);
		}

		return invocation.proceed();
	}
}
