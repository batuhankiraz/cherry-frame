package com.cherryframe.cherryframe.core.dao.importer.impl;

import com.cherryframe.cherryframe.core.dao.data.SelectionData;
import com.cherryframe.cherryframe.core.dao.importer.CPMSImportDao;
import com.cherryframe.cherryframe.core.dao.data.RowData;
import com.cherryframe.cherryframe.core.dao.strategy.CPMSDatabaseConnectionStrategy;
import com.cherryframe.cherryframe.core.dao.strategy.impl.CPMSDatabaseConnectionStrategyImpl;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cherryframe.cherryframe.core.constants.CPMSCoreConstants.DatabaseTable.TBLSTSBT;
import static com.cherryframe.cherryframe.core.dao.data.ValueTypeEnum.*;
import static com.cherryframe.cherryframe.core.dao.utils.CPMSDataAccessUtils.isLastIndexOf;
import static com.cherryframe.cherryframe.core.constants.CPMSCoreConstants.AvailableCheckBoxTitle.*;
import static java.util.Objects.nonNull;

public class CPMSImportDaoImpl implements CPMSImportDao {

    private static final String FIND_BY_STOCK_CODE_QUERY = "SELECT * FROM " + TBLSTSBT + " WHERE STOK_KODU = ?";
    private static final String INSERT_TO_TBLSTSBT_QUERY_DOMAIN_PREFIX = "INSERT INTO " + TBLSTSBT + " (";
    private static final String INSERT_TO_TBLSTSBT_QUERY_VALUES_PREFIX = " VALUES (";
    private static final String UPDATE_TO_TBLSTSBT_QUERY_DOMAIN_PREFIX = "UPDATE " + TBLSTSBT + " SET ";
    private static final String DELIMITER = ",";

    private final CPMSDatabaseConnectionStrategy connectionStrategy = new CPMSDatabaseConnectionStrategyImpl();


    @Override
    public void insertOrUpdate(final RowData rowData, final Connection connection, final TextArea infoTextArea) throws SQLException {
        final var result = findByStockCode(rowData, connection);
        if (result.next()){
            update(rowData, connection, infoTextArea);
        }
        else {
            insert(rowData, connection, infoTextArea);
        }
    }

    @Override
    public ResultSet findByStockCode(final RowData rowData, final Connection connection) throws SQLException {
        final var statement = connection.prepareStatement(FIND_BY_STOCK_CODE_QUERY);
        final var stockCodeSelectionData = rowData.getSelectionDataByTitle(STOK_KODU_TITLE);
        final var stockCodeValue = (String)stockCodeSelectionData.getValueData().getValue(STRING);
        statement.setString(1, stockCodeValue);
        return statement.executeQuery();
    }


    @Override
    public void insert(final RowData rowData, final Connection connection, final TextArea infoTextArea) {
        final var queryDomainBuilder = new StringBuilder(INSERT_TO_TBLSTSBT_QUERY_DOMAIN_PREFIX);
        final var queryValuesBuilder = new StringBuilder(INSERT_TO_TBLSTSBT_QUERY_VALUES_PREFIX);
        buildInsertQuery(rowData, queryDomainBuilder, queryValuesBuilder);
        final var query = queryDomainBuilder.append(queryValuesBuilder).toString();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            prepareAndExecuteInsertStatement(rowData, statement);
        } catch (final SQLException e){
            infoTextArea.setVisible(true);
            infoTextArea.setText("[" + e.getMessage() + "]");
        } finally {
            connectionStrategy.closeStatement(statement);
        }
    }

    @Override
    public void update(final RowData rowData, final Connection connection, final TextArea infoTextArea) {
        final var queryDomainBuilder = new StringBuilder(UPDATE_TO_TBLSTSBT_QUERY_DOMAIN_PREFIX);
        buildUpdateQuery(rowData, queryDomainBuilder);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(queryDomainBuilder.toString());
            prepareAndExecuteUpdateStatement(rowData, statement);
        } catch (final SQLException e){
            infoTextArea.setVisible(true);
            infoTextArea.setText("[" + e.getMessage() + "]");
        } finally {
            connectionStrategy.closeStatement(statement);
        }
    }

    private void prepareAndExecuteInsertStatement(final RowData rowData, final PreparedStatement statement) throws SQLException {
        int parameterIndex = 1;
        addStatementValues(statement, rowData.getSelectionDataList(), parameterIndex);
        statement.execute();
    }

    private void prepareAndExecuteUpdateStatement(final RowData rowData, final PreparedStatement statement) throws SQLException {
        int parameterIndex = 1;
        final List<SelectionData> selectionDataList = new ArrayList<>(rowData.getSelectionDataList());
        final var stockCodeSelectionData = rowData.getSelectionDataByTitle(STOK_KODU_TITLE);
        selectionDataList.remove(stockCodeSelectionData);
        parameterIndex = addStatementValues(statement, selectionDataList, parameterIndex);
        statement.setString(parameterIndex, stockCodeSelectionData.getValueData().getStringValue());
        statement.executeUpdate();
    }

    private void buildInsertQuery(final RowData rowData, final StringBuilder queryDomainBuilder,
                            final StringBuilder queryValuesBuilder) {
        final List<SelectionData> selectionDataList = new ArrayList<>(rowData.getSelectionDataList());
        for (final SelectionData selectionData : selectionDataList)
        {
            final var value = selectionData.getValueData();
            if (nonNull(value))
            {
                queryDomainBuilder.append(selectionData.getDbKey())
                        .append(isLastIndexOf(selectionDataList, selectionData) ? ") " : DELIMITER);

                queryValuesBuilder.append(isLastIndexOf(selectionDataList, selectionData) ? "?)" : "?,");
            }
        }
    }

    private void buildUpdateQuery(final RowData rowData, final StringBuilder queryDomainBuilder) {
        final List<SelectionData> selectionDataList = new ArrayList<>(rowData.getSelectionDataList());

        final var stockCodeSelectionData = rowData.getSelectionDataByTitle(STOK_KODU_TITLE);
        String whereClauseSuffix = "";
        if (nonNull(stockCodeSelectionData.getValueData()))
        {
            whereClauseSuffix = " WHERE " + stockCodeSelectionData.getDbKey() + "=?";
            selectionDataList.remove(stockCodeSelectionData);
        }

        for (final SelectionData selectionData : selectionDataList)
        {
            if (nonNull(selectionData.getValueData()))
            {
                queryDomainBuilder.append(selectionData.getDbKey())
                        .append("=").append("?")
                        .append(isLastIndexOf(selectionDataList, selectionData) ? whereClauseSuffix : DELIMITER);
            }
        }
    }

    private int addStatementValues(final PreparedStatement statement, final List<SelectionData> selectionDataList,
                                   int parameterIndex) throws SQLException {
        for (final SelectionData selectionData : selectionDataList)
        {
            final var value = selectionData.getValueData();
            if (nonNull(value))
            {
                final var valueType = selectionData.getValueData().getType();
                switch (valueType) {
                    case STRING -> statement.setString(parameterIndex, value.getStringValue());
                    case NUMERIC -> statement.setDouble(parameterIndex, value.getNumericValue());
                    case BOOLEAN -> statement.setBoolean(parameterIndex, value.getBooleanValue());
                    default -> throw new IllegalArgumentException("Unsupported ValueType: " + valueType);
                }
                parameterIndex ++;
            }
        }
        return parameterIndex;
    }
}
