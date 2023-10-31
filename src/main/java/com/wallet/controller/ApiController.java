package com.wallet.controller;


import com.wallet.domain.dto.transaction.NewTransactionDTO;
import com.wallet.domain.dto.user.UserRegistrationDTO;
import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;
import com.wallet.utility.TokenContext;
import com.wallet.utility.exceptions.UserAllreadyExistsException;
import com.wallet.utility.exceptions.UnauthorizedException;
import com.wallet.domain.dto.user.UserAuthDTO;
import com.wallet.service.account.AccountService;
import com.wallet.service.auth.AuthService;
import com.wallet.service.wallet.WalletService;
import com.wallet.utility.exceptions.UserIsNotExistsException;
import com.wallet.utility.exceptions.WalletIsNotExistsException;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {
    private final WalletService walletService;
    private final AccountService accountService;
    private final AuthService authService;
    private final TokenContext tokenContext;

    /**
     * Авторизация
     * JSON {
     *     login
     *     password
     * }
     * @return Bearer Auth TOKEN
    */
    @PostMapping(value = "/auth/login", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, String>> getAuthToken(@RequestBody UserAuthDTO request) {
        Map<String, String> data = new HashMap<>();
        try {
            String authToken = authService.getAuthToken(request).orElseThrow(UserIsNotExistsException::new);
            data.put("token", authToken);
            return ResponseEntity.ok(data);
        } catch (IOException | UserIsNotExistsException e) {
            data.put("errorMessage", "Ошибка. Проверьте правильность заполнения полей.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        }
    }

    /**
     * Регистрация
     * JSON {
     *     login
     *     password
     *     name
     *     surname
     *     email
     * }
     * @return Bearer Auth TOKEN
     */
    @PostMapping(value = "/auth/registration", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, String>> registerAndGetToken(
            @RequestBody UserRegistrationDTO userRegistrationDTO
    )
    {
        Map<String, String> data = new HashMap<>();
        try {
            authService.regUserInRealm(userRegistrationDTO);
            accountService.regUser(userRegistrationDTO);
            data.put("InfoMessage", "Пользователь успешно зарегистрирован.");
            return ResponseEntity.status(HttpStatus.OK).body(data);
        } catch (UserIsNotExistsException | IOException e) {
            data.put("errorMessage", "Ошибка. Проверьте правильность заполнения полей.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        } catch (UserAllreadyExistsException e) {
            data.put("errorMessage", "Ошибка. Пользователь уже существует.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(data);

        }
    }

    /**
     * Возвращает информацию о всех кошельках пользователя
     * Не требует входных данных
    */
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> getAllWalletsOfUser(
            @RequestHeader("Authorization") String authHeader
    )
    {
        Map<String, Object> data = new HashMap<>();
        try {
            String login = tokenContext.getLoginFromContext(authHeader);
            ArrayList<Wallet> wallets = accountService.getWalletsOfUser(login);
            data.put("wallets", wallets);
            return ResponseEntity.ok(data);
        } catch (UnauthorizedException e) {
            data.put("errorMessage", "У вас нет доступа к этому ресурсу.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(data);
        } catch (NoSuchAlgorithmException | UserIsNotExistsException | InvalidKeySpecException e) {
            data.put("errorMessage", "Произошла ошибка.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        }
    }

    /**
     * Возвращает информацию о кошельке пользователя по id
     * /api/wallet/1
     **/
    @GetMapping(value = "/wallet/{walletId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> getWalletInfo(
            @PathVariable long walletId,
            @RequestHeader("Authorization") String authHeader
    )
    {
        Map<String, Object> data = new HashMap<>();
        try {
            String login = tokenContext.getLoginFromContext(authHeader);
            Wallet wallet = walletService.getWalletById(walletId, login).orElseThrow(WalletIsNotExistsException::new);
            data.put("wallet", wallet);
            return ResponseEntity.ok(data);
        } catch (UnauthorizedException e) {
            data.put("errorMessage", "У вас нет доступа к этому ресурсу.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(data);
        } catch (NoSuchAlgorithmException | UserIsNotExistsException | InvalidKeySpecException e) {
            data.put("errorMessage", "Произошла ошибка.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        } catch (WalletIsNotExistsException e) {
            data.put("errorMessage", "Кошелька с таким id не найдено.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        }
    }

    /**
     * Получить историю транзакций
     * "wallet_id":
     */
    @GetMapping(value = "/wallet/{walletId}/transaction", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> getTransactionHistory(
            @PathVariable long walletId,
            @RequestHeader("Authorization") String authHeader
    )
    {
        Map<String, Object> data = new HashMap<>();
        try {
            String login = tokenContext.getLoginFromContext(authHeader);
            Wallet wallet = walletService.getWalletById(walletId, login).orElseThrow(WalletIsNotExistsException::new);
            ArrayList<Transaction> transactionHistory = walletService.getTransactionHistory(wallet);
            data.put("transactions", transactionHistory);
            return ResponseEntity.ok(data);

        } catch (UserIsNotExistsException | WalletIsNotExistsException e) {
            data.put("errorMessage", "Кошелька с таким id не найдено.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        } catch (UnauthorizedException  e) {
            data.put("errorMessage", "У вас нет доступа к этому ресурсу.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(data);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            data.put("errorMessage", "Что-то пошло не так.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        }
    }

    /**
     * Инициировать новую транзакцию
     * {
     *     "walletId": num,
     *     "transactionSum": string,
     *     "transactionType": "Deposit" OR "Withdraw"
     * }
    */
    @PostMapping(value = "/wallet/{walletId}/transaction", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> getTransactionDetails(
            @RequestBody NewTransactionDTO transactionDetails,
            @PathVariable long walletId,
            @RequestHeader("Authorization") String authHeader
    )
    {
        Map<String, Object> data = new HashMap<>();
        try {
            String login = tokenContext.getLoginFromContext(authHeader);
            Wallet wallet = walletService.getWalletById(walletId, login).orElseThrow(WalletIsNotExistsException::new);
            walletService.processTransactionFromRawData(wallet, transactionDetails.getTransactionSum(), transactionDetails.getTransactionType());
            data.put("message", "Транзакция направлена на обработку.");
            return ResponseEntity.ok(data);
        } catch (UserIsNotExistsException | WalletIsNotExistsException e) {
            data.put("errorMessage", "Кошелька с таким id не найдено.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        } catch (UnauthorizedException  e) {
            data.put("errorMessage", "У вас нет доступа к этому ресурсу.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(data);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            data.put("errorMessage", "Что-то пошло не так.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        }

    }






}
