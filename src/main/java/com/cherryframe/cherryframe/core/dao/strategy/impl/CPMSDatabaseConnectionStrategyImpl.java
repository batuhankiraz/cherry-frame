package com.cherryframe.cherryframe.core.dao.strategy.impl;

import com.cherryframe.cherryframe.core.dao.config.CPMSConfigurationService;
import com.cherryframe.cherryframe.core.dao.config.impl.CPMSConfigurationServiceImpl;
import com.cherryframe.cherryframe.core.dao.strategy.CPMSDatabaseConnectionStrategy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CPMSDatabaseConnectionStrategyImpl implements CPMSDatabaseConnectionStrategy {

    private static final String SQL_DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String SQL_CONNECTION_PREFIX = "jdbc:sqlserver://";
    private static final String SQL_CONNECTION_DATABASE_NAME_FILTER = "databaseName=";
    private static final String SQL_CONNECTION_ENCRYPT_FILTER = "encrypt=false;";
    private static final String DB_SERVER_NAME_KEY = "cpms.database.server.name";
    private static final String DB_PORT_NUMBER_KEY = "cpms.database.port.number";
    private static final String DB_NAME_KEY = "cpms.database.name";

    private final CPMSConfigurationService configurationService = new CPMSConfigurationServiceImpl();

    @Override
    public Connection connect()
    {
        try {
            Class.forName(SQL_DRIVER_CLASS_NAME);
            final var username = configurationService.getValue("cpms.database.user.name");
            final var password = configurationService.getValue("cpms.database.user.password");
            return DriverManager.getConnection(getConnectionUrl(), username, password);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection(final Connection connection) {
        try {
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeStatement(final PreparedStatement statement) {
        try {
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    private String getConnectionUrl() {
        return SQL_CONNECTION_PREFIX
                + configurationService.getValue(DB_SERVER_NAME_KEY) + ":"
                + configurationService.getValue(DB_PORT_NUMBER_KEY) + ";"
                + SQL_CONNECTION_ENCRYPT_FILTER
                + SQL_CONNECTION_DATABASE_NAME_FILTER + configurationService.getValue(DB_NAME_KEY) + ";";
    }
}
