module word.processor {
    requires javafx.controls;
    requires javafx.fxml;

    opens word.processor to javafx.fxml;
    opens word.processor.controllers to javafx.fxml;
    exports word.processor;
}
