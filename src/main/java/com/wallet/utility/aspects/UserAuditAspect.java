package com.wallet.utility.aspects;


import com.wallet.domain.dto.user.UserAuthDTO;
import com.wallet.domain.dto.user.UserDTO;
import com.wallet.domain.dto.user.UserRegistrationDTO;
import com.wallet.domain.entities.Log;
import com.wallet.service.audit.AuditService;
import com.wallet.utility.AuditConstants;
import com.wallet.utility.TokenContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAuditAspect {

    private final AuditService auditService;
    private final AuditConstants auditConstants;
    private final TokenContext tokenContext;

    /**
     * Регистрирует анонимное действие в журнал аудита
    */
    @Around("@annotation(com.wallet.utility.aspects.AuditRaw)")
    public Object auditGetInfoFromRaw(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        UserDTO userDTO = (UserDTO) args[0];

        anonymousLog(methodName);
        return joinPoint.proceed();
    }

    /**
     * Регистрирует действие пользователя в журнал аудита
     */
    @Around("@annotation(com.wallet.utility.aspects.AuditToken)")
    public Object auditGetInfoFromToken(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String authHeader = (String) args[0];
        String login = tokenContext.getLoginFromContext(authHeader);

        log(login, methodName);
        return joinPoint.proceed();
    }

    private void anonymousLog(String methodName) {
        AuditConstants.AuditNode node = auditConstants.getAuditConstant(methodName);
        Log.Actions action = node.action;
        String actionDescription = node.actionDesc;
        String description = String.format(
                "Неизвестный пользователь попытался выполнить действие '%s' для аккаунта с логином %s",
                actionDescription,
                "anonymous"
        );

        auditService.logThis("anonymous", action, description);
    }

    private void log(String login, String methodName) {
        AuditConstants.AuditNode node = auditConstants.getAuditConstant(methodName);
        Log.Actions action = node.action;
        String actionDescription = node.actionDesc;
        String description = String.format(
                "Пользователь %s попытался выполнить действие '%s'",
                login,
                actionDescription
        );

        auditService.logThis(login, action, description);
    }
}
