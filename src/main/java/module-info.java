module com.example.javaapi200543216 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.javaapi200543216 to javafx.fxml;
    exports com.example.javaapi200543216;
}