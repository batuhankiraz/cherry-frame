package com.cherryframe.cherryframe.service.file;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.List;

public interface CherryFrameFileService {
    Sheet readSingleExcelFile(String filePath) throws IOException;

    void initializeFileChooser(TextField filePathArea);

    void initializeAndImportFromFile(Sheet sheet, List<CheckBox> availableCheckBoxes, List<ChoiceBox<String>> availableChoiceBoxes);
}
