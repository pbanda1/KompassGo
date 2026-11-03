module hr.algebra.kompassgo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;
    requires jbcrypt;

    opens hr.algebra.kompassgo to javafx.fxml;
    exports hr.algebra.kompassgo;
}