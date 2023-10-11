package com.cherryframe.cherryframe.service.importer;


import com.cherryframe.cherryframe.dao.data.RowData;
import javafx.scene.control.TextArea;

import java.util.List;

public interface CPMSImportService {

    void importToServer(List<RowData> rowDataList, TextArea infoTextArea);
}
