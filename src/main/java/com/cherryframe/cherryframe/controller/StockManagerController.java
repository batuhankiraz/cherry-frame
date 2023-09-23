package com.cherryframe.cherryframe.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static com.cherryframe.cherryframe.CherryFrameApplication.stage;


public class StockManagerController {

    @FXML
    private Button importButton;
    @FXML
    private TextField filePathArea;
    @FXML
    private CheckBox productIdCheck, stockCountCheck, priceWithVatCheck, priceWithDiscountCheck, priceWithoutVatCheck;
    @FXML
    private TextField productIdColumnIdx, stockCountColumnIdx, priceWithVatColumnIdx, priceWithDiscountColumnIdx, priceWithoutVatColumnIdx;

    @FXML
    protected void handleCheckboxOnClick() {
        adjustVisibility(productIdCheck, productIdColumnIdx);
        adjustVisibility(stockCountCheck, stockCountColumnIdx);
        adjustVisibility(priceWithVatCheck, priceWithVatColumnIdx);
        adjustVisibility(priceWithDiscountCheck, priceWithDiscountColumnIdx);
        adjustVisibility(priceWithoutVatCheck, priceWithoutVatColumnIdx);
    }

    @FXML
    protected void uploadFile() {
        final FileChooser.ExtensionFilter excelFilesFilter = new FileChooser.ExtensionFilter("Excel File", "*.xlsx", "*.xls");
        final FileChooser.ExtensionFilter pdfFilesFilter = new FileChooser.ExtensionFilter("PDF File", "*.pdf");

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CherryFrame File Chooser");
        fileChooser.getExtensionFilters().addAll(excelFilesFilter, pdfFilesFilter);
        final File selectedFile = fileChooser.showOpenDialog(stage);
        filePathArea.appendText(selectedFile.getAbsolutePath());
        importButton.setDisable(false);
    }

    @FXML
    protected void importFromFile() throws IOException {
        if (!filePathArea.getText().isEmpty())
        {
            final FileInputStream fileInputStream = new FileInputStream(filePathArea.getText());
            final XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            final Sheet sheet = workbook.getSheetAt(0);
            sheet.forEach(row ->
            {
                //in case of row title, break the loop
                if (row.getRowNum() == 0)
                {
                    return;
                }
                System.out.println("-------\n" + row.getCell(Integer.parseInt(productIdColumnIdx.getText())).getStringCellValue() + "\n-------");
                System.out.println(row.getCell(Integer.parseInt(stockCountColumnIdx.getText())).getNumericCellValue());
                System.out.println(row.getCell(Integer.parseInt(priceWithVatColumnIdx.getText())).getNumericCellValue());
                System.out.println(row.getCell(Integer.parseInt(priceWithDiscountColumnIdx.getText())).getNumericCellValue());
                System.out.println(row.getCell(Integer.parseInt(priceWithoutVatColumnIdx.getText())).getNumericCellValue());
            });
            // TODO: Database connection
            // TODO: update table with provided values by file
        }
    }

    private void adjustVisibility(final CheckBox checkBox, final TextField textField) {
        textField.setVisible(checkBox.isSelected());
    }
}

