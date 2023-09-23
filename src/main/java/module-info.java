module com.cherryframe.cherryframe {
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            requires eu.hansolo.tilesfx;
    requires org.apache.poi.ooxml.schemas;
    requires org.apache.poi.ooxml;


    opens com.cherryframe.cherryframe to javafx.fxml;
    exports com.cherryframe.cherryframe;
    exports com.cherryframe.cherryframe.controller;
    opens com.cherryframe.cherryframe.controller to javafx.fxml;
}