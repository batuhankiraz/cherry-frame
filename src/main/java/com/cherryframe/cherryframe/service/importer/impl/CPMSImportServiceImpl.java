package com.cherryframe.cherryframe.service.importer.impl;

import com.cherryframe.cherryframe.dao.importer.CPMSImportDao;
import com.cherryframe.cherryframe.dao.importer.impl.CPMSImportDaoImpl;
import com.cherryframe.cherryframe.dao.data.RowData;
import com.cherryframe.cherryframe.dao.strategy.CPMSDatabaseConnectionStrategy;
import com.cherryframe.cherryframe.dao.strategy.impl.CPMSDatabaseConnectionStrategyImpl;
import com.cherryframe.cherryframe.service.importer.CPMSImportService;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.List;


public class CPMSImportServiceImpl implements CPMSImportService {

    private final CPMSImportDao CPMSImportDao = new CPMSImportDaoImpl();
    private final CPMSDatabaseConnectionStrategy connectionStrategy = new CPMSDatabaseConnectionStrategyImpl();

    @Override
    public void importToServer(final List<RowData> rowDataList, final TextArea infoTextArea) {
        rowDataList.removeIf(rowData -> rowData.getSelectionDataList().size() == 0);

        final var connection = connectionStrategy.connect();
        try{
            for (final RowData rowData : rowDataList) {
                CPMSImportDao.insertOrUpdate(rowData, connection, infoTextArea);
            }
            infoTextArea.setVisible(true);
            infoTextArea.setStyle("-fx-text-inner-color: green");
            infoTextArea.setText("Successfully Imported!");
        }
        catch (final SQLException e){
            infoTextArea.setVisible(true);
            infoTextArea.setText(e.getMessage());
        } finally {
            connectionStrategy.closeConnection(connection);
        }
    }

}
