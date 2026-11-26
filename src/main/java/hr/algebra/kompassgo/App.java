package hr.algebra.kompassgo;

import hr.algebra.kompassgo.poco.AppConfig;
import hr.algebra.kompassgo.util.AppConfigUtility;
import hr.algebra.kompassgo.util.DatabaseUtility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void init() throws Exception {

        DatabaseUtility.init();
    }

    @Override
    public void start(Stage stage) throws Exception {

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        AppConfig config = AppConfigUtility.getConfig();
        config.setWidth((int) screen.getWidth());
        config.setHeight((int) screen.getHeight());

        Parent root = FXMLLoader.load(App.class.getResource("view/register-view.fxml"));
        Scene scene = new Scene(root, config.getWidth(), config.getHeight());

        stage.setTitle("KompassGo");
        stage.setScene(scene);
        stage.setX(screen.getMinX());
        stage.setY(screen.getMinY());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}