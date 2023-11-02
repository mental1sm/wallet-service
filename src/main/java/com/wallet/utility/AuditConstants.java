package com.wallet.utility;

import com.wallet.domain.entities.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuditConstants {

    private final Map<String, AuditFunction> auditMap;

    interface AuditFunction {
        AuditNode apply();
    }

    @AllArgsConstructor
    @Builder
    public static class AuditNode {
        public Log.Actions action;
        public String actionDesc;
    }

    public AuditConstants() {
        auditMap = new HashMap<>();

        auditMap.put("getAuthToken", ()-> AuditNode.builder()
                .action(Log.Actions.auth)
                .actionDesc("авторизация")
                .build());

        auditMap.put("registerAndGetToken", ()-> AuditNode.builder()
                .action(Log.Actions.reg)
                .actionDesc("регистрация")
                .build());

        auditMap.put("getAllWalletsOfUser", ()-> AuditNode.builder()
                .action(Log.Actions.get_wallet)
                .actionDesc("просмотр всех кошельков")
                .build());

        auditMap.put("getWalletInfo", ()-> AuditNode.builder()
                .action(Log.Actions.get_wallet)
                .actionDesc("переход на кошелек")
                .build());

        auditMap.put("getTransactionHistory", ()-> AuditNode.builder()
                .action(Log.Actions.get_transactions)
                .actionDesc("Просмотр истории транзакций")
                .build());

        auditMap.put("processNewTransaction", ()-> AuditNode.builder()
                .action(Log.Actions.initiate_transaction)
                .actionDesc("Создание транзакции")
                .build());
    }

    /**
     * Получить константу аудита
     * @param methodName имя метода, для которого применяется аудит
     * @return нода из двух элементов - enum действие и String описание
    */
    public AuditNode getAuditConstant(String methodName) {
        AuditFunction function = auditMap.get(methodName);
        if (function != null) {
            return function.apply();
        }
        return null;
    }
}
