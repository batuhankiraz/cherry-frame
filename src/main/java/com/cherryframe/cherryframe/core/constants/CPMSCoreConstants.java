package com.cherryframe.cherryframe.core.constants;

public final class CPMSCoreConstants {

    public static final String USER_SESSION_KEY = "USER";

    public interface DatabaseTable {
        String TBLSTSBT = "TBLSTSBT";
        String CPMSUSER = "CPMSUSER";
    }

    public interface FileChooserExtension {
        String FILE_CHOOSER_TITLE = "CherryPick Management Studio File Chooser";
        String EXCEL_FILTER_NAME = "Excel";
        String PDF_FILTER_NAME = "PDF";
        String[] EXCEL_FILTER_OPTIONS = {"*.xlsx", "*.xls"};
        String[] PDF_FILTER_OPTIONS = {"*.pdf"};
    }

    public interface AvailableCheckBoxTitle {
        String STOK_KODU_TITLE = "Stok Kodu";
        String DOVIZ_ALIS_FIYATI_TITLE = "Doviz Alış Fiyatı";
        String DOVIZ_SATIS_FIYATI_TITLE = "Doviz Satış Fiyatı";
        String DOVIZ_MAL_FIYATI_TITLE = "Doviz Mal Fiyatı";
        String SATIS_FIYAT_1_TITLE = "Satış Fiyatı 1";
        String SATIS_FIYAT_2_TITLE = "Satış Fiyatı 2";
        String SATIS_FIYAT_3_TITLE = "Satış Fiyatı 3";
        String SATIS_FIYAT_4_TITLE = "Satış Fiyatı 4";
        String ALIS_FIYAT_1_TITLE = "Alış Fiyatı 1";
        String ALIS_FIYAT_2_TITLE = "Alış Fiyatı 2";
        String ALIS_FIYAT_3_TITLE = "Alış Fiyatı 3";
        String ALIS_FIYAT_4_TITLE = "Alış Fiyatı 4";
    }

    public interface DatabaseKeys {
        String STOK_KODU = "STOK_KODU";
        String SATIS_FIAT1 = "SATIS_FIAT1";
        String SATIS_FIAT2 = "SATIS_FIAT2";
        String SATIS_FIAT3 = "SATIS_FIAT3";
        String SATIS_FIAT4 = "SATIS_FIAT4";
        String ALIS_FIAT1 = "ALIS_FIAT1";
        String ALIS_FIAT2 = "ALIS_FIAT2";
        String ALIS_FIAT3 = "ALIS_FIAT3";
        String ALIS_FIAT4 = "ALIS_FIAT4";
        String DOV_ALIS_FIAT = "DOV_ALIS_FIAT";
        String DOV_MAL_FIAT = "DOV_MAL_FIAT";
        String DOV_SATIS_FIAT = "DOV_SATIS_FIAT";
    }
}
