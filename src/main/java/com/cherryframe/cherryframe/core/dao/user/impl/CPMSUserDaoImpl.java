package com.cherryframe.cherryframe.core.dao.user.impl;

import com.cherryframe.cherryframe.core.dao.user.CPMSUserDao;

import java.sql.Connection;
import java.sql.SQLException;

import static com.cherryframe.cherryframe.core.constants.CPMSCoreConstants.DatabaseTable.CPMSUSER;

public class CPMSUserDaoImpl implements CPMSUserDao {

    private static final String FIND_BY_USER_CREDENTIALS = "SELECT * FROM " + CPMSUSER + " WHERE UID=? AND PASSWORD=?";
    private static final String RESET_PASSWORD_QUERY = "UPDATE " + CPMSUSER + " SET PASSWORD=? WHERE UID=?";

    @Override
    public boolean validateUserCredentials(final String username, final String password, final Connection connection)
            throws SQLException {
        final var statement = connection.prepareStatement(FIND_BY_USER_CREDENTIALS);
        statement.setString(1, username);
        statement.setString(2, password);
        return statement.executeQuery().next();
    }

    @Override
    public void resetPassword(final String username, final String newPassword, final Connection connection)
            throws SQLException {
        final var statement = connection.prepareStatement(RESET_PASSWORD_QUERY);
        statement.setString(1, newPassword);
        statement.setString(2, username);
        statement.executeUpdate();
    }
}
