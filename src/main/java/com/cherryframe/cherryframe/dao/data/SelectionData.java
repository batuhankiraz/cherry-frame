package com.cherryframe.cherryframe.dao.data;

public class SelectionData {

    private int index;
    private int rowNumber;
    private String dbKey;
    private String title;
    private String code;
    private boolean selected;
    private ValueData valueData;

    public SelectionData() {
        // empty constructor
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public ValueData getValueData() {
        return valueData;
    }

    public void setValueData(ValueData valueData) {
        this.valueData = valueData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
