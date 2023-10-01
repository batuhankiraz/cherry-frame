package com.cherryframe.cherryframe.service.importer.impl;

import com.cherryframe.cherryframe.dao.importer.CherryFrameImportDao;
import com.cherryframe.cherryframe.dao.importer.impl.CherryFrameImportDaoImpl;
import com.cherryframe.cherryframe.dao.data.RowData;
import com.cherryframe.cherryframe.dao.strategy.CherryFrameDatabaseConnectionStrategy;
import com.cherryframe.cherryframe.dao.strategy.impl.CherryFrameDatabaseConnectionStrategyImpl;
import com.cherryframe.cherryframe.service.importer.CherryFrameImportService;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.List;


public class CherryFrameImportServiceImpl implements CherryFrameImportService {

    private final CherryFrameImportDao cherryFrameImportDao = new CherryFrameImportDaoImpl();
    private final CherryFrameDatabaseConnectionStrategy connectionStrategy = new CherryFrameDatabaseConnectionStrategyImpl();

    @Override
    public void importToServer(final List<RowData> rowDataList, final TextArea infoTextArea) {
        rowDataList.removeIf(rowData -> rowData.getSelectionDataList().size() == 0);

        final var connection = connectionStrategy.connect();
        try{
            for (final RowData rowData : rowDataList) {
                cherryFrameImportDao.insertOrUpdate(rowData, connection, infoTextArea);
            }
            infoTextArea.setVisible(true);
            infoTextArea.setStyle("-fx-text-inner-color: green");
            infoTextArea.setText("Successfully Imported!");
        }
        catch (final SQLException e){
            infoTextArea.setVisible(true);
            infoTextArea.setText("[" + e.getMessage() + "] \n\n");
        } finally {
            connectionStrategy.closeConnection(connection);
        }
    }

}
