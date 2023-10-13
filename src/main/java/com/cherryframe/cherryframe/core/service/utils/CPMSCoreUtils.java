package com.cherryframe.cherryframe.core.service.utils;

import com.cherryframe.cherryframe.core.dao.data.ValueData;
import com.cherryframe.cherryframe.core.dao.data.ValueTypeEnum;
import org.apache.poi.ss.usermodel.Cell;

public class CPMSCoreUtils {

    public static ValueData getCellValue(final Cell cell) {
        final var valueData = new ValueData();
        switch (cell.getCellType()) {
            case STRING -> {
                valueData.setStringValue(cell.getStringCellValue());
                valueData.setType(ValueTypeEnum.STRING);
            }
            case NUMERIC -> {
                valueData.setNumericValue(cell.getNumericCellValue());
                valueData.setType(ValueTypeEnum.NUMERIC);
            }
            case BOOLEAN -> {
                valueData.setBooleanValue(cell.getBooleanCellValue());
                valueData.setType(ValueTypeEnum.BOOLEAN);
            }
        }
        return valueData;
    }
}
