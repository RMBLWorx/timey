/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.nydi.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Daniel Nydegger
 */
public class CompositeInterceptor
    implements MethodInterceptor, InitializingBean {

    private MethodInterceptor[] interceptors = new MethodInterceptor[0];
    private MethodInterceptor interceptorChain = Interceptors.EMPTY;

    @Override
    public Object invoke(MethodInvocation invocation)
        throws Throwable {
        return interceptorChain.invoke(invocation);
    }

    public void setInterceptors(MethodInterceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public void afterPropertiesSet()
        throws Exception {
        interceptorChain = Interceptors.create(interceptors);
    }
}
