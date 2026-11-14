module hr.algebra.kompassgo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;
    requires jbcrypt;
    requires java.xml;
    requires org.slf4j;
    requires javafx.graphics;
    opens hr.algebra.kompassgo to javafx.fxml;
    opens hr.algebra.kompassgo.model.entity to javafx.base;
    exports hr.algebra.kompassgo;
}