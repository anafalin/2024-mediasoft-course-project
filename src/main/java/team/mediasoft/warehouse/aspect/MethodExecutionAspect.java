package team.mediasoft.warehouse.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MethodExecutionAspect {

    @Around("@annotation(team.mediasoft.warehouse.annotation.TimeExecutionMark)")
    public Object measureExecutionMethod(ProceedingJoinPoint jp) {
        long startTime = System.currentTimeMillis();

        String signatureMethod = jp.getSignature().toLongString();
        Object[] argsMethod = jp.getArgs();

        log.info("Started method {} ({})", signatureMethod, argsMethod);
        Object proceed;
        try {
            proceed = jp.proceed();
        } catch (Throwable ex) {
            log.info("Finished with exception {} in {} ({}) in {} s",
                    ex, signatureMethod, argsMethod, (System.currentTimeMillis() - startTime) / 1000.000);
            return null;
        }

        log.info("Finished method {} ({}) in {} s",
                signatureMethod, argsMethod, (System.currentTimeMillis() - startTime) / 1000.000);

        return proceed;
    }
}
