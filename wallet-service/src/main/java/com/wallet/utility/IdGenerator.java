package com.wallet.utility;

import java.util.UUID;


public class IdGenerator {
    /**
     * Предоставляет случайно сгенерированную строку, состоящую из 16 цифер.
     * @return Случайно сгенерированный ID в виде строки
     */
    public static UUID genId() {
        UUID id = UUID.randomUUID();
        return id;

    }
}
