package com.cherryframe.cherryframe.storefront.controller;

import com.cherryframe.cherryframe.service.file.CPMSFileService;
import com.cherryframe.cherryframe.service.file.impl.CPMSFileServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


public class CPMSStockManagerController {

    @FXML
    private Button importButton;
    @FXML
    private ImageView importIcon;
    @FXML
    private TextField filePathArea;
    @FXML
    private TextArea infoTextArea;
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
    private final CPMSFileService CPMSFileService = new CPMSFileServiceImpl();


    @FXML
    protected void uploadFile() throws IOException {
        if (nonNull(filePathArea.getText()))
        {
            filePathArea.setText(null);
        }
        infoTextArea.setVisible(false);
        // Initialize all available choice/check boxes into a list to reuse.
        initializeChoiceBoxList();
        initializeCheckBoxList();
        // initialize FileChooser
        CPMSFileService.initializeFileChooser(filePathArea);
        // fill selection options & if not selected -> remove from available choice box list
        for (int index = 0; index < availableCheckBoxes.size(); index++) {
            fillAvailableChoiceBoxOptions(availableChoiceBoxes.get(index));
        }
        // unlock import button
        importButton.setDisable(false);
        importIcon.setDisable(false);
    }

    @FXML
    protected void handleCheckboxOnClick() {
        infoTextArea.setVisible(false);
        // iterate and adjust visibility
        for (int i = 0; i < availableCheckBoxes.size(); i++) {
            adjustDropdownVisibility(availableCheckBoxes.get(i), availableChoiceBoxes.get(i));
        }
    }

    @FXML
    protected void importFromFile() {
        final var errorDetection = validateSelection();
        isInErrorDetection(errorDetection);
        if (!filePathArea.getText().isEmpty() && !errorDetection) {
            // Read & Initialize Sheet
            try {
                if (!stockUidCheck.isSelected()) {
                    throw new IllegalStateException("Stock Code field need to be checked!");
                }
                final Sheet sheet = CPMSFileService.readSingleExcelFile(filePathArea.getText());
                CPMSFileService.initializeAndImportFromFile(sheet, availableCheckBoxes, availableChoiceBoxes, infoTextArea);
                refreshImporter();
            } catch (final Exception e) {
                infoTextArea.setVisible(true);
                infoTextArea.setText(e.getMessage());
            }
        }
    }

    private void adjustDropdownVisibility(final CheckBox checkBox, final ChoiceBox<String> choiceBox) {
        choiceBox.setVisible(checkBox.isSelected());
    }

    private void fillAvailableChoiceBoxOptions(final ChoiceBox<String> choiceBox) throws IOException {
        if (nonNull(filePathArea.getText()))
        {
            final Sheet sheet = CPMSFileService.readSingleExcelFile(filePathArea.getText());
            for (final Row row : sheet) {
                if (row.getRowNum() == 0) {
                    if (choiceBox.getItems().size() == 0) {
                        row.forEach(cell -> choiceBox.getItems().add(cell.getStringCellValue()));
                    }
                }
                break;
            }
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

    private boolean validateSelection() {
        boolean errorDetection = false;
        for (int i = 0; i < this.availableCheckBoxes.size(); i++) {
            if (this.availableCheckBoxes.get(i).isSelected()) {
                final var choiceBox = this.availableChoiceBoxes.get(i);
                if (isNull(choiceBox.getValue())) {
                    errorDetection = true;
                    adjustChoiceBoxStyle(choiceBox);
                }
            }
        }
        return errorDetection;
    }

    private void isInErrorDetection(boolean errorDetection) {
        if (errorDetection) {
            infoTextArea.setVisible(true);
            infoTextArea.setText("Please check your selected items. There are several items which are not matched with a value!");
        }
    }

    private void adjustChoiceBoxStyle(final ChoiceBox<String> choiceBox) {
        choiceBox.setStyle("-fx-background-color: #f2be04; -fx-border-radius: 15; -fx-border-style: solid inside; -fx-background-radius: 15; -fx-border-color: red");
    }

    private void refreshImporter() {
        for (int i = 0; i < availableCheckBoxes.size(); i++) {
            availableCheckBoxes.get(i).setSelected(false);
            availableChoiceBoxes.get(i).setValue(null);
            adjustDropdownVisibility(availableCheckBoxes.get(i), availableChoiceBoxes.get(i));
        }
        filePathArea.setText(null);
        importButton.setDisable(true);
        importIcon.setDisable(true);
    }
}

