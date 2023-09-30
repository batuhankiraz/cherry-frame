package com.cherryframe.cherryframe.dao.data;

import java.util.List;

public class RowData {
    private List<SelectionData> selectionDataList;

    public RowData() {
        // empty constructor
    }

    public List<SelectionData> getSelectionDataList() {
        return selectionDataList;
    }

    public void setSelectionDataList(List<SelectionData> selectionDataList) {
        this.selectionDataList = selectionDataList;
    }

    public SelectionData getSelectionData(final int index){
        return getSelectionDataList()
                .stream()
                .filter(sd -> sd.getIndex() == index)
                .findAny()
                .orElse(null);
    }

    public SelectionData getSelectionDataByCode(final String code){
        return getSelectionDataList()
                .stream()
                .filter(sd -> sd.getCode().equals(code))
                .findAny()
                .orElse(null);
    }

    public SelectionData getSelectionDataByTitle(final String title){
        return getSelectionDataList()
                .stream()
                .filter(sd -> sd.getTitle().equals(title))
                .findAny()
                .orElse(null);
    }
}
