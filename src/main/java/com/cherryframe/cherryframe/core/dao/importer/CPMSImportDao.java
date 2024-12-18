package com.cherryframe.cherryframe.core.dao.importer;

import com.cherryframe.cherryframe.core.dao.data.RowData;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface CPMSImportDao {

    void insertOrUpdate(RowData rowData, Connection connection, TextArea infoTextArea) throws SQLException;

    ResultSet findByStockCode(RowData rowData, Connection connection) throws SQLException;

    void insert(RowData rowData, Connection connection, TextArea infoTextArea) throws SQLException;

    void update(RowData rowData, Connection connection, TextArea infoTextArea) throws SQLException;
}
