package com.cherryframe.cherryframe.dao.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface CherryFrameDatabaseConnectionStrategy {

    Connection connect();

    void closeConnection(final Connection connection);

    void closeStatement(final PreparedStatement statement);
}
