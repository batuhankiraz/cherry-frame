package com.cherryframe.cherryframe.controller;

import com.cherryframe.cherryframe.service.file.CherryFrameFileService;
import com.cherryframe.cherryframe.service.file.impl.CherryFrameFileServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public class StockManagerController {

    @FXML
    private Button importButton;
    @FXML
    private TextField filePathArea;
    @FXML
    private CheckBox stockUidCheck, usdPurchasePriceCheck, usdSellPriceCheck, usdProductPriceCheck, sellPrice1Check,
            sellPrice2Check, sellPrice3Check, sellPrice4Check, purchasePrice1Check, purchasePrice2Check,
            purchasePrice3Check, purchasePrice4Check;
    @FXML
    private ChoiceBox<String> stockUidChoice, usdPurchasePriceChoice, usdSellPriceChoice, usdProductPriceChoice, sellPrice1Choice,
            sellPrice2Choice, sellPrice3Choice, sellPrice4Choice, purchasePrice1Choice, purchasePrice2Choice,
            purchasePrice3Choice, purchasePrice4Choice;
    private final List<ChoiceBox<String>> availableChoiceBoxes = new ArrayList<>();
    private final List<CheckBox> availableCheckBoxes = new ArrayList<>();

    private final CherryFrameFileService cherryFrameFileService = new CherryFrameFileServiceImpl();


    @FXML
    protected void uploadFile() throws IOException {
        // Initialize all available choice/check boxes into a list to reuse.
        initializeChoiceBoxList();
        initializeCheckBoxList();
        // initialize FileChooser
        cherryFrameFileService.initializeFileChooser(filePathArea);
        // fill selection options & if not selected -> remove from available choice box list
        for (int index = 0; index < availableCheckBoxes.size(); index++){
            fillAvailableChoiceBoxOptions(availableCheckBoxes.get(index), availableChoiceBoxes.get(index));
        }
        // unlock import button
        importButton.setDisable(false);
    }

    @FXML
    protected void handleCheckboxOnClick() {
        // iterate and adjust visibility
        for (int i = 0; i < availableCheckBoxes.size(); i++) {
            adjustDropdownVisibility(availableCheckBoxes.get(i), availableChoiceBoxes.get(i));
        }
    }

    @FXML
    protected void importFromFile() throws IOException {
        if (!filePathArea.getText().isEmpty()) {
            // Read & Initialize Sheet
            final Sheet sheet = cherryFrameFileService.readSingleExcelFile(filePathArea.getText());
            cherryFrameFileService.initializeAndImportFromFile(sheet, availableCheckBoxes, availableChoiceBoxes);
        }
    }

    private void adjustDropdownVisibility(final CheckBox checkBox, final ChoiceBox<String> choiceBox) {
        choiceBox.setVisible(checkBox.isSelected());
    }

    private void fillAvailableChoiceBoxOptions(final CheckBox checkBox, final ChoiceBox<String> choiceBox) throws IOException {
        final Sheet sheet = cherryFrameFileService.readSingleExcelFile(filePathArea.getText());
        for (final Row row : sheet) {
            if (row.getRowNum() == 0) {
                row.forEach(cell -> choiceBox.getItems().add(cell.getStringCellValue()));
            }
            break;
        }
    }

    private void initializeCheckBoxList() {
        availableCheckBoxes.addAll(List.of(stockUidCheck, usdPurchasePriceCheck, usdSellPriceCheck, usdProductPriceCheck,
                sellPrice1Check, sellPrice2Check, sellPrice3Check, sellPrice4Check, purchasePrice1Check, purchasePrice2Check,
                purchasePrice3Check, purchasePrice4Check));
    }

    private void initializeChoiceBoxList() {
        availableChoiceBoxes.addAll(List.of(stockUidChoice, usdPurchasePriceChoice,
                usdSellPriceChoice, usdProductPriceChoice, sellPrice1Choice, sellPrice2Choice, sellPrice3Choice,
                sellPrice4Choice, purchasePrice1Choice, purchasePrice2Choice, purchasePrice3Choice,
                purchasePrice4Choice));
    }
}

