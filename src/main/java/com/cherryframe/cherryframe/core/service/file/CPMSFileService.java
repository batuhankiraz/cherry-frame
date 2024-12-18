package com.cherryframe.cherryframe.core.service.file;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.List;

public interface CPMSFileService {
    Sheet readSingleExcelFile(String filePath) throws IOException;

    void initializeFileChooser(TextField filePathArea);

    void initializeAndImportFromFile(Sheet sheet, List<CheckBox> availableCheckBoxes, List<ChoiceBox<String>> availableChoiceBoxes, TextArea infoTextArea);
}
