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
    private long id;
    private String login;
    private Date timestamp;
    private String action;
    private String description;
}
