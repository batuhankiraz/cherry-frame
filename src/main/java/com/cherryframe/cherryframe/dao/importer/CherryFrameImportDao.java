package com.cherryframe.cherryframe.dao.importer;

import com.cherryframe.cherryframe.dao.data.RowData;
import com.cherryframe.cherryframe.dao.data.SelectionData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CherryFrameImportDao {

    void insertOrUpdate(RowData rowData);

    ResultSet findByStockCode(RowData rowData, Connection connection) throws SQLException;

    void insert(RowData rowData, Connection connection) throws SQLException;

    void update(RowData rowData, Connection connection) throws SQLException;
}
