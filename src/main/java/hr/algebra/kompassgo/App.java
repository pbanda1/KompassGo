package hr.algebra.kompassgo;

import hr.algebra.kompassgo.poco.AppConfig;
import hr.algebra.kompassgo.util.AppConfigUtility;
import hr.algebra.kompassgo.util.DatabaseUtility;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void init() throws Exception {

        DatabaseUtility.init();
    }

    @Override
    public void start(Stage stage) {

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        AppConfig config = AppConfigUtility.getConfig();
        config.setWidth((int) screen.getWidth());
        config.setHeight((int) screen.getHeight());

        // change stack pane with:
        // Parent root = new FXMLLoader(App.class.getResource("view/login-view.fxml")).load();
        StackPane root = new StackPane();
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