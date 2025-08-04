module com.student.registration {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.student.registration to javafx.fxml;
    opens com.student.registration.controller to javafx.fxml;
    opens com.student.registration.model to javafx.base;
    
    exports com.student.registration;
    exports com.student.registration.controller;
    exports com.student.registration.model;
}
