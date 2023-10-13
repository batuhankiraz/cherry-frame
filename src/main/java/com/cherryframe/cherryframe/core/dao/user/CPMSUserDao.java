package com.cherryframe.cherryframe.core.dao.user;

import java.sql.Connection;
import java.sql.SQLException;

public interface CPMSUserDao {
    boolean validateUserCredentials(String username, String password, Connection connection) throws SQLException;

    void resetPassword(String username, String newPassword, Connection connection) throws SQLException;
}
