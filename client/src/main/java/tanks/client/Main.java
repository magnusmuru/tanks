package tanks.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tanks.client.models.TankLocal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
        Text frameRate = new Text(10, 50, "This is a test");

        Text mouse = new Text(10, 100, "This is a test");


        scene.setOnMouseMoved(event -> {
            tankLocal.setMousePosition(event.getSceneX(), event.getSceneY());
        });

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastTime;

            @Override
            public void start() {
                super.start();
                this.lastTime = System.nanoTime();
            }

            @Override
            public void handle(long now) {
                double deltaNanos = now - lastTime;
                double deltaMillis = deltaNanos / 1000000;

                frameRate.setText(String.format("%1$.2f", 1000 / deltaMillis) + " fps");
                mouse.setText(tankLocal.getMouseX() + "x" + tankLocal.getMouseY());


                tankLocal.render();

                lastTime = now;
            }
        };
        animationTimer.start();

        root.getChildren().add(tankLocal.getHullView());
        root.getChildren().add(frameRate);
        root.getChildren().add(mouse);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
