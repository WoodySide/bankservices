package com.woodyside.notification.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LogControllerAspect {

    @Pointcut(value = "within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {}


    @Before(value = "controller()")
    public void beforeController(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("Controller {} received HTTP request: {} {}",
                 joinPoint.getSignature().getDeclaringTypeName(),
                 request.getMethod(),
                 request.getRequestURI());
        log.info("Attributes, processing by a controller:  {}", Arrays.toString(joinPoint.getArgs()));
    }

    @Around(value = "controller()")
    public Object secure(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch clock = new StopWatch(joinPoint.toString());
        try {
            clock.start(joinPoint.toShortString());
            return joinPoint.proceed();
        } finally {
            clock.stop();
            log.info(clock.prettyPrint());
        }
    }

    @AfterReturning(
            pointcut = "controller()",
            returning = "result"
    )
    public void afterController(JoinPoint joinPoint, Object result) {

        log.info("Controller {} returned  {} ",
                joinPoint.getSignature().getDeclaringTypeName(),
                result);
    }
}


