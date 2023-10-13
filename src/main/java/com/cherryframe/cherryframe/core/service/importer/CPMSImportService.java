package com.cherryframe.cherryframe.core.service.importer;


import com.cherryframe.cherryframe.core.dao.data.RowData;
import javafx.scene.control.TextArea;

import java.util.List;

public interface CPMSImportService {

    void importToServer(List<RowData> rowDataList, TextArea infoTextArea);
}
