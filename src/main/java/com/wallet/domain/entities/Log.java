package com.wallet.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * Модель лога
*/
@Getter
@AllArgsConstructor
@Builder
public class Log {
    public enum Actions {
        auth,
        reg,
        get_wallets,
        get_wallet,
        get_transactions,
        initiate_transaction
    }
    private long id;
    private String login;
    private Date timestamp;
    private Actions action;
    private String description;
}
