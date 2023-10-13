package com.cherryframe.cherryframe.core.dao.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface CPMSDatabaseConnectionStrategy {

    Connection connect();

    void closeConnection(final Connection connection);

    void closeStatement(final PreparedStatement statement);
}
