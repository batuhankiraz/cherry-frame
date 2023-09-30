package com.cherryframe.cherryframe.service.importer.impl;

import com.cherryframe.cherryframe.dao.data.SelectionData;
import com.cherryframe.cherryframe.dao.importer.CherryFrameImportDao;
import com.cherryframe.cherryframe.dao.importer.impl.CherryFrameImportDaoImpl;
import com.cherryframe.cherryframe.dao.data.RowData;
import com.cherryframe.cherryframe.dao.strategy.CherryFrameDatabaseConnectionStrategy;
import com.cherryframe.cherryframe.dao.strategy.impl.CherryFrameDatabaseConnectionStrategyImpl;
import com.cherryframe.cherryframe.service.importer.CherryFrameImportService;

import java.util.List;


public class CherryFrameImportServiceImpl implements CherryFrameImportService {

    private final CherryFrameDatabaseConnectionStrategy connectionStrategy = new CherryFrameDatabaseConnectionStrategyImpl();
    private final CherryFrameImportDao cherryFrameImportDao = new CherryFrameImportDaoImpl();

    @Override
    public void importToServer(final List<RowData> rowDataList) {
        final var connection = connectionStrategy.connect();
        rowDataList.removeIf(rowData -> rowData.getSelectionDataList().size() == 0);
        for (final RowData rowData : rowDataList) {
            cherryFrameImportDao.insertOrUpdate(rowData, connection);
        }
    }

}