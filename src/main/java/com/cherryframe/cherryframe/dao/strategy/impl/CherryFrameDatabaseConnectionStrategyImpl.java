package com.cherryframe.cherryframe.dao.strategy.impl;

import com.cherryframe.cherryframe.dao.strategy.CherryFrameDatabaseConnectionStrategy;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.cherryframe.cherryframe.service.constants.CherryFrameCoreConstants.Database.*;

public class CherryFrameDatabaseConnectionStrategyImpl implements CherryFrameDatabaseConnectionStrategy {

    private static final String SQL_DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String SQL_CONNECTION_PREFIX = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT_NUMBER + ";";
    private static final String SQL_CONNECTION_DATABASE_NAME_FILTER = "databaseName=" + DATABASE_NAME + ";";
    private static final String SQL_CONNECTION_ENCRYPT_FILTER = "encrypt=false;";
    //private static final String SQL_CONNECTION_SECURITY_CREDENTIAL_FILTER = "user=" + USER_NAME + ";" + "password=" + PASSWORD + ";";
    //private static final String SQL_CONNECTION_INTEGRATED_SECURITY_FILTER = "integratedSecurity=true;";

    @Override
    public Connection connect()
    {
        try {
            Class.forName(SQL_DRIVER_CLASS_NAME);
            final var connection = DriverManager.getConnection(getConnectionUrl(), USER_NAME, PASSWORD);
            System.out.println("Successfully connected.");
            return connection;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getConnectionUrl() {
        return SQL_CONNECTION_PREFIX + SQL_CONNECTION_ENCRYPT_FILTER + SQL_CONNECTION_DATABASE_NAME_FILTER;
    }
}
