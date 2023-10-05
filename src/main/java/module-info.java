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
    exports com.cherryframe.cherryframe.controller;
    opens com.cherryframe.cherryframe.controller to javafx.fxml;
    exports com.cherryframe.cherryframe.controller.constants;
    opens com.cherryframe.cherryframe.controller.constants to javafx.fxml;
}