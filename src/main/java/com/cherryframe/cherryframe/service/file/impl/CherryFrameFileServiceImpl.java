package com.cherryframe.cherryframe.service.file.impl;

import com.cherryframe.cherryframe.dao.data.RowData;
import com.cherryframe.cherryframe.dao.data.SelectionData;
import com.cherryframe.cherryframe.service.file.CherryFrameFileService;
import com.cherryframe.cherryframe.service.importer.CherryFrameImportService;
import com.cherryframe.cherryframe.service.importer.impl.CherryFrameImportServiceImpl;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cherryframe.cherryframe.CherryFrameApplication.STAGE;
import static com.cherryframe.cherryframe.service.constants.CherryFrameCoreConstants.FileChooserExtension.*;
import static com.cherryframe.cherryframe.service.utils.CherryFrameCoreUtils.getCellValue;
import static java.util.Objects.nonNull;


public class CherryFrameFileServiceImpl implements CherryFrameFileService {

    private final CherryFrameImportService cherryFrameImportService = new CherryFrameImportServiceImpl();


    @Override
    public Sheet readSingleExcelFile(final String filePath) throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(filePath);
        final XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        return workbook.getSheetAt(0);
    }

    @Override
    public void initializeFileChooser(final TextField filePathArea) {
        // initialize filters.
        final FileChooser.ExtensionFilter excelFilesFilter = new FileChooser.ExtensionFilter(EXCEL_FILTER_NAME, EXCEL_FILTER_OPTIONS);
        final FileChooser.ExtensionFilter pdfFilesFilter = new FileChooser.ExtensionFilter(PDF_FILTER_NAME, PDF_FILTER_OPTIONS);
        // initialize File Chooser extension.
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(FILE_CHOOSER_TITLE);
        // add filters.
        fileChooser.getExtensionFilters().addAll(excelFilesFilter, pdfFilesFilter);
        // specify selected file.
        final File selectedFile = fileChooser.showOpenDialog(STAGE);
        if (nonNull(selectedFile))
        {
            filePathArea.appendText(selectedFile.getAbsolutePath());
        }
    }

    @Override
    public void initializeAndImportFromFile(final Sheet sheet, final List<CheckBox> availableCheckBoxes,
                                            final List<ChoiceBox<String>> availableChoiceBoxes, final TextArea infoTextArea) {
        final List<SelectionData> selectionDataList = createSelectionDataList(availableCheckBoxes, availableChoiceBoxes);
        final  List<SelectionData> rowSelectionDataList = new ArrayList<>();
        final List<RowData> rowDataList = new ArrayList<>();
        final HashMap<Integer, SelectionData> map = new HashMap<>();
        // initialize sheet
        for (final Row row : sheet) {
            // initialize RowData object.
            final var rowData = new RowData();
            // iterate on row
            for (final Cell cell : row) {
                if (row.getRowNum() == 0) {
                    final var selectedSelectionData = getSelectedSelectionData(cell, selectionDataList);
                    if (nonNull(selectedSelectionData))
                    {
                        map.put(cell.getColumnIndex(), selectedSelectionData);
                    }
                } else {
                    final var selectionData = new SelectionData();
                    final var retrivedSelectionData = map.get(cell.getColumnIndex());
                    if (nonNull(retrivedSelectionData)) {
                        selectionData.setRowNumber(row.getRowNum());
                        selectionData.setIndex(cell.getColumnIndex());
                        selectionData.setCode(retrivedSelectionData.getCode());
                        selectionData.setTitle(retrivedSelectionData.getTitle());
                        selectionData.setSelected(retrivedSelectionData.isSelected());
                        selectionData.setDbKey(retrivedSelectionData.getDbKey());
                        selectionData.setValueData(getCellValue(cell));
                        rowSelectionDataList.add(selectionData);
                    }
                }
                rowData.setSelectionDataList(rowSelectionDataList.stream().filter(sd -> sd.getRowNumber() == row.getRowNum()).toList());
            }
            // store as list
            rowDataList.add(rowData);
        }
        // send to db
        cherryFrameImportService.importToServer(rowDataList, infoTextArea);
    }

    private List<SelectionData> createSelectionDataList(final List<CheckBox> availableCheckBoxes,
                                                        final List<ChoiceBox<String>> availableChoiceBoxes) {
        final List<SelectionData> selectionDataList = new ArrayList<>();
        for (int index = 0; index < availableCheckBoxes.size(); index++) {
            final var selectionData = new SelectionData();
            selectionData.setSelected(availableCheckBoxes.get(index).isSelected());
            selectionData.setTitle(availableCheckBoxes.get(index).getText());
            selectionData.setDbKey(availableCheckBoxes.get(index).getId());
            selectionData.setCode(availableChoiceBoxes.get(index).getValue());
            selectionDataList.add(selectionData);
        }
        return selectionDataList;
    }

    private SelectionData getSelectedSelectionData(final Cell cell, final List<SelectionData> selectionDataList) {
        return selectionDataList.stream()
                .filter(SelectionData::isSelected)
                .filter(sd -> sd.getCode().equals(cell.getStringCellValue()))
                .findAny()
                .orElse(null);
    }
}

