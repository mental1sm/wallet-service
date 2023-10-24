package com.wallet.dao.log;

import com.wallet.domain.entities.Log;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogDaoImpl implements LogDao {
    PreparedStatementLog preparedStatementLog;

    public LogDaoImpl() {
        preparedStatementLog = PreparedStatementLog.getInstance();
    }

    @Override
    public void saveLog(Log log) {
        try (
            Connection connection = SetupConnection.getConnection();
            PreparedStatement preparedStatement = preparedStatementLog.saveLog(connection);
        )
        {
            preparedStatement.setString(2, log.getAction());
            preparedStatement.setString(3, log.getInfoLevel().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getAmountOfImportantLogs() {
        long importantLogsCount = 0;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementLog.getAmountOfImportantLogs(connection);
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            importantLogsCount = resultSet.getLong("log_count");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return importantLogsCount;
    }


    @Override
    public ArrayList<Log> getImportantLogs(long start, long end) {
        ArrayList<Log> logs = new ArrayList<>();
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementLog.getImportantLogs(connection);
        )
        {
            preparedStatement.setLong(1, start);
            preparedStatement.setLong(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logs.add(extractLogFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    @Override
    public ArrayList<Log> getAllLogs() {
        ArrayList<Log> logs = new ArrayList<>();
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementLog.getAllLogs(connection);
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logs.add(extractLogFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    private Log extractLogFromResultSet(ResultSet resultSet) throws SQLException {
        return Log.builder()
                .id(resultSet.getLong("id"))
                .timestamp(resultSet.getTimestamp("timestamp"))
                .action(resultSet.getString("action"))
                .infoLevel(Log.InfoLevels.valueOf(resultSet.getString("info_level")))
                .build();
    }
}
