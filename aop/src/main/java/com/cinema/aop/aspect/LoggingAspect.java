package com.cinema.aop.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    @Around(value = "@annotation(com.cinema.aop.annotation.Loggable)")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        StringBuffer beforeLog = new StringBuffer(joinPoint.getSignature().toLongString()).append(" args: ");

        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(obj -> beforeLog.append(obj).append(","));
        if(beforeLog.length() > 0)
            beforeLog.setLength(beforeLog.length() - 1);

        LOGGER.debug(beforeLog);

        Object joinObject = joinPoint.proceed();;

        StringBuffer afterLog = new StringBuffer(joinPoint.getSignature().toLongString()).append(" return: ").append(joinObject);
        LOGGER.debug(afterLog);

        return joinObject;
    }
}
