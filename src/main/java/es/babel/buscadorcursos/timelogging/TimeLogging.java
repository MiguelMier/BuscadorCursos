package es.babel.buscadorcursos.timelogging;

import es.babel.buscadorcursos.utils.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeLogging {


    @Around("execution(* es.babel.buscadorcursos.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // Log the execution time
        LogUtils.logInfo("Execution time of " + joinPoint.getSignature() + ": " + totalTime + " nanoseconds");

        return result;
    }
}
