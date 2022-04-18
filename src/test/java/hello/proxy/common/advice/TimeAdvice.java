package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {
    // target을 안 넣어줘도 된다

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

//        Object result = methodProxy.invoke(target, args);
        Object result = invocation.proceed(); // 알아서 target을 찾아서, 그 target에 있는 실체를 인수를 넘겨 실행하게 해준다

        long endTime = System.currentTimeMillis();
        long resultTime= endTime - startTime;

        log.info("TimeProxy 종료 resultTime ={}", resultTime);

        return result;
    }
}
