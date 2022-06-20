package com.daveayan.scalingwinner.greeting.fault;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Aspect
@Component
public class FaultInjectionAspect {
    private static final Logger LOG = LoggerFactory.getLogger(FaultInjectionAspect.class);

    public FaultInjectionAspect() {
        LOG.trace("IN FaultInjectionAspect constructor");
        LOG.trace("OUT FaultInjectionAspect constructor");
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object aroundAnyPublicOperation(ProceedingJoinPoint pjp) throws Throwable {
        LOG.trace("IN aroundAnyPublicOperation");

        HttpHeaders headers = (HttpHeaders) getParameter(pjp, "HttpHeaders");
        if(headers != null) {
            List<String> headerValues = headers.get("da-inject");
            if(headerValues != null && headerValues.size() == 1) {
                String headerValue = headerValues.get(0);
                if(headerValue.equalsIgnoreCase("404")) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFoundException");
                }
                if(headerValue.equalsIgnoreCase("503")) {
                    throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE");
                }
            }
        }
        
        Object returnObject = pjp.proceed();
        
        LOG.trace("OUT aroundAnyPublicOperation");
        return returnObject;
    }

    private Object getParameter(ProceedingJoinPoint pjp, String parameterType) {
        Object[] parameters = pjp.getArgs();
        for (int i = 0; i < parameters.length; i++) {
            Object param = parameters[i];
            String simpleName = param.getClass().getSimpleName();
            if(simpleName.equalsIgnoreCase(parameterType)) {
                return param;
            }
        }
        return null;
    }
}
