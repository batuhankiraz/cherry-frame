package com.cherryframe.cherryframe.core.dao.data;

public class ValueData {
    private Double numericValue;
    private String stringValue;
    private Boolean booleanValue;
    private ValueTypeEnum type;

    public ValueData() {
        // empty constructor
    }

    public Object getValue(ValueTypeEnum type) {
        switch (type) {
            case BOOLEAN -> {
                return getBooleanValue();
            }
            case STRING -> {
                return getStringValue();
            }
            case NUMERIC -> {
                return getNumericValue();
            }
            default -> throw new IllegalArgumentException("Unsupported ValueTypeEnum: " + type);
        }
    }

    public Double getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Double numericValue) {
        this.numericValue = numericValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public ValueTypeEnum getType() {
        return type;
    }

    public void setType(ValueTypeEnum type) {
        this.type = type;
    }
}
