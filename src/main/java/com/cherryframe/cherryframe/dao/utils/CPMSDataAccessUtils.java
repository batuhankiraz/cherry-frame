package com.cherryframe.cherryframe.dao.utils;

import java.util.List;

public class CPMSDataAccessUtils {

    public static boolean isLastIndexOf(final List<?> list, final Object element) {
        return list.indexOf(element) == list.size() - 1;
    }
}
