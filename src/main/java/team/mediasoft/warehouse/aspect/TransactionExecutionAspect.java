package team.mediasoft.warehouse.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
@Log4j2
public class TransactionExecutionAspect implements TransactionSynchronization {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final ThreadLocal<String> methodName = new ThreadLocal<>();

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerTransactionSynchronization(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        methodName.set(joinPoint.getSignature().toLongString());

        TransactionSynchronizationManager.registerSynchronization(this);
    }

    @Override
    public void afterCompletion(int status) {
        String statusName = switch (status) {
            case 0 -> "COMMITTED";
            case 1 -> "ROLLBACK";
            default -> "UNKNOWN";
        };

        log.info("Transaction in method {} was completed with status {} in {} s",
                methodName.get(), statusName, (System.currentTimeMillis() - startTime.get() / 1000.000));
    }
}