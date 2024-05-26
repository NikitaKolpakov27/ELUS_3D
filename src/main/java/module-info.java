module com.example.figure3d {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires java.management;
    requires org.controlsfx.controls;

    opens com.example.figure3d to javafx.fxml;
    exports com.example.figure3d;
}