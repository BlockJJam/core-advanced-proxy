package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LogTraceAspect {
    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // 밑에 주석 2개 합치면 어드바이저가 됨
    // @Around 포인트컷
    @Around("execution(* hello.proxy.app ..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        // 어드바이스
        // 규칙: invocation처럼 joinPoint 안에 다 들어있다 메서드나 클래스 들이
        TraceStatus status = null;

        try{
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // 로직 호출
            Object result = joinPoint.proceed();
            logTrace.end(status);

            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
