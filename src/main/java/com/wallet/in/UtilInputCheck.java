package com.wallet.in;

import com.wallet.presentation.Localisation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UtilInputCheck {

    public static boolean checkInputMenuPointer(ArrayList<String> menuPointers, String userInput) {
        try {
            if (menuPointers.contains(userInput)) { return true;}
            else throw new InputMismatchException(Localisation.INPUT_MISMATCH_EXCEPTION_RU);
        } catch (Exception e) {
            System.out.println(Localisation.INCORRECT_INPUT_RU);
        }
        return false;

    }

    public static boolean checkInputBounded(int boundF, int boundS, String userInput) {
        try {
            if (Integer.parseInt(userInput) <= boundF && Integer.parseInt(userInput) >= boundS) { return true; }
            else throw new InputMismatchException(Localisation.INPUT_MISMATCH_EXCEPTION_RU);
        } catch (Exception e) {
            System.out.println(Localisation.INCORRECT_INPUT_RU);
        }
        return false;
    }
}
