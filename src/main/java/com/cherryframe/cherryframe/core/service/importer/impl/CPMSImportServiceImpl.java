package com.cherryframe.cherryframe.core.service.importer.impl;

import com.cherryframe.cherryframe.core.dao.importer.CPMSImportDao;
import com.cherryframe.cherryframe.core.dao.importer.impl.CPMSImportDaoImpl;
import com.cherryframe.cherryframe.core.dao.data.RowData;
import com.cherryframe.cherryframe.core.dao.strategy.CPMSDatabaseConnectionStrategy;
import com.cherryframe.cherryframe.core.dao.strategy.impl.CPMSDatabaseConnectionStrategyImpl;
import com.cherryframe.cherryframe.core.service.importer.CPMSImportService;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.List;

import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.ValidationMessages.SUCCESSFULLY_IMPORTED;
import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.ValidationStyles.SUCCESSFULLY_IMPORTED_TEXT_STYLE;


public class CPMSImportServiceImpl implements CPMSImportService {

    private final CPMSImportDao CPMSImportDao = new CPMSImportDaoImpl();
    private final CPMSDatabaseConnectionStrategy connectionStrategy = new CPMSDatabaseConnectionStrategyImpl();

    @Override
    public void importToServer(final List<RowData> rowDataList, final TextArea infoTextArea) {
        rowDataList.removeIf(rowData -> rowData.getSelectionDataList().size() == 0);

        final var connection = connectionStrategy.connect();
        try {
            for (final RowData rowData : rowDataList) {
                CPMSImportDao.insertOrUpdate(rowData, connection, infoTextArea);
            }
            infoTextArea.setVisible(true);
            infoTextArea.setStyle(SUCCESSFULLY_IMPORTED_TEXT_STYLE);
            infoTextArea.setText(SUCCESSFULLY_IMPORTED);
        } catch (final SQLException e) {
            infoTextArea.setVisible(true);
            infoTextArea.setText(e.getMessage());
        } finally {
            connectionStrategy.closeConnection(connection);
        }
    }

}
