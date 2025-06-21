module com.hnmt.quizappp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires lombok;
    requires java.sql;
    opens com.hnmt.quizappp to javafx.fxml;
    exports com.hnmt.quizappp;
}
