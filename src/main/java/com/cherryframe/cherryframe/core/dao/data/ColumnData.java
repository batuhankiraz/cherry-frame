package com.cherryframe.cherryframe.core.dao.data;

public class ColumnData {
    private String columnCode;
    private int columnIndex;
    private ValueData valueData;

    public ColumnData() {
        // empty constructor
    }

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public ValueData getValueData() {
        return valueData;
    }

    public void setValueData(ValueData valueData) {
        this.valueData = valueData;
    }
}
