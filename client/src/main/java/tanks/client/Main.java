package tanks.client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import tanks.client.models.TankLocal;

public class Main extends Application {
    private static int windowWidth = 800;
    private static int windowHeight = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle( "The Game" );

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);

        TankLocal tankLocal = new TankLocal();

        root.getChildren().add(tankLocal.getHullView());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
