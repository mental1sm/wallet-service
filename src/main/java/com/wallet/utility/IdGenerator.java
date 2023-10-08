package com.wallet.utility;

import java.util.Random;

public class IdGenerator {
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
