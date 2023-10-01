package com.cherryframe.cherryframe.dao.strategy.impl;

import com.cherryframe.cherryframe.dao.strategy.CherryFrameDatabaseConnectionStrategy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.cherryframe.cherryframe.service.constants.CherryFrameCoreConstants.Database.*;

public class CherryFrameDatabaseConnectionStrategyImpl implements CherryFrameDatabaseConnectionStrategy {

    private static final String SQL_DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String SQL_CONNECTION_PREFIX = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT_NUMBER + ";";
    private static final String SQL_CONNECTION_DATABASE_NAME_FILTER = "databaseName=" + DATABASE_NAME + ";";
    private static final String SQL_CONNECTION_ENCRYPT_FILTER = "encrypt=false;";

    @Override
    public Connection connect()
    {
        try {
            Class.forName(SQL_DRIVER_CLASS_NAME);
            return DriverManager.getConnection(getConnectionUrl(), USER_NAME, PASSWORD);
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
        return SQL_CONNECTION_PREFIX + SQL_CONNECTION_ENCRYPT_FILTER + SQL_CONNECTION_DATABASE_NAME_FILTER;
    }
}
