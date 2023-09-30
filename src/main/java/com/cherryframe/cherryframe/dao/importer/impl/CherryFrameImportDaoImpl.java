package com.cherryframe.cherryframe.dao.importer.impl;

import com.cherryframe.cherryframe.dao.data.SelectionData;
import com.cherryframe.cherryframe.dao.data.ValueTypeEnum;
import com.cherryframe.cherryframe.dao.importer.CherryFrameImportDao;
import com.cherryframe.cherryframe.dao.data.RowData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.cherryframe.cherryframe.dao.utils.CherryFrameDataAccessUtils.isLastIndexOf;
import static com.cherryframe.cherryframe.service.constants.CherryFrameCoreConstants.AvailableCheckBoxTitle.*;
import static java.util.Objects.nonNull;

public class CherryFrameImportDaoImpl implements CherryFrameImportDao {

    private static final String DELIMITER = ",";

    @Override
    public void insertOrUpdate(final RowData rowData, final Connection connection) {
        try{
            final var result = findByStockCode(rowData, connection);
            if (result.next()){
                //update();
            }
            else {
                insert(rowData, connection);
            }
        }
        catch (final SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet findByStockCode(final RowData rowData, final Connection connection) throws SQLException {
        final var statement = connection.prepareStatement("SELECT * FROM TBLSTSBT WHERE STOK_KODU = ?");
        final var stockCodeSelectionData = rowData.getSelectionDataByTitle(STOK_KODU_TITLE);
        final var stockCodeValue = (String)stockCodeSelectionData.getValueData().getValue(ValueTypeEnum.STRING);
        statement.setString(1, stockCodeValue);
        return statement.executeQuery();
    }


    @Override
    public void insert(final RowData rowData, final Connection connection) throws SQLException {
        final var queryDomainBuilder = new StringBuilder("INSERT INTO TBLSTSBT (");
        final var queryValuesBuilder = new StringBuilder(" VALUES (");
        buildQuery(rowData, queryDomainBuilder, queryValuesBuilder);

        final var query = queryDomainBuilder.append(queryValuesBuilder).toString();
        final var statement = connection.prepareStatement(query);

        prepareAndExecuteStatement(queryDomainBuilder, queryValuesBuilder, rowData, statement);
    }

    @Override
    public void update() {

    }

    private void prepareAndExecuteStatement(final StringBuilder queryDomainBuilder, final StringBuilder queryValuesBuilder,
                                             final RowData rowData, final PreparedStatement statement) throws SQLException {
        int parameterIndex = 1;
        final var stockCodeSelectionData = rowData.getSelectionDataByTitle(STOK_KODU_TITLE);
        if (nonNull(stockCodeSelectionData))
        {
            statement.setString(parameterIndex, (String)stockCodeSelectionData.getValueData().getValue(ValueTypeEnum.STRING));
        }
        final var usdPurchaseSelectionData = rowData.getSelectionDataByTitle(DOVIZ_ALIS_FIYATI_TITLE);
        if (nonNull(usdPurchaseSelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)usdPurchaseSelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var usdSellSelectionData = rowData.getSelectionDataByTitle(DOVIZ_SATIS_FIYATI_TITLE);
        if (nonNull(usdSellSelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)usdSellSelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var usdProductSelectionData = rowData.getSelectionDataByTitle(DOVIZ_MAL_FIYATI_TITLE);
        if (nonNull(usdProductSelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)usdProductSelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var sellPrice1SelectionData = rowData.getSelectionDataByTitle(SATIS_FIYAT_1_TITLE);
        if (nonNull(sellPrice1SelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)sellPrice1SelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var sellPrice2SelectionData = rowData.getSelectionDataByTitle(SATIS_FIYAT_2_TITLE);
        if (nonNull(sellPrice2SelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)sellPrice2SelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var sellPrice3SelectionData = rowData.getSelectionDataByTitle(SATIS_FIYAT_3_TITLE);
        if (nonNull(sellPrice3SelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)sellPrice3SelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var sellPrice4SelectionData = rowData.getSelectionDataByTitle(SATIS_FIYAT_4_TITLE);
        if (nonNull(sellPrice4SelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)sellPrice4SelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var purchasePrice1SelectionData = rowData.getSelectionDataByTitle(ALIS_FIYAT_1_TITLE);
        if (nonNull(purchasePrice1SelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)purchasePrice1SelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var purchasePrice2SelectionData = rowData.getSelectionDataByTitle(ALIS_FIYAT_2_TITLE);
        if (nonNull(purchasePrice2SelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)purchasePrice2SelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var purchasePrice3SelectionData = rowData.getSelectionDataByTitle(ALIS_FIYAT_3_TITLE);
        if (nonNull(purchasePrice3SelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)purchasePrice3SelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }
        final var purchasePrice4SelectionData = rowData.getSelectionDataByTitle(ALIS_FIYAT_4_TITLE);
        if (nonNull(purchasePrice4SelectionData))
        {
            parameterIndex ++;
            statement.setDouble(parameterIndex, (Double)purchasePrice4SelectionData.getValueData().getValue(ValueTypeEnum.NUMERIC));
        }

        statement.executeQuery();
    }

    private void buildQuery(final RowData rowData, final StringBuilder queryDomainBuilder,
                            final StringBuilder queryValuesBuilder) {
        final var selectionDataList = rowData.getSelectionDataList();
        for (final SelectionData selectionData : selectionDataList)
        {
            queryDomainBuilder.append(selectionData.getDbKey())
                    .append(isLastIndexOf(selectionDataList, selectionData) ? ") " : DELIMITER);

            queryValuesBuilder.append(isLastIndexOf(selectionDataList, selectionData) ? "?)" : "?,");
        }
    }
}
