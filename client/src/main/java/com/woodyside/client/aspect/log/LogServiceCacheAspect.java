package com.woodyside.client.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogServiceCacheAspect {

    @Pointcut(value = "@annotation(org.springframework.cache.annotation.Cacheable))")
    public void cacheService() {}

    @Before(value = "cacheService()")
    public void beforeCacheService(JoinPoint joinPoint) {

        log.info("Cache service {} starts executing method {} ",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(
            pointcut = "cacheService()",
            returning = "result"
    )
    public void afterCacheService(JoinPoint joinPoint, Object result) {

        log.info("Cache service method {} returned {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                result);
    }
}
