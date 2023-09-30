package com.cherryframe.cherryframe.dao.importer.impl;

import com.cherryframe.cherryframe.dao.data.SelectionData;
import com.cherryframe.cherryframe.dao.importer.CherryFrameImportDao;
import com.cherryframe.cherryframe.dao.data.RowData;
import com.cherryframe.cherryframe.dao.strategy.CherryFrameDatabaseConnectionStrategy;
import com.cherryframe.cherryframe.dao.strategy.impl.CherryFrameDatabaseConnectionStrategyImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cherryframe.cherryframe.dao.data.ValueTypeEnum.*;
import static com.cherryframe.cherryframe.dao.utils.CherryFrameDataAccessUtils.isLastIndexOf;
import static com.cherryframe.cherryframe.service.constants.CherryFrameCoreConstants.AvailableCheckBoxTitle.*;
import static java.util.Objects.nonNull;

public class CherryFrameImportDaoImpl implements CherryFrameImportDao {

    private static final String DELIMITER = ",";

    private final CherryFrameDatabaseConnectionStrategy connectionStrategy = new CherryFrameDatabaseConnectionStrategyImpl();


    @Override
    public void insertOrUpdate(final RowData rowData) {
        final var connection = connectionStrategy.connect();
        try{
            final var result = findByStockCode(rowData, connection);
            if (result.next()){
                update(rowData, connection);
            }
            else {
                insert(rowData, connection);
            }
        }
        catch (final SQLException e){
            e.printStackTrace();
        } finally {
            connectionStrategy.closeConnection(connection);
        }
    }

    @Override
    public ResultSet findByStockCode(final RowData rowData, final Connection connection) throws SQLException {
        final var statement = connection.prepareStatement("SELECT * FROM TBLSTSBT WHERE STOK_KODU = ?");
        final var stockCodeSelectionData = rowData.getSelectionDataByTitle(STOK_KODU_TITLE);
        final var stockCodeValue = (String)stockCodeSelectionData.getValueData().getValue(STRING);
        statement.setString(1, stockCodeValue);
        return statement.executeQuery();
    }


    @Override
    public void insert(final RowData rowData, final Connection connection) {
        final var queryDomainBuilder = new StringBuilder("INSERT INTO TBLSTSBT (");
        final var queryValuesBuilder = new StringBuilder(" VALUES (");
        buildInsertQuery(rowData, queryDomainBuilder, queryValuesBuilder);
        final var query = queryDomainBuilder.append(queryValuesBuilder).toString();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            prepareAndExecuteInsertStatement(rowData, statement);
        } catch (final SQLException e){
            e.printStackTrace();
        } finally {
            connectionStrategy.closeStatement(statement);
        }
    }

    @Override
    public void update(final RowData rowData, final Connection connection) {
        final var queryDomainBuilder = new StringBuilder("UPDATE TBLSTSBT SET ");
        buildUpdateQuery(rowData, queryDomainBuilder);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(queryDomainBuilder.toString());
            prepareAndExecuteUpdateStatement(rowData, statement);
        } catch (final SQLException e){
            e.printStackTrace();
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
