package com.wallet.presentation.servlets.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.wallet.dao.transaction.TransactionDao;
import com.wallet.dao.transaction.TransactionDaoImpl;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.domain.entities.Transaction;
import com.wallet.domain.mappers.TransactionMapper;
import com.wallet.services.walletService.WalletService;
import com.wallet.services.walletService.WalletServiceImpl;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import com.wallet.utility.exceptions.UnauthorizedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public class TransactionServletAPI extends CustomServletTemplate {

    private final WalletService walletService;
    private final TransactionDao transactionDao;
    private final WalletDao walletDao;

    public TransactionServletAPI() {
        super();
        transactionDao = new TransactionDaoImpl();
        walletDao = new WalletDaoImpl();
        walletService = new WalletServiceImpl(walletDao, transactionDao);
    }

    /**
     * Получить историю транзакций
     * "wallet_id":
    */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try
        {
            String _login = getLogin(req).orElseThrow(PlayerIsNotExistsException::new);
            Map<String, Object> receivedData = readJson(req);
            long walletId = Long.parseLong(receivedData.get("wallet_id").toString());
            ArrayList<Transaction> transactionHistory = transactionDao.getTransactionsOfWallet(walletDao.getWalletById(walletId));

            TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);
            ArrayNode arrayNode = objectMapper.createArrayNode();

            for (Transaction transaction: transactionHistory) {
                JsonNode node = objectMapper.valueToTree(mapper.transactionToTransactionDTO(transaction));
                arrayNode.add(node);
            }

            String json = arrayNode.toString();
            sendResponse(resp, HttpServletResponse.SC_OK, json);
        } catch (UnauthorizedException ex) {
            sendResponse(resp, HttpServletResponse.SC_UNAUTHORIZED, "Ваши авторизационные неверны или устарели.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PlayerIsNotExistsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Инициировать транзакцию
     * "wallet_id":
     * "money_amount":
     * "operation_type": "DEPOSIT" или "WITHDRAW"
    */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            String _login = getLogin(req).orElseThrow(UnauthorizedException::new);
            Map<String, Object> receivedData = readJson(req);
            long walletId = Long.parseLong(receivedData.get("wallet_id").toString());
            BigDecimal moneyAmount = new BigDecimal(receivedData.get("money_amount").toString());
            Transaction.Type operationType = Transaction.Type.valueOf(receivedData.get("operation_type").toString());
            walletService.processTransactionFromRawData(walletId, moneyAmount, operationType);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (UnauthorizedException e) {
            sendResponse(resp, HttpServletResponse.SC_UNAUTHORIZED, "Ваши авторизационные неверны или устарели.");
        } catch (PlayerIsNotExistsException e) {
            sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Такого пользователя или кошелька не существует.");
        }
    }


}
