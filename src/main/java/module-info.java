module main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires org.jetbrains.annotations;

    exports main;
    opens main to javafx.fxml;
    opens model to javafx.base;
}