package com.cherryframe.cherryframe.dao.strategy;

import java.sql.Connection;

public interface CherryFrameDatabaseConnectionStrategy {

    Connection connect();
}
