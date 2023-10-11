module com.cherryframe.cherryframe {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml.schemas;
    requires org.apache.poi.ooxml;
    requires java.compiler;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;


    opens com.cherryframe.cherryframe to javafx.fxml;
    exports com.cherryframe.cherryframe;
    exports com.cherryframe.cherryframe.storefront.controller;
    opens com.cherryframe.cherryframe.storefront.controller to javafx.fxml;
    exports com.cherryframe.cherryframe.storefront.constants;
    opens com.cherryframe.cherryframe.storefront.constants to javafx.fxml;
}