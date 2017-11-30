package com.github.habiteria.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alex Ivchenko
 */
@Slf4j
@Aspect
@Component
@Profile({"dev-real-db", "dev-embedded-db"})
public class LoggingAspect {
    @Pointcut("execution(public * com.github.habiteria.core.domain.service..*.*(..))")
    public void coreDomainLogic() {

    }

    @Pointcut("execution(public * com.github.habiteria.integration.domain.service..*.*(..))")
    public void integrationDomainLogic() {

    }

    @Pointcut("execution(public * com.github.habiteria.security..*.*(..))")
    public void security() {

    }

    @Before("coreDomainLogic() || integrationDomainLogic() || security()")
    public void logBefore(JoinPoint jp) {
        log.info("invoke {}", lazyFormatMethodSignature(jp));
    }

    @AfterReturning(value = "coreDomainLogic() || integrationDomainLogic() || security()", returning = "ret")
    public void logAfterReturning(JoinPoint jp, Object ret) {
        log.info("method {} returned: {}", lazyFormatMethodSignature(jp), ret);
    }

    @AfterThrowing(value = "coreDomainLogic() || integrationDomainLogic() || security()", throwing = "thr")
    public void logAfterReturning(JoinPoint jp, Throwable thr) {
        log.error("method {} thrown: {}", lazyFormatMethodSignature(jp), thr);
    }

    private Object lazyFormatMethodSignature(JoinPoint jp) {
        return new Object() {
            @Override
            public String toString() {
                return formatMethodSignature(jp);
            }
        };
    }

    private String formatMethodSignature(JoinPoint jp) {
        String method = jp.getSignature().toShortString();
        String args = fetchArgs(jp);
        return method.replace("..", args);
    }

    private String fetchArgs(JoinPoint jp) {
        List<String> args = Stream.of(jp.getArgs())
                .map(String::valueOf)
                .collect(Collectors.toList());
        return String.join(", ", args);
    }
}