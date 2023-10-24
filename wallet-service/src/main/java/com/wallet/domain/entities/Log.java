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
    public enum InfoLevels {
        INFO,
        WARNING,
        ERROR
    }
    private long id;
    private Date timestamp;
    private String action;
    private InfoLevels infoLevel;
}
