package com.wallet.service.audit;

import com.wallet.dao.logger.LoggerDao;
import com.wallet.domain.entities.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final LoggerDao loggerDao;

    @Override
    public void logThis(String login, String action, String description) {
        Log log = Log.builder()
                .login(login)
                .action(action)
                .description(description)
                .build();
        loggerDao.saveLog(log);
    }

    @Override
    public ArrayList<Log> getLogsOfUser(String login) {
        return loggerDao.getLogsOfConcreteUser(login);
    }

    @Override
    public ArrayList<Log> getAllLogs() {
        return loggerDao.getAllLogs();
    }

    @Override
    public ArrayList<Log> getAllLogsFilterByAction(String action) {
        return loggerDao.getAllLogsFilterByAction(action);
    }
}
