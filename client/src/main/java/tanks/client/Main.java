package tanks.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
        Scene scene = new Scene(root, windowWidth, windowHeight);

        TankLocal tankLocal = new TankLocal();

        scene.setOnMouseMoved(event -> {
            tankLocal.setMousePosition(event.getSceneX(), event.getSceneY());
        });

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastTime = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                tankLocal.update(now - lastTime);

                tankLocal.render();

                lastTime = now;
            }
        };
        animationTimer.start();

        root.getChildren().add(tankLocal.getHullView());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
