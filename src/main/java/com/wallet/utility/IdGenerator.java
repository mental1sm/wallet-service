package com.wallet.utility;

import java.util.Random;


public class IdGenerator {
    /**
     * Предоставляет случайно сгенерированную строку, состоящую из 16 цифер.
     * @return Случайно сгенерированный ID в виде строки
     */
    public static String genId() {
        Random random = new Random();
        String pID = "";
        for (int i = 0; i < 16; i++) {
            int gen = random.nextInt(10); // Генерация чисел от 0 до 9
            pID += gen;
        }
        return pID;

    }
}
