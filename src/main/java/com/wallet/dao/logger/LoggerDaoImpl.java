package com.wallet.dao.logger;

import com.wallet.domain.entities.Log;
import com.wallet.domain.entities.User;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;


@Repository
@Slf4j
@RequiredArgsConstructor
public class LoggerDaoImpl implements LoggerDao {

    private SetupConnection setupConnection;
    private PreparedStatementLog preparedStatementLog;

    @Override
    public void saveLog(Log _log) {
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementLog.saveLog(connection);
        )
        {
            preparedStatement.setString(0, _log.getAction());
            preparedStatement.setString(1, _log.getLogin());
            preparedStatement.setString(2, _log.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public ArrayList<Log> getLogsOfConcreteUser(String userLogin) {
        ArrayList<Log> logs = new ArrayList<>();
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementLog.getAllLogsOfUser(connection);
        )
        {
            preparedStatement.setString(0, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logs.add(extractLogFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return logs;
    }

    @Override
    public ArrayList<Log> getAllLogsFilterByAction(String action) {
        ArrayList<Log> logs = new ArrayList<>();
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementLog.getAllLogsFilterByAction(connection);
        )
        {
            preparedStatement.setString(0, action);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logs.add(extractLogFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return logs;
    }

    @Override
    public ArrayList<Log> getAllLogs() {
        ArrayList<Log> logs = new ArrayList<>();
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementLog.getAllLogs(connection);
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logs.add(extractLogFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return logs;
    }


    private Log extractLogFromResultSet(ResultSet resultSet) throws SQLException {
        return Log.builder()
                .id(resultSet.getLong("id"))
                .timestamp(resultSet.getTimestamp("timestamp"))
                .action(resultSet.getString("action"))
                .login(resultSet.getString("login"))
                .description(resultSet.getString("description"))
                .build();

    }
}
