package com.wallet;

import com.wallet.presentation.player_interface.InterfaceMenu;
import com.wallet.presentation.player_interface.iInterface;

import java.util.Scanner;


public class WalletAppMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        iInterface ui = new InterfaceMenu(scanner);
        while (ui != null) {
            ui = ui.run();
        }
    }
}
