package com.wallet.services.accountService;

import com.wallet.infrastructure.UserSession;

public interface AccountService {
    UserSession regUser(String name, String surname, String pLogin, String pPassword);
    UserSession authUser(String pLogin, String pPassword);
}
